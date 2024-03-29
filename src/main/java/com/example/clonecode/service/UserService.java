package com.example.clonecode.service;

import com.example.clonecode.config.UserDetailsImpl;
import com.example.clonecode.domain.FollowRepository;
import com.example.clonecode.domain.User;
import com.example.clonecode.domain.UserRepository;
import com.example.clonecode.handler.CustomValidationException;
import com.example.clonecode.web.dto.UserLoginDto;
import com.example.clonecode.web.dto.UserProfileDto;
import com.example.clonecode.web.dto.UserUpdateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final FollowRepository followRepository;

    @Transactional
    public boolean save(UserLoginDto userLoginDto){
        if(userRepository.findUserByEmail(userLoginDto.getEmail()) != null) throw new CustomValidationException("이미 존재하는 이메일입니다.");

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        userRepository.save(User.builder()
                .email(userLoginDto.getEmail())
                .password(encoder.encode(userLoginDto.getPassword()))
                .phone(userLoginDto.getPhone())
                .name(userLoginDto.getName())
                .title(null)
                .website(null)
                .profileImgUrl("default_profile.jpg")
                .build());

        return true;
    }

    @Value("${profileImg.path}")
    private String uploadFolder;

    @Transactional
    public void update(UserUpdateDto userUpdateDto, MultipartFile multipartFile, UserDetailsImpl userDetails) {
        User user = userRepository.findUserById(userUpdateDto.getId());
        BCryptPasswordEncoder encoder=new BCryptPasswordEncoder();

        String imageFileName = user.getId() + "_" + multipartFile.getOriginalFilename();
        Path imageFilePath = Paths.get(uploadFolder + imageFileName);

        if(multipartFile.getSize() != 0){
            try{
                if(user.getProfileImgUrl() != null){
                    File file = new File(uploadFolder + user.getProfileImgUrl());
                    file.delete();
                }
                Files.write(imageFilePath, multipartFile.getBytes());
            }catch (Exception e){
                e.printStackTrace();
            }

            user.updateProfileImgUrl(imageFileName);
        }

        user.update(
                encoder.encode(userUpdateDto.getPassword()),
                userUpdateDto.getPhone(),
                userUpdateDto.getName(),
                userUpdateDto.getTitle(),
                userUpdateDto.getWebsite()
        );

        userDetails.updateUser(user);
    }

    @Transactional
    public UserProfileDto getUserProfileDto(Long profileId, Long sessionId){

        UserProfileDto userProfileDto = new UserProfileDto();

        User user = userRepository.findUserById(profileId);
        userProfileDto.setUser(user);
        userProfileDto.setPostCount(user.getPostList().size());

        User loginUser = userRepository.findUserById(sessionId);
        userProfileDto.setLoginUser(loginUser.getId() == user.getId());

        userProfileDto.setFollow(followRepository.findFollowByFromUserIdAndToUserId(sessionId, profileId) != null);

        userProfileDto.setUserFollowerCount(followRepository.findFollowerCountById(profileId));
        userProfileDto.setUserFollowingCount(followRepository.findFollowingCountById(profileId));

        user.getPostList().forEach(post->{
            post.setLikesCount(post.getLikesList().size());
        });

        return userProfileDto;


    }
}
