package com.example.shoppingMall.domain.service;

import com.example.shoppingMall.domain.CartItem;
import com.example.shoppingMall.domain.Item;
import com.example.shoppingMall.domain.Member;
import com.example.shoppingMall.repository.CartItemRepository;
import com.example.shoppingMall.repository.ItemRepository;
import com.example.shoppingMall.repository.MemberRepository;
import com.example.shoppingMall.web.CartItemController;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CartItemService {
    private final CartItemRepository cartItemRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;
    @Transactional
    public Long save(String userId,Long itemId,Long quantity){
        Member member = memberRepository.findByUserId(userId);
        Item item = itemRepository.findById(itemId);

        // 재고 수량을 넘지않게 제한.
        Long maxQuantity = item.getQuantity();
        quantity = Math.min(maxQuantity,quantity);

        List<CartItem> result = cartItemRepository.findByMemberId_AND_itemId(member.getId(),itemId);
        if(result.isEmpty()){
            CartItem cartItem = CartItem.builder().member(member).quantity(quantity).item(item).build(); // 저장된 CartItem이 없을 경우만 객체 생성.
            return this.saveCartItem(cartItem);
        }else{
            CartItem findCartItem = result.get(0);
            Long default_Quantity = findCartItem.getQuantity();
            Long UpdateQuantity = default_Quantity + quantity;

            // 재고 수량을 넘지않게 제한.
            UpdateQuantity = Math.min(maxQuantity,UpdateQuantity);
            return this.UpdateCartItemQuantity(findCartItem.getId(),UpdateQuantity);
        }
    }

    public CartItem findById(Long id) {
        return cartItemRepository.findById(id);
    }

    public List<CartItem> cartList(Long id){
        return cartItemRepository.findByMemberId(id);
    }

    public Long findMemberId(String userId){
        return memberRepository.findByUserId(userId).getId();
    }

    @Transactional
    public void delete(Long id){
        CartItem cartItem = cartItemRepository.findById(id);
        cartItemRepository.delete(cartItem);
    }
    @Transactional
    public Long UpdateCartItemQuantity(Long id,Long newQuantity){
        cartItemRepository.updateQuantity(id,newQuantity);
        return id;
    }

    @Transactional
    public Long saveCartItem(CartItem cartItem){
        return cartItemRepository.saveCartItem(cartItem);
    }

}
