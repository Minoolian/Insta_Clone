package com.example.clonecode.config;

import com.example.clonecode.domain.User;
import com.example.clonecode.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class Oauth2DetailsService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        Map<String, Object> user_map = oAuth2User.getAttributes();
        String email = (String) user_map.get("email");
        String name = (String) user_map.get("name");
        String password = new BCryptPasswordEncoder().encode(UUID.randomUUID().toString());

        User check_user = userRepository.findUserByEmail(email);
        if (check_user == null) {
            User user = User.builder()
                    .email(email)
                    .password(password)
                    .phone(null)
                    .name(name)
                    .profileImgUrl("default_profile.jpg")
                    .build();
            return new UserDetailsImpl(userRepository.save(user), user_map);
        }else {
            return new UserDetailsImpl(check_user);
        }
    }
}
