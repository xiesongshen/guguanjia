package com.xss.service;

import com.github.pagehelper.PageInfo;
import com.xss.entity.SysUser;

import java.util.List;

/**
 * @author XSS
 * @date 2020/7/31
 * @desc
 */
public interface SysUserService extends BaseService<SysUser>{
    List<SysUser> selectByRid(long rid);

    List<SysUser> selectNoRole(long oid, long rid);


    PageInfo<SysUser> selectPage(Integer pageNum, Integer pageSize, SysUser sysUser);
}
