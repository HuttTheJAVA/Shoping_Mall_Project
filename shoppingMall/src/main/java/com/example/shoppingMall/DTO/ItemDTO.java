package com.example.shoppingMall.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

public class ItemDTO {

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Upload{
        private Long Id;
        private Long price;
        private String Name;
        private Long quantity;
        private MultipartFile attachFile;
    }
}
