package com.xss.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.xss.entity.SysArea;
import com.xss.mapper.SysAreaMapper;

import java.util.LinkedList;
import java.util.List;

/**
 * @author XSS
 * @date 2020/7/30
 * @desc
 */
public class SysAreaListener extends AnalysisEventListener<SysArea> {


    SysAreaMapper sysAreaMapper;

    List<SysArea> list = new LinkedList<>();

    public SysAreaListener(SysAreaMapper sysAreaMapper) {
        this.sysAreaMapper = sysAreaMapper;
    }

    @Override
    public void invoke(SysArea sysArea, AnalysisContext analysisContext) {
        list.add(sysArea);
        if (list.size()==10){
            sysAreaMapper.insertMany(list);
            list.clear();
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        if (list.size()>0){
            sysAreaMapper.insertMany(list);
        }
    }
}
