package com.example.bbs.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;
import java.io.Serializable;

/**
 * (TApprove)实体类
 *
 * @author makejava
 * @since 2019-09-26 17:05:46
 */
public class Approve implements Serializable {
    private static final long serialVersionUID = 383577939232683279L;
    //点赞编号
    private Integer id;
    //用户编号
    private Integer userId;
    //点赞时间
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss ")
    private Date time;
    //帖子编号
    private Integer postId;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public Integer getPostId() {
        return postId;
    }

    public void setPostId(Integer postId) {
        this.postId = postId;
    }

}