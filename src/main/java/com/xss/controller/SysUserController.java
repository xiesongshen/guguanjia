package com.xss.controller;


import com.xss.entity.Result;
import com.xss.entity.SysUser;
import com.xss.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/manager/sysuser")  //由于tomcat默认会部署一个叫 manager的应用
public class SysUserController {

    @Autowired
    SysUserService service;

    @RequestMapping("/selectByRid")
    public Result selectByRid(long rid){
        return new Result(true,"查询成功",service.selectByRid(rid));
    }

    @RequestMapping("selectNoRole")
    public Result selectNoRole(long oid,long rid){
        return new Result(true,"查询成功",service.selectNoRole(oid,rid));
    }

    @RequestMapping("")
    public ModelAndView toIndex(){
        return new ModelAndView("user/user");
    }

    @RequestMapping("selectPage/{pageNum}/{pageSize}")
    public Result selectPage(@PathVariable("pageNum") Integer pageNum, @PathVariable("pageSize") Integer pageSize, @RequestBody SysUser sysUser) {
        return new Result(true, "查询成功", service.selectPage(pageNum,pageSize,sysUser));
    }

    /*@RequestMapping("toUpdate")
    public ModelAndView toUpdate(){
        return new ModelAndView("")
    }*/

}
