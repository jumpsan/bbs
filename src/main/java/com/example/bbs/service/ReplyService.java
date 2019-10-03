package com.example.bbs.service;

import com.example.bbs.entity.Reply;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * (TReply)表服务接口
 *
 * @author makejava
 * @since 2019-09-20 14:00:45
 */
@Transactional
public interface ReplyService {


    List<Reply> selectReplyByPostId(Integer id, Integer start, Integer num);

    List<Reply> selectReplyByUserId(Integer id, Integer start, Integer num);

    Integer addReply(Reply reply);

    Integer deleteReplyById(Integer id);

    Integer updateReply(Reply reply);

    Reply selectReplyById(Integer id);

    Integer selectAllReplyCountByPostId(Integer id);

    Integer selectAllReplyCountByUserId(Integer id);
}