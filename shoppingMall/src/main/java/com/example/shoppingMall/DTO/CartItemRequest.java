package com.example.shoppingMall.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartItemRequest {
    private Long itemId;
    private Integer quantity;

    private Long cartItemId;
}
