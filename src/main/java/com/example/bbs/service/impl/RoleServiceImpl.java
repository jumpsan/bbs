package com.example.bbs.service.impl;

import com.example.bbs.dao.UserDao;
import com.example.bbs.entity.Plate;
import com.example.bbs.entity.Role;
import com.example.bbs.dao.RoleDao;
import com.example.bbs.entity.User;
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
@Service("RoleService")
public class RoleServiceImpl implements RoleService {
    @Resource
    private RoleDao roleDao;
    @Resource
    private UserDao userDao;

    /**
     * 根据板块id查询
     *
     * @param id 板块id
     * @return 版主列表
     */
    @Override
    public List<User> selectRoleByPlateId(Integer id) {
        return roleDao.selectRoleByPlateId(id);
    }

    /**
     * 根据用户id查询
     *
     * @param id 用户id
     * @return 结果
     */
    @Override
    public List<Plate> selectRoleByUserId(Integer id) {
        return roleDao.selectRoleByUserId(id);
    }

    /**
     * 添加版主
     *
     * @param role 版主信息
     * @return 主键值
     */
    @Override
    public Integer addRole(Role role) {
        Role checkRole = roleDao.selectRoleByUserIdAndPlateId(role.getUserId(), role.getPlateId());
        if(checkRole!=null){
            return -2;
        }
        User user = userDao.selectUserById(role.getUserId());
        if(user==null){
            return -3;//用户不存在
        }
        Integer result = roleDao.addRole(role);
        if(result<=0){
            return -7;//添加失败
        }else{
            return role.getId();
        }
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

    @Override
    public Integer deleteRoleByPlateIdAndUserId(Integer plateId, Integer userId) {
        return roleDao.deleteRoleByPlateIdAndUserId(plateId,userId);
    }

    /**
     * 查找版主
     *
     * @param id
     * @return
     */
    @Override
    public Role selectRoleById(Integer id) {
        return roleDao.selectRoleById(id);
    }


}