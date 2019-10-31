package com.example.bbs.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;
import java.io.Serializable;

/**
 * (TBlacklist)实体类
 *
 * @author makejava
 * @since 2019-09-26 17:06:28
 */
@Data
public class Blacklist implements Serializable {
    private static final long serialVersionUID = 163901558561449195L;
    
    private Integer id;
    //用户编号
    private Integer userId;
    //权限编号；0禁评论，1禁发帖
    private Integer permission;
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss ")
    private Date time;
    //用户资料
    private User user;




}