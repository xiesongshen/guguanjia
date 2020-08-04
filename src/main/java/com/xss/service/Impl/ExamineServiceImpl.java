package com.xss.service.Impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xss.entity.Examine;
import com.xss.entity.ExamineMethod;
import com.xss.mapper.ExamineMapper;
import com.xss.service.ExamineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author XSS
 * @date 2020/7/21
 * @desc
 */
@Service
@Transactional
public class ExamineServiceImpl extends BaseServiceImpl<Examine> implements ExamineService {
    @Autowired
    ExamineMapper examineMapper;

    @Override
    public PageInfo<Examine> selectPage(Integer pageNum, Integer pageSize, ExamineMethod examineMethod){
        PageHelper.startPage(pageNum,pageSize);

        List<Examine> list = examineMapper.selectPage(examineMethod);

        Page p = (Page)list;
        if (p.getPages()<pageNum){
            PageHelper.startPage(1,pageSize);
            list = examineMapper.selectPage(examineMethod);
        }

        return new PageInfo<Examine>(list);
    }
}
