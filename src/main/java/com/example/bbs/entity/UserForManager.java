package com.example.bbs.entity;

import lombok.Data;

@Data
public class UserForManager  extends User{
    private Integer limitReply;
    private Integer limitPost;
}
