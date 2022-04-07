package com.example.clonecode.web.controller;

import com.example.clonecode.config.UserDetailsImpl;
import com.example.clonecode.domain.Post;
import com.example.clonecode.service.LikesService;
import com.example.clonecode.service.PostService;
import com.example.clonecode.web.dto.PostInfoDto;
import com.example.clonecode.web.dto.PostPreviewDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class PostApiController {

    private final PostService postService;
    private final LikesService likesService;

    @GetMapping("/post/{postId}")
    public PostInfoDto getPostInfo(@PathVariable long postId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return postService.getPostInfoDto(postId, userDetails.getUser().getId());
    }

    @PostMapping("/post/{postId}/likes")
    public void likes(@PathVariable long postId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        likesService.likes(postId, userDetails.getUser().getId());
    }

    @DeleteMapping("/post/{postId}/unLikes")
    public void unLikes(@PathVariable long postId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        likesService.unLikes(postId, userDetails.getUser().getId());
    }

    @GetMapping("/post/story")
    public Page<Post> mainStory(@AuthenticationPrincipal UserDetailsImpl userDetails, @PageableDefault(size = 3) Pageable pageable) {
        return postService.mainStory(userDetails.getUser().getId(), pageable);
    }

    @GetMapping("/post/search")
    public Page<Post> searchTag(@RequestParam String tag, @AuthenticationPrincipal UserDetailsImpl userDetails, @PageableDefault(size = 3) Pageable pageable) {
        return postService.searchResult(tag, userDetails.getUser().getId(), pageable);
    }

    @GetMapping("/post/likes")
    public Page<PostPreviewDto> getLikesPost(@AuthenticationPrincipal UserDetailsImpl userDetails, @PageableDefault(size = 12) Pageable pageable) {
        return postService.getLikesPost(userDetails.getUser().getId(), pageable);
    }

    @GetMapping("/post/popular")
    public List<PostPreviewDto> getLikesPost(){
        return postService.getPopularPost();
    }
}
