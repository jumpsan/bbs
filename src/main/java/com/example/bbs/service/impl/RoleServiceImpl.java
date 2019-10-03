package com.example.bbs.service.impl;

import com.example.bbs.entity.Role;
import com.example.bbs.dao.RoleDao;
import com.example.bbs.service.RoleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * (TRole)表服务实现类
 *
 * @author makejava
 * @since 2019-09-20 14:00:56
 */
@Service("tRoleService")
public class RoleServiceImpl implements RoleService {
    @Resource
    private RoleDao roleDao;

    /**
     * 根据板块id查询
     *
     * @param id 板块id
     * @return 版主列表
     */
    @Override
    public List<Role> selectRoleByPlateId(Integer id) {
        return roleDao.selectRoleByPlateId(id);
    }

    /**
     * 根据用户id查询
     *
     * @param id 用户id
     * @return 结果
     */
    @Override
    public List<Role> selectRoleByUserId(Integer id) {
        return roleDao.selectRoleByUserId(id);
    }

    /**
     * 添加版主
     *
     * @param role 版主信息
     * @return 主键值
     */
    @Override
    public Role addRole(Role role) {
        return roleDao.addRole(role);
    }

    /**
     * 根据版主id删除
     *
     * @param id 版主id
     * @return 结果
     */
    @Override
    public Integer deleteRoleById(Integer id) {
        return roleDao.deleteRoleById(id);
    }

    /**
     * 根据板块id删除
     *
     * @param id 板块id
     * @return 结果
     */
    @Override
    public Integer deleteRoleByPlateId(Integer id) {
        return roleDao.deleteRoleByPlateId(id);
    }

    /**
     * 根据用户id删除
     *
     * @param id 用户id
     * @return 结果
     */
    @Override
    public Integer deleteRoleByUserId(Integer id) {
        return roleDao.deleteRoleByUserId(id);
    }

    /**
     * 根据用户id和版块id
     *
     * @param user_id  用户id
     * @param plate_id 版块id
     * @return 结果
     */
    @Override
    public Role selectRoleByUserIdAndPlateId(Integer user_id, Integer plate_id) {
        return roleDao.selectRoleByUserIdAndPlateId(user_id, plate_id);
    }
}