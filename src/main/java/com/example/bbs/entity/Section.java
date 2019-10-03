package com.example.bbs.entity;

import java.util.Date;
import java.io.Serializable;

/**
 * (TSection)实体类
 *
 * @author makejava
 * @since 2019-09-26 17:10:47
 */
public class Section implements Serializable {
    private static final long serialVersionUID = 433501634515258211L;
    //分区编号
    private Integer id;
    //分区名
    private String name;
    //板块编号
    private Integer plateId;
    //帖子数
    private Integer postNum;
    //用户编号
    private Integer userId;
    //创建时间
    private Date createTime;
    //0禁用1启用
    private Integer status;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPlateId() {
        return plateId;
    }

    public void setPlateId(Integer plateId) {
        this.plateId = plateId;
    }

    public Integer getPostNum() {
        return postNum;
    }

    public void setPostNum(Integer postNum) {
        this.postNum = postNum;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

}