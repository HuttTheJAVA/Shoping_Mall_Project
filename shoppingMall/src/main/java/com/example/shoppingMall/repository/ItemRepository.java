package com.example.shoppingMall.repository;

import com.example.shoppingMall.domain.Item;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

@Repository
public class ItemRepository {
    @PersistenceContext
    private EntityManager em;

    public Long saveItem(Item item){
        em.persist(item);
        return item.getId();
    }

    public Item findById(Long id){
        return em.find(Item.class,id);
    }
}
