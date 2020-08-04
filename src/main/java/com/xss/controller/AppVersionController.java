package com.xss.controller;

import com.xss.entity.AppVersion;
import com.xss.entity.Result;
import com.xss.service.AppVersionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;


/**
 * @author XSS
 * @date 2020/7/16
 * @desc
 */

@RestController
@RequestMapping("manager/app")
public class AppVersionController {

    @Autowired
    AppVersionService service;

    @RequestMapping("index")
    public ModelAndView toIndex() {

        return new ModelAndView("app/index");
    }

    @RequestMapping("selectAll")
    public Result selectAll() {
        return new Result(true, "查询成功", service.selectAll());
    }


    @RequestMapping("selectPage/{pageNum}/{pageSize}")
    public Result selectPage(@PathVariable("pageNum") Integer pageNum, @PathVariable("pageSize") Integer pageSize) {
        return new Result(true, "查询成功", service.selectPage(pageNum, pageSize));
    }

    @RequestMapping("del")
    public Result del(Integer id) {
        System.out.println(id);
        return new Result(true, "删除成功", service.deleteByPrimaryKey(id));
    }

    @RequestMapping("toUpdate")
    public ModelAndView toUpdate() {
        return new ModelAndView("app/update");
    }

    @RequestMapping(value = "doUpdate", method = RequestMethod.PUT)
    public Result doUpdate(@RequestBody AppVersion appVersion) {
        appVersion.setUpdateDate(new Date());
        return new Result(true, "更新成功", service.updateByPrimaryKeySelective(appVersion));
    }

    @RequestMapping(value = "save", method = RequestMethod.POST)
    public Result save(@RequestBody AppVersion appVersion) {

        return new Result(true, "添加成功", service.insertSelective(appVersion));
    }
}
