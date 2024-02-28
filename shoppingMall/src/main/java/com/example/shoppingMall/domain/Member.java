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
    @OneToOne(mappedBy = "member",fetch = LAZY)
    private Cart cart;

    @OneToMany(mappedBy = "member",cascade = ALL)
    private List<Order> orders = new ArrayList<>();

    private String user_id; // 중복 불

    private Level level;

    private String password; // 중복 가능

    private String name; // 개인정보에 표시되는 이름, 중복 가능

    private String nickName; // 중복 불가

    private String phone;

    private LocalDateTime register_date;
}
