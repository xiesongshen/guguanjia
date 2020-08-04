package com.xss.controller;

import com.xss.entity.Result;
import com.xss.service.WorkOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

/**
 * @author XSS
 * @date 2020/7/21
 * @desc
 */
@RestController
@RequestMapping("manager")
public class WorkOrderController {

    @Autowired
    WorkOrderService service;


    @RequestMapping("admin/work")
    public ModelAndView index(){
        return new ModelAndView("work/admin/index");
    }

    @RequestMapping(value = "admin/work/selectPage/{pageNum}/{pageSize}",method = RequestMethod.POST)
    public Result selectPage(@PathVariable("pageNum") Integer pageNum,@PathVariable("pageSize") Integer pageSize, @RequestBody Map<String,Object> map){

        return new Result(true,"查询成功",service.selectPage(pageNum,pageSize,map));
    }

    @RequestMapping("admin/toDetails")
    public ModelAndView detail(){
        return new ModelAndView("work/work-detail");
    }

    @RequestMapping("admin/selectDetails")
    public Result selectDetails(long id){
        return new Result(true,"查询成功",service.selectDetail(id));
    }
}
