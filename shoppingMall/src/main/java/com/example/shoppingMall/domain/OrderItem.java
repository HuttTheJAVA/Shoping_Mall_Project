package com.example.shoppingMall.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class OrderItem {
    @Id @GeneratedValue
    @Column(name = "ORDERITEM_ID")
    private Long id;
}
