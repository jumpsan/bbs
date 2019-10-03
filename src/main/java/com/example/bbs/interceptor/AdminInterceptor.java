package com.example.bbs.interceptor;

import com.example.bbs.utils.JjwtUtils;
import io.jsonwebtoken.Claims;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 管理员登陆拦截器
 */

public class AdminInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Cookie[] cookies = request.getCookies();
        String token="";
        for (Cookie cookie:cookies){
            if(cookie.getName().equals("token")){
                token=cookie.getValue();
            }
        }
        //解析
        Claims claims = JjwtUtils.parseJWT(token);
        return claims!=null;
    }
}
