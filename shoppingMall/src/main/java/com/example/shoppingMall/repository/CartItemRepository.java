package com.example.shoppingMall.repository;

import com.example.shoppingMall.domain.CartItem;
import com.example.shoppingMall.domain.OrderItem;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CartItemRepository {
    @PersistenceContext
    private EntityManager em;

    //모든 Repository save나 findById를 호출하는 쪽에서 @Transactional 어노테이션으로 트랜잭션을 걸어줘야 한다.
    public Long saveCartItem(CartItem cartItem){
        em.persist(cartItem);
        return cartItem.getId();
    }
    public CartItem findById(Long id){
        return em.find(CartItem.class,id);
    }

    public List<CartItem> findByMemberId(Long id){
        String jpql = "SELECT ci From CartItem ci WHERE ci.member.id = :MEMBER_ID";
        List<CartItem> cartItems = em.createQuery(jpql,CartItem.class)
                .setParameter("MEMBER_ID",id)
                .getResultList();
        return cartItems;
    }
}
