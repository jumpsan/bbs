package com.example.bbs.service.impl;

import com.example.bbs.dao.ApproveDao;
import com.example.bbs.entity.Approve;
import com.example.bbs.service.ApproveService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * (TApprove)表服务实现类
 *
 * @author makejava
 * @since 2019-09-22 14:04:17
 */
@Service("ApproveService")
public class ApproveServiceImpl implements ApproveService {
    @Resource
    private ApproveDao approveDao;


    /**
     * 根据帖子Id查找
     *
     * @param id 帖子id
     * @return 点赞列表
     */
    @Override
    public List<Approve> selectApproveByPostId(Integer id) {
        return approveDao.selectApproveByPostId(id);
    }

    /**
     * 添加点赞记录
     *
     * @param approve 点赞信息
     * @return 主键值
     */
    @Override
    public Approve addApprove(Approve approve) {
        return approveDao.addApprove(approve);
    }

    /**
     * 删除点赞记录
     *
     * @param id 点赞id
     * @return 结果
     */
    @Override
    public boolean deleteApprove(Integer id) {
        return approveDao.deleteApprove(id) > 0;
    }
}