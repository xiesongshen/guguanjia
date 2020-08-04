package com.xss.mapper;

import org.apache.ibatis.jdbc.SQL;
import org.springframework.util.StringUtils;

import java.util.Map;

/**
 * @author XSS
 * @date 2020/7/22
 * @desc
 */
public class WorkOrderProvider {

    public String selectPage(Map<String,Object> map){
        return new SQL(){{

            SELECT("wo.*," +
                    "st.`name` transport_user_name," +
                    "sr.`name` recipient_user_name," +
                    "sc.`name` create_user_name," +
                    "co.`name` create_office_name ");
            FROM("work_order wo");
            LEFT_OUTER_JOIN("sys_user st ON wo.transport_user_id = st.id " +
                    " LEFT JOIN sys_user sr ON wo.recipient_user_id = sr.id " +
                    " LEFT JOIN sys_user sc ON wo.create_user_id = sc.id " +
                    " LEFT JOIN sys_office so ON st.office_id = so.id " +
                    " LEFT JOIN sys_office ro ON sr.office_id = ro.id " +
                    " LEFT JOIN sys_office co ON sc.office_id = co.id ");
            WHERE("wo.del_flag = 0  " +
                    " AND sc.del_flag = 0  " +
                    " AND co.del_flag = 0  ");

            if (map.containsKey("status")&& !StringUtils.isEmpty(map.get("status"))){
                WHERE(" wo.STATUS = #{status} ");
            }
            if (map.containsKey("startDate")&& !StringUtils.isEmpty(map.get("startDate"))){
                WHERE(" wo.create_date >= #{startDate} ");
            }
            if (map.containsKey("endDate")&& !StringUtils.isEmpty(map.get("endDate"))){
                WHERE(" wo.create_date <= #{endDate} ");
            }
            if (map.containsKey("officeId")&& !StringUtils.isEmpty(map.get("officeId"))){
                WHERE(" ( so.id = #{officeId} OR co.id = #{officeId} OR ro.id = #{officeId} ) ");
            }

        }}.toString();
    }
}
