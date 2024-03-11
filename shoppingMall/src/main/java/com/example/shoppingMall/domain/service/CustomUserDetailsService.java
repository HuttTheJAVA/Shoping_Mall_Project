package com.example.shoppingMall.domain.service;

import com.example.shoppingMall.DTO.CustomUserDetails;
import com.example.shoppingMall.DTO.MemberDTO;
import com.example.shoppingMall.domain.Address;
import com.example.shoppingMall.domain.Level;
import com.example.shoppingMall.domain.Member;
import com.example.shoppingMall.repository.MemberRepository_Security;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService{
    private final MemberRepository_Security userRepository;


    @Override
    @Transactional // 조회는 트랜잭션을 거는 것이 좋지 않지만 이렇게 하지 않으면 "could not initialize proxy - no Session 에러"가 발생한다. 이부분은 좀 더 고민해보자.
    public Member loadUserByUsername(String userId) throws UsernameNotFoundException {
        System.out.println("■□■□■□■□■□■□■□■□■□ ■□■□■□■□■□■□■□■□■□ userIs: "+userRepository.findByUserId(userId).get());
        return userRepository.findByUserId(userId)
                .orElseThrow(()->new IllegalArgumentException(userId));
    }
}