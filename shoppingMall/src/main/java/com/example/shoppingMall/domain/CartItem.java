package com.example.shoppingMall.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

import static jakarta.persistence.FetchType.*;

@Entity
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(indexes = {@Index(name = "MEMBER_ID", columnList = "MEMBER_ID")}) // 인덱스 추가
public class CartItem {
    @Id @GeneratedValue
    @Column(name = "CARTITEM_ID")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "ITEM_ID")
    private Item item;
    private Long quantity;

    @Builder
    public CartItem(Member member,Item item,Long quantity){
        this.member = member;
        this.item = item;
        this.quantity = quantity;
    }

//    아래 코드는 CartItem 끼리 item이 같다면 같은 CartItem으로 보는 코드이다. 이 코드는 장바구니에서 중복되는 item을 가진
//    CartItem들의 중복을 제거하고 quantity는 합치기 위해 사용된다.
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CartItem cartItem = (CartItem) o;
        return Objects.equals(item, cartItem.item);
    }

    @Override
    public int hashCode() {
        return Objects.hash(item);
    }
}
