package com.xss.aspect;

import com.xss.entity.SysLog;
import com.xss.entity.SysUser;
import com.xss.service.SysLogService;
import com.xss.utils.IPUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * @author XSS
 * @date 2020/8/6
 * @desc
 */
@Component
@Aspect
public class LogAspect {

    @Autowired
    SysLogService service;

    //spring会自动封装request对象到容器中
    @Autowired
    HttpServletRequest request;

    @Pointcut("within(com.xss.controller.*Controller)")
    public void pointCut() {}


    @AfterReturning(pointcut="pointCut()",returning = "o")
    public Object after(JoinPoint joinPoint,Object o) {
        String name = joinPoint.getTarget().getClass().getName();
        if (!name.equals("SysLogServiceImpl")){
            saveLog(joinPoint,null);
        }
        return o;
    }


    @AfterThrowing(pointcut = "pointCut()", throwing = "e")
    public void afterThrowing(JoinPoint joinPoint, Exception e) {
        saveLog(joinPoint,e);
    }

    private void saveLog(JoinPoint joinPoint, Exception e) {
        SysLog sysLog = new SysLog();
        sysLog.setType(e == null ? "1" : "2");
        sysLog.setException(e == null ? "" : e.toString());
        sysLog.setCreateDate(new Date());

        if (request != null) {
            SysUser loginUser = (SysUser) request.getSession().getAttribute("loginUser");

            if (loginUser != null) {
                sysLog.setCreateBy(loginUser.getName());
            }


            sysLog.setRemoteAddr(IPUtils.getClientAddress(request));//获取ip

            sysLog.setUserAgent(request.getHeader("user-agent"));

            sysLog.setRequestUri(request.getRequestURI());

            sysLog.setMethod(request.getMethod());
        }

        Object[] args = joinPoint.getArgs();

        if (args.length > 0 && args != null) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < args.length; i++) {
                Object arg = args[i];
                if (arg != null) {
                    String typeName = arg.getClass().getSimpleName();
                    sb.append("[参数").append(i + 1).append(",类型:").append(typeName).append(",值:").append(arg.toString()).append("]");
                } else {
                    sb.append("[参数").append(i + 1).append(",值:null]");
                }

                sb.append(",");
            }
            sb.deleteCharAt(sb.length()-1);
            sysLog.setParams(sb.toString());
        }

        service.insertSelective(sysLog);
    }
}
