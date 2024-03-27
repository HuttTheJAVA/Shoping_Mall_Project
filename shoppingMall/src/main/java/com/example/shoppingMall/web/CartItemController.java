package com.example.shoppingMall.web;


import com.example.shoppingMall.Cookie.CookieController;
import com.example.shoppingMall.DTO.CartItemRequest;
import com.example.shoppingMall.domain.CartItem;
import com.example.shoppingMall.domain.Member;
import com.example.shoppingMall.domain.service.CartItemService;
import com.example.shoppingMall.repository.CartItemRepository;
import com.example.shoppingMall.repository.MemberRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CartItemController {
    @Autowired
    private CookieController cookieController;

    @Autowired
    private CartItemService cartItemService;
    @Autowired
    private MemberRepository memberRepository;
    @PostMapping("/updateCookie")
    public String updateCookie(@RequestBody CartItemRequest cartItemRequest, HttpServletRequest request, HttpServletResponse response) {
        String userId = (String) request.getAttribute("id");
        Member member = null;

        if(!userId.equals("anonymousUser")){
            member = memberRepository.findByUserId(userId);
        }

        Long itemId = cartItemRequest.getItemId();
        Long newQuantity = Long.valueOf(cartItemRequest.getQuantity());
        Long cartItemId = cartItemRequest.getCartItemId(); // 비로그인 회원의 경우 해당 값은 null이다.(아직 db에 반영되지 않은 쿠키에만 존재하는 CartItem이기 때문.)

        Cookie[] cookies = request.getCookies();

        String cookieString = cookieController.merge(cookies); // cookie내 중복되는 아이템 병합.

        if(!userId.equals("anonymousUser")){ // 로그인 사용자라면.
            List<CartItem> cartItemList = cookieController.buildCartItemList(cookieString,member);
            cookieController.saveCookieCartItems(cartItemList);
            Cookie cookie = cookieController.setCookie("Cart","",0,"/");
            response.addCookie(cookie);

            // 혹시 쿠키에 값이 있었다면 위의 코드로 db에 반영하고
            // 이제 누른 버튼에 해당하는 아이템의 quantity를 DB에서 update해야 함.
            cartItemService.UpdateCartItemQuantity(cartItemId,newQuantity); // 멤버 아이디와 아이템 id가 필효 //TODO CartItemId를 바로 날리게 해야함.

        }else{  // update된 쿠키는 새로 List<CartItem>을 만들 필요없이 쿠키값만 잘 업데이트하면 됨. 그러나 로그인 상태면 List<CartItem>를 생성해서 db에 반영해야하며 쿠키도 지워야 됨.
            String update_cookieString = cookieController.updateCookie(cookieString,itemId,newQuantity);
            Cookie cookie = cookieController.setCookie("Cart",update_cookieString,1*24*60*60,"/");

            response.addCookie(cookie);
        }

        return "수량이 업데이트되었습니다.";
    }
    @ResponseBody
    @PostMapping("/cartItemDelete")
    public int cartDelete(HttpServletRequest request,HttpServletResponse response,@RequestParam("cart_item_no") String cart_item_no){
        Cookie[] cookies = request.getCookies();
        Long del_item_no = Long.valueOf(cart_item_no);

        String cartValue = cookieController.deleteCookieItem(cookies,del_item_no);

        Cookie cookie = cookieController.setCookie("Cart",cartValue,1*24*60*60,"/");

        response.addCookie(cookie);

        return 1;
    }

    @ResponseBody
    @PostMapping("/userCartItemDelete")
    public int userCartDelete(HttpServletRequest request,HttpServletResponse response,@RequestParam("CartItemId") String CartItemId){
        Long id = Long.valueOf(CartItemId);
        cartItemService.delete(id);
        return 1;
    }
}
