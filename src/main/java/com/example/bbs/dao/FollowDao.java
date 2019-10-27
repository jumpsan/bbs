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

    Integer addFollow(Follow follow);

    Integer deleteFollow(Follow follow);

    Integer deleteByFollowId(Integer followId);

    Integer deleteByFollowedId(Integer followed_id);

    Integer selectAllCountByFollowId(Integer id);

    Integer selectAllCountByFollowedId(Integer id);

    Follow selectAllCountByFollowIdAndFollowedId(Integer followId, Integer followedId);

    /**
     * 根据用户编号删除
     * @param id
     * @return
     */
    Integer deleteFollowByUserId(Integer id);

    /**
     * 根据编号查询
     * @param id
     * @return
     */
    Follow selectById(Integer id);
}