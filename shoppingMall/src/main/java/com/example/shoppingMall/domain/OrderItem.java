package com.example.shoppingMall.domain;

import jakarta.persistence.*;
import lombok.Data;

import static jakarta.persistence.FetchType.*;

@Entity
@Data
public class OrderItem {
    @Id @GeneratedValue
    @Column(name = "ORDERITEM_ID")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "ORDER_ID")
    private Order order;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "ITEM_ID")
    private Item item;
    private Long count;
}
