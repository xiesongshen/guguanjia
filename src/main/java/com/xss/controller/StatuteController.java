package com.xss.controller;

import com.xss.entity.Result;
import com.xss.entity.Statute;
import com.xss.service.StatuteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;

/**
 * @author XSS
 * @date 2020/7/21
 * @desc
 */
@RestController
@RequestMapping("manager/statute")
public class StatuteController {

    @Autowired
    StatuteService service;


    @RequestMapping("index")
    public ModelAndView index() {
        return new ModelAndView("statute/index");
    }

    @RequestMapping(value = "selectPage/{pageNum}/{pageSize}")
    public Result selectPage(@PathVariable("pageNum") Integer pageNum, @PathVariable("pageSize") Integer pageSize, Integer type) {
        Statute statute = new Statute();
        statute.setDelFlag("0");
        statute.setType(type);

        return new Result(true, "查询成功", service.selectPage(pageNum, pageSize, statute));
    }

    @RequestMapping("toUpdate")
    public ModelAndView toUpdate() {
        return new ModelAndView("statute/update");
    }

    @RequestMapping("doUpdate")
    public Result doUpdate(@RequestBody Statute statute) {
        statute.setUpdateDate(new Date());
        return new Result(true, "更新成功", service.updateByPrimaryKeySelective(statute));
    }

    @RequestMapping("save")
    public Result save(@RequestBody Statute statute) {
        statute.setCreateDate(new Date());
        statute.setDelFlag("0");
        statute.setUpdateDate(new Date());
        return new Result(true, "添加成功", service.insertSelective(statute));
    }

}
