package com.example.clonecode.service;

import com.example.clonecode.domain.User;
import com.example.clonecode.domain.UserRepository;
import com.example.clonecode.web.dto.UserDto;
import com.example.clonecode.web.dto.UserLoginDto;
import com.example.clonecode.web.dto.UserUpdateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findUserByEmail(username);

        if(user==null) return null;
        return new UserDetailsImpl(user);
    }

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

    @Transactional
    public void update(UserUpdateDto userUpdateDto) {
        User user = userRepository.findUserById(userUpdateDto.getId());
        BCryptPasswordEncoder encoder=new BCryptPasswordEncoder();
        user.update(
                encoder.encode(userUpdateDto.getPassword()),
                userUpdateDto.getPhone(),
                userUpdateDto.getName(),
                userUpdateDto.getTitle(),
                userUpdateDto.getWebsite(),
                userUpdateDto.getProfileImgUrl()
        );
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
}
