package com.xss.service.Impl;

import com.xss.entity.SysResource;
import com.xss.mapper.SysResourceMapper;
import com.xss.service.SysResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author XSS
 * @date 2020/8/5
 * @desc
 */
@Service
@Transactional
public class SysResourceServiceImpl extends BaseServiceImpl<SysResource> implements SysResourceService {

    @Autowired
    SysResourceMapper sysResourceMapper;

    @Override
    public List<SysResource> selectByRid(long rid){
        return sysResourceMapper.selectByRid(rid);
    }

    @Override
    public List<SysResource> getResourcesByUid(long uid){
        return sysResourceMapper.getResourcesByUid(uid);
    }

    @Override
    public List<SysResource> selectResources(){
        return sysResourceMapper.selectResources();
    }
}
