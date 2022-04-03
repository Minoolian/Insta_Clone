package com.example.clonecode.service;

import com.example.clonecode.domain.Comment;
import com.example.clonecode.domain.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Service
public class CommentService {

    private final CommentRepository commentRepository;

    @Transactional
    public Comment addComment(String text, long postId, long sessionId) {
        return commentRepository.saveComment(text, sessionId, postId);
    }

    @Transactional
    public void deleteComment(long id) {
        commentRepository.deleteById(id);
    }
}
