package com.example.bbs.entity;

import java.io.Serializable;

/**
 * (TPostImage)实体类
 *
 * @author jj
 * @since 2019-10-19 14:31:09
 */
public class PostImage implements Serializable {
    private static final long serialVersionUID = -85538165218220130L;
    //图片地址
    private String image;
    //帖子编号
    private Integer postId;
    //主键
    private Integer id;


    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Integer getPostId() {
        return postId;
    }

    public void setPostId(Integer postId) {
        this.postId = postId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

}