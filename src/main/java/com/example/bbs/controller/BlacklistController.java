package com.example.bbs.controller;

import com.example.bbs.entity.Blacklist;
import com.example.bbs.entity.Information;
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
    @GetMapping("manager/blacklist/select/user/{id}")
    public Information selectListByUserId(@PathVariable Integer id) {
        if(id==null){
            return Information.error(406,"关键信息不可为空");
        }else{
            List<Blacklist> blacklists = blacklistService.selectListByUserId(id);
            if(blacklists!=null){
                return Information.success(200,"结果列表",blacklists);
            }else{
                return Information.error(204,"无内容");
            }
        }
    }

    /**
     * 根据权限和用户id查询
     * @param userId 用户编号
     * @param permission 权限编号
     * @return 列表
     */
    @GetMapping("manager/blacklist/select/{userId}/{permission}")
    public Information selectListByUserIdAndPermission(@PathVariable Integer userId, @PathVariable Integer permission){
        if(userId==null || permission==null){
            return Information.error(406,"关键信息不能为空");
        }else{
            Blacklist blacklist = blacklistService.selectListByUserIdAndPermission(userId, permission);
            return Information.success(200,"查询结果",blacklist);
        }
    }


    /**
     * 添加黑名单
     *
     * @param blacklist 信息
     * @return 主键值
     */
    @PostMapping("manager/blacklist/add")
    public Information addBlackList(Blacklist blacklist) {
        if(blacklist.getUserId()==null || blacklist.getPermission()==null){
            return Information.error(406,"关键信息不可为空");
        }else{
            Integer result = blacklistService.addBlackList(blacklist);
            if (result==-7){
                return Information.error(400,"添加失败");
            }else if(result==-2){
                return Information.error(402,"重复添加");
            }else{
                Blacklist newBlacklist = blacklistService.selectListById(result);
                return Information.success(200,"黑名单",newBlacklist);
            }
        }
    }

    /**
     * 删除黑名单
     *
     * @param id 黑名单编号
     * @return 结果
     */
    @GetMapping("manager/blacklist/delete/{id}/{permission}")
    public Information deleteBlackListByIdAndPermission(@PathVariable Integer id,@PathVariable Integer permission) {
        Integer result = blacklistService.deleteBlackListByUserIdAndPermission(id,permission);
        if(result<=0){
            return Information.error(400,"删除失败");
        }else{
            return Information.success("删除");
        }
    }

}