package com.xss.mapper;

import com.xss.entity.SysOffice;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface SysOfficeMapper extends Mapper<SysOffice> {

    //根据角色id查询已授权公司
    @Select("SELECT " +
            " sof.*  " +
            "FROM " +
            " sys_role_office sro, " +
            " sys_office sof  " +
            "WHERE " +
            " sro.role_id = #{rid}  " +
            " AND sof.del_flag = 0  " +
            " AND sro.office_id = sof.id ")
    List<SysOffice> selectByRid(long rid);
}