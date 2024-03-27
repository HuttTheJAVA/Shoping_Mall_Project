package com.example.shoppingMall.web;

import com.example.shoppingMall.DTO.MemberDTO;
import com.example.shoppingMall.domain.Member;
import com.example.shoppingMall.repository.MemberRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Collection;
import java.util.Iterator;

@Controller
@RequestMapping("/")
public class HomeController {

    @Autowired
    private MemberRepository memberRepository;
    @GetMapping("")
    public String home(Model model, HttpServletRequest request){

        /// 아래 코드를 request에서 직접 id,role을 추출해 model에 직접 담지
        /// 않아도 스프링 mvc가 알아서 뷰로 id,role을 렌더링해준다.
        /// 그러나 명시성을 위해 직접 꺼내 담아 보내자.
        String id = (String) request.getAttribute("id");
        String role = (String) request.getAttribute("role");

        model.addAttribute("id",id);
        model.addAttribute("role",role);
        ////////////////////////////////////////////////////////

        return "main";
    }
    @GetMapping("contact")
    public String contact(){
        return "contactus";
    }

    @GetMapping("quantity")
    public String quantity(){
        return "quantity";
    }
}
