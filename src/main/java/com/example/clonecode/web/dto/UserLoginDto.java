package com.example.clonecode.web.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UserLoginDto {
    private String email;
    private String password;
    private String phone;
    private String name;
}
