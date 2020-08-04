package com.xss.service;

import com.github.pagehelper.PageInfo;
import com.xss.entity.WorkOrder;

import java.util.Map;

/**
 * @author XSS
 * @date 2020/7/21
 * @desc
 */
public interface WorkOrderService extends BaseService<WorkOrder> {
    PageInfo<WorkOrder> selectPage(Integer pageNum, Integer pageSize, Map<String, Object> map);

    Map<String, Object> selectDetail(long id);
}
