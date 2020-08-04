package com.xss.mapper;

import com.xss.entity.SysRole;
import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface SysRoleMapper extends Mapper<SysRole> {


    @SelectProvider(value = SysRoleProvider.class,method = "selectSysRoles")
    public List<SysRole> selectSysRoles(SysRole sysRole);

    @DeleteProvider(type = SysRoleProvider.class,method="deleteBatch")
    int deleteBatch(@Param("rid") long rid, @Param("ids") long[] ids);

    @InsertProvider(type = SysRoleProvider.class,method = "insertBatch")
    int insertBatch(@Param("rid")long rid,@Param("cids") List<Long> cids);
}