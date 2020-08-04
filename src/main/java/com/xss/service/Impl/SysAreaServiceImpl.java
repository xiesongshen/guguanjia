package com.xss.service.Impl;

import com.alibaba.excel.EasyExcel;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xss.entity.SysArea;
import com.xss.listener.SysAreaListener;
import com.xss.mapper.SysAreaMapper;
import com.xss.service.SysAreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

/**
 * @author XSS
 * @date 2020/7/29
 * @desc
 */
@Service
@Transactional
public class SysAreaServiceImpl extends BaseServiceImpl<SysArea> implements SysAreaService {

    @Autowired
    SysAreaMapper sysAreaMapper;

    @Override
    public PageInfo<SysArea> selectPage(Integer pageNum, Integer pageSize, SysArea area) {
        PageHelper.startPage(pageNum, pageSize);

        List<SysArea> list = sysAreaMapper.selectPage(area);

        Page p = (Page) list;
        if (p.getPages() < pageNum) {
            PageHelper.startPage(1, pageSize);
            area.setDelFlag("0");

            list = sysAreaMapper.selectPage(area);
        }

        return new PageInfo<SysArea>(list);
    }

    @Override
    public PageInfo<SysArea> selectByPid(Integer pageNum, Integer pageSize, Long id) {
        PageHelper.startPage(pageNum, pageSize);

        List<SysArea> list = sysAreaMapper.selectByParentId(id);

        Page p = (Page) list;
        if (p.getPages() < pageNum) {
            PageHelper.startPage(1, pageSize);

            list = sysAreaMapper.selectByParentId(id);
        }
        return new PageInfo<SysArea>(list);
    }

    @Override
    public int updateByPrimaryKeySelective(SysArea sysArea) {
        int result = 0;
        try {
            result = sysAreaMapper.updateByPrimaryKeySelective(sysArea);
            if (sysArea.getParentIds() != null && !sysArea.getParentIds().equals(sysArea.getOldParentIds())) {
                result += sysAreaMapper.updateParentIds(sysArea);
            }
        } catch (Exception e) {
            throw new RuntimeException("更新失败");
        }

        return result;
    }

    @Override
    public void download(OutputStream outputStream) {

        SysArea sysArea = new SysArea();
        sysArea.setDelFlag("0");
        List<SysArea> sysAreas = sysAreaMapper.select(sysArea);
        EasyExcel.write(outputStream, SysArea.class).sheet().doWrite(sysAreas);
    }

    @Override
    public void upload(InputStream inputStream){
        EasyExcel.read(inputStream,SysArea.class,new SysAreaListener(sysAreaMapper)).sheet().doRead();
    }
}
