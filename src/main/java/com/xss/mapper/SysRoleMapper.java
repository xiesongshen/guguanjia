package com.xss.mapper;

import com.xss.entity.SysOffice;
import com.xss.entity.SysResource;
import com.xss.entity.SysRole;
import org.apache.ibatis.annotations.*;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface SysRoleMapper extends Mapper<SysRole> {


    @SelectProvider(value = SysRoleProvider.class,method = "selectSysRoles")
    public List<SysRole> selectSysRoles(SysRole sysRole);

    @DeleteProvider(type = SysRoleProvider.class,method="deleteBatch")
    int deleteBatch(@Param("rid") long rid, @Param("ids") long[] ids);

    @InsertProvider(type = SysRoleProvider.class,method = "insertBatch")
    int insertBatch(@Param("rid")long rid,@Param("cids") List<Long> cids);

    /**
     * 删除原已选资源
     */
    @Delete("delete from sys_role_resource where role_id=#{rid}")
    int deleteByRid(long rid);

    /**
     * 批量插入已选资源
     */
    @InsertProvider(type=SysRoleProvider.class,method = "insertRoleResources")
    int insertRoleResources(@Param("rid") long rid,@Param("resources") List<SysResource> resources);

    /**
     * 删除原已选公司
     */
    @Delete("delete from sys_role_office where role_id=#{rid}")
    int deleteOfficeByRid(long rid);

    /**
     * 批量插入已选公司
     */
    @InsertProvider(type=SysRoleProvider.class,method = "insertRoleOffices")
    int insertRoleOffices(@Param("rid") long rid,@Param("offices") List<SysOffice> offices);
}