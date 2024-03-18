package com.example.shoppingMall.web;

import com.example.shoppingMall.DTO.MemberDTO;
import com.example.shoppingMall.domain.service.JoinService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class JoinController {
    private final JoinService joinService;
    @GetMapping("join")
    public String joinForm(@ModelAttribute("joinMember") MemberDTO.Join joinMember){
        return "join";
    }

    @PostMapping("join")
    public String join(@Valid @ModelAttribute("joinMember") MemberDTO.Join joinMember, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "join";
        }
        if(joinMember.getUserId().equals("anonymousUser")){ //anonymousUser는 로직에서 인증여부로 사용되기 때문에 해당 닉네임은 못 짓게 막아 놈.
            bindingResult.reject("joinfail","이미 존재하는 아이디나 닉네임입니다.");
            return "join";
        }
        try{
            Long id = joinService.join(joinMember);
        }catch (DataIntegrityViolationException e){
            bindingResult.reject("joinfail","이미 존재하는 아이디나 닉네임입니다.");
            return "join";
        }
        return "welcome"; // 가입했으면 환영합니다 창으로 보내기
    }
}
