package com.example.clonecode.web.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UserDto {
    private long id;
    private String email;
    private String phone;
    private String name;
    private String title;
    private String website;
    private String profileImgUrl;
}
