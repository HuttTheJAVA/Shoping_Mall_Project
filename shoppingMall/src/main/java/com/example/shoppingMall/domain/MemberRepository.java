package com.example.shoppingMall.domain;

import com.example.shoppingMall.exception.ExistFieldException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.PersistenceContext;
import lombok.Data;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Data
public class MemberRepository {
    @PersistenceContext
    private EntityManager em;

    @Transactional
    public void Join(Member member){
        List<Member> members_user_id = em.createQuery(
                "SELECT m FROM Member m WHERE m.user_id = :userId",Member.class)
                .setParameter("userId",member.getUser_id())
                .getResultList();

        List<Member> members_nickName = em.createQuery(
                        "SELECT m FROM Member m WHERE m.nickName = :nickName",Member.class)
                .setParameter("nickName",member.getNickName())
                .getResultList();
        if(!members_user_id.isEmpty()){
            throw new ExistFieldException("중복되는 id를 가진 유저가 존재합니다.");
            //TODO join을 호출한 service에서 이에 대한 처리 후 html로 빨간 표시 강조(이미 존재하는 필드입니다...)
        }
        if(!members_nickName.isEmpty()){
            throw new ExistFieldException("중복되는 닉네임을 가진 유저가 존재합니다.");
        }
        em.persist(member);
    }

}