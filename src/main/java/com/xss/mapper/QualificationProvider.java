package com.xss.mapper;

import com.xss.entity.Qualification;
import com.xss.entity.QualificationMethod;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * @author XSS
 * @date 2020/7/20
 * @desc
 */
public class QualificationProvider {

    public String selectPage(QualificationMethod qualification){
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT " +
                "    qu.*, " +
                "    uu.NAME upload_name, " +
                "    cu.NAME check_name " +
                "FROM " +
                "    qualification qu " +
                "    LEFT JOIN sys_user uu ON qu.upload_user_id = uu.id " +
                "    LEFT JOIN sys_user cu ON qu.check_user_id = cu.id " +
                "WHERE " +
                "    qu.del_flag=0 ");

        if (!StringUtils.isEmpty(qualification.getType())){
            sb.append("and qu.type = #{type} ");
        }
        if (!StringUtils.isEmpty(qualification.getCheck())){
            sb.append("AND qu.CHECK = #{check} ");
        }
        if (!StringUtils.isEmpty(qualification.getStartDate())){
            sb.append("AND qu.create_date >= #{startDate} ");
        }
        if (!StringUtils.isEmpty(qualification.getEndDate())){
            sb.append("AND qu.create_date <= #{endDate} ");
        }

        return sb.toString();
    }
}
