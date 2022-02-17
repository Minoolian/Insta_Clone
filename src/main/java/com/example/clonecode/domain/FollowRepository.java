package com.example.clonecode.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface FollowRepository extends JpaRepository<Follow, Long> {

    Follow findFollowByFromUserAndToUser(User fromUser, User toUser);
}
