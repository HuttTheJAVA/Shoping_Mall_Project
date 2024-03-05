package com.example.shoppingMall.domain;

import jakarta.persistence.*;
import lombok.Data;

import static jakarta.persistence.FetchType.*;

@Entity
@Data
public class CartItem {
    @Id @GeneratedValue
    @Column(name = "CARTITEM_ID")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "CART_ID")
    private Cart cart;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "ITEM_ID")
    private Item item;

    private Long quantity;
}
