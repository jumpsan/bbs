package com.example.bbs.entity;

import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class Information<T> {
    //对象
    private T data;
    //描述
    private String msg;
    //状态码
    private Integer status;

    /**
     * @param status 错误代码
     * @param msg 描述信息
     * @return 错误信息
     */
    public static Information error(Integer status,String msg){
        return Information.builder().status(status).msg(msg).build();
    }

    /**
     *
     * @return 返回简单成功信息
     */
    public static Information success(String msg){
        return Information.builder().status(200).msg(msg+"成功").build();
    }

    /**
     *
     * @param status 错误代码
     * @param msg 描述
     * @param data 数据
     * @param <T> 范型
     * @return 成功信息
     */
    public static <T>Information<T> success(Integer status,String msg,T data){
        return Information.<T>builder().status(status).msg(msg).data(data).build();
    }

}
