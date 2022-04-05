package com.example.clonecode.web.dto;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserUpdateDto {
    private long id;
    private String password;
    private String phone;
    private String name;
    private String title;
    private String website;
}
