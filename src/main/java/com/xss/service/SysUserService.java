package com.xss.service;

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
}
