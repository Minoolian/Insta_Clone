package com.example.clonecode.web.controller;

import com.example.clonecode.config.UserDetailsImpl;
import com.example.clonecode.domain.Comment;
import com.example.clonecode.service.CommentService;
import com.example.clonecode.web.dto.CommentUploadDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class CommentApiController {

    private final CommentService commentService;

    @PostMapping("/comment")
    public Comment addComment(@RequestBody CommentUploadDto commentUploadDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return commentService.addComment(commentUploadDto.getText(), commentUploadDto.getPostId(), userDetails.getUser().getId());
    }

    @DeleteMapping("/comment/{id}")
    public void deleteComment(@PathVariable long id) {
        commentService.deleteComment(id);
    }
}
