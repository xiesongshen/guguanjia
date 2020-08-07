package com.xss.mapper;

import com.xss.entity.SysOffice;
import com.xss.entity.SysResource;
import com.xss.entity.SysRole;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * @author XSS
 * @date 2020/8/3
 * @desc
 */
public class SysRoleProvider {

    public String selectSysRoles(SysRole sysRole) {
        return new SQL(){{
            SELECT("sr.*,so.`name` sys_office_name");
            FROM("sys_role sr,sys_office so ");
            WHERE("sr.office_id = so.id AND sr.del_flag = 0 ");
            if (!StringUtils.isEmpty(sysRole.getName())){
                WHERE("sr.NAME LIKE CONCAT( '%', #{name}, '%' )");
            }

            if (!StringUtils.isEmpty(sysRole.getDataScope())){
                WHERE(" sr.data_scope = #{dataScope}");
            }

            if (!StringUtils.isEmpty(sysRole.getOfficeId())){
                WHERE("sr.office_id = #{officeId}");
            }

        }}.toString();
    }

    public String deleteBatch(@Param("rid") long rid, @Param("ids") long[] ids){
        StringBuilder sb = new StringBuilder();
        sb.append("DELETE  from sys_user_role where role_id=#{rid} and user_id in ");
        sb.append("(");
        for (int i = 0; i < ids.length; i++) {
            sb.append("#{ids["+i+"]},");
        }
        sb.deleteCharAt(sb.length()-1);
        sb.append(")");
        return sb.toString();
    }

    public String insertBatch(@Param("rid")long rid, @Param("cids") List<Long> cids){
        StringBuilder sb = new StringBuilder();
        sb.append("INSERT INTO `sys_user_role`( `role_id`, `user_id`, `create_by`, `create_date`, " +
                "`update_by`, `update_date`, `del_flag`) VALUES ");

        for (int i = 0; i < cids.size(); i++) {
            sb.append("(#{rid},#{cids["+i+"]},null,now(),null,now(),0),");
        }
        sb.deleteCharAt(sb.length()-1);
        return sb.toString();
    }

    public String insertRoleResources(@Param("rid") long rid,@Param("resources") List<SysResource> resources){
        StringBuilder sb = new StringBuilder();
        sb.append("INSERT INTO `guguanjia`.`sys_role_resource`(`role_id`, `resource_id`, `create_by`, `create_date`, `update_by`, `update_date`, `del_flag`) VALUES ");

        for (int i = 0; i < resources.size(); i++) {
            sb.append("(#{rid},#{resources["+i+"].id},null,now(),null,now(),0),");
        }
        sb.deleteCharAt(sb.length()-1);
        return sb.toString();
    }

    public String insertRoleOffices(@Param("rid") long rid,@Param("offices") List<SysOffice> offices){
        StringBuilder sb = new StringBuilder();
        sb.append("INSERT INTO `guguanjia`.`sys_role_office`(`role_id`, `office_id`, `create_by`, `create_date`, `update_by`, `update_date`, `del_flag`) VALUES ");

        for (int i = 0; i < offices.size(); i++) {
            sb.append("(#{rid},#{offices["+i+"].id},null,now(),null,now(),0),");
        }
        sb.deleteCharAt(sb.length()-1);
        return sb.toString();
    }
}
