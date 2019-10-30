package com.example.bbs.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.List;

@Data
public class ReplyPost {
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss ")
    private java.sql.Timestamp replyTime;
    private Integer id;
    private Integer userId;
    private Integer postId;
    private String content;
    //用户
    private User user;
    //所回复的帖子
    private Post post;

}
