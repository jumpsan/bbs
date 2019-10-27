package com.example.bbs.dao;

import com.example.bbs.entity.PostImage;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * (TPostImage)表数据库访问层
 *
 * @author jj
 * @since 2019-10-19 14:31:09
 */
public interface PostImageDao {


    /**
     * 根据帖子编号查找图片
     * @param postId
     * @return
     */
    List<String> selectImagesByPostId(Integer postId);

    /**
     * 根据帖子编号删除图片
     * @param postId
     * @return
     */
    Integer deleteImageByPostId(Integer postId);

    /**
     * 添加图片记录
     * @param postImage
     * @return
     */
    Integer addImages(PostImage postImage);
}