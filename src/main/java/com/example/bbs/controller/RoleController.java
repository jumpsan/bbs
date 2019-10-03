package com.example.bbs.controller;

import com.example.bbs.entity.Role;
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
@RequestMapping("tRole")
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
    @GetMapping("selectRoleByPlateId")
    public List<Role> selectRoleByPlateId(Integer id) {
        return roleService.selectRoleByPlateId(id);
    }

    /**
     * 根据用户id查找其所管理的板块
     *
     * @param id 用户id
     * @return 板主列表
     */
    @GetMapping("selectRoleByUserId")
    public List<Role> selectRoleByUserId(Integer id) {
        return roleService.selectRoleByUserId(id);
    }

    /**
     * 根据用户id和版块id
     *
     * @param user_id  用户id
     * @param plate_id 版块id
     * @return 结果
     */
    @GetMapping("selectRoleByUserIdAndPlateId")
    public Role selectRoleByUserIdAndPlateId(Integer user_id, Integer plate_id) {
        return roleService.selectRoleByUserIdAndPlateId(user_id, plate_id);
    }


    /**
     * 添加版主
     *
     * @param role 版主信息
     * @return 主键值
     */
    @GetMapping("addRole")
    public Integer addRole(Role role) {
        role = roleService.addRole(role);
        return role.getId();
    }

    /**
     * 根据版主id删除
     *
     * @param id 版主id
     * @return 结果
     */
    @GetMapping("deleteRoleById")
    public boolean deleteRoleById(Integer id) {
        return roleService.deleteRoleById(id) > 0;
    }

    /**
     * 根据板块id删除
     *
     * @param id 板块id
     * @return 结果
     */
    @GetMapping("deleteRoleByPlateId")
    public boolean deleteRoleByPlateId(Integer id) {
        return roleService.deleteRoleByPlateId(id) > 0;
    }

    /**
     * 根据用户id删除
     *
     * @param id 用户id
     * @return 结果
     */
    @GetMapping("deleteRoleByUserId")
    public boolean deleteRoleByUserId(Integer id) {
        return roleService.deleteRoleByUserId(id) > 0;
    }

}