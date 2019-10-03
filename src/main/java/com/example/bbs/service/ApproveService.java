package com.example.bbs.service;

import com.example.bbs.entity.Approve;
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
    boolean deleteApprove(Integer id);
}