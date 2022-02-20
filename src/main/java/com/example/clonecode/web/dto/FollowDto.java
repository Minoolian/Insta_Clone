package com.example.clonecode.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class FollowDto {

    private long id;
    private String name;
    private String profileImgUrl;
    private int followState;
    private int loginUser;

    public FollowDto(BigInteger id, String name, String profileImgUrl, int followState, int loginUser) {
        this.id = id.longValue();
        this.name = name;
        this.profileImgUrl = profileImgUrl;
        this.followState = followState;
        this.loginUser = loginUser;
    }
}
