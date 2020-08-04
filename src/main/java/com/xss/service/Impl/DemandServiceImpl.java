package com.xss.service.Impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xss.entity.Demand;
import com.xss.service.DemandService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @author XSS
 * @date 2020/7/18
 * @desc
 */
@Service
@Transactional
public class DemandServiceImpl extends BaseServiceImpl<Demand> implements DemandService {
    @Override
    public PageInfo<Demand> selectPage(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);

        Demand demand = new Demand();
        demand.setDelFlag("0");

        List<Demand> list = map.select(demand);

        Page p = (Page) list;
        if (pageNum>p.getPages()){
            PageHelper.startPage(1, pageSize);
            demand.setDelFlag("0");
            list = map.select(demand);
        }
        return new PageInfo<Demand> (list);
    }

    @Override
    public int insertSelective(Demand demand) {
        demand.setCreateDate(new Date());
        demand.setUpdateDate(new Date());
        demand.setDelFlag("0");
        return super.insertSelective(demand);
    }
}
