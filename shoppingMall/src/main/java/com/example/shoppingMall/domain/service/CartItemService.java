package com.example.shoppingMall.domain.service;

import com.example.shoppingMall.domain.CartItem;
import com.example.shoppingMall.domain.Item;
import com.example.shoppingMall.domain.Member;
import com.example.shoppingMall.repository.CartItemRepository;
import com.example.shoppingMall.repository.ItemRepository;
import com.example.shoppingMall.repository.MemberRepository;
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
        String filePath = itemRepository.findById(itemId).getStoreFileName();
        CartItem cartItem = CartItem.builder().member(member).quantity(quantity).item(item).build();
        return cartItemRepository.saveCartItem(cartItem);
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
}
