package com.xss.controller;

import com.xss.entity.ExamineMethod;
import com.xss.entity.Result;
import com.xss.service.ExamineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author XSS
 * @date 2020/7/21
 * @desc
 */
@RestController
@RequestMapping("manager/examine")
public class ExamineController {

    @Autowired
    ExamineService service;


    @RequestMapping("index")
    public ModelAndView index(){
        return new ModelAndView("examine/index");
    }

    @RequestMapping(value = "selectPage/{pageNum}/{pageSize}",method = RequestMethod.POST)
    public Result selectPage(@PathVariable("pageNum") Integer pageNum,@PathVariable("pageSize") Integer pageSize, @RequestBody ExamineMethod examineMethod){

        return new Result(true,"查询成功",service.selectPage(pageNum,pageSize,examineMethod));
    }
}
