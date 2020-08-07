package com.xss.interceptor;

import com.xss.entity.SysResource;
import com.xss.service.SysResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author XSS
 * @date 2020/8/5
 * @desc
 */
public class ResourceInterceptor implements HandlerInterceptor {

    @Autowired
    SysResourceService sysResourceService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        List<SysResource> resourceList = sysResourceService.selectResources();

        String url = request.getRequestURI().replace(request.getContextPath() + "/", "");
        boolean flag = false; //授权标记  false表示不需要授权  true表示需要授权

        for (SysResource sysResource : resourceList) {
            if (sysResource.getUrl().equals(url)){
                flag = true;
                break;
            }
        }

        if (flag){
            List<SysResource> resources = (List<SysResource>) request.getSession().getAttribute("resources");
            for (SysResource resource : resources) {
                if (resource.getUrl().equals(url)){
                    return true;
                }
            }
            //用户无权访问
            request.getRequestDispatcher("/notauth.html").forward(request,response);
            return false;
        }


        return true;
    }
}
