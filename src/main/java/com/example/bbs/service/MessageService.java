package com.example.bbs.service;

import com.example.bbs.entity.Message;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Integer
 * (TMessage)表服务接口
 *
 * @author makejava
 * @since 2019-09-20 13:59:51
 */
@Transactional
public interface MessageService {


    List<Message> selectContent(Integer receive_id, Integer send_id, Integer start, Integer num);

    Integer addMessage(Message message);

    Integer selectAllCountByTalkers(Integer receiveId, Integer sendId);

    /**
     * 根据主键查信息
     * @param id
     * @return
     */
    Message selectMessageById(Integer id);
}