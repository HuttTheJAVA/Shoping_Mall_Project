package com.example.shoppingMall.web;

import com.example.shoppingMall.Cookie.CookieController;
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
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
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
@Slf4j
public class CartController {

    private final CartItemService cartItemService;
    private final ItemService itemService;

    private final MemberRepository memberRepository;
    private final FileStore fileStore;

    private final CookieController cookieController;
    private final String anonymousUser = "anonymousUser";
    @GetMapping("cart")
    public String cart(HttpServletRequest request,Model model,HttpServletResponse response){
        String userId = (String) request.getAttribute("id"); // 멤버 엔티티의 userId
        Member member = memberRepository.findByUserId(userId);

        String cookieString = cookieController.merge(request.getCookies());
        List<CartItem> cartItems = cookieController.buildCartItemList(cookieString,member);

        //cookieString을 cartItems와 동기화해야함.
        cookieString = cookieController.sync_CartItem_Cookie(cartItems,cookieString);

        if(!userId.equals(anonymousUser)){
            cookieController.saveCookieCartItems(member.getId(),cartItems);
            Cookie cookie = cookieController.setCookie("Cart","",0,"/");
            response.addCookie(cookie);
            List<CartItem> member_CartItems = cartItemService.cartList(member.getId());

            model.addAttribute("cartItems",member_CartItems);
        }else{
            if(!cookieString.isBlank()) {
                Cookie cookie = cookieController.setCookie("Cart", cookieString, 1 * 24 * 60 * 60, "/");
                response.addCookie(cookie);
            }
            model.addAttribute("cartItems",cartItems);
        }

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
                        break;
                    }
                }
            }
            cartValueBuilder.append("|");  // 쿠키에 중복되는 itemId가 있어도 일단 뒤에 추가 한다. 중복 수량 병합은 장바구니 조회시 병함 됨.
            cartValueBuilder.append(itemId.toString());
            cartValueBuilder.append(":");
            cartValueBuilder.append(quantity.toString());

            String cartValue = cartValueBuilder.toString();

            Cookie cookie = cookieController.setCookie("Cart",cartValue,1*24*60*60,"/");
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
