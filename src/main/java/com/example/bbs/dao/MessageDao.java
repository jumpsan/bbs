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

    Integer addMessage(Message message);

    Integer selectAllCountByTalkers(Integer receiveId, Integer sendId);

    /**
     * 根据用户编号删除
     * @param id
     * @return
     */
    Integer deleteMessageByUserId(Integer id);

    /**
     * 根据主键查信息
     * @param id
     * @return
     */
    Message selectMessageById(Integer id);
}