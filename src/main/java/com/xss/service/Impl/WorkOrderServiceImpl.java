package com.xss.service.Impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xss.entity.WorkOrder;
import com.xss.mapper.DetailMapper;
import com.xss.mapper.TransferMapper;
import com.xss.mapper.WorkOrderMapper;
import com.xss.service.WorkOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @author XSS
 * @date 2020/7/21
 * @desc
 */
@Service
@Transactional
public class WorkOrderServiceImpl extends BaseServiceImpl<WorkOrder> implements WorkOrderService {

    @Autowired
    WorkOrderMapper workOrderMapper;

    @Autowired
    TransferMapper transferMapper;

    @Autowired
    DetailMapper detailMapper;


    @Override
    public PageInfo<WorkOrder> selectPage(Integer pageNum, Integer pageSize, Map<String,Object> map){
        PageHelper.startPage(pageNum,pageSize);

        List<WorkOrder> list = workOrderMapper.selectPage(map);

        Page p = (Page)list;

        if (p.getPages()<pageNum){
            PageHelper.startPage(1,pageSize);
            list = workOrderMapper.selectPage(map);
        }

        return new PageInfo<WorkOrder>(list);
    }

    @Override
    public Map<String, Object> selectDetail(long id) {
        Map<String, Object> workOrder = workOrderMapper.selectById(id);
        List<Map<String, Object>> details = detailMapper.selectById(id);
        List<Map<String, Object>> transfers = transferMapper.selectById(id);
        //放入其他查询结果数据
        workOrder.put("details",details);
        workOrder.put("transfers",transfers);

        return workOrder;
    }
}
