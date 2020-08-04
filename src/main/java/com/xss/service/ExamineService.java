package com.xss.service;

import com.github.pagehelper.PageInfo;
import com.xss.entity.Examine;
import com.xss.entity.ExamineMethod;

/**
 * @author XSS
 * @date 2020/7/21
 * @desc
 */
public interface ExamineService extends BaseService<Examine>{
    PageInfo<Examine> selectPage(Integer pageNum, Integer pageSize, ExamineMethod examineMethod);
}
