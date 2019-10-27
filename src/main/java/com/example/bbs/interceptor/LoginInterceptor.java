package com.example.bbs.interceptor;

import com.example.bbs.utils.JjwtUtils;
import io.jsonwebtoken.Claims;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
                if(token!=null){
                    claims= JjwtUtils.parseJWT(token);
                }
                if(claims!=null){
                    Integer userId= (Integer) claims.get("userId");
                    request.setAttribute("userId",userId);
                    return true;
                }
                return false;
            }catch (Exception e){
                e.printStackTrace();
                return false;
            }
        }
    }
}
