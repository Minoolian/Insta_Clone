package com.example.clonecode.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@DataJpaTest
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
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
        toUser = User.builder().email("to@test").name("test").password("asd")
                .phone("123123").title(null).website(null).profileImgUrl(null).build();

        fromUser = User.builder().email("from@test").name("test1").password("asd")
                .phone("1231233").title(null).website(null).profileImgUrl(null).build();

        userRepository.save(toUser);
        userRepository.save(fromUser);

    }

    @Test
    public void save_success(){
        //given
        followRepository.save(Follow.builder().fromUser(fromUser).toUser(toUser).build());

        //when
        Follow result = followRepository.findFollowByFromUserIdAndToUserId(fromUser.getId(), toUser.getId());

        //then
        assertThat(result.getFromUser().getId(), is(fromUser.getId()));

    }

}