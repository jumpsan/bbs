package com.example.bbs.dao;

import com.example.bbs.entity.Approve;
import com.example.bbs.entity.Post;

import java.util.List;

/**
 * (TApprove)表数据库访问层
 *
 * @author makejava
 * @since 2019-09-22 14:04:16
 */
public interface ApproveDao {

    /**
     * 根据帖子Id查找
     *
     * @param id 帖子id
     * @return 点赞列表
     */
    Integer selectApproveCountByPostId(Integer id);

    /**
     * 添加点赞记录
     *
     * @param approve 点赞信息
     * @return 主键值
     */
    Integer addApprove(Approve approve);

    /**
     * 删除点赞记录
     *
     * @param id 点赞id
     * @return 结果
     */
    Integer deleteApprove(Integer id);

    /**
     * 根据板块Id删除
     * @param id
     * @return
     */
    Integer deleteApproveByPlateId(Integer id);

    Integer deleteApproveBySectionId(Integer id);

    Integer deleteApproveByPostId(Integer id);



    Integer deleteApproveByPostType(Integer type);

    Integer deleteApproveByPostUserId(Integer id);

    Approve selectApproveById(Integer id);

    Integer deleteApproveByUserId(Integer id);

    Approve selectApproveByUserIdAndPostId(Integer userId, Integer postId);

    Integer deleteApproveByUserIdAndPostId(Integer id, Integer postId);
    /**
     * 根据用户编号，查看点赞过的帖子
     *
     * @param userId
     * @return
     */
    List<Post> selectApprovedPostByUserId(Integer userId,Integer start,Integer size);
    /**
     * 根据用户编号，查看点赞过的帖子数量
     *
     * @param userId
     * @return
     */
    Integer selectApprovedPostCountByUserId(Integer userId);
}