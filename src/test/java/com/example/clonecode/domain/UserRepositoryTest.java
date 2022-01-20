package com.example.clonecode.domain;

import org.hamcrest.core.Is;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ExtendWith(SpringExtension.class)
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    private User user;

    @BeforeEach
    public void setup(){
        user = User.builder().email("test@test").name("test").password("asd")
                .phone("123123").title(null).website(null).profileImgUrl(null).build();
    }

    @Test
    public void save(){
        userRepository.save(user);

        User email = userRepository.findUserByEmail("test@test");

        assertThat(email.getEmail(), is(user.getEmail()));
    }

}