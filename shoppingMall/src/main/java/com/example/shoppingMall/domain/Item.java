package com.example.shoppingMall.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Entity
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Item {
    @Id @GeneratedValue
    @Column(name = "ITEM_ID")
    private Long id;
    private String name;
    private Long price;
    private Long quantity;
    private String storeFileName; //file.dir 경로에 저장된 파일의 고유 이름.

    @Builder
    public Item(String name,Long price,Long quantity,String storeFileName){
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.storeFileName = storeFileName;
    }

    // 아이템 객체간 이름이 같으면 같은 아이템으로 구분.
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return Objects.equals(name, item.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

}
