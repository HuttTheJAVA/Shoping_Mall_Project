package com.example.shoppingMall.repository;

import com.example.shoppingMall.domain.Order;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.Data;
import org.springframework.stereotype.Repository;

@Repository
public class OrderRepository {
    @PersistenceContext
    private EntityManager em;
    public Long createOrder(Order order){
        em.persist(order);
        return order.getId(); //TODO id를 돌려주면 이 함수를 호출한 쪽에서 관련된 모든 상품의 주문 ID를 이 id로 설정.
    }

    public Order findById(Long id){
        return em.find(Order.class,id);
    }
}
