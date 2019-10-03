package com.example.bbs.service.impl;

import com.example.bbs.entity.User;
import com.example.bbs.dao.UserDao;
import com.example.bbs.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * (TUser)表服务实现类
 *
 * @author makejava
 * @since 2019-09-20 14:01:19
 */
@Service("tUserService")
public class UserServiceImpl implements UserService {
    @Resource
    private UserDao userDao;

    /**
     * 通过账号选择用户
     *
     * @param id 账号
     * @return 用户
     */
    @Override
    public User selectUserById(Integer id) {
        return userDao.selectUserById(id);
    }

    /**
     * 通过账号和密码查找
     *
     * @param id       账号
     * @param password 密码
     * @return 用户
     */
    @Override
    public User selectUserByIdAndPassword(Integer id, String password) {
        return userDao.selectUserByIdAndPassword(id, password);
    }

    /**
     * 添加用户
     *
     * @param user 用户信息
     * @return 账号
     */
    @Override
    public Integer addUser(User user) {
        return userDao.addUser(user);
    }

    /**
     * 删除用户
     *
     * @param id 用户账号
     * @return 结果
     */
    @Override
    public boolean deleteUserById(Integer id) {
        return userDao.deleteUserById(id) > 0;
    }

    /**
     * 修改用户信息
     *
     * @param user 用户需要修改的信息
     * @return 结果
     */
    @Override
    public boolean updateUserById(User user) {
        return userDao.updateUserById(user) > 0;
    }


}