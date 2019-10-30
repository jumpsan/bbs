package com.example.bbs.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class CommentReply{
    //主键
    private Integer id;
    //主回复编号
    private Integer replyId;
    //回复对象的用户名，如果不是楼中楼则不用添加
    private String toUsername;
    //回复者的编号
    private Integer userId;
    //回复时间
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss ")
    private Date commentTime;
    //评论内容
    private String comment;
    //回复对象的用户编号
    private Integer toUserId;
    //评论者
    private  User user;
    private ReplyPost replyPost;
}
