package com.stosz.admin.mapper.oa;

import com.stosz.admin.ext.model.Department;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by XCY on 2017/8/18.
 * desc: oa同步部门信息的Mapper
 */
@Repository
public interface OaDepartmentMapper {

    /**
     * @return oa所有的部门信息
     */
//    @Select("SELECT id, departmentname, supdepid as parent_id, tlevel,ecology_pinyin_search,departmentcode as department_no FROM HrmDepartment")
//    List<Department> findAll();
    @Select("SELECT hd.id, hd.departmentname, hd.supdepid AS parent_id, hd.tlevel,hd.ecology_pinyin_search,hd.departmentcode AS department_no,hdd.feiygsbmfzr AS master_id " +
            "FROM HrmDepartment hd LEFT JOIN HrmDepartmentDefined hdd ON hd.id = hdd.deptid ")
    List<Department> findAll();


}
