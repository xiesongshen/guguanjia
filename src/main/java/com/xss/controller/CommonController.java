package com.xss.controller;

import com.xss.entity.Result;
import com.xss.entity.SysResource;
import com.xss.entity.SysUser;
import com.xss.service.SysResourceService;
import com.xss.service.SysUserService;
import com.xss.utils.EncryptUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author XSS
 * @date 2020/7/17
 * @desc
 */
@Controller
@RequestMapping("common")
public class CommonController {
    @Autowired
    SysUserService service;

    @Autowired
    SysResourceService sysResourceService;

    @RequestMapping("navbar")
    public String navbar(){

        return "common/navbar";
    }

    @RequestMapping("sidebar")
    public String sidebar(){

        return "common/sidebar";
    }

    @RequestMapping("login")
    @ResponseBody
    public Result login(String username, String password, String code, @SessionAttribute("checkCode") String checkCode,HttpSession session){

        if (checkCode.equals(code)){
            SysUser user = new SysUser();
            user.setUsername(username);
            user.setPassword(EncryptUtils.MD5_HEX(EncryptUtils.MD5_HEX(password)+username));

            SysUser one = service.selectOne(user);

            if (one!=null){
                List<SysResource> resourceList = sysResourceService.getResourcesByUid(one.getId());
                one.setPassword(null);
                one.setResourceList(resourceList);
                session.setAttribute("loginUser",one);
                session.setAttribute("resources",resourceList);
                return new Result(true,"登陆成功",one);
            }
        }
        return new Result(false,"账号密码或者验证码错误",null);
    }

    @RequestMapping("toIndex")
    public ModelAndView toIndex(){
        return new ModelAndView("index");
    }

    @RequestMapping("logOut")
    public String logOut(HttpSession session){
        session.invalidate();

        return "redirect:/login.html";
    }


}
