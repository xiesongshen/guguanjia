package com.xss.mapper;

import com.xss.entity.WorkOrder;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

public interface WorkOrderMapper extends Mapper<WorkOrder> {

    @SelectProvider(value = WorkOrderProvider.class, method = "selectPage")
    public List<WorkOrder> selectPage(Map<String, Object> map);


    //根据工单id查询  工单和用户、公司信息
    @Select("SELECT " +
            "   wo.*, " +
            "   st.`name` transport_user_name, " +
            "   st.phone transport_user_phone, " +
            "   sr.`name` recipient_user_name, " +
            "   sr.phone recipient_user_phone, " +
            "   sc.`name` create_user_name, " +
            "   sc.phone create_user_phone, " +
            "   co.`name` create_office_name, " +
            "   so.`name` transport_office_name, " +
            "   ro.`name` recipient_office_name  " +
            "FROM " +
            "   work_order wo " +
            "   LEFT JOIN sys_user st ON wo.transport_user_id = st.id " +
            "   LEFT JOIN sys_user sr ON wo.recipient_user_id = sr.id " +
            "   LEFT JOIN sys_user sc ON wo.create_user_id = sc.id " +
            "   LEFT JOIN sys_office so ON st.office_id = so.id " +
            "   LEFT JOIN sys_office ro ON sr.office_id = ro.id " +
            "   LEFT JOIN sys_office co ON sc.office_id = co.id  " +
            "WHERE " +
            "   wo.del_flag = 0  " +
            "   AND sc.del_flag = 0  " +
            "   AND co.del_flag = 0  " +
            "   AND wo.id = #{id}")
    Map<String, Object> selectById(long id);
}