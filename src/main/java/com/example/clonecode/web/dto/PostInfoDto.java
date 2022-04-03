package com.example.clonecode.web.dto;

import com.example.clonecode.domain.Comment;
import com.example.clonecode.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class PostInfoDto {

    private long id;
    private String text;
    private String tag;
    private LocalDateTime createdate;
    private User postUploader;
    private String postImgUrl;

    private long likesCount;
    private boolean likeState;
    private boolean uploader;
    private List<Comment> commentList;
}
