package com.example.bbs.config;


import com.example.bbs.interceptor.AdminInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class InterceptorConfig implements WebMvcConfigurer {


    @Bean
    public AdminInterceptor getMyInterceptor(){
        return  new AdminInterceptor();
    }
    /**
     * 拦截器注册类
     * @param registry 注册拦截器
     */
    public void addInterceptor(InterceptorRegistry  registry){
        System.out.println("拦截器");
        registry.addInterceptor(this.getMyInterceptor()).addPathPatterns("/**");
    }
}
