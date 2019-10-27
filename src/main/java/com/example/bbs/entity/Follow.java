package com.example.bbs.entity;

import lombok.Data;

import java.util.Date;
import java.io.Serializable;

/**
 * (TFollowed)实体类
 *
 * @author makejava
 * @since 2019-09-26 17:07:22
 */
@Data
public class Follow implements Serializable {
    private static final long serialVersionUID = 751085516048568401L;
    //关注编号
    private Integer id;
    //关注人用户编号
    private Integer followId;
    //被关注人用户编号
    private Integer followedId;
    
    private Date time;


}