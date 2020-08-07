package com.xss.config;

import com.xss.interceptor.LoginInterceptor;
import com.xss.interceptor.ResourceInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

/**
 * @author XSS
 * @date 2020/7/16
 * @desc
 */
@Configuration
@ComponentScan({"com.xss.controller","com.xss.aspect"})
@EnableWebMvc
@EnableAspectJAutoProxy
public class SpringMvcConfig implements WebMvcConfigurer {

    @Autowired
    private ResourceInterceptor resourceInterceptor;

    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

    @Bean
    public InternalResourceViewResolver internalResourceViewResolver(){
        return new InternalResourceViewResolver("/WEB-INF/html/",".html");
    }

    @Bean("multipartResolver")
    public CommonsMultipartResolver getMultipartResolver(){
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
        return multipartResolver;
    }

    //注册拦截器，配置拦截和放行规则

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        LoginInterceptor loginInterceptor = new LoginInterceptor();
        InterceptorRegistration registration = registry.addInterceptor(loginInterceptor);//登录拦截注册处理对象
        registration.addPathPatterns("/**");//设置拦截逻辑
        registration.excludePathPatterns("/common/login","/common/logOut");//设置放行逻辑

        InterceptorRegistration interceptorRegistration = registry.addInterceptor(resourceInterceptor);
        interceptorRegistration.addPathPatterns("/manager/**");
    }

}
