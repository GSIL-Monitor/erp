package com.stosz.admin.ext.service;

import com.stosz.BaseTest;
import com.stosz.admin.ext.common.DepartmentNode;
import com.stosz.admin.ext.model.Department;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author xiongchenyang
 * @version [1.0 , 2017/12/21]
 */
public class IDepartmentServiceTest extends BaseTest {

    public static final Logger logger = LoggerFactory.getLogger(IDepartmentServiceTest.class);

    @Resource
    private IDepartmentService departmentService;

    @Test
    public void getNodeByDeptList() throws Exception {
        List<Department> departmentList = new ArrayList<>();
        Department department = new Department();
        department.setDepartmentNo("01");
        departmentList.add(department);
        Department department1 = new Department();
        department1.setDepartmentNo("02");
        departmentList.add(department1);
        Department department2 = new Department();
        department2.setDepartmentNo("03");
        departmentList.add(department2);
        Department department3 = new Department();
        department3.setDepartmentNo("0201");
        departmentList.add(department3);
        DepartmentNode departmentNode = departmentService.getNodeByDeptList(departmentList);
        logger.info(""+departmentNode);
    }

}