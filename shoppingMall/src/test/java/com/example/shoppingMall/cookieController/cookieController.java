package com.example.shoppingMall.cookieController;

import com.example.shoppingMall.Cookie.CookieController;
import com.example.shoppingMall.domain.CartItem;
import com.example.shoppingMall.domain.Item;
import com.example.shoppingMall.repository.ItemRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@Transactional
public class cookieController {

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private CookieController cookieController;

    @Test
    public void sync_test(){
        String cookieString = "|1:5|2:3|3:11|4:1";
        List<CartItem> cartItemList = new ArrayList<>();
        Item item1 = Item.builder().quantity(2l).build();
        Item item2 = Item.builder().quantity(2l).build();
        Item item3 = Item.builder().quantity(2l).build();
        Item item4 = Item.builder().quantity(2l).build();
        itemRepository.saveItem(item1);
        itemRepository.saveItem(item2);
        itemRepository.saveItem(item3);
        itemRepository.saveItem(item4);
        cartItemList = cookieController.buildCartItemList(cookieString,null);
        System.out.println(cookieString);
        cookieString = cookieController.sync_CartItem_Cookie(cartItemList,cookieString);
        System.out.println(cookieString);
    }
}
