package com.example.shoppingMall.web;

import com.example.shoppingMall.DTO.MemberDTO;
import com.example.shoppingMall.domain.Member;
import com.example.shoppingMall.domain.service.LoginService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/")
public class LoginController {

    private final LoginService loginService;

    @GetMapping("login")
    public String loginForm(@ModelAttribute("loginMember") MemberDTO.Login loginMember){
        return "login";
    }

    @PostMapping("login") // redirect된 홈페이지에는 메뉴바에 로그인이 아닌 사용자 닉네임이 떠야된다. 나중에는 로그인 세션까지 구현하자
    public String login(@Valid @ModelAttribute("loginMember") MemberDTO.Login loginMember, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "login";
        }
        Member findMember = loginService.login(loginMember);
        if(findMember == null){
            bindingResult.reject("loginFail","아이디 또는 비밀번호가 맞지 않습니다.");
            return "login";
        }
        return "redirect:/"; //TODO 로그인이 됐다면 홈페이지에 로그인 상태로 레더링 해야한다.
    }
}
