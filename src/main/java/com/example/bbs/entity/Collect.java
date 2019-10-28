package com.example.bbs.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;
import java.io.Serializable;

/**
 * (TCollect)实体类
 *
 * @author makejava
 * @since 2019-09-26 17:06:54
 */
public class Collect implements Serializable {
    private static final long serialVersionUID = 604547920758316850L;
    //收藏人用户编号
    private Integer userId;
    //收藏的帖子编号
    private Integer postId;
    
    private Integer id;
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss ")
    private Date time;


    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getPostId() {
        return postId;
    }

    public void setPostId(Integer postId) {
        this.postId = postId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

}