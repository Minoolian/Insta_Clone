package com.example.clonecode.web.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class CommentUploadDto {

    @NotBlank
    private String text;

    @NotNull
    private Long postId;

}
