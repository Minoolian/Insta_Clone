package com.example.clonecode.web.controller;

import com.example.clonecode.config.UserDetailsImpl;
import com.example.clonecode.service.PostService;
import com.example.clonecode.service.UserService;
import com.example.clonecode.web.dto.PostDto;
import com.example.clonecode.web.dto.PostUpdateDto;
import com.example.clonecode.web.dto.PostUploadDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
    public String upload() {
        return "post/upload";
    }

    @PostMapping("post")
    public String uploadPost(PostUploadDto postUploadDto, @RequestParam("uploadImgUrl") MultipartFile multipartFile, RedirectAttributes redirectAttributes, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        postService.save(postUploadDto, multipartFile, userDetails);
        redirectAttributes.addAttribute("id", userDetails.getUser().getId());
        return "redirect:/user/profile";
    }

    @GetMapping("/post/update/{postId}")
    public String update(@PathVariable long postId, Model model) {
        PostDto postDto = postService.getPostDto(postId);
        model.addAttribute("postDto", postDto);
        return "post/update";
    }

    @PostMapping("/post/update")
    public String postUpdate(PostUpdateDto postUpdateDto, @AuthenticationPrincipal UserDetailsImpl userDetails, RedirectAttributes redirectAttributes) {
        postService.update(postUpdateDto);
        redirectAttributes.addAttribute("id", userDetails.getUser().getId());
        return "redirect:/user/profile";
    }

    @PostMapping("/post/delete")
    public String delete(@RequestParam("postId") long postId, @AuthenticationPrincipal UserDetailsImpl userDetails, RedirectAttributes redirectAttributes) {
        postService.delete(postId);
        redirectAttributes.addAttribute("id", userDetails.getUser().getId());
        return "redirect:/user/profile";
    }

    @GetMapping("/post/search")
    public String search(@RequestParam("tag") String tag, Model model) {
        model.addAttribute("tag", tag);
        return "post/search";
    }

    @PostMapping("/post/searchForm")
    public String searchForm(String tag, RedirectAttributes redirectAttributes) {
        redirectAttributes.addAttribute("tag", tag);
        return "redirect:/post/search";
    }
}
