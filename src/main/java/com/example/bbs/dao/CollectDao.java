package com.example.bbs.dao;

import com.example.bbs.entity.Collect;
import com.example.bbs.entity.Post;
import com.example.bbs.entity.User;


import java.util.List;

/**
 * (TCollect)表数据库访问层
 *
 * @author makejava
 * @since 2019-09-20 13:48:47
 */
public interface CollectDao {

    List<Post> selectCollectPostByUserId(Integer id, Integer start, Integer num);

    List<User> selectCollectUserByPostId(Integer id, Integer start, Integer num);

    Integer addCollect(Collect collect);

    Integer deleteCollect(Integer id);

    Integer deleteCollectByUserId(Integer id);

    Integer deleteAllCollectByPostId(Integer id);

    Integer deleteAllCollectByPlateId(Integer id);

    Integer deleteAllCollectBySectionId(Integer id);


    Integer deleteAllCollectByPostType(Integer type);

    Integer deleteAllCollectByPostUserId(Integer id);

    Integer selectAllCollectCountByUserId(Integer id);

    Integer selectAllCollectCountByPostId(Integer id);

    Collect selectCollectById(Integer id);

    Collect selectCollectUserIdAndPostId(Integer userId, Integer postId);

    Integer deleteCollectByUserIdAndPostId(Integer userId, Integer postId);

    Collect selectCollectByUserIdAndPostId(Integer userId, Integer postId);
}