package com.xss.service.Impl;

import com.xss.entity.SysUser;
import com.xss.mapper.SysUserMapper;
import com.xss.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author XSS
 * @date 2020/7/31
 * @desc
 */
@Service
@Transactional
public class SysUserServiceImpl extends BaseServiceImpl<SysUser> implements SysUserService {

    @Autowired
    SysUserMapper sysUserMapper;


    @Override
    public List<SysUser> selectByRid(long rid) {
        return sysUserMapper.selectByRid(rid);
    }

    @Override
    public List<SysUser> selectNoRole(long oid, long rid) {
        return sysUserMapper.selectNoRole(oid, rid);
    }
}
