package com.example.bbs.dao;

import com.example.bbs.entity.Role;
import java.util.List;

/**
 * (TRole)表数据库访问层
 *
 * @author makejava
 * @since 2019-09-20 14:00:56
 */
public interface RoleDao {
    List<Role> selectRoleByPlateId(Integer id);

    Role selectRoleByUserIdAndPlateId(Integer user_id, Integer plate_id);

    List<Role> selectRoleByUserId(Integer id);

    Role addRole(Role role);

    Integer deleteRoleById(Integer id);

    Integer deleteRoleByPlateId(Integer id);

    Integer deleteRoleByUserId(Integer id);
}