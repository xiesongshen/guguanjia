package com.xss.service.Impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xss.entity.Statute;
import com.xss.mapper.StatuteMapper;
import com.xss.service.StatuteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author XSS
 * @date 2020/7/23
 * @desc
 */
@Service
@Transactional
public class StatuteServiceImpl extends BaseServiceImpl<Statute> implements StatuteService {


    @Autowired
    StatuteMapper statuteMapper;

    @Override
    public PageInfo<Statute> selectPage(Integer pageNum, Integer pageSize, Statute statute){
        PageHelper.startPage(pageNum,pageSize);

        statute.setDelFlag("0");
        List<Statute> list = statuteMapper.select(statute);

        Page p = (Page)list;
        if (p.getPages()<pageNum){
            PageHelper.startPage(1,pageSize);
            statute.setDelFlag("0");

            list = statuteMapper.select(statute);
        }

        return new PageInfo<Statute>(list);
    }
}
