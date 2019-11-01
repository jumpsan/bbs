package com.example.bbs.interceptor;

import com.example.bbs.entity.Information;
import com.example.bbs.utils.JjwtUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 管理员登陆拦截器
 */
@Component
public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler){
        String token = request.getHeader("token");
        System.out.println("LoginInterceptor...");
        if(token==null){
            return false;
        }else{
            try{
                //解析
                Claims claims=null;
                claims= JjwtUtils.parseJWT(token);
                if(claims!=null){
                    Integer userId= (Integer) claims.get("userId");
                    System.out.println(userId);
                    request.setAttribute("userId",userId);
                    return true;
                }
                return false;
            }catch (UnsupportedJwtException e){
                try {
                    response.setCharacterEncoding("UTF-8");
                    response.getWriter().write(new ObjectMapper().writeValueAsString(Information.error(401,"token不存在")));
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                e.printStackTrace();
                return false;
            }catch (ExpiredJwtException e){
                try {
                    response.setCharacterEncoding("UTF-8");
                    response.getWriter().write(new ObjectMapper().writeValueAsString(Information.error(401,"token过期")));
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                e.printStackTrace();
                return false;
            }
        }
    }
}
