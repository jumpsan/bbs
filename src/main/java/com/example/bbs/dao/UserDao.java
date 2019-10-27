package com.example.bbs.dao;

import com.example.bbs.entity.User;

import java.util.List;


/**
 * (TUser)表数据库访问层
 *
 * @author makejava
 * @since 2019-09-20 14:01:19
 */
public interface UserDao {


    User selectUserById(Integer id);

    User selectUserByNameAndPassword(String username, String password);

    Integer addUser(User user);

    Integer deleteUserById(Integer id);

    Integer updateUserById(User user);

    User selectUserByName(String username);

    Integer selectAllUserCount();

    List<User> selectAllUser(Integer start, Integer size);
}