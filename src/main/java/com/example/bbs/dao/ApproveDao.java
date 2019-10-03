package com.example.bbs.dao;

import com.example.bbs.entity.Approve;

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
    List<Approve> selectApproveByPostId(Integer id);

    /**
     * 添加点赞记录
     *
     * @param approve 点赞信息
     * @return 主键值
     */
    Approve addApprove(Approve approve);

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
}