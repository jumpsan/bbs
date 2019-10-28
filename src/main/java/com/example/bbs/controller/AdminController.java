package com.example.bbs.controller;

import com.example.bbs.entity.Admin;
import com.example.bbs.entity.Information;
import com.example.bbs.service.AdminService;
import com.example.bbs.utils.JjwtUtils;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;


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
    private AdminService adminService;

    /**
     * 根据管理员账号和密码查询
     * 管理员登陆并且将其添加到拦截器
     * @param name   用户名
     * @param password 密码
     * @return 管理员
     */
    @PostMapping("/login")
    public Information selectAdminByNameAndPassword(String name, String password) {
        Admin admin = adminService.selectAdminByNameAndPassword(name, password);
        if (admin == null) {
            return Information.error(202,"用户名或密码错误");
        } else {
            try {
                Integer adminId=admin.getId();
                String token = JjwtUtils.createJWT(adminId, 15 * 60 * 1000);
                return Information.success(200,"登陆成功",token);
            } catch (Exception e) {
                e.printStackTrace();
                return Information.error(404,"获取token出错，重试");
            }
        }
    }

    //废弃
//    /**
//     * 根据账号查询
//     *
//     * @param id 账号
//     * @return 结果
//     */
//    @GetMapping("selectAdminById")
//    public Information<Admin> selectAdminById(Integer id) {
//        Admin admin = AdminService.selectAdminById(id);
//        Information<Admin> information=new Information<>();
//        return information;
//    }

    /**
     * 添加管理员
     *
     * @param admin 管理员信息
     * @return 账号
     */
    @PostMapping("/add")
    public Information addAdmin(Admin admin) {
        if(admin.getName()==null || "".equals(admin.getName().trim())|| admin.getPassword()==null || "".equals(admin.getPassword().trim())){
            return Information.error(406,"关键信息不可为空");
        }else{
            Integer pwdLength=admin.getPassword().trim().length();
            if(pwdLength<6 || pwdLength>=12){
                return Information.error(400,"密码长度必须在6-12之间");
            }
            Integer result = adminService.addAdmin(admin);
            if(result>0){
                admin.setPassword("*");
                return Information.success(200,"添加成功",admin);
            }else if(result==-2){
                return Information.error(402,"名称重复");
            }else{
                return Information.error(400,"添加失败");
            }
        }
    }

    /**
     * 根据账号删除管理员
     *
     * @return 结果
     */
    @GetMapping("delete")
    public Information deleteAdminById(HttpServletRequest request) {
        Integer userId = (Integer)request.getAttribute("userId");
        Integer result = adminService.deleteAdminById(userId);
        if(result<=0){
            return Information.error(400,"删除");
        }else{
            return Information.success("删除");
        }
    }
}