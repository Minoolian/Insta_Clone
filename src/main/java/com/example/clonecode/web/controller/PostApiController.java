package com.example.clonecode.web.controller;

import com.example.clonecode.service.LikesService;
import com.example.clonecode.service.PostService;
import com.example.clonecode.web.dto.PostInfoDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class PostApiController {

    private final PostService postService;
    private final LikesService likesService;

    @GetMapping("/post/{postId}")
    public PostInfoDto getPostInfo(@PathVariable long postId, Authentication authentication) {
        return postService.getPostInfoDto(postId, authentication.getName());
    }

    @PostMapping("/post/{postId}/likes")
    public void likes(@PathVariable long postId, Authentication authentication) {
        likesService.likes(postId, authentication.getName());
    }

    @DeleteMapping("/post/{postId}/unLikes")
    public void unLikes(@PathVariable long postId, Authentication authentication) {
        likesService.unLikes(postId, authentication.getName());
    }
}
