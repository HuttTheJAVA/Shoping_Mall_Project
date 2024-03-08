package com.example.shoppingMall.DTO;

import com.example.shoppingMall.domain.Address;
import com.example.shoppingMall.domain.Level;
import jakarta.persistence.Embedded;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class MemberDTO {
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Login{
        private String userId;
        private String password;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Join{

        private String userId;
        private Level level;
        private String password;
        private String name;
        private String nickName;
        private String phone;

        @Embedded
        private Address address;
    }

}
