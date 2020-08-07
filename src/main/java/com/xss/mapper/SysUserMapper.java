package com.xss.mapper;

import com.xss.entity.SysUser;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface SysUserMapper extends Mapper<SysUser> {
    @Select("SELECT " +
            "    su.id, " +
            "    su.NAME  " +
            "FROM " +
            "    sys_user_role sur, " +
            "    sys_user su  " +
            "WHERE " +
            "    sur.role_id = #{rid}  " +
            "    AND sur.user_id = su.id")
    List<SysUser> selectByRid(long rid);

    /*
     * 查询公司未分配某个角色的人员
     * */
    @Select("SELECT " +
            "    su.id,su.name  " +
            "FROM " +
            "    sys_user su  " +
            "WHERE " +
            "    su.office_id = #{oid}  " +
            "    AND su.id NOT IN ( SELECT user_id FROM sys_user_role WHERE role_id = #{rid} )")
    List<SysUser> selectNoRole(@Param("oid") long oid, @Param("rid") long rid);


    @SelectProvider(value = SysUserProvider.class,method = "selectUser")
    List<SysUser> selectUser(SysUser sysUser);

}