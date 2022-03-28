package com.example.clonecode.service;

import com.example.clonecode.config.UserDetailsImpl;
import com.example.clonecode.domain.FollowRepository;
import com.example.clonecode.domain.User;
import com.example.clonecode.domain.UserRepository;
import com.example.clonecode.web.dto.UserDto;
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

    public boolean save(UserLoginDto userLoginDto){
        if(userRepository.findUserByEmail(userLoginDto.getEmail()) != null) return false;

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        userRepository.save(User.builder()
                .email(userLoginDto.getEmail())
                .password(encoder.encode(userLoginDto.getPassword()))
                .phone(userLoginDto.getPhone())
                .name(userLoginDto.getName())
                .title(null)
                .website(null)
                .profileImgUrl("/img/default_profile.jpg")
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

            user.setProfileImgUrl(imageFileName);
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

    public UserDto getUserDtoByEmail(String email){
        User user = userRepository.findUserByEmail(email);
        return UserDto.builder()
                .email(user.getEmail())
                .name(user.getName())
                .phone(user.getPhone())
                .title(user.getTitle())
                .id(user.getId())
                .profileImgUrl(user.getProfileImgUrl())
                .website(user.getWebsite())
                .build();
    }

    public Long getUserIdByEmail(String email) {
        return userRepository.findUserByEmail(email).getId();
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
            post.setLikesCount(post.getLikeList().size());
        });

        return userProfileDto;


    }
}
