package com.xss.mapper;

import com.xss.entity.SysArea;
import org.apache.ibatis.annotations.*;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface SysAreaMapper extends Mapper<SysArea> {


    @SelectProvider(value = SysAreaProvider.class,method = "selectPage")
    List<SysArea> selectPage(SysArea sysArea);

    @Select("select  sub.*,parent.name parent_name from sys_area sub , sys_area parent " +
            "where " +
            "sub.del_flag='0' " +
            "and " +
            "sub.parent_ids like concat('%,',#{id},',%') " +
            "and " +
            "sub.parent_id=parent.id")
    List<SysArea> selectByParentId(Long id);

    @Update("UPDATE sys_area " +
            "SET parent_ids = REPLACE ( parent_ids, #{oldParentIds}, #{parentIds} )," +
            "update_date = NOW( )  " +
            "WHERE " +
            "parent_ids LIKE concat( '%,', #{id}, ',%' )")
    int updateParentIds(SysArea parentArea);


    @InsertProvider(value = SysAreaProvider.class,method = "insertMany")
    int insertMany(@Param("areas") List<SysArea> areas);

}