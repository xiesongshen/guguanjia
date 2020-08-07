package com.xss.mapper;

import com.xss.entity.SysResource;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface SysResourceMapper extends Mapper<SysResource> {


    @Select("SELECT " +
            "    sre.*  " +
            "FROM " +
            "    sys_role_resource srr, " +
            "    sys_resource sre  " +
            "WHERE " +
            "    srr.role_id = #{rid}  " +
            "    AND sre.del_flag = 0  " +
            "    AND srr.resource_id = sre.id")
    List<SysResource> selectByRid(long rid);

    @Select("SELECT DISTINCT " +
            "    ssr.*  " +
            "FROM " +
            "    sys_user su, " +
            "    sys_resource ssr, " +
            "    sys_role_resource srr, " +
            "    sys_user_role sur  " +
            "WHERE " +
            "    su.id = sur.user_id  " +
            "    AND sur.role_id = srr.role_id  " +
            "    AND srr.resource_id = ssr.id  " +
            "    AND ssr.del_flag = 0  " +
            "    AND su.id = #{uid}  " +
            "    AND ssr.type = 0")
    public List<SysResource> getResourcesByUid(long uid);

    /**
     * 查询所有需要授权的权限
     * @return
     */
    @Select("select * from sys_resource where type=0 and del_flag=0 and url <> ''")
    List<SysResource> selectResources();
}