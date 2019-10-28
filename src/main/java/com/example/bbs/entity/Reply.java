package com.example.bbs.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.List;

@Data
public class Reply {
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss ")
    private java.sql.Timestamp replyTime;
    private Integer id;
    private Integer userId;
    private Integer postId;
    private String content;
    private List<Comment> comments;


}
