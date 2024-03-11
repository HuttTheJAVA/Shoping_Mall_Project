package com.example.shoppingMall.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
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
public class Member implements UserDetails {
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
    private Cart cart = new Cart();     // member 생성 시 cart도 생성
    @OneToMany(mappedBy = "member") //xToMany는 기본 fetch가 LAZY다. Order는 따로 persist하자.
    private List<Order> orders = new ArrayList<>();

    // 연관관계 편의 메서드 (Cart)
    public void setCart(Cart cart) {
        this.cart = cart;
        cart.setMember(this);
    }

    public void setOrder(Order order) {
        if (order != null) {
            orders.add(order);
            order.setMember(this);
        }
    }

    @Builder //Cart는 따로 생성, Order는 주문 발생 시 생성 (Persist도 둘 다 따로 )
    public Member(String userId,String password,String name,String nickName,Address address,String phone,LocalDateTime registerDate,Level level) {
        this.userId = userId;
        this.password = password;
        this.name = name;
        this.nickName = nickName;
        this.address = address;
        this.phone = phone;
        this.registerDate = registerDate;
        this.level = level;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(this.level.name())); // 권한이 있는지 확인하는 코드는 "ROLE_ADMIN" 이런 식으로 확인해야 함.
    }

    @Override
    public String getUsername() {
        return userId;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}