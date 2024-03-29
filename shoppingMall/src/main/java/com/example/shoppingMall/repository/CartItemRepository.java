package com.example.shoppingMall.repository;

import com.example.shoppingMall.domain.CartItem;
import com.example.shoppingMall.domain.OrderItem;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.Metamodel;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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

    public void delete(CartItem cartItem){
        em.remove(cartItem);
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
    public List<CartItem> findByMemberId_AND_itemId(Long id,Long itemId){
        String jpql = "SELECT ci From CartItem ci WHERE ci.member.id = :MEMBER_ID AND ci.item.id = :ITEM_ID";
        List<CartItem> cartItems = em.createQuery(jpql,CartItem.class)
                .setParameter("MEMBER_ID",id)
                .setParameter("ITEM_ID",itemId)
                .getResultList();
        return cartItems;
    }


    public void updateQuantity(Long id,Long quantity){
//        String jpql = "UPDATE CartItem c SET c.quantity = :newQuantity WHERE c.id = :itemId";
//        em.createQuery(jpql)
//                .setParameter("newQuantity",quantity)
//                .setParameter("itemId",id)
//                .executeUpdate();
        CartItem cartItem = em.find(CartItem.class, id); // 해당 엔티티를 조회합니다.
        if (cartItem != null) {
            cartItem.setQuantity(quantity); // 속성을 변경합니다.
            em.merge(cartItem); // 변경된 엔티티를 영속성 컨텍스트에 병합합니다.
        }
    }

    // 모든 엔티티 조회 메서드
    public void findAllEntities() {
        List<Object> allEntities = new ArrayList<>();
        Metamodel metamodel = em.getMetamodel();
        for (EntityType<?> entityType : metamodel.getEntities()) {
            String entityName = entityType.getName();
            List<Object> entities = findAllByEntityName(entityName);
            allEntities.addAll(entities);
        }
        for(Object o:allEntities){
            System.out.println(o);
        }
    }

    // 특정 엔티티 클래스에 대한 엔티티 조회 메서드
    public List<Object> findAllByEntityName(String entityName) {
        String jpql = "SELECT e FROM " + entityName + " e";
        return em.createQuery(jpql).getResultList();
    }
}
