package com.example.clonecode.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PostRepository extends JpaRepository<Post, Long> {

    Post findPostById(Long id);

    @Query(value = "SELECT * FROM post WHERE user_id IN (SELECT to_user_id FROM follow WHERE from_user_id = :sessionId) ORDER BY id DESC", nativeQuery = true)
    Page<Post> mainStory(long sessionId, Pageable pageable);
}
