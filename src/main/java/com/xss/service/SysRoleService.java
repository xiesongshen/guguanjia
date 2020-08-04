package com.xss.service;

import com.github.pagehelper.PageInfo;
import com.xss.entity.SysRole;

import java.util.List;

/**
 * @author XSS
 * @date 2020/7/29
 * @desc
 */
public interface SysRoleService extends BaseService<SysRole>{

    PageInfo<SysRole> selectPage(int pageNum, int pageSize, SysRole sysRole);

    int insertBatch(long rid, List<Long> cids);

    int deleteBatch(long rid, long[] ids);
}
