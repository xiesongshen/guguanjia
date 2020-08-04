package com.xss.controller;


import com.xss.entity.Result;
import com.xss.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
