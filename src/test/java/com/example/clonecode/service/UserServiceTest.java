package com.example.clonecode.service;

import com.example.clonecode.domain.User;
import com.example.clonecode.domain.UserRepository;
import com.example.clonecode.web.dto.UserLoginDto;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(SpringExtension.class)
class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    private User user;

    @BeforeEach
    public void setUp() {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        user = User.builder()
                .email("test@test")
                .name("test")
                .password(encoder.encode("test1234!"))
                .phone("123123")
                .title(null)
                .website(null)
                .profileImgUrl(null)
                .build();
    }

    @Test
    public void save_success(){
        //given
        UserLoginDto userLoginDto = UserLoginDto.builder()
                .email(user.getEmail())
                .password(user.getPassword())
                .name(user.getName())
                .phone(user.getPhone())
                .build();

        given(userRepository.findUserByEmail(any())).willReturn(null);
        given(userRepository.save(any())).willReturn(user);

        //when
        boolean save = userService.save(userLoginDto);

        //then
        assertThat(save, is(true));

    }

}