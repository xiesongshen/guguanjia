package com.xss.service;

import com.github.pagehelper.PageInfo;
import com.xss.entity.Statute;

/**
 * @author XSS
 * @date 2020/7/23
 * @desc
 */
public interface StatuteService extends BaseService<Statute>{

    PageInfo<Statute> selectPage(Integer pageNum, Integer pageSize, Statute statute);
}
