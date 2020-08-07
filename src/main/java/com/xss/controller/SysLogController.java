package com.xss.controller;


import com.xss.entity.Result;
import com.xss.entity.SysLog;
import com.xss.service.SysLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("manager/syslog")  //由于tomcat默认会部署一个叫 manager的应用
public class SysLogController {

    @Autowired
    SysLogService service;

    @RequestMapping("")
    public ModelAndView toLog(){
        return new ModelAndView("log/log");
    }

    @RequestMapping("selectPage/{pageNum}/{pageSize}")
    public Result selectPage(@PathVariable("pageNum") Integer pageNum, @PathVariable("pageSize") Integer pageSize, @RequestBody SysLog sysLog) {
        return new Result(true, "查询成功",service.selectPage(pageNum,pageSize,sysLog));
    }

    @RequestMapping("toDetail")
    public ModelAndView toDetail(){
        return new ModelAndView("log/log-detail");
    }

    @RequestMapping("doDel")
    public Result doDel(Long id){
        return new Result(true,"删除成功",service.deleteByPrimaryKey(id));
    }
}
