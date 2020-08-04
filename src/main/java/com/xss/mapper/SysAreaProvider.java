package com.xss.mapper;

import com.xss.entity.SysArea;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * @author XSS
 * @date 2020/7/29
 * @desc
 */
public class SysAreaProvider {

    public String selectPage(SysArea sysArea) {
        return new SQL() {{
            SELECT("sub.*, parent.`name` parent_name ");
            FROM("sys_area sub, sys_area parent ");
            WHERE("sub.parent_id = parent.id AND sub.del_flag = 0");
            if (!StringUtils.isEmpty(sysArea.getName())) {
                WHERE("sub.`name` LIKE concat ( '%', #{name}, '%' ) ");
            }
        }}.toString();
    }

    public String insertMany(@Param("areas") List<SysArea> areas) {
        return new SQL() {{
            INSERT_INTO(" `guguanjia`.`sys_area`");
            INTO_COLUMNS("`parent_id`, `parent_ids`, `code`, `name`, `type`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`, `icon`");
            for (int i = 0; i < areas.size(); i++) {
                INTO_VALUES("#{areas["+i+"].parentId}, #{areas["+i+"].parentIds}, #{areas["+i+"].code}, " +
                        "#{areas["+i+"].name},#{areas["+i+"].type}, #{areas["+i+"].createBy}," +
                        "now(), #{areas["+i+"].updateBy}, now(), " +
                        "#{areas["+i+"].remarks}, #{areas["+i+"].delFlag}, #{areas["+i+"].icon}");
                ADD_ROW();
            }
        }}.toString();
    }
}
