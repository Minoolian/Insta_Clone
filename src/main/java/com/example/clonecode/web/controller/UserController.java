package com.example.clonecode.web.controller;

import com.example.clonecode.config.UserDetailsImpl;
import com.example.clonecode.service.UserService;
import com.example.clonecode.web.dto.UserProfileDto;
import com.example.clonecode.web.dto.UserUpdateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping({"/", "user/story"})
    public String story() { return "user/story"; }

    @GetMapping("/user/profile")
    public String profile(@RequestParam long id, @AuthenticationPrincipal UserDetailsImpl userDetails, Model model){
        UserProfileDto userProfileDto = userService.getUserProfileDto(id, userDetails.getUser().getId());
        model.addAttribute("userProfileDto", userProfileDto);

        return "user/profile";

    }

    @GetMapping("user/update")
    public String update() {
        return "user/update";
    }

    @PostMapping("user/update")
    public String updateUser(UserUpdateDto userUpdateDto, @RequestParam("profileImgUrl") MultipartFile multipartFile, RedirectAttributes redirectAttributes,
                             @AuthenticationPrincipal UserDetailsImpl userDetails){
        userService.update(userUpdateDto, multipartFile, userDetails);
        redirectAttributes.addAttribute("id", userUpdateDto.getId());
        return "redirect:/user/profile";
    }
}
