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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
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

    @Transactional
    public void save(PostUploadDto postUploadDto, MultipartFile multipartFile, UserDetailsImpl userDetails) {
        UUID uuid = UUID.randomUUID();
        String imgFileName = uuid + "_" + multipartFile.getOriginalFilename();

        Path imageFilePath = Paths.get(uploadUrl + imgFileName);
           if(multipartFile.getSize()!=0) {
            try {
                Files.write(imageFilePath, multipartFile.getBytes());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else {
            imgFileName = "default_post_img.jpg";
        }

        postRepository.save(Post.builder()
                        .postImgUrl(imgFileName)
                        .tag(postUploadDto.getTag())
                        .text(postUploadDto.getText())
                        .user(userDetails.getUser())
                        .likesCount(0)
                        .build());
    }

    @Transactional
    public void update(PostUpdateDto postUpdateDto) {
        Post post = postRepository.findPostById(postUpdateDto.getId());
        post.update(postUpdateDto.getTag(), postUpdateDto.getText());
    }

    @Transactional
    public PostInfoDto getPostInfoDto(long postId, long sessionId){
        PostInfoDto postInfoDto = new PostInfoDto();
        postInfoDto.setId(postId);

        Post post = postRepository.findById(postId).get();
        postInfoDto.setTag(post.getTag());
        postInfoDto.setText(post.getText());
        postInfoDto.setPostImgUrl(post.getPostImgUrl());
        postInfoDto.setCreatedate(post.getCreateDate());

        postInfoDto.setLikesCount(post.getLikesList().size());
        post.getLikesList().forEach(likes->{
            if(likes.getUser().getId() == sessionId) postInfoDto.setLikeState(true);
        });
        postInfoDto.setCommentList(post.getCommentList());

        User user = userRepository.findById(post.getUser().getId()).get();

        postInfoDto.setPostUploader(user);
        postInfoDto.setUploader(sessionId == post.getUser().getId());

        return postInfoDto;
    }

    @Transactional
    public PostDto getPostDto(long postId) {
        Post post = postRepository.findById(postId).get();

        return PostDto.builder()
                .id(postId)
                .text(post.getText())
                .postImgUrl(post.getPostImgUrl())
                .tag(post.getTag())
                .build();

    }

    @Transactional
    public void delete(long postId) {
        Post post = postRepository.findPostById(postId);

        File file = new File(uploadUrl + post.getPostImgUrl());
        file.delete();

        postRepository.deleteById(postId);
    }

    @Transactional
    public Page<Post> mainStory(long sessionId, Pageable pageable) {
        Page<Post> postList = postRepository.mainStory(sessionId, pageable);

        postList.forEach(post -> {
            post.setLikesCount(post.getLikesList().size());
            post.getLikesList().forEach(likes -> {
                if(likes.getUser().getId() == sessionId) post.updateLikesState(true);
            });
        });

        return postList;
    }

    @Transactional
    public Page<Post> searchResult(String tag, long sessionId, Pageable pageable) {
        Page<Post> postList = postRepository.searchResult(tag, pageable);

        postList.forEach(post -> {
            post.setLikesCount(post.getLikesList().size());
            post.getLikesList().forEach(likes -> {
                post.updateLikesState(likes.getUser().getId() == sessionId);
            });
        });

        return postList;
    }
}
