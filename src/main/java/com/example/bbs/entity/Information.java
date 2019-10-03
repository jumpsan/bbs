package com.example.bbs.entity;

import lombok.Data;

@Data
public class Information<T> {
    //对象
    private T data;
    //描述
    private String msg;
    //状态码
    private Integer status;


}
