package com.xss.controller;


import com.xss.entity.Result;
import com.xss.entity.SysResource;
import com.xss.service.SysResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author XSS
 * @date 2020/8/5
 * @desc
 */
@RestController
@RequestMapping("manager/menu")
public class SysResourceController {

    @Autowired
    SysResourceService service;


    /**
     * 查询所有的资源
     * @return
     */
    @RequestMapping("list")
    public Result list(){
        SysResource sysResource = new SysResource();
        sysResource.setDelFlag("0");
        return new Result(true,"查询成功",service.select(sysResource));
    }

    @RequestMapping("selectByRid")
    public Result selectByRid(long rid){
        return new Result(true,"查询成功",service.selectByRid(rid));
    }

    @RequestMapping("selectResources")
    public Result select(){
        return new Result(true,"操作成功",service.selectResources());
    }
}
