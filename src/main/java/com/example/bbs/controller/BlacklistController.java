package com.example.bbs.controller;

import com.example.bbs.entity.Blacklist;
import com.example.bbs.service.BlacklistService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * (TBlacklist)表控制层
 *
 * @author makejava
 * @since 2019-09-22 14:20:30
 */
@RestController
@RequestMapping("tBlacklist")
public class BlacklistController {
    /**
     * 服务对象
     */
    @Resource
    private BlacklistService blacklistService;

    /**
     * 根据用户id查询
     *
     * @param id 用户id
     * @return 黑名单列表
     */
    @GetMapping("selectListByUserId")
    public List<Blacklist> selectListByUserId(Integer id) {
        return blacklistService.selectListByUserId(id);
    }

    @GetMapping("selectListByUserIdAndPermission")
    public Blacklist selectListByUserIdAndPermission(Integer userId,Integer permission){
        return blacklistService.selectListByUserIdAndPermission(userId,permission);
    }


    /**
     * 添加黑名单
     *
     * @param blacklist 信息
     * @return 主键值
     */
    @GetMapping("addBlackList")
    public Integer addBlackList(Blacklist blacklist) {
        blacklist = blacklistService.addBlackList(blacklist);
        return blacklist.getId();
    }

    /**
     * 删除黑名单
     *
     * @param id 黑名单编号
     * @return 结果
     */
    @GetMapping("deleteBlackListById")
    public boolean deleteBlcakListById(Integer id) {
        return blacklistService.deleteBlackListById(id);
    }

}