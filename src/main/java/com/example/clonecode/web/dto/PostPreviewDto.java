package com.example.clonecode.web.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigInteger;

@Getter
@Setter
@NoArgsConstructor
public class PostPreviewDto {
    private long id;
    private String postImgUrl;
    private long likesCount;

    public PostPreviewDto(BigInteger id, String postImgUrl, BigInteger likesCount) {
        this.id = id.longValue();
        this.postImgUrl = postImgUrl;
        this.likesCount = likesCount.longValue();
    }
}
