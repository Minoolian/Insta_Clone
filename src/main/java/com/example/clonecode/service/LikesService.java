package com.example.clonecode.service;

import com.example.clonecode.domain.LikesRepository;
import com.example.clonecode.domain.User;
import com.example.clonecode.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Service
public class LikesService {

    private final LikesRepository likesRepository;
    private final UserRepository userRepository;

    @Transactional
    public void likes(long postId, String loginEmail){
        User user = userRepository.findUserByEmail(loginEmail);
        likesRepository.likes(postId, user.getId());
    }

    @Transactional
    public void unLikes(long postId, String loginEmail){
        User user = userRepository.findUserByEmail(loginEmail);
        likesRepository.unLikes(postId, user.getId());
    }
}
