package com.xss.web;

import com.alibaba.druid.support.http.WebStatFilter;

import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;

/**
 * 自定义过滤器  继承 WebStatFilter  实现web监控配置
 */
@WebFilter(urlPatterns = "/*",initParams = {
        @WebInitParam(name = "profileEnable",value = "true"),
        @WebInitParam(name = "sessionStatEnable",value = "true"),//session监控
        @WebInitParam(name = "exclusions",value = "*.js,*.css,*.png,*.jpg")//忽略监控


})
public class DruidWebStateFilter extends WebStatFilter {
}
