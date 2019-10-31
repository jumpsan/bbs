package com.example.bbs.dao;

import com.example.bbs.dto.UserForManagerDto;
import com.example.bbs.entity.User;
import com.example.bbs.entity.UserForManager;

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

    List<UserForManager> selectAllUser(Integer start, Integer size);

    /**
     * 黑名单用户数
     * @return
     */
    Integer selectUserInBlacklistCount();

    /**
     * 黑名单
     * @param start
     * @param size
     * @return
     */
    List<UserForManager> selectUserInBlacklist( Integer start, Integer size);

    /**
     * 管理员根据用户编号
     * @param id
     * @return
     */
    UserForManager selectUserByIdForManager(Integer id);
}