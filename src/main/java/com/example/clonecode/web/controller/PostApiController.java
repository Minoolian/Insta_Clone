package com.example.clonecode.web.controller;

import com.example.clonecode.service.PostService;
import com.example.clonecode.web.dto.PostInfoDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class PostApiController {

    private final PostService postService;

    @GetMapping("/post/{postId}")
    public PostInfoDto getPostInfo(@PathVariable long postId, Authentication authentication) {
        return postService.getPostInfoDto(postId, authentication.getName());
    }
}
