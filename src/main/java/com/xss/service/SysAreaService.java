package com.xss.service;

import com.github.pagehelper.PageInfo;
import com.xss.entity.SysArea;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author XSS
 * @date 2020/7/29
 * @desc
 */
public interface SysAreaService extends BaseService<SysArea>{
    PageInfo<SysArea> selectPage(Integer pageNum, Integer pageSize, SysArea area);

    PageInfo<SysArea> selectByPid(Integer pageNum, Integer pageSize, Long id);

    void download(OutputStream outputStream);

    void upload(InputStream inputStream);
}
