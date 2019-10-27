package com.example.bbs.entity;

import lombok.Data;

import java.util.Date;
import java.io.Serializable;
import java.util.List;

/**
 * (TPlate)实体类
 *
 * @author makejava
 * @since 2019-09-26 17:09:26
 */
@Data
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
    //浏览数
    private Integer viewNum;
    //帖子数
    private Integer postNum;
    //分区
    private List<Section> sections;




}