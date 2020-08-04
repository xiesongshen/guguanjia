package com.xss.service.Impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xss.entity.Qualification;
import com.xss.entity.QualificationMethod;
import com.xss.entity.SysUser;
import com.xss.mapper.QualificationMapper;
import com.xss.mapper.SysUserMapper;
import com.xss.service.QualificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @author XSS
 * @date 2020/7/18
 * @desc
 */
@Service
@Transactional
public class QualificationServiceImpl extends BaseServiceImpl<Qualification> implements QualificationService {
    @Autowired
    QualificationMapper mapper;

    @Autowired
    SysUserMapper userMapper;

    @Value("${path}")
    String path;

    @Override
    public PageInfo<Qualification> selectPage(Integer pageNum, Integer pageSize,
                                              QualificationMethod qualificationMethod) {
        PageHelper.startPage(pageNum, pageSize);

        List<Qualification> list = mapper.selectPage(qualificationMethod);

        Page p = (Page) list;
        if (pageNum>p.getPages()){
            PageHelper.startPage(1, pageSize);
            list = mapper.selectPage(qualificationMethod);
        }

        return new PageInfo<Qualification> (list);
    }

    /*
     * 根据上传用户id获取用户的公司id，动态平均出用户保存图片目录
     * path：虚拟路径+公司id
     *
     * */
    @Override
    public String getPath(long uid){
        SysUser sysUser = userMapper.selectByPrimaryKey(uid);

        return path+sysUser.getOfficeId();
    }

}
