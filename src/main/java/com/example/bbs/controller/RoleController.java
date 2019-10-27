package com.example.bbs.controller;

import ch.qos.logback.core.joran.conditional.IfAction;
import com.example.bbs.annotation.RightChecker;
import com.example.bbs.entity.Information;
import com.example.bbs.entity.Plate;
import com.example.bbs.entity.Role;
import com.example.bbs.entity.User;
import com.example.bbs.service.RoleService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * (TRole)表控制层
 *
 * @author makejava
 * @since 2019-09-20 14:00:56
 */
@RestController
public class RoleController {
    /**
     * 服务对象
     */
    @Resource
    private RoleService roleService;

    /**
     * 根据板块Id查找版主
     *
     * @param id 板块id
     * @return 列表版主
     */
    @GetMapping("role/select/plate/{id}")
    public Information selectRoleByPlateId(@PathVariable Integer id) {
        if(id==null){
            return Information.error(406,"关键信息不可为空");
        }else{
            List<User> users = roleService.selectRoleByPlateId(id);
            if(users==null || users.size()==0){
                return Information.error(204,"无内容");
            }
            return Information.success(200,"版主",users);

        }
    }

    /**
     * 根据用户id查找其所管理的板块
     *
     * @param id 用户id
     * @return 板主列表
     */
    @GetMapping("role/select/user/{id}")
    public Information selectRoleByUserId(@PathVariable Integer id) {
        if(id==null){
            return Information.error(406,"关键信息不可为空");
        }else{
            List<Plate> plates = roleService.selectRoleByUserId(id);
            if(plates==null || plates.size()==0){
                return Information.error(204,"无内容");
            }
            return Information.success(200,"板块",plates);
        }
    }



    /**
     * 添加版主
     *
     * @param role 版主信息
     * @return 主键值
     */
    @PostMapping("manager/role/add")
    public Information addRole(Role role) {
        if(role.getPlateId()==null || role.getUserId()==null){
            return Information.error(406,"关键信息不可为空");
        }else {
            Integer result = roleService.addRole(role);
            if (result == -7) {
                return Information.error(400,"添加失败");
            } else if(result==-2){
                return Information.error(402,"重复添加");
            }else if(result==-3){
                return Information.error(404,"用户不存在");
            } else {
                Role newRole = roleService.selectRoleById(result);
                return Information.success(200,"主键",newRole);
            }
        }
    }

    /**
     * 根据版主id删除
     *
     * @param id 版主id
     * @return 结果
     */
    @GetMapping("manager/role/delete/{id}")
    public Information deleteRoleById(@PathVariable Integer id) {
        if(id==null){
            return Information.error(406,"关键信息不可为空");

        }else{
            Integer result = roleService.deleteRoleById(id);
            if(result<=0){
                return Information.error(400,"删除失败");
            }else{
                return Information.success("删除");
            }
        }
    }

    /**
     * 根据用户id以及板块id删除
     * @param plateId
     * @param id
     * @return
     */
    @GetMapping("manager/role/delete/plate/user/{plateId}/{id}")
    public Information deleteRoleByPlateIdAndUserId(@PathVariable Integer plateId,@PathVariable Integer id) {
        if (plateId == null || id == null) {
            return Information.error(406, "关键信息不可为空");
        } else {
            Integer result = roleService.deleteRoleByPlateIdAndUserId(plateId, id);
            if (result <= 0) {
                return Information.error(400, "删除失败");
            } else {
                return Information.success("删除");
            }
        }
    }
}