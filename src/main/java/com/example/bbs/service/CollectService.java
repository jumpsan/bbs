package com.example.bbs.service;

import com.example.bbs.entity.Collect;
import com.example.bbs.entity.Post;
import com.example.bbs.entity.User;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * (TCollect)表服务接口
 *
 * @author makejava
 * @since 2019-09-20 13:48:47
 */
@Transactional
public interface CollectService {

    List<Post> selectCollectPostByUserId(Integer id, Integer start, Integer num);

    List<User> selectCollectUserByPostId(Integer id, Integer start, Integer num);

    Integer addCollect(Collect collect);

    Integer deleteCollect(Integer id);

    Integer selectAllCollectCountByUserId(Integer id);

    Integer selectAllCollectCountByPostId(Integer id);

    Collect selectCollectById(Integer id);

    Integer deleteCollectByUserIdAndPostId(Integer userId, Integer postId);

    /**
     * 根据用户编号和帖子编号查询
     * @param userId
     * @param postId
     * @return
     */
    Collect selectCollectByUserIdAndPostId(Integer userId, Integer postId);
}