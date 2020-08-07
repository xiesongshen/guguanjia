package com.xss.service;

import com.xss.entity.SysOffice;
import org.springframework.cache.annotation.CachePut;

import java.util.List;

/**
 * @author XSS
 * @date 2020/7/21
 * @desc
 */

public interface SysOfficeService extends BaseService<SysOffice>{


    @CachePut(key = "'com.xss.service.Impl.SysOfficeServiceImpl.select:sysOffice:'+#sysOffice.id")
    SysOffice updateByPrimaryKeySelectiveCache(SysOffice sysOffice);

    List<SysOffice> selectByRid(long rid);
}
