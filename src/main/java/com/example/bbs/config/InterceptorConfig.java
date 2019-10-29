package com.example.bbs.config;


import com.example.bbs.interceptor.AdminInterceptor;
import com.example.bbs.interceptor.LoginInterceptor;
import com.example.bbs.interceptor.ManagerInterceptor;
import org.apache.catalina.filters.CorsFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
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
        registry.addInterceptor(loginInterceptor).addPathPatterns("/**").excludePathPatterns("/admin/login").excludePathPatterns("/user/login").excludePathPatterns("/user/register").excludePathPatterns("/post/select/**")
                .excludePathPatterns("/reply/select/**").excludePathPatterns("/plate/search/**").excludePathPatterns("/section/select/**").excludePathPatterns("/user/select/**");
        registry.addInterceptor(managerInterceptor).addPathPatterns("/manager/**");
        registry.addInterceptor(adminInterceptor).addPathPatterns("/admin/**").excludePathPatterns("/admin/login");
    }

    //跨域设置
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowCredentials(false)
                        .allowedMethods("POST", "GET", "PUT", "OPTIONS", "DELETE")
                        .allowedOrigins("*");
            }
        };
    }

}
