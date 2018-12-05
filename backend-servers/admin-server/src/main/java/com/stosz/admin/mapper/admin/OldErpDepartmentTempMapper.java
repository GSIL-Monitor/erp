package com.stosz.admin.mapper.admin;

import com.stosz.olderp.ext.model.OldErpDepartment;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.springframework.stereotype.Repository;

/**
 * 老erp部门信息临时表
 *
 * @author xiongchenyang
 * @version [1.0 , 2017/8/29]
 */
@Repository
public interface OldErpDepartmentTempMapper {

    /**
     * 将未匹配的老erp部门信息存入临时表
     */
    @Insert("INSERT  INTO  department_temp(id_department, parent_id, id_users, type, title) VALUES (#{idDepartment}, #{parentId}, #{idUsers}, #{type}, #{title} )")
    Integer insert(OldErpDepartment oldErpDepartment);

    /**
     * 截断临时表
     */
    @Delete("delete from department_temp")
    void truncate();
}  
