package com.example.clonecode.web.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class PostDto {

    private long id;
    private String tag;
    private String text;
    private String postImgUrl;
}
