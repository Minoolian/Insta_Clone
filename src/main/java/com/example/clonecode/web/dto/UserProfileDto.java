package com.example.clonecode.web.dto;

import com.example.clonecode.domain.User;
import lombok.*;

@NoArgsConstructor
@Getter
@Setter
public class UserProfileDto {

    private boolean loginUser;
    private boolean follow;
    private User user;
    private int postCount;
    private int userFollowerCount;
    private int userFollowingCount;

}
