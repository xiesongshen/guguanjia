package com.xss.service.Impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xss.entity.AppVersion;
import com.xss.service.AppVersionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @author XSS
 * @date 2020/7/16
 * @desc
 */
@Service
@Transactional
public class AppVersionServiceImpl extends BaseServiceImpl<AppVersion> implements AppVersionService {
    @Override
    public PageInfo<AppVersion> selectPage(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);

        AppVersion appVersion = new AppVersion();
        appVersion.setDelFlag("0");

        List<AppVersion> list = map.select(appVersion);

        Page p = (Page) list;
        if (pageNum>p.getPages()){
            PageHelper.startPage(1, pageSize);
            appVersion.setDelFlag("0");
            list = map.select(appVersion);
        }
        return new PageInfo<AppVersion> (list);
    }

    @Override
    public int insertSelective(AppVersion appVersion) {
        appVersion.setCreateDate(new Date());
        appVersion.setUpdateDate(new Date());
        appVersion.setDelFlag("0");
        return super.insertSelective(appVersion);
    }
}
