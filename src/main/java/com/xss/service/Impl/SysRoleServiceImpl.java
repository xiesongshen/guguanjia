package com.xss.service.Impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xss.entity.SysRole;
import com.xss.mapper.SysRoleMapper;
import com.xss.service.SysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author XSS
 * @date 2020/8/3
 * @desc
 */
@Service
@Transactional
public class SysRoleServiceImpl extends BaseServiceImpl<SysRole> implements SysRoleService {

    @Autowired
    SysRoleMapper sysRoleMapper;


    @Override
    public PageInfo<SysRole> selectPage(int pageNum, int pageSize, SysRole sysRole){
        PageHelper.startPage(pageNum,pageSize);

        List<SysRole> list = sysRoleMapper.selectSysRoles(sysRole);

        Page p = (Page)list;
        if (p.getPages()<pageNum){
            PageHelper.startPage(1,pageSize);
            list = sysRoleMapper.selectSysRoles(sysRole);
        }

        return new PageInfo<SysRole>(list);
    }

    @Override
    public int insertBatch(long rid, List<Long> cids){
        return sysRoleMapper.insertBatch(rid,cids);
    }

    @Override
    public int deleteBatch(long rid, long[] ids) {
        return sysRoleMapper.deleteBatch(rid,ids);
    }

}
