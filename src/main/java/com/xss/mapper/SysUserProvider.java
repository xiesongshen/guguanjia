package com.xss.mapper;

import com.xss.entity.SysUser;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.util.StringUtils;

/**
 * @author XSS
 * @date 2020/8/6
 * @desc
 */
public class SysUserProvider {

    public String selectUser(SysUser sysUser){
        return new SQL(){{
            SELECT("sys_user.*, " +
                    "sys_office.`name` office_name, " +
                    "sys_role.`name` role_name ");
            FROM("sys_user, " +
                    "sys_office, " +
                    "sys_user_role, " +
                    "sys_role");
            WHERE("sys_user.office_id = sys_office.id  " +
                    "AND sys_user.id = sys_user_role.user_id  " +
                    "AND sys_user_role.role_id = sys_role.id  " +
                    "AND sys_user.del_flag = 0 ");
            if (!StringUtils.isEmpty(sysUser.getPhone())){
                WHERE("sys_user.phone = #{phone}");
            }
            if (!StringUtils.isEmpty(sysUser.getName())){
                WHERE("sys_user.`name` LIKE CONCAT('%',#{name},'%')");
            }
            if (!StringUtils.isEmpty(sysUser.getRoleId())){
                WHERE("sys_role.id = #{roleId}");
            }
        }}.toString();
    }
}
