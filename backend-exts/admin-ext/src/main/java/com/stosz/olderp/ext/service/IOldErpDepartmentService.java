package com.stosz.olderp.ext.service;

import com.stosz.admin.ext.model.Department;
import com.stosz.olderp.ext.model.OldErpDepartment;

import java.util.List;

/**
 * @author xiongchenyang
 * @version [1.0 , 2017/8/25]
 */
public interface IOldErpDepartmentService {
    String url = "/admin/remote/IOldErpDepartmentService";
    Integer insertOldErpDepartment(Department department);

    void deleteOldErpDepartment(Integer id);

    List<OldErpDepartment> findByNoRepeat();

    List<OldErpDepartment> findByRepeat();

}  
