package com.example.bbs.service;

import com.example.bbs.entity.Blacklist;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * (TBlacklist)表服务接口
 *
 * @author makejava
 * @since 2019-09-22 14:20:30
 */
@Transactional
public interface BlacklistService {


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
    boolean deleteBlackListById(Integer id);

    Blacklist selectListByUserIdAndPermission(Integer userId, Integer permission);
}