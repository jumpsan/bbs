package com.example.bbs.interceptor;

import com.example.bbs.entity.Admin;
import com.example.bbs.entity.Plate;
import com.example.bbs.service.AdminService;
import com.example.bbs.service.RoleService;
import com.example.bbs.utils.JjwtUtils;
import io.jsonwebtoken.Claims;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Component
public class ManagerInterceptor implements HandlerInterceptor {
    @Resource
    RoleService roleService;
    @Resource
    AdminService adminService;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("ManagerInterceptor...");
        String token = request.getHeader("token");
        //String adminToken=request.getHeader("adminToken");
        if(token==null){
            return false;
        }
        //解析
        Claims claims=null;
        Admin admin=null;
        if(token!=null){
            claims = JjwtUtils.parseJWT(token);
        }
        List plates=null;
        if(claims!=null){
            Integer userId = (Integer)claims.get("userId");
            plates= roleService.selectRoleByUserId(userId);
            admin = adminService.selectAdminById(userId);
        }
        if(admin==null && (plates==null || plates.size()<=0)){
            return false;
        }else{
            return true;
        }
    }
}
