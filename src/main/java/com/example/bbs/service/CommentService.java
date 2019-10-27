package com.example.bbs.service;

import com.example.bbs.entity.Comment;
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
}