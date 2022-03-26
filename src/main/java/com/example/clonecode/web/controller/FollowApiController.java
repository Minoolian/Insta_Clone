package com.example.clonecode.web.controller;

import com.example.clonecode.config.UserDetailsImpl;
import com.example.clonecode.domain.Follow;
import com.example.clonecode.domain.FollowRepository;
import com.example.clonecode.service.FollowService;
import com.example.clonecode.web.dto.FollowDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
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
        return new ResponseEntity<>("언팔 성공", HttpStatus.OK)
    }

    @GetMapping("/follow/{profileId}/follower")
    public List<FollowDto> getFollower(@PathVariable long profileId, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return followService.getFollower(profileId, userDetails.getUser().getId());
    }

    @GetMapping("/follow/{profileId}/following")
    public List<FollowDto> getFollowing(@PathVariable long profileId, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return followService.getFollowing(profileId, userDetails.getUser().getId());
    }
}
