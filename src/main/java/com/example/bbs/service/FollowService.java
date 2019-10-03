package com.example.bbs.service;

import com.example.bbs.entity.Follow;
import com.example.bbs.entity.User;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * (TFollowed)表服务接口
 *
 * @author makejava
 * @since 2019-09-20 13:59:33
 */
@Transactional
public interface FollowService {
    List<User> selectByFollowId(Integer follow_id, Integer start, Integer num);

    List<User> selectByFollowedId(Integer followed_id, Integer start, Integer num);

    Integer addFollow(Follow followed);

    boolean deleteFollow(Follow followed);

    boolean deleteByFollowId(Integer follow_id);

    boolean deleteByFollowedId(Integer followed_id);
}