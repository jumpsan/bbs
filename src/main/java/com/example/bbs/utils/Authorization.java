package com.example.bbs.utils;

import com.example.bbs.entity.Admin;
import com.example.bbs.entity.Plate;
import com.example.bbs.entity.User;
import com.example.bbs.service.AdminService;
import com.example.bbs.service.RoleService;
import com.example.bbs.service.UserService;
import io.jsonwebtoken.Claims;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
public class Authorization {

    @Resource
    private static AdminService adminService;
    @Resource
    private  static UserService userService;
    @Resource
    private  static RoleService roleService;


    public static boolean verify(HttpServletRequest request,Integer id) throws Exception {
        Integer userId = getId(request);
        if(userId!=null && userId.equals(id)){
            return true;
        }
        return false;
    }
//    public static boolean verifyAdmin(HttpServletRequest request,String name) throws Exception {
//        Integer userId = getId(request);
//        Admin admin = adminDao.selectAdminByName(name);
//        if(userId!=null && admin!=null && userId.equals(admin.getId())){
//            return true;
//        }
//        return false;
//    }
//    public static boolean verifyUser(HttpServletRequest request,String name) throws Exception {
//        Integer userId = getId(request);
//        User user = userDao.selectUserByName(name);
//        if(userId!=null && user!=null && userId.equals(user.getId())){
//            return true;
//        }
//        return false;
//    }

    public static boolean verifyAdmin(HttpServletRequest request) throws Exception {
        Integer userId=getId(request);
        if(userId==null){
            return false;
        }
        System.out.println(adminService);
        Admin admin = adminService.selectAdminById(userId);
        return admin!=null;
    }
    public static boolean verifyManager(HttpServletRequest request) throws Exception {
        Integer userId=getId(request);
        if(userId==null){
            return false;
        }
        List<Plate> plates = roleService.selectRoleByUserId(userId);
        return plates!=null && plates.size()>0;
    }
    public static boolean verifyUser(HttpServletRequest request)throws Exception{
        Integer userId=getId(request);
        if(userId==null){
            return false;
        }
        User user = userService.selectUserById(userId);
        return user!=null;
    }

    private static Integer getId(HttpServletRequest request)throws Exception{
        String token=request.getHeader("token");
        Integer id=null;
        if(token!=null){
            Claims claims = JjwtUtils.parseJWT(token);
            if(claims!=null){
                id= (Integer) claims.get("userId");
            }
        }
        return id;
    }
}

