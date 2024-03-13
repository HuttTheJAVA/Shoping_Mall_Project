package com.example.shoppingMall.web;

import com.example.shoppingMall.DTO.ItemDTO;
import com.example.shoppingMall.domain.Item;
import com.example.shoppingMall.domain.service.ItemService;
import com.example.shoppingMall.repository.FileStore;
import com.example.shoppingMall.repository.ItemRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

@Controller
@RequestMapping("/item")
@RequiredArgsConstructor
public class ItemController {
    private final ItemService itemService;
    private final FileStore fileStore;
    @GetMapping("/upload")
    public String newItem(){
        return "upload-form";
    }
    @PostMapping("/upload")
    public String saveFile(@ModelAttribute ItemDTO.Upload itemDTO,
                           RedirectAttributes redirectAttributes) throws IOException{

        Long id = itemService.save(itemDTO);
        redirectAttributes.addAttribute("itemId", id);

        return "redirect:/item/{itemId}";
    }

    @GetMapping("/{itemId}")
    public String item(@PathVariable Long itemId, Model model){
        Item item = itemService.findById(itemId);
        model.addAttribute("item",item);
        return "item-view";
    }

    @ResponseBody
    @GetMapping("images/{fileName}")
    public Resource itemImage(@PathVariable String fileName) throws MalformedURLException{
        return new UrlResource("file:"+fileStore.getFullPath(fileName));
    }
}