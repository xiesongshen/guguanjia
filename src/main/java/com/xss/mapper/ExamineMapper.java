package com.xss.mapper;

import com.xss.entity.Examine;
import com.xss.entity.ExamineMethod;
import org.apache.ibatis.annotations.SelectProvider;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface ExamineMapper extends Mapper<Examine> {


    @SelectProvider(value = ExamineProvider.class,method = "selectPage")
    List<Examine> selectPage(ExamineMethod examineMethod);
}