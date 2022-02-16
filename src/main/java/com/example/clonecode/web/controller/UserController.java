package com.example.clonecode.web.controller;

import com.example.clonecode.service.UserService;
import com.example.clonecode.web.dto.UserUpdateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("user/update")
    public String update() {
        return "user/update";
    }

    @PostMapping("user/update")
    public String updateUser(UserUpdateDto userUpdateDto, RedirectAttributes redirectAttributes){
        userService.update(userUpdateDto);
        redirectAttributes.addAttribute("id", userUpdateDto.getId());
        return "redirect:/user/profile";
    }
}