package com.example.shoppingMall;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class HomeController {
    @GetMapping("")
    public String home(){
        return "main";
    }

    @GetMapping("login")
    public String login(){
        return "login";
    }

    @GetMapping("join")
    public String join(){
        return "join";
    }

    @GetMapping("contact")
    public String contact(){
        return "contactus";
    }
}
