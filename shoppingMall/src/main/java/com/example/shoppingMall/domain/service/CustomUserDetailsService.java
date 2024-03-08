package com.example.shoppingMall.domain.service;

import com.example.shoppingMall.DTO.CustomUserDetails;
import com.example.shoppingMall.domain.Member;
import com.example.shoppingMall.repository.MemberRepository_Security;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private MemberRepository_Security memberRepository_security;

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        Member member = memberRepository_security.findByUserId(userId);
        System.out.println("userId"+userId);
        System.out.println("member:"+member);
        if(member != null){
            return new CustomUserDetails(member);
        }
        throw new IllegalArgumentException(userId);
    }
}
