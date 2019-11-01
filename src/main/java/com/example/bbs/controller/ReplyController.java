package com.example.bbs.controller;

import com.example.bbs.entity.*;
import com.example.bbs.service.ReplyService;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * (TReply)表控制层
 *
 * @author makejava
 * @since 2019-09-20 14:00:45
 */
@RestController
public class ReplyController {
    /**
     * 服务对象
     */
    @Resource
    private ReplyService replyService;

    /**
     * 根据回复id查出
     *
     * @param id 回复id
     * @return
     */
    @GetMapping("reply/select/{id}")
    public Information selectReplyById(@PathVariable Integer id) {
        if(id==null){
            return Information.error(406,"回复编号不可为空");
        }else{
            Reply reply = replyService.selectReplyById(id);
            if(reply!=null) {
                return Information.success(200,"回复",reply);
            }else {
                return Information.error(204,"无内容");
            }
        }
    }

    /**
     * 根据帖子id
     *
     * @param id    帖子id
     * @param page 第一行偏移量
     * @param size   行数
     * @return 回复列表
     */
    @GetMapping("reply/select/post/{id}/{page}/{size}")
    public Information selectReplyByPostId(@PathVariable Integer id, @PathVariable Integer page,@PathVariable Integer size) {
        if(id==null){
            return Information.error(406,"关键信息不可为空");
        }else{
            Integer total=replyService.selectAllReplyCountByPostId(id);
            if(total==null || total==0){
                return Information.error(204,"内容为空");
            }
            //总页数
            Integer totalPage=total/size+1;
            Integer start=(page-1)*size+1;
            List<Reply> replies= replyService.selectReplyByPostId(id,start, size);
            Page<Reply> replyPage=new Page<>();
            replyPage.setDatas(replies);
            replyPage.setTotalPage(totalPage);
            replyPage.setTotalNum(total);
            if(replies!=null) {
                return Information.success(200,"回复列表",replyPage);
            }else {
                return Information.error(204,"无内容");
            }
        }
    }

    /**
     * 根据用户id查询
     *
     * @param id    用户Id
     * @param page 第一行偏移量
     * @param size   行数
     * @return 回复列表
     */
    @GetMapping("reply/select/user/{id}/{page}/{size}")
    public Information selectReplyByUserId(@PathVariable Integer id, @PathVariable Integer page,@PathVariable Integer size) {
        if(id==null){
            return Information.error(406,"关键信息不可为空");
        }else{
            //总条数
            Integer total=replyService.selectAllReplyCountByUserId(id);
            if(total==null || total==0){
                return Information.error(204,"无内容");
            }
            //总页数
            Integer totalPage=total/size+1;
            Integer start=(page-1)*size+1;
            List<Reply> replies= replyService.selectReplyByUserId(id,start, size);
            if(replies!=null) {
                Page<Reply> replyPage=new Page<>();
                replyPage.setDatas(replies);
                replyPage.setTotalPage(totalPage);
                replyPage.setTotalNum(total);
                return Information.success(200,"回复列表",replyPage);
            }else {
                return Information.error(204,"无内容");
            }
        }
    }

    /**
     * 添加回复
     * id user_id post_id content
     * @param reply 回复
     * @return 主键值
     */
    @PostMapping("reply/add")
    public Information addReply(Reply reply,HttpServletRequest request) {
        Integer userId=(Integer)request.getAttribute("userId");
        reply.setUserId(userId);
        //System.out.println(userId);
        if (reply.getUserId()==null || reply.getContent()==null || reply.getPostId()==null || reply.getContent().trim().length()<15) {
            return Information.error(406,"关键信息不可为空,回复内容不得少于15");
        }else{
            Integer replyId = replyService.addReply(reply);
            if (replyId==-3) {
                return Information.error(404,"用户不存在");
            }else if (replyId==-4) {
                return Information.error(407,"帖子被禁用");
            } else if (replyId==-5) {
                return Information.error(407,"目标帖子不存在");
            }else if(replyId==-6){
                return Information.error(401,"用户在黑名单无权");
            } else if(replyId==0){
                return Information.error(400,"添加失败");
            } else {
                Reply newReply = replyService.selectReplyById(replyId);
                return Information.success(200,"添加回复",newReply);
            }
        }
    }

    /**
     * 删除回复
     * @param replyId 回复Id
     * @return 结果
     */
    @GetMapping("reply/delete/{replyId}")
    public Information deleteReplyById(@PathVariable Integer replyId,HttpServletRequest request) {
        Reply reply = replyService.selectReplyById(replyId);
        if(reply==null){
            return Information.error(404,"不存在");
        }
        Integer userId =(Integer) request.getAttribute("userId");
        if(!userId.equals(reply.getUserId())){
            return Information.error(411,"非法操作");
        }
        Integer result = replyService.deleteReplyById(replyId);
        if(result>0){
            return Information.success("删除");
        }
        else {
            return Information.error(400,"删除失败");
        }
    }

    /**
     * 修改回复，需要主键
     * id user_id post_id content
     * @param reply
     * @return
     */
    @PostMapping("reply/update")
    public Information updateReply(Reply reply,HttpServletRequest request) {
        if(reply.getId()==null){
            return Information.error(406,"关键信息不可为空");
        }else{
            Integer userId=(Integer)request.getAttribute("userId");
            Reply checkReply=replyService.selectReplyById(reply.getId());
            if(userId!=checkReply.getUserId()){
                return Information.error(411,"非法操作");
            }
            if(reply.getContent().trim().length()<15){
                return Information.error(400,"文字内容长度不得少于15");
            }
            Integer re=replyService.updateReply(reply);
            if(re==null || re==0){
                return Information.error(400,"更新失败");
            } else{
                Reply newReply = replyService.selectReplyById(reply.getId());
                return Information.success(200,"更新成功",newReply);
            }
        }
    }

    /**
     * 管理员删除评论
     * @param replyId
     * @return
     */
    @GetMapping("manager/delete/reply/{replyId}")
    public Information deleteReplyByManager(@PathVariable Integer replyId){
        Reply reply = replyService.selectReplyById(replyId);
        if(reply==null){
            return Information.error(404,"不存在");
        }
        Integer result = replyService.deleteReplyById(replyId);
        if(result>0){
            return Information.success("删除");
        }
        else {
            return Information.error(400,"删除失败");
        }
    }

    /**
     * 查看所有回复
     * @param page
     * @param size
     * @return
     */
    @GetMapping("manager/reply/select/all/{page}/{size}")
    public Information selectAllReply(@PathVariable Integer page,@PathVariable Integer size){
        Integer total=replyService.selectAllReplyCount();
        if(total==null){
            return Information.error(204,"无内容");
        }
        Integer totalPage=total/size+1;
        Integer start=(page-1)*size+1;
        List<Reply> replies=replyService.selectAllReply(start,size);
        if(replies==null || replies.size()==0){
            return Information.error(204,"无内容");
        }
        Page<Reply> replyPage=new Page<>();
        replyPage.setTotalNum(total);
        replyPage.setTotalPage(totalPage);
        replyPage.setDatas(replies);
        return Information.success(200,"回复",replyPage);
    }


    /**
     * 根据回复id查出
     *
     * @param replyId 回复id
     * @return
     */
    @GetMapping("manager/reply/select/{replyId}")
    public Information selectReplyByIdForManager(@PathVariable Integer replyId) {
        if(replyId==null){
            return Information.error(406,"回复编号不可为空");
        }else{
            Reply reply = replyService.selectReplyById(replyId);
            if(reply!=null) {
                return Information.success(200,"回复",reply);
            }else {
                return Information.error(204,"无内容");
            }
        }
    }
}