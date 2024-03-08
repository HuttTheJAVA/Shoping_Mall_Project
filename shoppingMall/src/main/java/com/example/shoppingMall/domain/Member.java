package com.example.shoppingMall.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.CascadeType.*;
import static jakarta.persistence.FetchType.*;

@Entity
@Table(name = "member",indexes = {
        @Index(name = "userId",columnList = "userId"),
        @Index(name = "nickName",columnList = "nickName")
})
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {
    @Id @GeneratedValue
    @Column(name = "MEMBER_ID")
    private Long id;

    //TODO xToOne으로 된 애들 다 LAZY로
    @Column(unique = true)
    @NotEmpty
    private String userId; // 중복 불가

    @NotEmpty
    private String password; // 중복 가능

    @NotEmpty
    private String name; // 개인정보에 표시되는 이름, 중복 가능

    @Column(unique = true)
    @NotEmpty
    private String nickName; // 중복 불가

    @Embedded
    private Address address;
    private String phone;
    private Level level;
    private LocalDateTime registerDate;

    //TODO 멤버를 생성하면 하나의 카트도 같이 생성해줘야 한다.
    @OneToOne(mappedBy = "member",fetch = LAZY,cascade = PERSIST)
    private Cart cart;
    @OneToMany(mappedBy = "member",cascade = ALL)
    private List<Order> orders = new ArrayList<>();

    // 연관관계 편의 메서드 (Cart)
    public void setCart(Cart cart) {
        this.cart = cart;
        cart.setMember(this);
    }

    private Member(Builder builder){
        this.userId = builder.userId;
        this.password = builder.password;
        this.name = builder.name;
        this.nickName = builder.nickName;
        this.address = builder.address;
        this.phone = builder.phone;
        this.level = builder.level;
        this.registerDate = builder.register_date;
    }

    public static class Builder{
        private String userId;
        private String password;
        private String name;
        private String nickName;
        @Embedded
        private Address address;
        private String phone;
        private Level level;
        private LocalDateTime register_date;
        public Builder(String id){
            this.userId = id;
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

        public Builder date(LocalDateTime registDate){
            this.register_date = registDate;
            return this;
        }


        public Member build(){
            return new Member(this);
        }
    }
}