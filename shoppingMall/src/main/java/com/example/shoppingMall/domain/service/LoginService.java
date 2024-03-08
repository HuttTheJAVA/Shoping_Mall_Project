package com.example.shoppingMall.domain.service;

import com.example.shoppingMall.DTO.MemberDTO;
import com.example.shoppingMall.domain.Member;
import com.example.shoppingMall.repository.MemberRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ModelAttribute;

@Service
@RequiredArgsConstructor
public class LoginService {
    private final MemberRepository memberRepository;

    /**
     * @ return null이면 로그인 실패
     */
//    @Transactional
    public Member login(@Valid @ModelAttribute MemberDTO.Login loginMember){
        Member findMember = memberRepository.findByUserId(loginMember.getUserId());
        if(findMember == null || !findMember.getPassword().equals(loginMember.getPassword())){
            return null;
        }

        return findMember;
    }
}
