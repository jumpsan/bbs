package com.example.bbs.service;

import com.example.bbs.dto.UserForManagerDto;
import com.example.bbs.entity.User;
import com.example.bbs.entity.UserForManager;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * (TUser)表服务接口
 *
 * @author makejava
 * @since 2019-09-20 14:01:19
 */
@Transactional
public interface UserService {


    User selectUserById(Integer id);

    User selectUserByNameAndPassword(String username, String password);

    Integer addUser(User user);

    Integer deleteUserById(Integer id);

    Integer updateUserById(User user);


    Integer selectAllUserCount();

    List<User> selectAllUser(Integer start, Integer size);

    /**
     * 黑名单用户数
     * @return
     */
    Integer selectUserInBlacklistCount();

    /**
     * 黑名单用户
     * @param start
     * @param size
     * @return
     */
    List<User> selectUserInBlacklist(Integer start, Integer size);

    /**
     * 管理员更新用户
     * @param user
     * @return
     */
    Integer updateUserByIdForManager(User user);

    /**
     * 管理员根据编号查询用户
     * @param id
     * @return
     */
    User selectUserByIdForManager(Integer id);
}