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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<PostInfoDto> getPostInfo(@PathVariable long postId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return new ResponseEntity<>(postService.getPostInfoDto(postId, userDetails.getUser().getId()), HttpStatus.OK);
    }

    @PostMapping("/post/{postId}/likes")
    public ResponseEntity<String> likes(@PathVariable long postId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        likesService.likes(postId, userDetails.getUser().getId());
        return new ResponseEntity<>("좋아요 성공", HttpStatus.OK);
    }

    @DeleteMapping("/post/{postId}/likes")
    public ResponseEntity<String> unLikes(@PathVariable long postId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        likesService.unLikes(postId, userDetails.getUser().getId());
        return new ResponseEntity<>("좋아요 취소 성공", HttpStatus.OK);
    }

    @GetMapping("/post")
    public ResponseEntity<Page<Post>> mainStory(@AuthenticationPrincipal UserDetailsImpl userDetails, @PageableDefault(size = 3) Pageable pageable) {
        return new ResponseEntity<>(postService.mainStory(userDetails.getUser().getId(), pageable), HttpStatus.OK);
    }

    @GetMapping("/post/tag")
    public ResponseEntity<Page<Post>> searchTag(@RequestParam String tag, @AuthenticationPrincipal UserDetailsImpl userDetails, @PageableDefault(size = 3) Pageable pageable) {
        return new ResponseEntity<>(postService.searchResult(tag, userDetails.getUser().getId(), pageable), HttpStatus.OK);
    }

    @GetMapping("/post/likes")
    public ResponseEntity<Page<PostPreviewDto>> getLikesPost(@AuthenticationPrincipal UserDetailsImpl userDetails, @PageableDefault(size = 12) Pageable pageable) {
        return new ResponseEntity<>(postService.getLikesPost(userDetails.getUser().getId(), pageable), HttpStatus.OK);
    }

    @GetMapping("/post/popular")
    public ResponseEntity<List<PostPreviewDto>> getLikesPost(){
        return new ResponseEntity<>(postService.getPopularPost(), HttpStatus.OK);
    }
}
