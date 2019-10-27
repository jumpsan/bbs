package com.example.bbs.service;

import com.example.bbs.entity.Approve;
import com.example.bbs.entity.Post;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * (TApprove)表服务接口
 *
 * @author makejava
 * @since 2019-09-22 14:04:16
 */
@Transactional
public interface ApproveService {

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

    Approve selectApproveById(Integer id);

    /**
     * 通过用户编号和帖子编号删除
     * @param id 用户编号
     * @param postId 帖子编号
     * @return 结果
     */
    Integer deleteApproveByUserIdAndPostId(Integer id, Integer postId);

    /**
     * 根据用户编号，查看点赞过的帖子
     * @param userId
     * @return
     */
    List<Post> selectApprovedPostByUserId(Integer userId,Integer start,Integer size);

    /**
     * 根据用户编号，查看点赞过的帖子 总条数
     * @param userId
     * @return
     */
    Integer selectApprovedPostCountByUserId(Integer userId);
}