package com.example.shoppingMall.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Item {
    @Id @GeneratedValue
    @Column(name = "ITEM_ID")
    private Long id;
}
