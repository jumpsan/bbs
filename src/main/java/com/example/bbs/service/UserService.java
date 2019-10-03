package com.example.bbs.service;

import com.example.bbs.entity.User;
import org.springframework.transaction.annotation.Transactional;

/**
 * (TUser)表服务接口
 *
 * @author makejava
 * @since 2019-09-20 14:01:19
 */
@Transactional
public interface UserService {


    User selectUserById(Integer id);

    User selectUserByIdAndPassword(Integer id, String password);

    Integer addUser(User user);

    boolean deleteUserById(Integer id);

    boolean updateUserById(User user);
}