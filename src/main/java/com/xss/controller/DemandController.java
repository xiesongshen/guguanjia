package com.xss.controller;

import com.xss.entity.AppVersion;
import com.xss.entity.Demand;
import com.xss.entity.Result;
import com.xss.service.DemandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;

/**
 * @author XSS
 * @date 2020/7/18
 * @desc
 */
@RestController
@RequestMapping("manager/demand")
public class DemandController {
    @Autowired
    DemandService service;

    @RequestMapping("index")
    public ModelAndView toIndex(){
        return new ModelAndView("demand/index");
    }

    @RequestMapping("selectPage/{pageNum}/{pageSize}")
    public Result selectPage(@PathVariable("pageNum") Integer pageNum, @PathVariable("pageSize") Integer pageSize){
        return new Result(true, "查询成功", service.selectPage(pageNum, pageSize));
    }

    @RequestMapping("toDetails")
    public ModelAndView Details(){
        return new ModelAndView("demand/detail");
    }

    @RequestMapping("toUpdate")
    public ModelAndView toUpdate(){
        return new ModelAndView("demand/update");
    }

    @RequestMapping(value = "doUpdate", method = RequestMethod.PUT)
    public Result doUpdate(@RequestBody Demand demand) {
        demand.setUpdateDate(new Date());
        return new Result(true, "更新成功", service.updateByPrimaryKeySelective(demand));
    }
}
