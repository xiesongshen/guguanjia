package com.xss.service.Impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
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



    @Override
    public PageInfo<SysUser> selectPage(Integer pageNum, Integer pageSize,SysUser sysUser) {
        PageHelper.startPage(pageNum, pageSize);

        List<SysUser> list = sysUserMapper.selectUser(sysUser);

        Page p = (Page) list;
        if (pageNum>p.getPages()){
            PageHelper.startPage(1, pageSize);

            list = sysUserMapper.selectUser(sysUser);
        }
        return new PageInfo<SysUser> (list);
    }
}
