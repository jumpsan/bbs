package com.example.bbs.service.impl;

import com.example.bbs.entity.Message;
import com.example.bbs.dao.MessageDao;
import com.example.bbs.service.MessageService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * (TMessage)表服务实现类
 *
 * @author makejava
 * @since 2019-09-20 13:59:51
 */
@Service("tMessageService")
public class MessageServiceImpl implements MessageService {
    @Resource
    private MessageDao messageDao;

    /**
     * 查看消息内容
     *
     * @param receive_id 接收者
     * @param send_id    发送者
     * @return 消息列表
     */
    @Override
    public List<Message> selectContent(Integer receive_id, Integer send_id, Integer start, Integer num) {
        return messageDao.selectContent(receive_id, send_id, start, num);
    }

    /**
     * 添加消息
     *
     * @param message
     * @return
     */
    @Override
    public Integer addMessage(Message message) {
        message = messageDao.addMessage(message);
        return message.getId();
    }

}