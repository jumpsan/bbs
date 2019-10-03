package com.example.bbs.controller;

import com.example.bbs.entity.Information;
import com.example.bbs.entity.User;
import com.example.bbs.service.RoleService;
import com.example.bbs.service.UserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

/**
 * (TUser)表控制层
 *
 * @author makejava
 * @since 2019-09-20 14:01:19
 */
@RestController
@RequestMapping("tUser")
public class UserController {
    /**
     * 服务对象
     */
    @Resource
    private UserService userService;

    @Resource
    private RoleService roleService;

    /**
     * 根据账号查询
     *
     * @param id
     * @return
     */
    @PostMapping("selectUserById")
    public User selectUserById(Integer id) {
        return userService.selectUserById(id);
    }

    /**
     * 根据账号密码查询
     *
     * @param id       账号
     * @param password 密码
     * @return 用户
     */
    @PostMapping("selectUserByIdAndPassword")
    public Information<User> selectUserByIdAndPassword(Integer id, String password, HttpSession session) {
        User user = userService.selectUserByIdAndPassword(id, password);
        String msg = "";
        if (user == null) {
            msg = "error";
        } else {
            msg = "success";
            if(roleService.selectRoleByUserId(user.getId())==null){
                session.setAttribute("role_session", user);
            }else{
                session.setAttribute("user_session",user);
            }
        }
        Information<User> information =new Information<>();
        information.setData(user);
        information.setMsg(msg);
        return information;
    }

    /**
     * 添加用户
     *
     * @param user 用户信息
     * @return 账号
     */
    @PostMapping("addUser")
    public Integer addUser(User user) {
        return userService.addUser(user);
    }

    /**
     * 根据账号删除用户
     *
     * @param id 账号
     * @return 结果
     */
    @GetMapping("deleteUser")
    public boolean deleteUser(Integer id) {
        return userService.deleteUserById(id);
    }

    /**
     * 修改用户信息
     *
     * @param user 用户
     * @return 结果
     */
    @PostMapping("updateUser")
    public boolean updateUser(User user) {
        return userService.updateUserById(user);
    }


}