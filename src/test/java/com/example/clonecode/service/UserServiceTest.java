package com.example.clonecode.service;

import com.example.clonecode.config.UserDetailsImpl;
import com.example.clonecode.domain.FollowRepository;
import com.example.clonecode.domain.User;
import com.example.clonecode.domain.UserRepository;
import com.example.clonecode.handler.CustomValidationException;
import com.example.clonecode.web.dto.UserLoginDto;
import com.example.clonecode.web.dto.UserProfileDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.multipart.MultipartFile;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;

@ExtendWith(SpringExtension.class)
class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private FollowRepository followRepository;

    @Mock
    private User mock_user;

    @Mock
    private UserDetailsImpl userDetails;

    @Mock
    private MultipartFile multipartFile;

    private User user;

    @BeforeEach
    public void setUp() {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        user = User.builder()
                .email("test@test")
                .name("test")
                .password(encoder.encode("test1234!"))
                .phone("123123")
                .title(null)
                .website(null)
                .profileImgUrl(null)
                .build();
    }

    public UserLoginDto make_loginDto(){
        UserLoginDto userLoginDto = UserLoginDto.builder()
                .email(user.getEmail())
                .password(user.getPassword())
                .name(user.getName())
                .phone(user.getPhone())
                .build();

        return userLoginDto;
    }

    @Test
    public void save_success(){
        //given
        UserLoginDto userLoginDto = make_loginDto();

        given(userRepository.findUserByEmail(any())).willReturn(null);
        given(userRepository.save(any())).willReturn(user);

        //when
        boolean save = userService.save(userLoginDto);

        //then
        assertThat(save, is(true));
    }

    @Test
    public void save_fail(){
        //given
        UserLoginDto userLoginDto = make_loginDto();

        given(userRepository.findUserByEmail(any())).willReturn(user);

        //when
        assertThrows(CustomValidationException.class, ()->{
            userService.save(userLoginDto);
        });
    }

    @Test
    public void getUserProfileDto_success(){
        //given
        given(userRepository.findUserById(any())).willReturn(mock_user);
        given(followRepository.findFollowByFromUserIdAndToUserId(anyLong(), anyLong())).willReturn(null);
        given(followRepository.findFollowerCountById(anyLong())).willReturn(0);
        given(followRepository.findFollowingCountById(anyLong())).willReturn(0);

        //when
        UserProfileDto userProfileDto = userService.getUserProfileDto(user.getId(), user.getId());

        //then
        assertThat(userProfileDto.getUser(),is(mock_user));
        assertThat(userProfileDto.getUserFollowerCount(),is(0));
        assertThat(userProfileDto.getUserFollowingCount(),is(0));
        assertThat(userProfileDto.isLoginUser(), is(true));
        assertThat(userProfileDto.isFollow(), is(false));
    }

}