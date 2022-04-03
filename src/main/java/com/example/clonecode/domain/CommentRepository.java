package com.example.clonecode.domain;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query(value = "INSERT INTO comment(text, user_id, post_id) VALUES(:text, :userId, :postId)", nativeQuery = true)
    Comment saveComment(String text, long userId, long postId);
}
