package com.example.bbs.dao;

import com.example.bbs.entity.Comment;
import com.example.bbs.entity.Reply;
import com.example.bbs.entity.Section;


import java.util.List;

/**
 * (TReply)表数据库访问层
 *
 * @author makejava
 * @since 2019-09-20 14:00:45
 */
public interface ReplyDao {


    List<Reply> selectReplyByPostId(Integer id, Integer start, Integer num);

    List<Reply> selectReplyByUserId(Integer id, Integer start, Integer num);

    Integer addReply(Reply reply);

    Integer deleteReplyById(Integer id);

    Integer updateReply(Reply reply);

    Reply selectReplyById(Integer id);

    Integer deleteReplyByPlateId(Integer id);

    Integer deleteReplyBySectionId(Integer id);

    Integer deleteReplyByPostId(Integer id);


    Integer deleteReplyByPostType(Integer type);

    Integer deleteReplyByPostUserId(Integer id);

    Integer selectAllReplyCountByPostId(Integer id);

    Integer selectAllReplyCountByUserId(Integer id);

    Integer deleteReplyByUserId(Integer id);

    /**
     * 数量
     * @return
     */
    Integer selectAllReplyCount();

    /**
     *
     * @param start
     * @param size
     * @return
     */
    List<Reply> selectAllReply(Integer start, Integer size);
}