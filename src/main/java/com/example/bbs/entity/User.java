package com.example.bbs.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

@Data
public class User {

    private Integer id;
    private String username;
    private String password;
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss ")
    private java.sql.Timestamp registerTime;
    private String introduce;
    private String image;
    private Integer postNum;
    private Integer replyNum;
    private Integer collectNum;






}
