package com.example.clonecode.service;

import com.example.clonecode.config.UserDetailsImpl;
import com.example.clonecode.domain.Post;
import com.example.clonecode.domain.PostRepository;
import com.example.clonecode.domain.User;
import com.example.clonecode.domain.UserRepository;
import com.example.clonecode.web.dto.*;
import lombok.RequiredArgsConstructor;
import org.qlrm.mapper.JpaResultMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    private final EntityManager em;

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

    @Transactional
    public Page<PostPreviewDto> getLikesPost(long sessionId, Pageable pageable) {
        StringBuffer sb = new StringBuffer();
        sb.append("SELECT p.id, p.post_img_url, COUNT(p.id) as likesCount ");
        sb.append("FROM likes l, post p ");
        sb.append("WHERE l.post_id = p.id ");
        sb.append("AND p.id IN (SELECT p.id FROM likes l, post p WHERE l.user_id = ? AND p.id = l.post_id) ");
        sb.append("GROUP BY p.id ");
        sb.append("ORDER BY p.id");

        Query query = em.createNativeQuery(sb.toString()).setParameter(1, sessionId);

        JpaResultMapper result= new JpaResultMapper();
        List<PostPreviewDto> postLikesList = result.list(query, PostPreviewDto.class);

        int start = (int) pageable.getOffset();
        int end = (start + pageable.getPageSize()) > postLikesList.size() ? postLikesList.size() : (start + pageable.getPageSize());

        if(start > postLikesList.size()) return new PageImpl<PostPreviewDto>(postLikesList.subList(0,0), pageable, 0);

        Page<PostPreviewDto> postLikesPage = new PageImpl<>(postLikesList.subList(start, end), pageable, postLikesList.size());
        return postLikesPage;
    }

    @Transactional
    public List<PostPreviewDto> getPopularPost() {
        StringBuffer sb = new StringBuffer();
        sb.append("SELECT p.id, p.post_img_url, COUNT(p.id) as likesCount ");
        sb.append("FROM likes l, post p ");
        sb.append("WHERE l.post_id = p.id ");
        sb.append("AND p.id IN (SELECT p.id FROM likes l, post p WHERE p.id = l.post_id) ");
        sb.append("GROUP BY p.id ");
        sb.append("ORDER BY likesCount DESC, p.id ");
        sb.append("LIMIT 12 ");

        Query query = em.createNativeQuery(sb.toString());

        JpaResultMapper result= new JpaResultMapper();
        List<PostPreviewDto> postPreviewDtoList = result.list(query, PostPreviewDto.class);

        return postPreviewDtoList;
    }
}
