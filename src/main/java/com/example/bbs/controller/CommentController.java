package com.example.bbs.controller;

import com.example.bbs.entity.Comment;
import com.example.bbs.entity.CommentReply;
import com.example.bbs.entity.Information;
import com.example.bbs.entity.Page;
import com.example.bbs.service.CommentService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * (Comment)表控制层
 *
 * @author jj
 * @since 2019-10-26 14:54:23
 */
@RestController
@RequestMapping("comment")
public class CommentController {
    /**
     * 服务对象
     */
    @Resource
    private CommentService commentService;

    /**
     * 添加楼中楼回复
     * @param comment 回复
     * @param request 请求
     * @return 结果
     */
    @PostMapping("add")
    public Information addComment(Comment comment, HttpServletRequest request){
        Integer userId=(Integer)request.getAttribute("userId");
        comment.setUserId(userId);
        boolean condition= comment.getComment()==null || comment.getReplyId()==null || comment.getComment().trim().equals("") ;
        if(condition){
            return Information.error(406,"关键信息不可为空");
        }
        Integer commentId = commentService.addComment(comment);
        if(commentId==-6){
            return Information.error(401,"用户不可评论");
        }else if(commentId==-4){
            return Information.error(405,"板块或分区被禁用");
        }else if(commentId==-5){
            return Information.error(400,"目标帖子或回复或用户不存在");
        }else if(commentId==-7){
            return Information.error(400,"添加失败");
        }else{

            Comment newComment = commentService.selectCommentById(commentId);
            return Information.success(200,"回复成功",newComment);
        }
    }

    /**
     * 根据编号删除回复
     * @param commentId 编号
     * @param request 请求
     * @return 信息
     */
    @DeleteMapping("delete/{commentId}")
    public Information deleteComment(@PathVariable Integer commentId,HttpServletRequest request){
        //身份验证
        Comment comment = commentService.selectCommentById(commentId);
        Integer userId=(Integer)request.getAttribute("userId");
        if(comment.getUserId()!=userId){
            return Information.error(411,"非法操作");
        }
        Integer result = commentService.deleteComment(commentId);
        if(result<=0){
            return Information.error(400,"删除失败");
        }else{
            return Information.success("删除评论");
        }
    }

    /**
     * 根据用户编号查询
     * @param userId
     * @return
     */
    @GetMapping("select/user/{userId}/{page}/{size}")
    public Information selectCommentByReplyId(@PathVariable Integer userId,@PathVariable Integer page,@PathVariable Integer size){
        Integer total=commentService.selectCommentCountByUserId(userId);
        if(total==null || total.equals(0)){
            return  Information.error(204,"无内容");
        }
        Integer totalPage=total/size+1;
        Integer start=(page-1)*size+1;
        List<CommentReply> comments = commentService.selectCommentByUserId(userId,start,size);
        if(comments==null || comments.size()<=0){
            return  Information.error(204,"无内容");
        }
        Page<CommentReply> commentPage=new Page<>();
        commentPage.setDatas(comments);
        commentPage.setTotalPage(totalPage);
        commentPage.setTotalNum(total);
        return Information.success(200,"评论列表",commentPage);
    }



}