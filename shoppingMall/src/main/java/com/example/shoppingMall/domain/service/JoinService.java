package com.example.shoppingMall.domain.service;

import com.example.shoppingMall.DTO.MemberDTO;
import com.example.shoppingMall.domain.Level;
import com.example.shoppingMall.domain.Member;
import com.example.shoppingMall.repository.MemberRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class JoinService {
    @Autowired
    private final MemberRepository memberRepository;

    @Autowired
    private final BCryptPasswordEncoder bCryptPasswordEncoder;


    /**
     * @return null이면 회원가입 실패
     */

    @Transactional
    public Long join(@Valid @ModelAttribute MemberDTO.Join joinMember){
        try{
            Member member = new Member.Builder(joinMember.getUserId())
                    .password(bCryptPasswordEncoder.encode(joinMember.getPassword())) // bcrypt 암호화로 사용자 비밀번호 저장.
                    .name(joinMember.getName())
                    .nickName(joinMember.getNickName())
                    .address(joinMember.getAddress())
                    .phone(joinMember.getPhone())
                    .level(Level.ROLE_USER)
                    .date(LocalDateTime.now())
                    .build();

            Long id = memberRepository.Join(member);
            return id;
        }catch (DataIntegrityViolationException e){
            throw e;
        }
    }
}
