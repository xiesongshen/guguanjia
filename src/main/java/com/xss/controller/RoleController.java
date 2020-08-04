package com.xss.controller;

import com.xss.entity.Result;
import com.xss.entity.SysRole;
import com.xss.service.SysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Arrays;
import java.util.List;

/**
 * @author XSS
 * @date 2020/7/21
 * @desc
 */
@RestController
@RequestMapping("manager/role")
public class RoleController {

    @Autowired
    SysRoleService service;


    @RequestMapping("role")
    public ModelAndView index() {
        return new ModelAndView("role/role");
    }

    @RequestMapping(value = "selectPage/{pageNum}/{pageSize}", method = RequestMethod.POST)
    public Result selectPage(@PathVariable("pageNum") Integer pageNum, @PathVariable("pageSize") Integer pageSize, @RequestBody SysRole sysRole) {
        return new Result(true, "查询成功", service.selectPage(pageNum, pageSize, sysRole));
    }
    @RequestMapping("toUser")
    public ModelAndView toUser(){
        return new ModelAndView("role/role-user");
    }

    @RequestMapping("insertBatch")
    public Result insertBatch(long rid, Long[] cids){
        /*List<Long> list = new ArrayList<>();
        list.addAll()*/
        List<Long> list = Arrays.asList(cids);
        return new Result(true,"角色添加人员成功",service.insertBatch(rid,list));
    }
    @RequestMapping("deleteBatch")
    public Result selectDetail(long rid ,long[] ids){
        return new Result(true,"移除人员成功",service.deleteBatch(rid,ids));
    }
}
