package com.base.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        InterceptorRegistration registration = registry.addInterceptor(new TokenInterceptor());
//        registration.addPathPatterns("/**");
//    }
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // 所有接口
                .allowCredentials(true) // 是否发送 Cookie
                .allowedOrigins("*")// 支持域
                .allowedMethods(new String[]{"GET", "POST", "PUT", "DELETE"}) // 支持方法
                .allowedHeaders("*")
                .exposedHeaders("*");
    }
}
