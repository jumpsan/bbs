package com.example.bbs.dao;

import com.example.bbs.entity.Comment;
import com.example.bbs.entity.CommentReply;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * (Comment)表数据库访问层
 *
 * @author jj
 * @since 2019-10-26 14:54:23
 */
public interface CommentDao {

    /**
     * 添加评论
     * @param comment 评论
     * @return 主键
     */
    Integer addComment(Comment comment);

    /**
     * 根据编号查询
     * @param commentId
     * @return
     */
    Comment selectCommentById(Integer commentId);

    /**
     * 删除楼中楼
     * @param commentId
     * @return
     */
    Integer deleteComment(Integer commentId);

    /**
     * 根据回复编号删除
     * @param id
     * @return
     */
    Integer deleteCommentByReplyId(Integer id);

    /**
     * 根据回复编号查询
     */
    List<Comment> selectCommentByReplyId(Integer replyId);

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

    /**
     * 数量
     * @param replyId
     * @return
     */
    Integer selectCommentCountByReplyId(Integer replyId);

    List<Comment> selectCommentByReplyIdForUser(Integer replyId, Integer start, Integer size);

    /**
     * 数量
     * @return
     */
    Integer selectAllCommentCount();

    /**
     * 所有
     * @param start
     * @param size
     * @return
     */
    List<Comment> selectAllComment(Integer start, Integer size);
}