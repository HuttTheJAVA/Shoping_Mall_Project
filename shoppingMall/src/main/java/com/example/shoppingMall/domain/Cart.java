//package com.example.shoppingMall.domain;
//
//import jakarta.persistence.*;
//import lombok.AccessLevel;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import static jakarta.persistence.CascadeType.*;
//import static jakarta.persistence.FetchType.*;
//
//@Entity
//@Data
//@Table(name = "cart")
//@NoArgsConstructor(access = AccessLevel.PROTECTED) //protected는 상속받은 클래스나 같은 패키지에 존재하는 클래스까지는 접근 가능.
//public class Cart {
//    @Id
//    @GeneratedValue
//    @Column(name = "CART_ID")
//    private Long id;
//
//    @OneToOne(fetch = LAZY)
//    @JoinColumn(name = "MEMBER_ID")
//    private Member member;
//
//    @OneToMany(mappedBy = "cart", fetch = LAZY,cascade = ALL)
//    private List<CartItem> cartItems = new ArrayList<>();

//    public void addCartItem(CartItem cartItem){
//        cartItems.add(cartItem);
//        cartItem.setCart(this);
//    }
//}
