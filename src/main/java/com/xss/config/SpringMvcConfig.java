package com.xss.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

/**
 * @author XSS
 * @date 2020/7/16
 * @desc
 */
@Configuration
@ComponentScan("com.xss.controller")
@EnableWebMvc
public class SpringMvcConfig implements WebMvcConfigurer {

    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

    @Bean
    public InternalResourceViewResolver internalResourceViewResolver(){
        return new InternalResourceViewResolver("/WEB-INF/html/",".html");
    }

    //注册拦截器，配置拦截和放行规则

   /* @Override
    public void addInterceptors(InterceptorRegistry registry) {
        LoginInterceptor loginInterceptor = new LoginInterceptor();
        InterceptorRegistration registration = registry.addInterceptor(loginInterceptor);//登录拦截注册处理对象
        registration.addPathPatterns("/**");//设置拦截逻辑
        registration.excludePathPatterns("/common/login","/common/logOut");//设置放行逻辑
    }*/

}
