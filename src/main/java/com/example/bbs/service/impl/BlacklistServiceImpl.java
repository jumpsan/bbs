package com.example.bbs.service.impl;

import com.example.bbs.dao.BlacklistDao;
import com.example.bbs.entity.Blacklist;
import com.example.bbs.service.BlacklistService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * (TBlacklist)表服务实现类
 *
 * @author makejava
 * @since 2019-09-22 14:20:30
 */
@Service("tBlacklistService")
public class BlacklistServiceImpl implements BlacklistService {
    @Resource
    private BlacklistDao blacklistDao;

    /**
     * 根据用户id查询
     *
     * @param id 用户id
     * @return 黑名单列表
     */
    @Override
    public List<Blacklist> selectListByUserId(Integer id) {
        return blacklistDao.selectListByUserId(id);
    }

    /**
     * 添加黑名单
     *
     * @param blacklist 信息
     * @return 主键值
     */
    @Override
    public Blacklist addBlackList(Blacklist blacklist) {
        return blacklistDao.addBlackList(blacklist);
    }

    /**
     * 删除黑名单
     *
     * @param id 黑名单编号
     * @return 结果
     */
    @Override
    public boolean deleteBlackListById(Integer id) {
        return blacklistDao.deleteBlackListById(id) > 0;
    }

    /**
     * 检查特定权限
     * @param userId
     * @param permission
     * @return
     */
    @Override
    public Blacklist selectListByUserIdAndPermission(Integer userId, Integer permission) {
        return blacklistDao.selectListByUserIdAndPermission(userId,permission);
    }
}