package com.example.clonecode.web.controller;

import com.example.clonecode.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(AccountController.class)
class AccountControllerMvcTest {

    @Autowired
    MockMvc mvc;

    @MockBean
    private UserService userService;

    @Test
    public void signup_success() throws Exception{
        //given
        given(userService.save(any())).willReturn(true);

        //when then
        mvc.perform(post("/signup")
                .param("email","test@test")
                .param("password","2321123")
                .param("phone","01010101010")
                .param("name","minoo"))
                .andExpect(status().isOk())
                .andDo(print());

    }

}