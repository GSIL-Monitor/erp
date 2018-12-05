package com.stosz.purchase.service;

import com.stosz.admin.ext.common.DepartmentNode;
import com.stosz.admin.ext.enums.JobAuthorityRelEnum;
import com.stosz.admin.ext.model.Department;
import com.stosz.admin.ext.model.JobAuthorityRel;
import com.stosz.admin.ext.service.IDepartmentService;
import com.stosz.admin.ext.service.IJobAuthorityRelService;
import com.stosz.plat.common.ThreadLocalUtils;
import com.stosz.plat.common.UserDto;
import com.stosz.plat.utils.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 用户数据权限的接口
 * @author xiongchenyang
 * @version [1.0 , 2017/12/20]
 */
@Service
public class UserAuthorityService {

    @Resource
    private IJobAuthorityRelService iJobAuthorityRelService;

    @Resource
    private IDepartmentService iDepartmentService;

    @Resource
    private UserBuDeptRelService userBuDeptRelService;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 获取当前用户的职位数据权限
     */
    public JobAuthorityRel getJobAuthorityRel(){
        UserDto userDto = ThreadLocalUtils.getUser();
        Assert.notNull(userDto,"尚未登录！");
        return iJobAuthorityRelService.getByUser(userDto.getId());
    }

    /**
     * 获取当前用户拥有的事业部集合
     * @return 事业部集合
     */
    public List<Department> findBuDeptList(){
        UserDto userDto = ThreadLocalUtils.getUser();
        Assert.notNull(userDto,"尚未登录！");
        JobAuthorityRel jobAuthorityRel = iJobAuthorityRelService.getByUser(userDto.getId());
        Assert.notNull(jobAuthorityRel,"获取用户的职位权限失败！");
        //如果当前用户配置的职位数据权限是"全公司"，那么直接返回所有的一级部门
        if(jobAuthorityRel.getJobAuthorityRelEnum().equals(JobAuthorityRelEnum.all)){
            return iDepartmentService.findAllTopDept();
        }else{
            //不是“全公司”，判断是否是采购员，是直接返回分配的部门。
            List<Department> buDeptList = userBuDeptRelService.findByCurrentUser(userDto.getId());
            if(CollectionUtils.isNotNullAndEmpty(buDeptList)){
                return buDeptList;
            }
            //不是采购员，就判断是否是总监，如果该用户的所属部门是一级部门，那么直接返回该用户的所属部门。
            if(jobAuthorityRel.getJobAuthorityRelEnum().equals(JobAuthorityRelEnum.myDepartment)) {
                Integer MyDeptId = userDto.getDeptId();
                Department department = iDepartmentService.get(MyDeptId);
                if (department != null && department.getParentId() == 0) {
                    List<Department> departmentList = new ArrayList<>();
                    departmentList.add(department);
                    return departmentList;
                }
            }
            return null;
        }
    }

    /**
     * 获取当前用户拥有的部门树
     * @return 部门树
     */
    public DepartmentNode findDeptNode(){
        UserDto userDto = ThreadLocalUtils.getUser();
        Assert.notNull(userDto,"尚未登录！");
        JobAuthorityRel jobAuthorityRel = iJobAuthorityRelService.getByUser(userDto.getId());
        Assert.notNull(jobAuthorityRel,"获取用户的职位权限失败！");
        //如果当前用户配置的职位数据权限是"全公司"，那么直接返回所有的一级部门
        if(jobAuthorityRel.getJobAuthorityRelEnum().equals(JobAuthorityRelEnum.all)){
            return iDepartmentService.getNode();
        }else{
            // 不是“全公司”，判断是否是采购员，是直接返回分配的部门。
            List<Department> buDeptList = userBuDeptRelService.findByCurrentUser(userDto.getId());
            if(CollectionUtils.isNotNullAndEmpty(buDeptList)){
                return iDepartmentService.getNodeByDeptList(buDeptList);
            }
            // 不是采购员，就判断是否是总监，如果该用户的所属部门是一级部门，那么直接返回该用户的所属部门。
            Integer MyDeptId = userDto.getDeptId();
            Department department = iDepartmentService.get(MyDeptId);
            if(department!=null && department.getParentId() == 0){
                List<Department> departmentList = new ArrayList<>();
                departmentList.add(department);
                return iDepartmentService.getNodeByDeptList(departmentList);
            }
            return null;
        }
    }

    /**
     * 获取当前用户有权限访问的叶子部门集合
     * @return 叶子部门集合
     */
    public List<Department> findList(){
        UserDto userDto = ThreadLocalUtils.getUser();
        Assert.notNull(userDto,"尚未登录！");
        JobAuthorityRel jobAuthorityRel = iJobAuthorityRelService.getByUser(userDto.getId());
        Assert.notNull(jobAuthorityRel,"获取用户的职位权限失败！");
        //如果当前用户配置的职位数据权限是"全公司"，那么直接返回所有的一级部门
        if(jobAuthorityRel.getJobAuthorityRelEnum().equals(JobAuthorityRelEnum.all)){
            return iDepartmentService.findAll();
        }else{
            // 不是“全公司”，判断是否是采购员，是直接返回分配的部门。
            List<Department> buDeptList = userBuDeptRelService.findByCurrentUser(userDto.getId());
            if(CollectionUtils.isNotNullAndEmpty(buDeptList)){
                return iDepartmentService.findByBuDeptList(buDeptList);
            }
            // 不是采购员，就判断是否是总监，如果该用户的所属部门是一级部门，那么直接返回该用户的所属部门。
            Integer MyDeptId = userDto.getDeptId();
            Department department = iDepartmentService.get(MyDeptId);
            if(department!=null && department.getParentId() == 0){
                return iDepartmentService.findByNo(department.getDepartmentNo());
            }
            return null;
        }
    }





}  
