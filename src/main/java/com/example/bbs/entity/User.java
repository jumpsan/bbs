package com.example.bbs.entity;

import lombok.Data;

@Data
public class User {

    private Integer id;
    private String account;
    private String username;
    private String password;
    private java.sql.Timestamp registerTime;
    private String introduce;
    private String image;






}
