package com.example.clonecode.web.dto;

import com.example.clonecode.domain.Comment;
import com.example.clonecode.domain.User;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
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
