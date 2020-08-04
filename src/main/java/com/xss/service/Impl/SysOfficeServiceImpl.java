package com.xss.service.Impl;

import com.xss.entity.SysOffice;
import com.xss.service.SysOfficeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author XSS
 * @date 2020/7/21
 * @desc
 */
@Service
@Transactional
@CacheConfig(cacheNames = "officeCache")//缓存配置，设置整个类的全局缓存设置
public class SysOfficeServiceImpl extends BaseServiceImpl<SysOffice> implements SysOfficeService {

    @Autowired
    RedisTemplate<Object, Object> template;

    /*@Override
    public List<SysOffice> select(SysOffice sysOffice) {
        String key = "com.xss.service.Impl.SysOfficeServiceImpl.select:sysOffice:"+sysOffice;
        ValueOperations<Object, Object> ops = template.opsForValue();
        Object obj = ops.get(key);
        List<SysOffice> list;

        if (obj!=null){
            list = (List<SysOffice>) obj;
        }else {
            list = map.select(sysOffice);
            ops.set(key,list);
        }

        return list;
    }*/

    @Override
    @Cacheable(/*value = "officeCache",*/ key = "'com.xss.service.Impl.SysOfficeServiceImpl.select:sysOffice:'+#sysOffice")
    public List<SysOffice> select(SysOffice sysOffice) {
        return super.select(sysOffice);
    }

    @Override
    @CacheEvict(/*value = "officeCache",*/allEntries = true)
    public int updateByPrimaryKeySelective(SysOffice sysOffice) {
        return super.updateByPrimaryKeySelective(sysOffice);
    }

    @Override
    @Cacheable(key = "'com.xss.service.Impl.SysOfficeServiceImpl.select:Object:'+#o")
    public SysOffice selectByPrimaryKey(Object o) {
        return super.selectByPrimaryKey(o);
    }


    @Override
    @CachePut(key = "'com.xss.service.Impl.SysOfficeServiceImpl.select:sysOffice:'+#sysOffice.id")
    public SysOffice updateByPrimaryKeySelectiveCache(SysOffice sysOffice) {

        selectByPrimaryKey(sysOffice);
        return sysOffice;
    }

}
