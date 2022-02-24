package com.example.clonecode.web.controller;

import com.example.clonecode.service.PostService;
import com.example.clonecode.service.UserService;
import com.example.clonecode.web.dto.PostDto;
import com.example.clonecode.web.dto.PostUpdateDto;
import com.example.clonecode.web.dto.PostUploadDto;
import com.example.clonecode.web.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final UserService userService;

    @GetMapping("/post/upload")
    public String upload(Authentication authentication, Model model) {
        UserDto userDto = userService.getUserDtoByEmail(authentication.getName());
        model.addAttribute("userDto", userDto);
        return "post/upload";
    }

    @PostMapping("post")
    public String uploadPost(PostUploadDto postUploadDto, @RequestParam("uploadImgUrl") MultipartFile multipartFile, RedirectAttributes redirectAttributes, Authentication authentication) {
        long id = userService.getUserIdByEmail(authentication.getName());
        postService.save(postUploadDto, id, multipartFile);
        redirectAttributes.addAttribute("id", id);
        return "redirect:/user/profile";
    }

    @GetMapping("/post/update/{postId}")
    public String update(@PathVariable long postId, Model model, Authentication authentication) {
        PostDto postDto = postService.getPostDto(postId);
        model.addAttribute("postDto", postDto);
        return "post/update";
    }

    @PostMapping("/post/update")
    public String postUpdate(PostUpdateDto postUpdateDto, Authentication authentication, RedirectAttributes redirectAttributes) {
        long id = userService.getUserIdByEmail(authentication.getName());
        postService.update(postUpdateDto);
        redirectAttributes.addAttribute("id", id);
        return "redirect:/user/profile";
    }

    @PostMapping("/post/delete")
    public String delete(@RequestParam("postId") long postId, Authentication authentication, RedirectAttributes redirectAttributes) {
        long id = userService.getUserIdByEmail(authentication.getName());
        postService.delete(postId);
        redirectAttributes.addAttribute("id", id);
        return "redirect:/user/profile";
    }
}
