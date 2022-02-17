package com.example.clonecode.service;

import com.example.clonecode.domain.Follow;
import com.example.clonecode.domain.FollowRepository;
import com.example.clonecode.domain.User;
import com.example.clonecode.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class FollowService {

    private final FollowRepository followRepository;
    private final UserRepository userRepository;

    @Transactional
    public long getFollowIdByFromEmailToId(String email, Long toId){
        User fromUser = userRepository.findUserByEmail(email);
        User toUser = userRepository.findUserById(toId);

        Follow follow = followRepository.findFollowByFromUserAndToUser(fromUser, toUser);

        if(follow != null) return follow.getId();
        else return -1L;
    }

    @Transactional
    public Follow save(String email, Long toUserId){
        User fromUser = userRepository.findUserByEmail(email);
        User toUser = userRepository.findUserById(toUserId);

        return followRepository.save(Follow.builder()
                .fromUser(fromUser)
                .toUser(toUser)
                .build());
    }
}
