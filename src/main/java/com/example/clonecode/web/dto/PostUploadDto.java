package com.example.clonecode.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@AllArgsConstructor
@Getter
@Setter
public class PostUploadDto {

//    private long id;
    private String text;
    private String tag;
}
