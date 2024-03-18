package com.example.shoppingMall.CartItemMerger;

import com.example.shoppingMall.domain.CartItem;
import com.example.shoppingMall.domain.Item;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@Transactional

public class CartItemMerger {

    public void addToCartItems(List<CartItem> cartItems){
        cartItems.add(CartItem.builder().item(Item.builder()
                .name("psp")
                .price(12000l)
                .quantity(30l)
                .build()).quantity(30l).build());

        cartItems.add(CartItem.builder().item(Item.builder()
                .name("psp")
                .price(12000l)
                .quantity(70l)
                .build()).quantity(70l).build());
        cartItems.add(CartItem.builder().item(Item.builder()
                .name("pod")
                .price(12000l)
                .quantity(30l)
                .build()).quantity(30l).build());
        cartItems.add(CartItem.builder().item(Item.builder()
                .name("pod")
                .price(12000l)
                .quantity(10l)
                .build()).quantity(10l).build());
    }

    public static List<CartItem> merge(List<CartItem> cartItems) {
        // Item을 기준으로 그룹화하고 quantity를 합산
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

    @Test
    public void result(){
        List<CartItem> cartItems = new ArrayList<>();
        addToCartItems(cartItems);
        List<CartItem> list = merge(cartItems);
        for(CartItem i : list){
            System.out.println(i.getItem().getName()+" "+i.getQuantity());
        }
    }
}
