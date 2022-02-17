package com.example.clonecode.web.controller;

import com.example.clonecode.domain.Follow;
import com.example.clonecode.domain.FollowRepository;
import com.example.clonecode.service.FollowService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class FollowApiController {

    private final FollowService followService;
    private final FollowRepository followRepository;

    @PostMapping("/follow/{toUserId}")
    public Follow followUser(@PathVariable long toUserId, Authentication authentication){
        return followService.save(authentication.getName(), toUserId);
    }

    @DeleteMapping("/follow/{toUserId}")
    public void unFollowUser(@PathVariable long toUserId, Authentication authentication){
        Long id = followService.getFollowIdByFromEmailToId(authentication.getName(), toUserId);
        followRepository.deleteById(id);
    }
}
