package com.example.clonecode.web.controller;

import com.example.clonecode.config.UserDetailsImpl;
import com.example.clonecode.domain.Comment;
import com.example.clonecode.service.CommentService;
import com.example.clonecode.web.dto.CommentUploadDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class CommentApiController {

    private final CommentService commentService;

    @PostMapping("/comment")
    public ResponseEntity<Comment> addComment(@Valid @RequestBody CommentUploadDto commentUploadDto, BindingResult bindingResult, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return new ResponseEntity<>(commentService.addComment(commentUploadDto.getText(), commentUploadDto.getPostId(), userDetails.getUser().getId()), HttpStatus.OK);
    }

    @DeleteMapping("/comment/{id}")
    public ResponseEntity<String> deleteComment(@PathVariable long id) {
        commentService.deleteComment(id);
        return new ResponseEntity<>("댓글 삭제 성공", HttpStatus.OK);
    }
}
