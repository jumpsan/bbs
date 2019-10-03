package com.example.bbs.dao;

import com.example.bbs.entity.Follow;
import com.example.bbs.entity.User;

import java.util.List;

/**
 * (TFollowed)表数据库访问层
 *
 * @author makejava
 * @since 2019-09-20 13:59:32
 */
public interface FollowDao {

    List<User> selectByFollowId(Integer follow_id, Integer start, Integer num);

    List<User> selectByFollowedId(Integer followed_id, Integer start, Integer num);

    Follow addFollow(Follow followed);

    Integer deleteFollow(Follow followed);

    Integer deleteByFollowId(Integer follow_id);

    Integer deleteByFollowedId(Integer followed_id);
}