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

    Integer addPost(Post post);

//    Integer addWordPost(Post post);
//
//    Integer addVideoPost(Post post);

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

    List<Post> selectUncheckPostBySectionId(Integer sectionId, Integer start, Integer size);

    Integer selectUncheckPostCountBySectionId(Integer sectionId);

    Post selectPostByIdForUser(Integer id);
    /**
     * @param plateName   板块名
     * @param sectionName 分区名
     * @param start
     * @param size
     * @return 帖子
     */
    List<Post> selectPostByPlateNameAndSectionName(String plateName, String sectionName, Integer start, Integer size);
    /**
     * @param plateName   板块名
     * @param sectionName 分区名
     * @return 帖子总数
     */
    Integer selectPostCountByPlateNameAndSectionName(String plateName, String sectionName);

    /**
     * 查找置顶帖子
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
     * 查找一个板块下的帖子数量
     * @param plateId
     * @return
     */
    Integer selectPostCountByPlateId(Integer plateId);
    /**
     * 根据最新更新时间查找帖子
     *
     * @param plateId 板块
     * @param start   起始
     * @param size    行数
     * @return
     */
    List<Post> selectPostInPlateByUpdateTime(Integer plateId, Integer start, Integer size);

    /**
     * 查找一个板块下的帖子数量
     * @param plateId
     * @return
     */
    Integer selectPostCountByPlateIdForManager(Integer plateId);
    /**
     * 根据最新更新时间查找帖子
     *
     * @param plateId 板块
     * @param start   起始
     * @param size    行数
     * @return
     */
    List<Post> selectPostInPlateByUpdateTimeForManager(Integer plateId, Integer start, Integer size);
    /**
     * 板块下需审核帖子
     *
     * @param plateId
     * @param start
     * @param size
     * @return
     */
    List<Post> selectUncheckPostByPlateId(Integer plateId, Integer start, Integer size);
    /**
     * 板块下的审核帖子
     *
     * @param plateId
     * @return
     */
    Integer selectUncheckPostCountByPlateId(Integer plateId);
    /**
     * 根据发布时间查找 降序
     *
     * @param plateId
     * @param start
     * @param size
     * @return
     */
    List<Post> selectPostInPlateBySendTime(Integer plateId, Integer start, Integer size);

    /**
     * 根据热度查找
     * @param plateId
     * @param start
     * @param size
     * @return
     */
    List<Post> selectPostInPlateByHot(Integer plateId, Integer start, Integer size);
    /**
     * 根据分区最新更新帖子
     *
     * @param sectionId
     * @param start
     * @param size
     * @return
     */
    List<Post> selectPostBySectionIdAndUpdateTime(Integer sectionId, Integer start, Integer size);
    /**
     * 分区根据发布时间查找 降序
     *
     * @param sectionId
     * @param start
     * @param size
     * @return
     */
    List<Post> selectPostBySectionIdAndSendTime(Integer sectionId, Integer start, Integer size);
    /**
     * 分区根据热度查询
     *
     * @param sectionId
     * @param start
     * @param size
     * @return
     */
    List<Post> selectPostBySectionIdAndHot(Integer sectionId, Integer start, Integer size);

    /**
     * 查找首页帖子
     * @param start
     * @param size
     * @return
     */
    List<Post> selectIndexPagePost(Integer start, Integer size);

    /**
     * 所有帖子的数量
     * @return
     */
    Integer selectIndexPagePostCount();

    /**
     * 所有审核的帖子数量
     * @return
     */
    Integer selectUncheckPostCount();

    /**
     * 所有需要审核的帖子
     * @param start
     * @param size
     * @return
     */
    List<Post> selectUncheckPost(Integer start, Integer size);
}