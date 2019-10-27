package com.example.bbs.controller;

import com.example.bbs.annotation.AuthChecker;
import com.example.bbs.entity.Information;
import com.example.bbs.entity.Message;
import com.example.bbs.entity.Page;
import com.example.bbs.service.MessageService;
import com.example.bbs.utils.Authorization;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * (TMessage)表控制层
 *
 * @author makejava
 * @since 2019-09-20 13:59:51
 */
@RestController
@RequestMapping("message")
public class MessageController {
    /**
     * 服务对象
     */
    @Resource
    private MessageService messageService;

    /**
     * 获取聊天记录
     * @param receiveId 接收人
     * @return 返回信息列表
     */
    @GetMapping("content/{receiveId}/{page}/{size}")
    public Information selectContent(@PathVariable Integer receiveId, @PathVariable Integer page, @PathVariable Integer size,HttpServletRequest request) {
        Integer id=(Integer)request.getAttribute("userId");
        if(receiveId==null || id==null || page==null || size==null){
            return Information.error(406,"关键信息不可为空");
        }else{
            Integer total= messageService.selectAllCountByTalkers(receiveId, id);
            if(total==null || total==0){
                return Information.success(204,"分页无内容返回",null);
            }else{
                Integer totalPage =total/size+1;
                Page<Message> messagePage=new Page<>();
                Integer start=(page-1)*size;
                List<Message> messages = messageService.selectContent(receiveId, id, start, size);
                messagePage.setDatas(messages);
                messagePage.setTotalPage(totalPage);
                return Information.success(200,"消息列表",messagePage);
            }
        }
    }

    /**
     * 添加信息
     * @param receiveId 接收者编号
     * @return 主键值
     */
    @PostMapping("add")
    public Information addMessage(Integer receiveId,String content,HttpServletRequest request) {
        Message message=new Message();
        Integer id =(Integer) request.getAttribute("userId");
        if(receiveId==null || id==null || content==null || content.length()<=0){
            return Information.error(406,"关键信息不可为空");
        }else{
            message.setContent(content);
            message.setReceiveId(receiveId);
            message.setSendId(id);
            Integer result = messageService.addMessage(message);
            if(result==-7){
                return Information.error(400,"添加失败");
            }else if(result==-3){
                return Information.error(404,"用户不存在");
            }else{
                Message newMessage = messageService.selectMessageById(result);
                return Information.success(200,"消息",newMessage);
            }
        }
    }

}