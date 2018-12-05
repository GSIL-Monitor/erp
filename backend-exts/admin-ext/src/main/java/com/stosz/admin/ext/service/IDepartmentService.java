package com.stosz.admin.ext.service;

import com.stosz.admin.ext.common.DepartmentNode;
import com.stosz.admin.ext.model.Department;

import java.util.List;

public interface IDepartmentService {

    String url ="/admin/remote/IDepartmentService";

    Department get(Integer id);

    /**
     * 获取所有的部门列表
     */
    List<Department> findAll();

    /**
     * 获取到所有部门的组织结构
     */
    DepartmentNode getNode();

    /**
     * 根据部门编号获取到该部门下所有的部门（包括该部门）
     */
    List<Department> findByNo(String no);

    /**
     * 根据部门编号获取到该部门的子树（包括该部门）
     */
    DepartmentNode getNodeByNo(String no);

    /**
     * 根据部门id获取到老erp部门id
     */
    Integer getOldIdById(Integer id);

    /**
     * 根据老erp部门id获取到部门
     */
    Department getByOldId(Integer oldId);

    /**
     * 根据老erp用户id获取到用户所属部门
     */
    Department getByUserOldId(Integer oldId);

    List<Department> findByIds(List<Integer> ids);

    List<Department> findByNos(List<String> nos);

    List<Department> findByParentId(Integer parentId);

    /**
     * 查询该部门所在的一级部门
     * @param deptId 一级部门
     * @return 一级部门
     */
    Department findParentByDeptId(Integer deptId);

    /**
     * 返回所有一级部门
     * @return 一级部门
     */
    List<Department> findAllTopDept();

    /**
     * 根据一堆部门返回这些部门的部门树
     * @param departmentList 部门list
     * @return 结果
     */
    DepartmentNode getNodeByDeptList(List<Department> departmentList);

    /**
     * 根据事业部获取这些事业不下的所有叶子部门
     * @param buDeptList  事业部
     * @return 叶子部门
     */
    List<Department> findByBuDeptList(List<Department> buDeptList);


}
