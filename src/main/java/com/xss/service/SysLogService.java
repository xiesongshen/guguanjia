package com.xss.service;

import com.github.pagehelper.PageInfo;
import com.xss.entity.SysLog;

/**
 * @author XSS
 * @date 2020/8/6
 * @desc
 */
public interface SysLogService extends BaseService<SysLog>{
    PageInfo<SysLog> selectPage(Integer pageNum, Integer pageSize, SysLog sysLog);
}
