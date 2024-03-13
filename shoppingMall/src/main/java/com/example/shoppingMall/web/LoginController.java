package com.example.shoppingMall.web;

import com.example.shoppingMall.DTO.MemberDTO;
import com.example.shoppingMall.domain.Member;
import com.example.shoppingMall.domain.service.LoginService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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

    /*Spring Security의 .loginProcessingUrl("/login")으로 인해 여기로(@PostMapping("login")) 요청이 오지 않고
    spring security에 의해 가로채지고 자체적으로 인증(bycrypt)하여 처리된다.*/
}
