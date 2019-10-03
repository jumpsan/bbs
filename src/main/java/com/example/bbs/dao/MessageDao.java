package com.example.bbs.dao;

import com.example.bbs.entity.Message;

import java.util.List;

/**
 * (TMessage)表数据库访问层
 *
 * @author makejava
 * @since 2019-09-20 13:59:51
 */
public interface MessageDao {
    List<Message> selectContent(Integer receive_id, Integer send_id, Integer start, Integer num);

    Message addMessage(Message message);
}