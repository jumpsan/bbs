package com.example.bbs.interceptor;

import com.example.bbs.entity.Admin;
import com.example.bbs.service.AdminService;
import com.example.bbs.utils.JjwtUtils;
import io.jsonwebtoken.Claims;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class AdminInterceptor implements HandlerInterceptor {
    @Resource
    AdminService adminService;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("AdminInterceptor...");
        String adminToken = request.getHeader("token");
        if(adminToken==null){
            return false;
        }
        //解析
        Claims claims = JjwtUtils.parseJWT(adminToken);
        if(claims==null){
            return false;
        }
        Integer userId = (Integer)claims.get("userId");
        Admin admin = adminService.selectAdminById(userId);
        if(admin==null){
            return false;
        }else{
            return true;
        }
    }
}
