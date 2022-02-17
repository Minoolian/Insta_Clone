package com.example.clonecode.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@DataJpaTest
@ExtendWith(SpringExtension.class)
class FollowRepositoryTest {

    @Autowired
    private FollowRepository followRepository;

    @Autowired
    private UserRepository userRepository;

    private Follow follow;
    private User toUser;
    private User fromUser;

    @BeforeEach
    public void setup(){
        toUser = User.builder().email("test@test").name("test").password("asd")
                .phone("123123").title(null).website(null).profileImgUrl(null).build();

        fromUser = User.builder().email("test1@test").name("test1").password("asd")
                .phone("1231233").title(null).website(null).profileImgUrl(null).build();

        follow= Follow.builder()
                .fromUser(fromUser)
                .toUser(toUser)
                .build();
    }

    @Test
    public void save(){
        //given
        followRepository.save(follow);

        //then



    }

}