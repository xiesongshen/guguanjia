package com.xss.service.Impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xss.entity.SysOffice;
import com.xss.entity.SysResource;
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

    /**
     * 动态更新role：
     * 1.更新role信息
     * 2.如果角色授权信息发生改变，修改角色授权(a.删除原授权信息， b.批量插入新授权信息)
     * 3.如果角色跨机构授权信息发生改变 修改角色跨机构授权(a.删除原授权信息， b.批量插入新授权信息)
     *
     * @param sysRole
     * @return
     */
    @Override
    public int updateByPrimaryKeySelective(SysRole sysRole) {
        int result = 0;

        sysRoleMapper.updateByPrimaryKeySelective(sysRole);
        result++;
        List<SysResource> resources = sysRole.getResources();
        List<SysResource> oldResources = sysRole.getOldResources();
        //2.如果角色授权信息发生改变，修改角色授权(a.删除原授权信息， b.批量插入新授权信息)
        if(resources!=null&&oldResources!=null){
            //需要修改
            if(resources.size()!=oldResources.size()||
                    (resources.size()==oldResources.size()&&!resources.containsAll(oldResources))){
                sysRoleMapper.deleteByRid(sysRole.getId());
                sysRoleMapper.insertRoleResources(sysRole.getId(),resources);
            }/*else {
                //不需要修改
            }*/
        }
        result++;
        //3.如果角色跨机构授权信息发生改变 修改角色跨机构授权(a.删除原授权信息， b.批量插入新授权信息)
        List<SysOffice> offices = sysRole.getOffices();
        List<SysOffice> oldOffices = sysRole.getOldOffices();
        if(offices!=null&&oldOffices!=null){
            //需要修改
            if(offices.size()!=oldOffices.size()||
                    (offices.size()==oldOffices.size()&&!resources.containsAll(oldOffices))){
                sysRoleMapper.deleteOfficeByRid(sysRole.getId());
                sysRoleMapper.insertRoleOffices(sysRole.getId(),offices);
            }
        }else if(oldOffices==null&&offices!=null){//原来dataScope不是9  改为9
            sysRoleMapper.deleteOfficeByRid(sysRole.getId());
            sysRoleMapper.insertRoleOffices(sysRole.getId(),offices);
        }else if(oldOffices!=null&&offices==null){
            sysRoleMapper.deleteOfficeByRid(sysRole.getId());
            sysRoleMapper.insertRoleOffices(sysRole.getId(),offices);
        }
        result++;
        return result;
    }

}
