package com.xss.service.Impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xss.entity.SysLog;
import com.xss.mapper.SysLogMapper;
import com.xss.service.SysLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author XSS
 * @date 2020/8/6
 * @desc
 */
@Service
@Transactional(readOnly = false, isolation = Isolation.DEFAULT)
public class SysLogServiceImpl extends BaseServiceImpl<SysLog> implements SysLogService {

    @Autowired
    SysLogMapper sysLogMapper;


    //单独开启事务，不受其他事务影响
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
    public int insertSelective(SysLog sysLog) {
        return super.insertSelective(sysLog);
    }


    @Override
    public PageInfo<SysLog> selectPage(Integer pageNum, Integer pageSize, SysLog sysLog) {
        PageHelper.startPage(pageNum, pageSize);

        List<SysLog> list = sysLogMapper.select(sysLog);

        Page p = (Page) list;
        if (p.getPages() < pageNum) {
            PageHelper.startPage(1, pageSize);

            list = sysLogMapper.select(sysLog);
        }
        return new PageInfo<SysLog>(list);
    }
}
