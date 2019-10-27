package com.example.bbs.entity;

import lombok.Data;

import java.util.List;

@Data
public class Reply {

    private java.sql.Timestamp replyTime;
    private Integer id;
    private Integer userId;
    private Integer postId;
    private String content;
    private List<Comment> comments;


}
