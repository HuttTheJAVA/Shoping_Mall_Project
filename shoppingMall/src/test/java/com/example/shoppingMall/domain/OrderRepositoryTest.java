package com.example.shoppingMall.domain;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@Transactional // 모든 @Test에 트랜잭션 적용.

class OrderRepositoryTest {

    @Autowired
    private OrderRepository repository;
    @Autowired
    private MemberRepository memberRepository;
    @Test
    public void createOrder(){
        Member member = new Member();
        member.setName("kim");
        member.setNickName("yeah");
        memberRepository.Join(member);
        Order order = new Order(member,false, LocalDateTime.now(),
                "City of Seoul","테헤란로","142002","1243-808");
        Long id = repository.createOrder(order);
        Assertions.assertThat(id).isEqualTo(order.getId());

    }
}