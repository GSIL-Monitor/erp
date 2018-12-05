package com.stosz.admin.ext.service;

import com.stosz.plat.common.RestResult;
import com.stosz.product.ext.model.UserDepartmentRel;

import java.util.List;

/**
 * @auther carter
 * create time    2017-11-29
 */
public interface IUserDepartmentRelService {
    String url = "/admin/remote/IUserDepartmentRelService";

    void add(UserDepartmentRel param);

    void delete(Integer id);

    void update(UserDepartmentRel param);

    RestResult find(UserDepartmentRel param);

//    List<UserDepartmentRel> findByUserId(Integer userId);

    List<UserDepartmentRel> findByUserId(Integer userId, Boolean usable);

    List<UserDepartmentRel> findByParam(UserDepartmentRel param);

    /**
     * 根据用户id获取到当前用户的部门数据权限
     * @param userId 用户id
     * @return 返回结果
     */
    List<Integer> findDeptIdByUser(Integer userId);
}
