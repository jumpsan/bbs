package com.example.bbs.service;

import com.example.bbs.entity.Comment;
import com.example.bbs.entity.CommentReply;

import java.util.List;

/**
 * (Comment)表服务接口
 *
 * @author jj
 * @since 2019-10-26 14:54:23
 */
public interface CommentService {

    /**
     * 修改数据
     *
     * @param comment 实例对象
     * @return 实例对象
     */
    Comment update(Comment comment);


    /**
     * 添加回复
     * @param comment 回复
     * @return 主键
     */
    Integer addComment(Comment comment);

    /**
     * 根据编号查询楼中楼
     * @param commentId
     * @return
     */
    Comment selectCommentById(Integer commentId);

    /**
     * 删除楼中楼
     * @param commentId 编号
     * @return 结果
     */
    Integer deleteComment(Integer commentId);

    /**
     * 根据用户编号查询
     * @param userId
     * @return
     */
    List<CommentReply> selectCommentByUserId(Integer userId, Integer start, Integer size);

    /**
     * 根据用户编号查询评论数量
     * @param userId
     * @return
     */
    Integer selectCommentCountByUserId(Integer userId);

}