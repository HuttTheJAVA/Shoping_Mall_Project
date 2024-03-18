package com.example.shoppingMall.domain.service;

import com.example.shoppingMall.DTO.MemberDTO;
import com.example.shoppingMall.domain.Level;
import com.example.shoppingMall.domain.Member;
import com.example.shoppingMall.repository.MemberRepository_Security;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class JoinService {
    private final MemberRepository_Security userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;


    /**
     * @return null이면 회원가입 실패
     */

    @Transactional
    public Long join(MemberDTO.Join dto){
        return userRepository
                .save(Member.builder()
                        .userId(dto.getUserId())
                        .level(Level.ROLE_USER)
                        .password(bCryptPasswordEncoder.encode(dto.getPassword()))
                        .name(dto.getName())
                        .nickName(dto.getNickName())
                        .address(dto.getAddress())
                        .phone(dto.getPhone())
                        .registerDate(LocalDateTime.now())
                        .build()).getId();
    }

}
