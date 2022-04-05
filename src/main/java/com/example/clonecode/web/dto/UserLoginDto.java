package com.example.clonecode.web.dto;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserLoginDto {
    private String email;
    private String password;
    private String phone;
    private String name;
}
