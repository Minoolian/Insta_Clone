package com.example.clonecode.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@AllArgsConstructor
@Data
public class PostUploadDto {

//    private long id;
    private String text;
    private String tag;
}
