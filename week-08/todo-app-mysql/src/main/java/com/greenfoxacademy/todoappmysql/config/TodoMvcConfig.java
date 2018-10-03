package com.greenfoxacademy.todoappmysql.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class TodoMvcConfig implements WebMvcConfigurer {

    @Autowired
    private TodoRequestInterceptor todoRequestInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(todoRequestInterceptor).addPathPatterns("/todo/**/");
    }

}
