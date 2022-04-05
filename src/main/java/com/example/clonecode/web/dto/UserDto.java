package com.example.clonecode.web.dto;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserDto {
    private long id;
    private String email;
    private String phone;
    private String name;
    private String title;
    private String website;
    private String profileImgUrl;
}
