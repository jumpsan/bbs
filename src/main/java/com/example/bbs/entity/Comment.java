package com.example.bbs.entity;

import java.util.Date;
import java.io.Serializable;

/**
 * (Comment)实体类
 *
 * @author jj
 * @since 2019-10-26 14:54:23
 */
public class Comment implements Serializable {
    private static final long serialVersionUID = 199500634457047321L;
    //主键
    private Integer id;
    //主回复编号
    private Integer replyId;
    //回复对象的用户名，如果不是楼中楼则不用添加
    private String toUsername;
    //回复者的编号
    private Integer userId;
    //回复时间
    private Date commentTime;
    //评论内容
    private String comment;
    //回复对象的用户编号
    private Integer toUserId;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getReplyId() {
        return replyId;
    }

    public void setReplyId(Integer replyId) {
        this.replyId = replyId;
    }

    public String getToUsername() {
        return toUsername;
    }

    public void setToUsername(String toUsername) {
        this.toUsername = toUsername;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Date getCommentTime() {
        return commentTime;
    }

    public void setCommentTime(Date commentTime) {
        this.commentTime = commentTime;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Integer getToUserId() {
        return toUserId;
    }

    public void setToUserId(Integer toUserId) {
        this.toUserId = toUserId;
    }

}