package com.example.clonecode.service;

import com.example.clonecode.domain.LikesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Service
public class LikesService {

    private final LikesRepository likesRepository;

    @Transactional
    public void likes(long postId, long sessionId){
        likesRepository.likes(postId, sessionId);
    }

    @Transactional
    public void unLikes(long postId, long sessionId){
        likesRepository.unLikes(postId, sessionId);
    }
}
