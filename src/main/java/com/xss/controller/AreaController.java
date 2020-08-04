package com.xss.controller;

import com.xss.entity.Result;
import com.xss.entity.SysArea;
import com.xss.service.SysAreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

/**
 * @author XSS
 * @date 2020/7/21
 * @desc
 */
@RestController
@RequestMapping("manager/area")
public class AreaController {

    @Autowired
    SysAreaService service;


    @RequestMapping()
    public ModelAndView index() {
        return new ModelAndView("area/area");
    }

    @RequestMapping(value = "selectPage/{pageNum}/{pageSize}")
    public Result selectPage(@PathVariable("pageNum") Integer pageNum, @PathVariable("pageSize") Integer pageSize, @RequestBody SysArea area) {

        return new Result(true, "查询成功", service.selectPage(pageNum, pageSize, area));
    }

    @RequestMapping(value = "selectByPid/{pageNum}/{pageSize}")
    public Result selectByPid(@PathVariable("pageNum") Integer pageNum, @PathVariable("pageSize") Integer pageSize, Long id) {

        return new Result(true, "查询成功", service.selectByPid(pageNum, pageSize, id));
    }

    @RequestMapping("select")
    public Result select() {
        SysArea area = new SysArea();
        area.setDelFlag("0");
        return new Result(true, "查询成功", service.select(area));
    }

    @RequestMapping("toUpdate")
    public ModelAndView toUpdate() {
        return new ModelAndView("area/save");
    }

    @RequestMapping("doUpdate")
    public Result doUpdate(@RequestBody SysArea area) {
        area.setUpdateDate(new Date());
        return new Result(true, "更新成功", service.updateByPrimaryKeySelective(area));
    }

    @RequestMapping("toInsert")
    public ModelAndView toInsert(){
        return new ModelAndView("area/add");
    }

    @RequestMapping("doInsert")
    public Result doInsert(@RequestBody SysArea area) {
        area.setCreateDate(new Date());
        area.setDelFlag("0");
        area.setUpdateDate(new Date());
        return new Result(true, "添加成功", service.insertSelective(area));
    }

    @RequestMapping("toModule")
    public ModelAndView toModule(){
        return new ModelAndView("modules/font-awesome");
    }

    @RequestMapping("toSelect")
    public ModelAndView toSelect(){
        return new ModelAndView("area/select");
    }

    @RequestMapping("download")
    public void download(HttpServletResponse response) throws IOException {
        //new String("区域.xls".getBytes(),"iso-8859-1")  解决客户端显示中文文件名乱码问题
        response.setHeader("Content-Disposition","attachment;filename="+new String("区域.xls".getBytes(),"iso-8859-1"));

        service.download(response.getOutputStream());
    }


    @RequestMapping(value = "upload",method = RequestMethod.POST)
    public Result upload(@RequestBody MultipartFile file) throws IOException {
        service.upload(file.getInputStream());

        return new Result(true,"批量插入成功",null);
    }

}
