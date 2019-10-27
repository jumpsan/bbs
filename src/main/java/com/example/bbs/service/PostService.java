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


    Integer addPost(Post post);


    Integer deletePostById(Integer id);

//    Integer deletePostByUserId(Integer id);
//
//    Integer deletePostBySectionId(Integer id);
//
//    Integer deletePostByType(Integer type);

    Integer updatePost(Post post);

    List<Post> selectPostBySectionId(Integer id, Integer start, Integer num);

    List<Post> selectPostByUserId(Integer id, Integer start, Integer num);

    List<Post> selectPostByType(Integer type, Integer start, Integer num);

    List<Post> selectPostByTitle(String title, Integer start, Integer num);

    Integer selectAllPostBySectionId(Integer id);

    Integer selectAllPostByUserId(Integer id);

    Integer selectAllPostByType(Integer type);

    Integer selectAllPostByTitle(String title);

    /**
     * 分区下的审核帖子
     * @param sectionId
     * @param start
     * @param size
     * @return
     */
    List<Post> selectUncheckPostBySectionId(Integer sectionId, Integer start, Integer size);
    /**
     * 分区下的审核帖子数量
     * @param sectionId
     * @return
     */
    Integer selectUncheckPostCountBySectionId(Integer sectionId);

    Post selectPostByIdForUser(Integer id);

    /**
     *
     * @param plateName 板块名
     * @param sectionName 分区名
     * @return 帖子
     */
    List<Post> selectPostByPlateNameAndSectionName(String plateName, String sectionName,Integer start,Integer size);
    /**
     *
     * @param plateName 板块名
     * @param sectionName 分区名
     * @return 帖子总数
     */
    Integer selectPostCountByPlateNameAndSectionName(String plateName, String sectionName);

    /**
     * 置顶帖子
     * @param plateId
     * @return
     */
    List<Post> selectTopPostByPlateId(Integer plateId);
    /**
     * 查找公告帖子
     * @param plateId
     * @return
     */
    List<Post> selectAnnouncedPostByPlateId(Integer plateId);

    /**
     * 查找一个板块下的所有帖子数量
     * @param plateId 板块编号
     * @return 帖子数量
     */
    Integer selectPostCountByPlateId(Integer plateId);

    /**
     * 根据最新更新时间查找帖子
     * @param plateId 板块
     * @param start 起始
     * @param size 行数
     * @return
     */
    List<Post> selectPostInPlateByUpdateTime(Integer plateId, Integer start, Integer size);

    /**
     * 查找一个板块下的所有帖子数量
     * @param plateId 板块编号
     * @return 帖子数量
     */
    Integer selectPostCountByPlateIdForManager(Integer plateId);

    /**
     * 根据最新更新时间查找帖子
     * @param plateId 板块
     * @param start 起始
     * @param size 行数
     * @return
     */
    List<Post> selectPostInPlateByUpdateTimeForManager(Integer plateId, Integer start, Integer size);
    /**
     * 板块下的审核帖子
     * @param plateId
     * @return
     */
    Integer selectUncheckPostCountByPlateId(Integer plateId);

    /**
     * 板块下需审核帖子
     * @param plateId
     * @param start
     * @param size
     * @return
     */
    List<Post> selectUncheckPostByPlateId(Integer plateId, Integer start, Integer size);
    /**
     * 添加帖子
     *
     * @param post
     * @return
     */
    Integer addPostForManager(Post post);

    /**
     * 根据发布时间查找 降序
     * @param plateId
     * @param start
     * @param size
     * @return
     */
    List<Post> selectPostInPlateBySendTime(Integer plateId, Integer start, Integer size);

    /**
     * 根据热度查询
     * @param plateId
     * @param start
     * @param size
     * @return
     */
    List<Post> selectPostInPlateByHot(Integer plateId, Integer start, Integer size);

    /**
     * 根据分区最新更新帖子
     * @param sectionId
     * @param start
     * @param size
     * @return
     */
    List<Post> selectPostBySectionIdAndUpdateTime(Integer sectionId, Integer start, Integer size);
    /**
     * 分区根据发布时间查找 降序
     * @param sectionId
     * @param start
     * @param size
     * @return
     */
    List<Post> selectPostBySectionIdAndSendTime(Integer sectionId, Integer start, Integer size);
    /**
     * 分区根据热度查询
     * @param sectionId
     * @param start
     * @param size
     * @return
     */
    List<Post> selectPostBySectionIdAndHot(Integer sectionId, Integer start, Integer size);
}