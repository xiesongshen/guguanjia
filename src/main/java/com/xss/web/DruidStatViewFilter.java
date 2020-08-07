package com.xss.web;

import com.alibaba.druid.support.http.StatViewServlet;

import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;

/**
 * 继承显示servlet，设置初始化参数，druid会自动生成登录/显示监控页面
 */
@WebServlet(urlPatterns = "/druid/*",initParams = {
        @WebInitParam(name="loginUsername",value = "druid"),
        @WebInitParam(name="loginPassword",value = "123")
})
public class DruidStatViewFilter extends StatViewServlet {
}
