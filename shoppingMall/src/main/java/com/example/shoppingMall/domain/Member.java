package com.example.shoppingMall.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.CascadeType.*;
import static jakarta.persistence.FetchType.*;

@Entity
@Table(name = "member",indexes = {
        @Index(name = "user_id",columnList = "user_id"),
        @Index(name = "nickName",columnList = "nickName")
})
@Data
public class Member {
    @Id @GeneratedValue
    @Column(name = "MEMBER_ID")
    private Long id;

    //TODO 멤버 address 필드 선언 & 멤버 생성자 어떻게 구현할까?
    //TODO xToOne으로 된 애들 다 LAZY로

    @Column(unique = true)
    private String user_id; // 중복 불가
    private String password; // 중복 가능
    private String name; // 개인정보에 표시되는 이름, 중복 가능

    @Column(unique = true)
    private String nickName; // 중복 불가

    @Embedded
    private Address address;
    private String phone;
    private Level level;
    private LocalDateTime register_date;
    @OneToOne(mappedBy = "member",fetch = LAZY)
    private Cart cart;
    @OneToMany(mappedBy = "member",cascade = ALL)
    private List<Order> orders = new ArrayList<>();

    private Member(Builder builder){
        this.user_id = builder.user_id;
        this.password = builder.password;
        this.name = builder.name;
        this.nickName = builder.nickName;
        this.address = builder.address;
        this.phone = builder.phone;
        this.level = builder.level;
        this.register_date = builder.register_date;
        this.cart = builder.cart;
    }

    public static class Builder{
        private String user_id;
        private String password;
        private String name;
        private String nickName;
        @Embedded
        private Address address;
        private String phone;
        private Level level;
        private LocalDateTime register_date;
        private Cart cart;

        public Builder(String id){
            this.user_id = id;
        }

        public Builder password(String password){
            this.password = password;
            return this;
        }

        public Builder name(String name){
            this.name = name;
            return this;
        }

        public Builder nickName(String nickName){
            this.nickName = nickName;
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

        public Builder level(Level level){
            this.level = level;
            return this;
        }

        public Builder date(LocalDateTime regist_date){
            this.register_date = regist_date;
            return this;
        }

        public Builder cart(Cart cart){
            this.cart = cart;
            return this;
        }

        public Member build(){
            return new Member(this);
        }
    }
}