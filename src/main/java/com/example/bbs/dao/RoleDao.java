package com.example.bbs.dao;

import com.example.bbs.entity.Plate;
import com.example.bbs.entity.Role;
import com.example.bbs.entity.User;

import java.util.List;

/**
 * (TRole)表数据库访问层
 *
 * @author makejava
 * @since 2019-09-20 14:00:56
 */
public interface RoleDao {
    List<User> selectRoleByPlateId(Integer id);


    List<Plate> selectRoleByUserId(Integer id);

    Integer addRole(Role role);

    Integer deleteRoleById(Integer id);

    Integer deleteRoleByPlateId(Integer id);

    Integer deleteRoleByUserId(Integer id);

    Role selectRoleByUserIdAndPlateId(Integer userId, Integer plateId);

    Integer deleteRoleByPlateIdAndUserId(Integer plateId, Integer userId);

    Role selectRoleById(Integer id);
}