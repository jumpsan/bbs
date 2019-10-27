package com.example.bbs.config;


import com.example.bbs.interceptor.AdminInterceptor;
import com.example.bbs.interceptor.LoginInterceptor;
import com.example.bbs.interceptor.ManagerInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;


@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
    @Resource
    AdminInterceptor adminInterceptor;
    @Resource
    LoginInterceptor loginInterceptor;
    @Resource
    ManagerInterceptor managerInterceptor;
    public void addInterceptors(InterceptorRegistry registry) {
        System.out.println("拦截器注册");
        registry.addInterceptor(loginInterceptor).addPathPatterns("/**").excludePathPatterns("/admin/login").excludePathPatterns("/user/login").excludePathPatterns("/user/register").excludePathPatterns("/post/select/**");
        registry.addInterceptor(managerInterceptor).addPathPatterns("/manager/**");
        registry.addInterceptor(adminInterceptor).addPathPatterns("/admin/**").excludePathPatterns("/admin/login");
    }
}
