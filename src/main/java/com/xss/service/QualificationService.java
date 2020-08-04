package com.xss.service;

import com.github.pagehelper.PageInfo;
import com.xss.entity.Qualification;
import com.xss.entity.QualificationMethod;

/**
 * @author XSS
 * @date 2020/7/20
 * @desc
 */
public interface QualificationService extends BaseService<Qualification>{


    PageInfo<Qualification> selectPage(Integer pageNum, Integer pageSize, QualificationMethod qualificationMethod);

    /*
     * 根据上传用户id获取用户的公司id，动态平均出用户保存图片目录
     * path：虚拟路径+公司id
     *
     * */
    String getPath(long uid);
}
