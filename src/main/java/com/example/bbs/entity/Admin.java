package com.example.bbs.entity;

import java.io.Serializable;

/**
 * (TAdmin)实体类
 *
 * @author makejava
 * @since 2019-09-26 17:01:02
 */
public class Admin implements Serializable {
    private static final long serialVersionUID = -78925707625164247L;
    //管理员编号
    private Integer id;
    //管理员名称
    private String name;
    //密码
    private String password;
    //头像
    private String image;


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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

}