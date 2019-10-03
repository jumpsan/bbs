package com.example.bbs.entity;

import java.util.Date;
import java.io.Serializable;

/**
 * (TFollowed)实体类
 *
 * @author makejava
 * @since 2019-09-26 17:07:22
 */
public class Follow implements Serializable {
    private static final long serialVersionUID = 751085516048568401L;
    //关注编号
    private Integer id;
    //关注人用户编号
    private Integer followId;
    //被关注人用户编号
    private Integer followedId;
    
    private Date time;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getFollowId() {
        return followId;
    }

    public void setFollowId(Integer followId) {
        this.followId = followId;
    }

    public Integer getFollowedId() {
        return followedId;
    }

    public void setFollowedId(Integer followedId) {
        this.followedId = followedId;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

}