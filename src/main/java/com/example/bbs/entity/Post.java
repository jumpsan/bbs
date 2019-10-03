package com.example.bbs.entity;

import lombok.Data;

@Data
public class Post {

    private Integer id;
    private String titile;
    private Integer userId;
    private Integer sectionId;
    private Integer replyNum;
    private Integer viewNum;
    private Integer approveNum;
    private Integer collectNum;
    private String content;
    private java.sql.Timestamp postTime;
    private java.sql.Timestamp updateTime;
    private String image;
    private String video;
    private Integer type;
    private Integer status;


}
