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
            // 혹시 쿠키에 값이 있었다면 위의 코드로 db에 반영하고
            // 이 아래 코드가 동작할 상황이 없을 것으로 추정됨. 왜냐하면 로그인 상태인 사람이 cart를 조회하면 그때 쿠키를 반영하고 쿠키 없애는데 여기서 -,+누른다고
            // 쿠키가 생기거나 아직 미반영한 쿠키는 없을 것이다. 왜냐면 cart 들어온 순간 이미 쿠키는 모두 반영되고 사라지기 때문이며, 로그인 상태는 그 이후에도 쿠키를 안쓰니까.
            // 따라서 일단 과감하게 지워보고 에러가 생기나 관심기울여서 확인해보자.
//            List<CartItem> cartItemList = cookieController.buildCartItemList(cookieString,member);
//            cookieController.saveCookieCartItems(cartItemList);
//            Cookie cookie = cookieController.setCookie("Cart","",0,"/");
//            response.addCookie(cookie);

            // 누른 버튼에 해당하는 아이템의 quantity를 DB에서 update해야 함.
            // new Quantity가 재고를 넘지 않을까 걱정할 필요 x 왜냐하면 이 함수는 -,+버튼을 누를때 호출되는 함수인데 프론트단에서 재고를 넘지 못하도록 -,+ 조작을 비활성화 함.
            cartItemService.UpdateCartItemQuantity(cartItemId,newQuantity); // 멤버 아이디와 아이템 id가 필효

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
        int age = 1*24*60*60;
        if(cartValue.isBlank()) {
            age = 0;
        }
        Cookie cookie = cookieController.setCookie("Cart", cartValue, age, "/");
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
