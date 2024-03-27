package com.example.shoppingMall.Cookie;

import com.example.shoppingMall.domain.CartItem;
import com.example.shoppingMall.domain.Item;
import com.example.shoppingMall.domain.Member;
import com.example.shoppingMall.domain.service.CartItemService;
import com.example.shoppingMall.domain.service.ItemService;
import com.example.shoppingMall.repository.CartItemRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class CookieController {
    @Autowired
    private ItemService itemService;

    @Autowired
    private CartItemRepository cartItemRepository;

    // cartItem의 member.id and item.id를 가지는 cartItem이 있는지 db에 조회
    // 있다면 수량만 추가.
    // 없다면 새로 cartItem 저장.
    // 해당 함수는 "로그인한 사용자"의 쿠키를 반영할 때만 호출된다.
    @Transactional
    public void saveCookieCartItems(List<CartItem> cartItems){
        for(CartItem cartItem : cartItems){
            List<CartItem> result = cartItemRepository.findByMemberId_AND_itemId(cartItem.getMember().getId(),cartItem.getItem().getId());
            if(result.isEmpty()){
                cartItemRepository.saveCartItem(cartItem);
            }
            else{
                CartItem findCartItem = result.get(0);
                Long default_Quantity = findCartItem.getQuantity();
                Long UpdateQuantity = default_Quantity + cartItem.getQuantity();
                cartItemRepository.updateQuantity(findCartItem.getId(),UpdateQuantity);
            }
        }
    }

    public String merge(Cookie[] cookies) {
        HashMap<String, Long> hashMap = new HashMap<>();
        StringBuilder cookieBuilder = new StringBuilder();
        if(cookies != null){
            for(Cookie cookie : cookies){
                if(cookie.getName().equals("Cart")){
                    String cartProduct = cookie.getValue();
                    String[] products = cartProduct.split("\\|");
                    for (String product : products) {
                        String[] productAndQuantity = product.split(":");
                        if(productAndQuantity[0].equals("")){
                            continue;
                        }
                        String productId = productAndQuantity[0];
                        Long quantity = Long.valueOf(productAndQuantity[1]);

                        if(hashMap.containsKey(productId)){
                            hashMap.merge(productId,quantity,Long::sum);
                        }
                        else{
                            hashMap.put(productId,quantity);
                        }
                    }
                    break;
                }
            }
            for(Map.Entry<String,Long> entry : hashMap.entrySet()){
                cookieBuilder.append("|"+entry.getKey()+":"+entry.getValue().toString());
            }
        }
        return cookieBuilder.toString();
    }
    public List<CartItem> buildCartItemList(String cookieString,Member member) {
        List<CartItem> cartItems = new ArrayList<>();

        String[] products = cookieString.split("\\|");
        for(String product : products){
            String[] productAndQuantity = product.split(":");
            if (productAndQuantity[0].equals("")) {
                continue;
            }
            Long productId = Long.valueOf(productAndQuantity[0]);
            Long quantity = Long.valueOf(productAndQuantity[1]);
            Item item = itemService.findById(productId);
            CartItem cartItem = CartItem.builder().member(member).item(item).quantity(quantity).build();
            cartItems.add(cartItem);
        }
        return cartItems;
    }

    public String updateCookie(String cookieString, Long itemId,Long newQuantity){
        StringBuilder cartValueBuilder = new StringBuilder();

        String[] products = cookieString.split("\\|");
        for (String product : products) {
            String[] productAndQuantity = product.split(":");
            if(productAndQuantity[0].equals("")){
                continue;
            }
            cartValueBuilder.append("|");
            Long productId = Long.valueOf(productAndQuantity[0]);
            Long quantity = Long.valueOf(productAndQuantity[1]);
            if(productId.equals(itemId)){
                cartValueBuilder.append(itemId.toString());
                cartValueBuilder.append(":");
                cartValueBuilder.append(newQuantity.toString());

            }else{
                cartValueBuilder.append(productId.toString());
                cartValueBuilder.append(":");
                cartValueBuilder.append(quantity);
            }
        }

        return cartValueBuilder.toString();
    }

    public String deleteCookieItem(Cookie[] cookies,Long del_item_id){
        StringBuilder cartValueBuilder = new StringBuilder();
        if(cookies != null){
            for(Cookie cookie: cookies){
                if(cookie.getName().equals("Cart")){
                    String cartProduct = cookie.getValue();
                    String[] products = cartProduct.split("\\|");
                    for (String product : products) {
                        String[] productAndQuantity = product.split(":");
                        if(productAndQuantity[0].equals("")){
                            continue;
                        }
                        Long productId = Long.valueOf(productAndQuantity[0]);
                        Long quantity = Long.valueOf(productAndQuantity[1]);
                        if(!productId.equals(del_item_id)){
                            cartValueBuilder.append("|");
                            cartValueBuilder.append(productId.toString());
                            cartValueBuilder.append(":");
                            cartValueBuilder.append(quantity);

                        }else{
                            continue;
                        }
                    }
                    break;
                }
            }
        }
        return cartValueBuilder.toString();
    }

    public Cookie setCookie(String name,String cookieValue,int age,String path ){
        Cookie cookie = new Cookie("Cart",cookieValue);
        cookie.setMaxAge(age);
        cookie.setPath(path);
        return cookie;
    }
}