package com.xss.mapper;

import com.xss.entity.ExamineMethod;
import org.springframework.util.StringUtils;

/**
 * @author XSS
 * @date 2020/7/21
 * @desc
 */
public class ExamineProvider {

    public String selectPage(ExamineMethod examineMethod) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT " +
                "    ex.*, " +
                "    su.NAME user_name, " +
                "    so.NAME office_name  " +
                "FROM " +
                "    examine ex, " +
                "    sys_user su, " +
                "    sys_office so  " +
                "WHERE " +
                "    ex.del_flag = 0 ");

        if (!StringUtils.isEmpty(examineMethod.getType())){
            sb.append("AND ex.type = #{type} ");
        }
        if (!StringUtils.isEmpty(examineMethod.getOfficeId())){
            sb.append("AND so.id = #{officeId} ");
        }
        if (!StringUtils.isEmpty(examineMethod.getName())){
            sb.append("AND su.NAME LIKE concat( '%', #{name}, '%' ) ");
        }
        sb.append("AND ex.examine_user_id = su.id " +
                "  AND su.office_id = so.id ");

        return sb.toString();
    }
}
