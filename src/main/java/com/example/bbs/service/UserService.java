package com.example.bbs.service;

import com.example.bbs.entity.User;
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
}