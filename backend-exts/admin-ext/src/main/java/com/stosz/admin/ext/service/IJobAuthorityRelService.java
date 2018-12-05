package com.stosz.admin.ext.service;

import com.stosz.admin.ext.model.Department;
import com.stosz.admin.ext.model.JobAuthorityRel;
import com.stosz.admin.ext.model.User;
import com.stosz.plat.common.RestResult;
import com.stosz.plat.common.UserDto;

import java.util.List;

/**
 * @auther carter
 * create time    2017-11-29
 */
public interface IJobAuthorityRelService {
    String url = "/admin/remote/IJobAuthorityRelService";

    void insert(JobAuthorityRel jobAuthorityRel);

    void delete(Integer id);

    RestResult findByJobId(Integer jobId);

    Boolean hasSelfAuthority(UserDto userDto);

    List<JobAuthorityRel> findByUser(UserDto user);

    /**
     * 获取当前用户拥有的所有部门Id
     * @param user 用户
     * @return 部门id集合
     */
    List<Integer> findDepartmentIdsByUser(UserDto user);

    /**
     * 获取当前用户的职位数据权限。
     * @param userId 用户
     * @return 较大的职位数据权限
     */
    JobAuthorityRel getByUser(Integer userId);

    List<Department> findDepartmentByUser(UserDto userDto);

}
