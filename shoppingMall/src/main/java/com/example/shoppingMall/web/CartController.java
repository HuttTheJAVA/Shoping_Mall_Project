package com.example.shoppingMall.web;

import com.example.shoppingMall.domain.CartItem;
import com.example.shoppingMall.domain.Item;
import com.example.shoppingMall.domain.Member;
import com.example.shoppingMall.domain.service.CartItemService;
import com.example.shoppingMall.domain.service.ItemService;
import com.example.shoppingMall.repository.FileStore;
import com.example.shoppingMall.repository.MemberRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class CartController {

    private final CartItemService cartItemService;
    private final ItemService itemService;

    private final MemberRepository memberRepository;
    private final FileStore fileStore;

    private final String anonymousUser = "anonymousUser";

    @GetMapping("cart")
    public String cart(HttpServletRequest request,Model model){
        String userId = (String) request.getAttribute("id"); // 멤버 엔티티의 userId
        Member member = memberRepository.findByUserId(userId);
        Cookie[] cookies = request.getCookies();
        List<CartItem> cartItems = new ArrayList<>(); // 여기서 쿠키에 등록된 장바구니 상품들이 담김.
        boolean cookieUsed = false;
        if(cookies != null){
            for(Cookie cookie: cookies){
                if(cookie.getName().equals("Cart")){
                    String cartProduct = cookie.getValue();
                    String[] products = cartProduct.split("\\|");
                    for (String product : products) {
                        String[] productAndQuantity = product.split(":");

                        Long productId = Long.valueOf(productAndQuantity[0]);
                        Long quantity = Long.valueOf(productAndQuantity[1]);

                        Item item = itemService.findById(productId);

                        CartItem cartItem = CartItem.builder().member(member).item(item).quantity(quantity).build();
                        cartItems.add(cartItem);
                        cookieUsed = true;
                    }
                    break;
                }
            }
        }
        if(!userId.equals(anonymousUser)){
            List<CartItem> UsercartItems = cartItemService.cartList(member.getId());
            cartItems.addAll(UsercartItems);
        }
        cartItems = merge(cartItems);
        if(!userId.equals(anonymousUser) && cookieUsed){ // 쿠키가

        }
        model.addAttribute("cartItems",cartItems);

        return "cart";
    }
    @GetMapping("cart/{itemId}")
    public String addCart(@PathVariable Long itemId, @RequestParam Long quantity, RedirectAttributes redirectAttributes, HttpServletRequest request, HttpServletResponse response){
        String userId = (String) request.getAttribute("id");

        redirectAttributes.addAttribute("itemId",itemId); // 로그인 사용자이든 아니든 장바구니에 상품 추가하면 동일하게 현재 페이지 새로고침.

        if(userId.equals(anonymousUser)){
            Cookie[] cookies = request.getCookies();
            StringBuilder cartValueBuilder = new StringBuilder();
            if(cookies != null){
                for (Cookie cookie : cookies) {
                    if (cookie.getName().equals("Cart")) {
                        // 기존 장바구니 값을 StringBuilder에 추가
                        cartValueBuilder.append(cookie.getValue());
                        cartValueBuilder.append("|");
                        break;
                    }
                }
            }
            cartValueBuilder.append(itemId.toString());
            cartValueBuilder.append(":");
            cartValueBuilder.append(quantity.toString());

            String cartValue = cartValueBuilder.toString();

            Cookie cookie = new Cookie("Cart",cartValue);
            cookie.setMaxAge(1*24*60*60); // 기본 하루 유지
            cookie.setPath("/");
            response.addCookie(cookie);

            return "redirect:/item/{itemId}";
        }

        cartItemService.save(userId,itemId,quantity);

        return "redirect:/item/{itemId}";
    }

    public List<CartItem> merge(List<CartItem> cartItems) {
        if(cartItems.isEmpty()){
            return cartItems;
        }
        // Item을 기준으로 그룹화하고 quantity를 합산 // 번외: 같은 Item을 정의하기 위해 item이름이 같으면 같은 item으로 보는 equals,hashcode 작성. CartItem은 같은 item을 가지면 같은 CartItem으로 보는 equals,hashcode 작성.
        Map<Item, Long> itemQuantityMap = cartItems.stream()
                .collect(Collectors.groupingBy(CartItem::getItem, Collectors.summingLong(CartItem::getQuantity)));

        // 합산된 quantity로 새로운 CartItem 객체 생성
        return itemQuantityMap.entrySet().stream()
                .map(entry -> CartItem.builder()
                        .item(entry.getKey())
                        .quantity(entry.getValue())
                        .build())
                .collect(Collectors.toList());
    }

}
