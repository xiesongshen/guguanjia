package com.xss.controller;


import com.xss.entity.Result;
import com.xss.entity.SysRole;
import com.xss.entity.SysUser;
import com.xss.service.SysRoleService;
import com.xss.service.SysUserService;
import com.xss.utils.EncryptUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;

@RestController
@RequestMapping("/manager/sysuser")  //由于tomcat默认会部署一个叫 manager的应用
public class SysUserController {

    @Autowired
    SysUserService service;

    @Autowired
    SysRoleService sysRoleService;

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

    @RequestMapping("toUpdate")
    public ModelAndView toUpdate(){
        return new ModelAndView("user/user-save");
    }

    @RequestMapping(value = "doUpdate", method = RequestMethod.PUT)
    public Result doUpdate(@RequestBody SysUser user) {
        String password = user.getPassword();
        String username = user.getUsername();
        user.setPassword(EncryptUtils.MD5_HEX(EncryptUtils.MD5_HEX(password)+username));
        user.setUpdateDate(new Date());
        return new Result(true, "更新成功", service.updateByPrimaryKeySelective(user));
    }

    @RequestMapping(value = "save", method = RequestMethod.POST)
    public Result save(@RequestBody SysUser user) {
        String password = user.getPassword();
        String name = user.getName();
        user.setUsername(name);
        Long roleId = user.getRoleId();
        SysRole sysRole = sysRoleService.selectByPrimaryKey(roleId);
        user.setOfficeId(sysRole.getOfficeId());
        user.setCompanyId(sysRole.getOfficeId());
        user.setPassword(EncryptUtils.MD5_HEX(EncryptUtils.MD5_HEX(password)+user.getUsername()));
        user.setCreateDate(new Date());
        user.setUpdateDate(new Date());
        return new Result(true, "添加成功", service.insertSelective(user));
    }

}
