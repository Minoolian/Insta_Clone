package com.example.clonecode.web.controller;

import com.example.clonecode.domain.User;
import com.example.clonecode.domain.UserRepository;
import com.example.clonecode.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class AccountControllerTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;

    private User newUser;
    private String username = "test@test";
    private String password = "12345";

    @BeforeEach
    public void setup(){
        mvc= MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        newUser = User.builder()
                .email(username)
                .password(encoder.encode(password))
                .phone("")
                .name("")
                .title(null)
                .profileImgUrl(null)
                .website(null)
                .build();
    }


    @Test
    @Transactional
    public void login_success() throws Exception{
        //given
        userRepository.save(newUser);

        //when then
        mvc.perform(formLogin("/loginForm").user(username).password(password))
                .andDo(print())
                .andExpect(redirectedUrl("/user/story"));
    }

}