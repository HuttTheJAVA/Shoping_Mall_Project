package com.example.shoppingMall.web;

import com.example.shoppingMall.domain.Member;
import com.example.shoppingMall.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Controller
@RequiredArgsConstructor
@RequestMapping("/")
public class MemberInfoController {

    @Autowired
    private MemberRepository memberRepository;

    @GetMapping("info/{id}")
    public String userInfo(@PathVariable String id, Model model){
        Member findMemeber = memberRepository.findByUserId(id);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String date = findMemeber.getRegisterDate().format(formatter);

        model.addAttribute("joinDate",date);

        model.addAttribute("member",findMemeber);
        return "info";
    }
}
