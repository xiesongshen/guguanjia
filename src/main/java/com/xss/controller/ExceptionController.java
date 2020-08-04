package com.xss.controller;

import com.xss.entity.Result;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author XSS
 * @date 2020/7/17
 * @desc
 */
@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler
    @ResponseBody
    public Result doException(Exception e){


        return new Result(false,e.getMessage(),null);
    }
}
