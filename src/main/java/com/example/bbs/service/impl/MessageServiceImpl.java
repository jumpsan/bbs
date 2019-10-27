package com.example.bbs.service.impl;

import com.example.bbs.dao.UserDao;
import com.example.bbs.entity.Message;
import com.example.bbs.dao.MessageDao;
import com.example.bbs.entity.User;
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
@Service("MessageService")
public class MessageServiceImpl implements MessageService {
    @Resource
    private MessageDao messageDao;
    @Resource
    private UserDao userDao;
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
        Integer receiveId=message.getReceiveId();
        User user=userDao.selectUserById(receiveId);
        if(user==null){
            return -3;
        }
        Integer result = messageDao.addMessage(message);
        if(result<=0){
            return -7;//添加失败
        }
        return message.getId();
    }

    /**
     * 分页 查询总页数
     * @param receiveId
     * @param sendId
     * @return
     */
    @Override
    public Integer selectAllCountByTalkers(Integer receiveId, Integer sendId) {
        return messageDao.selectAllCountByTalkers(receiveId,sendId);
    }

    /**
     * 根据主键查信息
     *
     * @param id
     * @return
     */
    @Override
    public Message selectMessageById(Integer id) {
        return messageDao.selectMessageById(id);
    }

}