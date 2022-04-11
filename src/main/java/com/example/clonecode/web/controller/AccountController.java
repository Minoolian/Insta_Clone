package com.example.clonecode.web.controller;

import com.example.clonecode.service.UserService;
import com.example.clonecode.web.dto.UserLoginDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
public class AccountController {

    private final UserService userService;

    @PostMapping("/signup")
    public String signup(@Valid  UserLoginDto userLoginDto, BindingResult bindingResult) {
            if (userService.save(userLoginDto)) {
                return "redirect:/login";
            } else {
                return "redirect:/signup?error";
            }
    }
}

