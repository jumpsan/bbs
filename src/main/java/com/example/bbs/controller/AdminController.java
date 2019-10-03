package com.example.bbs.controller;

import com.example.bbs.entity.Admin;
import com.example.bbs.entity.Information;
import com.example.bbs.service.AdminService;
import com.example.bbs.utils.JjwtUtils;

import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;


/**
 * (TAdmin)表控制层
 *
 * @author makejava
 * @since 2019-09-22 13:37:51
 */
@RestController
@RequestMapping("admin")
public class AdminController {
    /**
     * 服务对象
     */
    @Resource
    private AdminService AdminService;

    /**
     * 根据管理员账号和密码查询
     * 管理员登陆并且将其添加到拦截器
     * @param name   用户名
     * @param password 密码
     * @return 管理员
     */
    @PostMapping("/login")
    public Information<Admin> selectAdminByNameAndPassword(String name, String password, HttpServletResponse response) {
        Admin admin = AdminService.selectAdminByNameAndPassword(name, password);
        String msg = "";
        Integer status=202;
        Information<Admin> information =new Information<>();
        if (admin == null) {
            msg = "用户名或密码错误";
        } else {
            //todo
            try {
                Integer adminId=admin.getId();
                String token = JjwtUtils.createJWT(adminId, 15 * 60 * 1000);
                msg = "登陆成功";
                status=200;
                Cookie cookie=new Cookie("token",token);
                response.addCookie(cookie);
                System.out.println(token);
                admin.setPassword("*");
                information.setData(admin);
                information.setMsg(msg);
                information.setStatus(status);
            } catch (Exception e) {
                e.printStackTrace();
                information.setData(null);
                information.setMsg("发生错误，重试");
                information.setStatus(404);
            }

        }


        return information;
    }

    /**
     * 根据账号查询
     *
     * @param id 账号
     * @return 结果
     */
    @GetMapping("selectAdminById")
    public Admin selectAdminById(Integer id) {
        return AdminService.selectAdminById(id);
    }

    /**
     * 添加管理员
     *
     * @param admin 管理员信息
     * @return 账号
     */
    @PostMapping("addAdmin")
    public Integer addAdmin(Admin admin) {
        admin = AdminService.addAdmin(admin);
        return admin.getId();
    }

    /**
     * 根据账号删除管理员
     *
     * @param id 账号
     * @return 结果
     */
    @GetMapping("deleteAdminById")
    public boolean deleteAdminById(Integer id) {
        return AdminService.deleteAdminById(id);
    }


}