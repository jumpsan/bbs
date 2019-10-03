package com.example.bbs.dao;

import com.example.bbs.entity.User;


/**
 * (TUser)表数据库访问层
 *
 * @author makejava
 * @since 2019-09-20 14:01:19
 */
public interface UserDao {


    User selectUserById(Integer id);

    User selectUserByIdAndPassword(Integer id, String password);

    Integer addUser(User user);

    Integer deleteUserById(Integer id);

    Integer updateUserById(User user);
}