package com.example.clonecode.web.dto;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PostUpdateDto {

    private long id;
    private String tag;
    private String text;
}
