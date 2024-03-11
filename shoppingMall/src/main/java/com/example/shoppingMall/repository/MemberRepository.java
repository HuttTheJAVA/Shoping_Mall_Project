package com.example.shoppingMall.repository;

import com.example.shoppingMall.domain.Member;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.PersistenceContext;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Repository
public class MemberRepository {

    @PersistenceContext
    private EntityManager em;

    public Long Join(Member member){
        em.persist(member);
        return member.getId();
    }

    // @Transactional 조회에 트랜잭션이 필요한가?
    public Member findById(Long id){
        return em.find(Member.class,id);
    }

    public Member findByUserId(String userId){
        String jpql = "SELECT m FROM Member m WHERE m.userId = :userId";
        List<Member> members = em.createQuery(jpql,Member.class)
                .setParameter("userId",userId)
                .getResultList();
        if(members.isEmpty()){
            return null;
        }
        return members.get(0); // user_id는 유니크라서 memebers는 0 또는 1개의 멤버만을 포함.
    }

}
