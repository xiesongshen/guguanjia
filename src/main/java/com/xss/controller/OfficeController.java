package com.xss.controller;

import com.xss.entity.Result;
import com.xss.entity.SysOffice;
import com.xss.service.SysOfficeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author XSS
 * @date 2020/7/21
 * @desc
 */
@RestController
@RequestMapping("manager/office")
public class OfficeController {

    @Autowired
    SysOfficeService service;


    @RequestMapping("select")
    public Result select(){
        SysOffice office = new SysOffice();
        office.setDelFlag("0");
        return new Result(true,"查询成功",service.select(office));
    }
}
