package com.example.bbs.service;

import com.example.bbs.entity.Post;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * (TPost)表服务接口
 *
 * @author makejava
 * @since 2019-09-20 14:00:31
 */
@Transactional
public interface PostService {
    Post selectPostById(Integer id);


    Integer addImagePost(Post post);

    Integer addWordPost(Post post);

    Integer addVideoPost(Post post);

    Integer deletePostById(Integer id);

    Integer deletePostByUserId(Integer id);

    Integer deletePostBySectionId(Integer id);

    Integer deletePostByType(Integer type);

    Integer updatePost(Post post);

    List<Post> selectPostBySectionId(Integer id, Integer start, Integer num);

    List<Post> selectPostByUserId(Integer id, Integer start, Integer num);

    List<Post> selectPostByType(Integer type, Integer start, Integer num);

    List<Post> selectPostByTitle(String title, Integer start, Integer num);

    Integer selectAllPostBySectionId(Integer id);

    Integer selectAllPostByUserId(Integer id);

    Integer selectAllPostByType(Integer type);

    Integer selectAllPostByTitle(String title);



}