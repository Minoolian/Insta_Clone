package com.example.clonecode.web.controller;

import com.example.clonecode.service.UserService;
import com.example.clonecode.web.dto.UserLoginDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class AccountController {

    private final UserService userService;

    @PostMapping("/signup")
    public String signup(UserLoginDto userLoginDto) {
        if (userService.save(userLoginDto)) {
            return "redirect:/login";
        } else {
            return "redirect:/signup?error";
        }
    }
}
