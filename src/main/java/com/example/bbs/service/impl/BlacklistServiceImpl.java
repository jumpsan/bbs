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
@Service("BlacklistService")
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
    public Integer addBlackList(Blacklist blacklist) {
        Blacklist checkBlackList = blacklistDao.selectListByUserIdAndPermission(blacklist.getUserId(), blacklist.getPermission());
        if(checkBlackList!=null){
            return -2;//重复添加
        }
        Integer result = blacklistDao.addBlackList(blacklist);
        if(result<=0)
            return -7;
        return blacklist.getId();
    }

    /**
     * 删除黑名单
     *
     * @param id 黑名单编号
     * @return 结果
     */
    @Override
    public Integer deleteBlackListById(Integer id) {
        return blacklistDao.deleteBlackListById(id);
    }

    /**
     * 检查特定权限
     * @param userId 用户编号
     * @param permission 权限
     * @return 结果
     */
    @Override
    public Blacklist selectListByUserIdAndPermission(Integer userId, Integer permission) {
        return blacklistDao.selectListByUserIdAndPermission(userId,permission);
    }
    /**
     * 根据用户编号和权限编号删除
     * @param id 用户编号
     * @param permission 权限编号
     * @return 结果
     */
    @Override
    public Integer deleteBlackListByUserIdAndPermission(Integer id, Integer permission) {
        return blacklistDao.deleteBlackListByUserIdAndPermission(id,permission);
    }

    /**
     * 根据编号查询
     *
     * @param id
     * @return
     */
    @Override
    public Blacklist selectListById(Integer id) {
        return blacklistDao.selectListById(id);
    }
}