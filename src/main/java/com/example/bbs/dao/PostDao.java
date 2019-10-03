package com.example.bbs.dao;

import com.example.bbs.entity.Post;
import com.example.bbs.entity.Section;

import java.util.List;

/**
 * (TPost)表数据库访问层
 *
 * @author makejava
 * @since 2019-09-20 14:00:31
 */
public interface PostDao {
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

    Integer deletePostByPlateId(Integer id);

    Integer selectAllPostCountBySectionId(Integer id);

    Integer selectAllPostCountByUserId(Integer id);

    Integer selectAllPostCountByType(Integer type);

    Integer selectAllPostCountByTitle(String title);


    List<Integer> selectAllPostByUserId(Integer id);
}