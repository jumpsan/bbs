package com.example.bbs.entity;

import java.util.Date;
import java.io.Serializable;

/**
 * (TPlate)实体类
 *
 * @author makejava
 * @since 2019-09-26 17:09:26
 */
public class Plate implements Serializable {
    private static final long serialVersionUID = 790680273782087976L;
    //板块编号
    private Integer id;
    //板块名
    private String name;
    //创建人编号
    private Integer userId;
    //创建时间
    private Date createTime;
    //相关信息
    private String describes;
    //0禁用1启动
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

    public String getDescribes() {
        return describes;
    }

    public void setDescribes(String descripe) {
        this.describes = descripe;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

}