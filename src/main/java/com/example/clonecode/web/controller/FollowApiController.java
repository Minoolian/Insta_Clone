package com.example.clonecode.web.controller;

import com.example.clonecode.config.UserDetailsImpl;
import com.example.clonecode.service.FollowService;
import com.example.clonecode.web.dto.FollowDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class FollowApiController {

    private final FollowService followService;

    @PostMapping("/follow/{toUserId}")
    public ResponseEntity<?> followUser(@PathVariable long toUserId, @AuthenticationPrincipal UserDetailsImpl userDetails){
        followService.follow(userDetails.getUser().getId(), toUserId);
        return new ResponseEntity<>("팔로우 성공", HttpStatus.OK);
    }

    @DeleteMapping("/follow/{toUserId}")
    public ResponseEntity<?> unFollowUser(@PathVariable long toUserId, @AuthenticationPrincipal UserDetailsImpl userDetails){
        followService.unFollow(userDetails.getUser().getId(), toUserId);
        return new ResponseEntity<>("언팔 성공", HttpStatus.OK);
    }

    @GetMapping("/follow/{profileId}/follower")
    public ResponseEntity<List<FollowDto>> getFollower(@PathVariable long profileId, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return new ResponseEntity<>(followService.getFollower(profileId, userDetails.getUser().getId()), HttpStatus.OK);
    }

    @GetMapping("/follow/{profileId}/following")
    public ResponseEntity<List<FollowDto>> getFollowing(@PathVariable long profileId, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return new ResponseEntity<>(followService.getFollowing(profileId, userDetails.getUser().getId()), HttpStatus.OK);
    }
}
