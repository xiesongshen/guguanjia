package com.xss.controller;

import com.xss.entity.Qualification;
import com.xss.entity.QualificationMethod;
import com.xss.entity.Result;
import com.xss.service.QualificationService;
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
@RequestMapping("manager/qualification")
public class QualificationController {

    @Autowired
    QualificationService service;

    @RequestMapping("index")
    public ModelAndView toIndex() {

        return new ModelAndView("qualification/index");
    }


    @RequestMapping(value = "selectPage/{pageNum}/{pageSize}", method = RequestMethod.POST)
    public Result selectPage(@PathVariable("pageNum") Integer pageNum, @PathVariable("pageSize") Integer pageSize, @RequestBody QualificationMethod qualificationMethod) {
        return new Result(true, "查询成功", service.selectPage(pageNum, pageSize, qualificationMethod));
    }


    @RequestMapping("getPath/{uid}")
    public Result getPath(@PathVariable("uid") Integer uid) {
        return new Result(true, "查询成功", service.getPath(uid));
    }

    @RequestMapping("toUpdate")
    public ModelAndView toUpdate() {

        return new ModelAndView("qualification/update");
    }

    @RequestMapping(value = "doUpdate", method = RequestMethod.PUT)
    public Result doUpdate(@RequestBody Qualification qualification) {
        qualification.setUpdateDate(new Date());
        return new Result(true, "更新成功", service.updateByPrimaryKeySelective(qualification));
    }


}
