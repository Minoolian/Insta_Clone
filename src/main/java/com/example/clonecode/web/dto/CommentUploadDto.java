package com.example.clonecode.web.dto;

import lombok.Data;

@Data
public class CommentUploadDto {

    private String text;
    private Long postId;

}
