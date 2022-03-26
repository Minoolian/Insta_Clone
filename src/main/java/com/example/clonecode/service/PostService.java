package com.example.clonecode.service;

import com.example.clonecode.config.UserDetailsImpl;
import com.example.clonecode.domain.Post;
import com.example.clonecode.domain.PostRepository;
import com.example.clonecode.domain.User;
import com.example.clonecode.domain.UserRepository;
import com.example.clonecode.web.dto.PostDto;
import com.example.clonecode.web.dto.PostInfoDto;
import com.example.clonecode.web.dto.PostUpdateDto;
import com.example.clonecode.web.dto.PostUploadDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Value("${post.path}")
    private String uploadUrl;

    public void save(PostUploadDto postUploadDto, MultipartFile multipartFile, UserDetailsImpl userDetails) {
        UUID uuid = UUID.randomUUID();
        String imgFileName = uuid + "_" + multipartFile.getOriginalFilename();

        Path imageFilePath = Paths.get(uploadUrl + imgFileName);
        try {
            Files.write(imageFilePath, multipartFile.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }

        postRepository.save(Post.builder()
                        .postImgUrl(imgFileName)
                        .tag(postUploadDto.getTag())
                        .text(postUploadDto.getText())
                        .user(userDetails.getUser())
                        .build());
    }

    public void update(PostUpdateDto postUpdateDto) {
        Post post = postRepository.findPostById(postUpdateDto.getId());
        post.update(postUpdateDto.getTag(), postUpdateDto.getText());
    }

    public PostInfoDto getPostInfoDto(long postId, String email){
        PostInfoDto postInfoDto = new PostInfoDto();
        postInfoDto.setId(postId);

        Post post = postRepository.findById(postId).get();
        postInfoDto.setTag(post.getTag());
        postInfoDto.setText(post.getText());
        postInfoDto.setPostImgUrl(post.getPostImgUrl());
        postInfoDto.setCreatedate(post.getCreateDate());

        User user = userRepository.findUserByEmail(email);
        postInfoDto.setPostUploader(user);

        return postInfoDto;
    }

    public PostDto getPostDto(long postId) {
        Post post = postRepository.findById(postId).get();

        return PostDto.builder()
                .id(post.getId())
                .text(post.getText())
                .postImgUrl(post.getPostImgUrl())
                .tag(post.getTag())
                .build();

    }

    public void delete(long postId) {
        Post post = postRepository.findPostById(postId);

        File file = new File(uploadUrl + post.getPostImgUrl());
        file.delete();

        postRepository.deleteById(postId);
    }
}
