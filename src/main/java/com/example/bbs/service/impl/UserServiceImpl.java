package com.example.bbs.service.impl;

import com.example.bbs.dao.*;
import com.example.bbs.dto.UserForManagerDto;
import com.example.bbs.entity.*;
import com.example.bbs.service.UserService;
import com.example.bbs.utils.UploadUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

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
    @Resource
    private PostDao postDao;
    @Resource
    private ApproveDao approveDao;
    @Resource
    private CollectDao collectDao;
    @Resource
    private FollowDao followDao;
    @Resource
    private MessageDao messageDao;
    @Resource
    private ReplyDao replyDao;
    @Resource
    private RoleDao roleDao;

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
     * @param username 用户名
     * @param password 密码
     * @return 用户
     */
    @Override
    public User selectUserByNameAndPassword(String username, String password) {
        return userDao.selectUserByNameAndPassword(username, password);
    }

    /**
     * 添加用户
     *
     * @param user 用户信息
     * @return 账号
     */
    @Override
    public Integer addUser(User user) {
        //用户名不能重复
        User checkUser = userDao.selectUserByName(user.getUsername());
        if(checkUser!=null && !checkUser.getId().equals(user.getId())){
            return -2;//名称重复
        }else{
            userDao.addUser(user);
            return user.getId();
        }

    }

    /**
     * 删除用户
     *
     * @param id 用户账号
     * @return 结果
     */
    @Override
    public Integer deleteUserById(Integer id) {
        User user = userDao.selectUserById(id);
        if(user==null){
            return -3;
        }
        postDao.deletePostByUserId(id);
        approveDao.deleteApproveByUserId(id);
        collectDao.deleteCollectByUserId(id);
        replyDao.deleteReplyByUserId(id);
        //messageDao.deleteMessageByUserId(id);
        //blacklistDao.deleteBlackListByUserId(id);
        followDao.deleteFollowByUserId(id);
        roleDao.deleteRoleByUserId(id);
        if(user.getImage()!=null ){
            UploadUtils.deleteFile(2,user.getImage());
        }
        return userDao.deleteUserById(id);
    }

    /**
     * 修改用户信息
     *
     * @param user 用户需要修改的信息
     * @return 结果
     */
    @Override
    public Integer updateUserById(User user) {
        User checkUser = userDao.selectUserById(user.getId());
        if(checkUser==null){
            return -3;
        }
        if(user.getImage()!=null && checkUser.getImage()!=null && !user.getImage().equals(checkUser.getImage())){
            UploadUtils.deleteFile(2,checkUser.getImage());
        }
        if(!checkUser.getUsername().equals(user.getUsername())){
            //检查用户名重复
            User  checkName = userDao.selectUserByName(user.getUsername());
            if(checkName!=null){
                return -2;//用户名重复
            }
        }
        return userDao.updateUserById(user);
    }

    /**
     * 修改用户信息
     *
     * @param user 用户需要修改的信息
     * @return 结果
     */
    @Override
    public Integer updateUserByIdForManager(User user) {
        User checkUser = userDao.selectUserById(user.getId());
        if(checkUser==null){
            return -3;
        }
        if(user.getImage()!=null && checkUser.getImage()!=null && !user.getImage().equals(checkUser.getImage())){
            UploadUtils.deleteFile(2,checkUser.getImage());
        }
        if(!checkUser.getUsername().equals(user.getUsername())){
            //检查用户名重复
            User  checkName = userDao.selectUserByName(user.getUsername());
            if(checkName!=null){
                return -2;//用户名重复
            }
        }
        user.setId(checkUser.getId());
        return userDao.updateUserById(user);
    }

    /**
     * 管理员根据编号查询用户
     *
     * @param id
     * @return
     */
    @Override
    public User selectUserByIdForManager(Integer id) {
        return userDao.selectUserByIdForManager(id);
    }

    /**
     * 根据用户名查看
     *
     * @param username
     * @return
     */
    @Override
    public User selectUserByUsername(String username) {
        return userDao.selectUserByName(username);
    }

    @Override
    public Integer selectAllUserCount() {
        return userDao.selectAllUserCount();

    }

    @Override
    public List<User> selectAllUser(Integer start, Integer size) {
        return userDao.selectAllUser(start,size);
    }

    /**
     * 黑名单用户数
     *
     * @return
     */
    @Override
    public Integer selectUserInBlacklistCount() {
        return userDao.selectUserInBlacklistCount();
    }

    /**
     * 黑名单用户
     *
     * @param start
     * @param size
     * @return
     */
    @Override
    public List<User> selectUserInBlacklist( Integer start, Integer size) {
        return userDao.selectUserInBlacklist(start,size);
    }


}