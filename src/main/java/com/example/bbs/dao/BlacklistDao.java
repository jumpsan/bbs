package com.example.bbs.dao;


import com.example.bbs.entity.Blacklist;

import java.util.List;

/**
 * (TBlacklist)表数据库访问层
 *
 * @author makejava
 * @since 2019-09-22 14:20:30
 */
public interface BlacklistDao {
    /**
     * 根据用户id查询
     *
     * @param id 用户id
     * @return 黑名单列表
     */
    List<Blacklist> selectListByUserId(Integer id);

    /**
     * 添加黑名单
     *
     * @param blacklist 信息
     * @return 主键值
     */
    Blacklist addBlackList(Blacklist blacklist);

    /**
     * 删除黑名单
     *
     * @param id 黑名单编号
     * @return 结果
     */
    Integer deleteBlackListById(Integer id);

    Blacklist selectListByUserIdAndPermission(Integer userId, Integer permission);
}