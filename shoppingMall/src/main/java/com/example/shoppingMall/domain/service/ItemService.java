package com.example.shoppingMall.domain.service;

import com.example.shoppingMall.DTO.ItemDTO;
import com.example.shoppingMall.domain.Item;
import com.example.shoppingMall.repository.FileStore;
import com.example.shoppingMall.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepository itemRepository;
    private final FileStore fileStore;
    @Transactional
    public Long save(ItemDTO.Upload itemDTO) throws IOException {
        String storeFileName = fileStore.storeFile(itemDTO.getFile());

        Item newItem = Item.builder()
                .name(itemDTO.getName())
                .price(itemDTO.getPrice())
                .quantity(itemDTO.getQuantity())
                .storeFileName(storeFileName)
                .build();

        itemRepository.saveItem(newItem);

        return newItem.getId();
    }

    public Item findById(Long id){
        return itemRepository.findById(id);
    }

}

