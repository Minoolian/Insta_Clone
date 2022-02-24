package com.example.clonecode.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class PostDto {

    private long id;
    private String tag;
    private String text;
    private String postImgUrl;
}