package com.xss.mapper;

import com.xss.entity.Qualification;
import com.xss.entity.QualificationMethod;
import org.apache.ibatis.annotations.SelectProvider;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface QualificationMapper extends Mapper<Qualification> {

    @SelectProvider(value = QualificationProvider.class,method = "selectPage")
    public List<Qualification> selectPage(QualificationMethod qualificationMethod);
}