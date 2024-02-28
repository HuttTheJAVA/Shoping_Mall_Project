package com.example.shoppingMall.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "ORDERS") // Order는 H2 예약어라서 s를 붙혀주자
public class Order {
    @Id @GeneratedValue
    @Column(name = "ORDER_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    private Boolean isPay;

    private LocalDateTime orderDate;

    @Embedded
    private Address address;

    private String phone;

    //TODO 이거 어디서 계산처리할지?
    private Long totalPrice;

    public Order(Member member,Boolean isPay,LocalDateTime orderDate,String city,String street,String zipCode,String phone){
        this.member = member;
        this.orderDate = orderDate;
        this.address = new Address(city,street,zipCode);
        this.isPay = isPay; // 기본값 설정
        this.phone = phone; // 기본값 설정
        this.totalPrice = 0L; // 기본값 설정
    }
}
