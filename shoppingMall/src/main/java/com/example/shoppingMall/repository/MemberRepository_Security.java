package com.example.shoppingMall.repository;

import com.example.shoppingMall.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface MemberRepository_Security extends JpaRepository<Member,Long> {

    boolean existsByUserId(String userId);
    Member findByUserId(String userId);

}
