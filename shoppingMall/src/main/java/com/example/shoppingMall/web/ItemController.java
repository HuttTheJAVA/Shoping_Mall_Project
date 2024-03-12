package com.example.shoppingMall.web;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Configuration
@Controller
@RequestMapping("/item")
public class ItemController {
    
    @Value("${file.dir}")
    private String fileDir;
    
    @GetMapping("/upload")
    public String newItem(){
        System.out.println("WESTSIDE~");
        return "upload-form";
    }

    @PostMapping("/upload")
    public String saveFile(@RequestParam String itemName,
                           @RequestParam MultipartFile file,
                           HttpServletRequest request) throws IOException{

        if(!file.isEmpty()){
            String fullpath = fileDir + file.getOriginalFilename();
            file.transferTo(new File(fullpath));
        }

        return "upload-form";
    }

    @GetMapping("/{itemId}")
    public String item(){
        //아이템 상세 보여주기 컨트롤러
        return "itemId에 해당하는 아이템을 넘겨준다";
    }


}