package com.example.clonecode.service;

import com.example.clonecode.domain.Follow;
import com.example.clonecode.domain.FollowRepository;
import com.example.clonecode.domain.User;
import com.example.clonecode.domain.UserRepository;
import com.example.clonecode.web.dto.FollowDto;
import lombok.RequiredArgsConstructor;
import org.qlrm.mapper.JpaResultMapper;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FollowService {

    private final FollowRepository followRepository;
    private final UserRepository userRepository;

    private final EntityManager em;



    @Transactional
    public long getFollowIdByFromEmailToId(String email, Long toId){
        User fromUser = userRepository.findUserByEmail(email);
        User toUser = userRepository.findUserById(toId);

        Follow follow = followRepository.findFollowByFromUserAndToUser(fromUser, toUser);

        if(follow != null) return follow.getId();
        else return -1L;
    }

    @Transactional
    public Follow save(String email, Long toUserId){
        User fromUser = userRepository.findUserByEmail(email);
        User toUser = userRepository.findUserById(toUserId);

        return followRepository.save(Follow.builder()
                .fromUser(fromUser)
                .toUser(toUser)
                .build());
    }

    @Transactional
    public List<FollowDto> getFollowDtoListByProfileIdAboutFollower(long profileId, String loginEmail){
        long loginId = userRepository.findUserByEmail(loginEmail).getId();

        StringBuffer sb = new StringBuffer();
        sb.append("SELECT u.id, u.name, u.profile_img_url, ");
        sb.append("if ((SELECT 1 FROM follow WHERE from_user_id = ? and to_user_id = u.id), TRUE, FALSE) AS followState, ");
        sb.append("if ((?=u.id), TRUE, FALSE) AS loginUser ");
        sb.append("FROM user u, follow f ");
        sb.append("WHERE u.id = f.from_user_id AND f.to_user_id = ?");

        Query query = em.createNativeQuery(sb.toString())
                .setParameter(1, loginId)
                .setParameter(2, loginId)
                .setParameter(3, profileId);

        JpaResultMapper result = new JpaResultMapper();
        List<FollowDto> followDtoList = result.list(query, FollowDto.class);
        return followDtoList;

    }

    @Transactional
    public List<FollowDto> getFollowDtoListByProfileIdAboutFollowing(long profileId, String loginEmail){
        long loginId = userRepository.findUserByEmail(loginEmail).getId();

        StringBuffer sb = new StringBuffer();
        sb.append("SELECT u.id, u.name, u.profile_img_url, ");
        sb.append("if ((SELECT 1 FROM follow WHERE from_user_id = ? AND to_user_id = u.id), TRUE, FALSE) AS followState, ");
        sb.append("if ((?=u.id), TRUE, FALSE) AS loginUser ");
        sb.append("FROM user u, follow f ");
        sb.append("WHERE u.id = f.to_user_id AND f.from_user_id = ?");

        Query query = em.createNativeQuery(sb.toString())
                .setParameter(1, loginId)
                .setParameter(2, loginId)
                .setParameter(3, profileId);

        JpaResultMapper result = new JpaResultMapper();
        List<FollowDto> followDtoList = result.list(query, FollowDto.class);
        return followDtoList;

    }
}
