package com.example.bbs.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.List;

@Data
public class Post {

    private Integer id;
    private String title;
    private Integer userId;
    private Integer sectionId;
    private Integer replyNum;
    private Integer viewNum;
    private Integer approveNum;
    private Integer collectNum;
    private String content;
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss ")
    private java.sql.Timestamp postTime;
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss ")
    private java.sql.Timestamp updateTime;
    private String video;
    private Integer type;
    private Integer status;
    private Integer plateId;
    private List<String> images;
    //发帖人
    private User user;
    //是否收藏了
    private Integer isCollected;



}
