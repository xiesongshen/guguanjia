package com.xss.service;

import com.xss.entity.SysResource;

import java.util.List;

/**
 * @author XSS
 * @date 2020/8/5
 * @desc
 */
public interface SysResourceService extends BaseService<SysResource>{
    List<SysResource> selectByRid(long rid);

    List<SysResource> getResourcesByUid(long uid);

    List<SysResource> selectResources();
}
