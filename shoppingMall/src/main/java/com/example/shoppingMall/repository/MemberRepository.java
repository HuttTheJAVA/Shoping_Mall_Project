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
//        List<Member> members_user_id = em.createQuery(
//                "SELECT m FROM Member m WHERE m.user_id = :userId",Member.class)
//                .setParameter("userId",member.getUser_id())
//                .getResultList();
//
//        List<Member> members_nickName = em.createQuery(
//                        "SELECT m FROM Member m WHERE m.nickName = :nickName",Member.class)
//                .setParameter("nickName",member.getNickName())
//                .getResultList();
//        if(!members_user_id.isEmpty()){
//            throw new ExistFieldException("중복되는 id를 가진 유저가 존재합니다.");
//            //TODO join을 호출한 service에서 이에 대한 처리 후 html로 빨간 표시 강조(이미 존재하는 필드입니다...)
//        }
//        if(!members_nickName.isEmpty()){
//            throw new ExistFieldException("중복되는 닉네임을 가진 유저가 존재합니다.");
//        }
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
