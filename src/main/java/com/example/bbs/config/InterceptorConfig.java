package com.example.bbs.config;


import com.example.bbs.interceptor.AdminInterceptor;
import com.example.bbs.interceptor.LoginInterceptor;
import com.example.bbs.interceptor.ManagerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
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
                .excludePathPatterns("/reply/select/**").excludePathPatterns("/plate/search/**").excludePathPatterns("/section/select/**").excludePathPatterns("/user/select/**")
                .excludePathPatterns("/image/**").excludePathPatterns("/video/**").excludePathPatterns("/head/**").excludePathPatterns("/comment/select/**");
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

    //静态资源映射
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 当访问/file下的资源时，会到/root/myFile/下去找
        // 例如访问：http://localhost:8080/file/1.png时会去找/root/myFile/1.png
        registry.addResourceHandler("/image/**").addResourceLocations("file:C:\\java\\images\\");
        registry.addResourceHandler("/video/**").addResourceLocations("file:C:\\java\\videos\\");
        registry.addResourceHandler("/head/**").addResourceLocations("file:C:\\java\\head\\");
    }
}
