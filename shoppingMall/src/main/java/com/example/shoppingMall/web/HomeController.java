package com.example.shoppingMall.web;

import com.example.shoppingMall.DTO.MemberDTO;
import com.example.shoppingMall.domain.Member;
import com.example.shoppingMall.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class HomeController {

    @Autowired
    private MemberRepository memberRepository;
    @GetMapping("")
    public String home(){
        return "main";
    }
    @GetMapping("contact")
    public String contact(){
        return "contactus";
    }
}
