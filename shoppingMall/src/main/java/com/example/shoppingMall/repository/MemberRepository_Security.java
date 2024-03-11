package com.example.shoppingMall.repository;

import com.example.shoppingMall.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository_Security extends JpaRepository<Member,Long> {

    boolean existsByUserId(String userId);

    Optional<Member> findByUserId(String userId);

}
