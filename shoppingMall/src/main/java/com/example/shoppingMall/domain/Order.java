package com.example.shoppingMall.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.CascadeType.*;
import static jakarta.persistence.FetchType.*;

@Entity
@Data
@Table(name = "ORDERS") // Order는 H2 예약어라서 s를 붙혀주자
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order {
    @Id @GeneratedValue
    @Column(name = "ORDER_ID")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @OneToMany(mappedBy = "order",fetch = LAZY,cascade = ALL)
    private List<OrderItem> orderItems = new ArrayList<>();
    private Boolean isPay;

    private LocalDateTime orderDate;

    @Embedded
    private Address address;

    private String phone;

    //TODO 이거 어디서 계산처리할지?
    private Long totalPrice;

    private Order(Builder builder){
        this.member = builder.member;
        this.isPay = builder.isPay;
        this.orderDate = builder.orderDate;
        this.address = builder.address;
        this.phone = builder.phone;
        this.totalPrice = builder.totalPrice;
    }

    public static class Builder{
        private Member member;
        private Boolean isPay;
        private LocalDateTime orderDate;
        private Address address;
        private String phone;
        private Long totalPrice;

        public Builder member(Member member){
            this.member = member;
            return this;
        }

        public Builder isPay(Boolean isPay){
            this.isPay = isPay;
            return this;
        }

        public Builder orderDate(LocalDateTime orderDate){
            this.orderDate = orderDate;
            return this;
        }

        public Builder address(Address address){
            this.address = address;
            return this;
        }

        public Builder phone(String phone){
            this.phone = phone;
            return this;
        }

        public Builder totalPrice(Long totalPrice){
            this.totalPrice = totalPrice;
            return this;
        }

        public Order build(){
            return new Order(this);
        }
    }
}
