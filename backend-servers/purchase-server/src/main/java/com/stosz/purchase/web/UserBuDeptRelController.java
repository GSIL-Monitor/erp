package com.stosz.purchase.web;

import com.stosz.admin.ext.common.DepartmentNode;
import com.stosz.admin.ext.model.Department;
import com.stosz.admin.ext.model.User;
import com.stosz.admin.ext.service.IDepartmentService;
import com.stosz.admin.ext.service.IUserService;
import com.stosz.plat.common.MBox;
import com.stosz.plat.common.RestResult;
import com.stosz.plat.common.ThreadLocalUtils;
import com.stosz.plat.common.UserDto;
import com.stosz.plat.web.AbstractController;
import com.stosz.product.ext.constant.MenuKeyword;
import com.stosz.purchase.ext.enums.UserBuDeptState;
import com.stosz.purchase.ext.model.UserBuDept;
import com.stosz.purchase.service.UserAuthorityService;
import com.stosz.purchase.service.UserBuDeptRelService;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.List;

/**
 * 用户与事业部关联的controller
 * @author xiongchenyang
 * @version [1.0 , 2017/12/14]
 */
@Controller
@RequestMapping("/purchase/base/userBuDeptRel")
public class UserBuDeptRelController extends AbstractController{

    @Resource
    private UserBuDeptRelService userBuDeptRelService;
    @Resource
    private IUserService iUserService;
    @Resource
    private IDepartmentService iDepartmentService;
    @Resource
    private UserAuthorityService userAuthorityService;



    @RequestMapping(method = RequestMethod.GET, value = "/find")
    @ResponseBody
    public RestResult queryUserBuDeptList(UserBuDept userBuDept) {
        RestResult restResult = new RestResult();
        int count = userBuDeptRelService.count(userBuDept);
        restResult.setTotal(count);
        if (count == 0) {
            restResult.setCode(RestResult.OK);
            return restResult;
        }

        List<UserBuDept> userBuDeptList = userBuDeptRelService.find(userBuDept);
        restResult.setItem(userBuDeptList);
        return restResult;
    }

    /**
     * 新增采购专员与部门关系
     *
     */
    @RequestMapping("/insert")
    @ResponseBody
    public RestResult addUserDeptRel(UserBuDept userBuDept) {
        RestResult restResult = new RestResult();
        Assert.notNull(userBuDept.getUserId(), "用户不能为空");
        Assert.notNull(userBuDept.getBuDepartmentId(), "部门不能为空");

        User spuUser = iUserService.getById(userBuDept.getUserId());
        Assert.notNull(spuUser, "用户id[" + userBuDept.getUserId() + "]不存在");

        Department department = iDepartmentService.get(userBuDept.getBuDepartmentId());
        Assert.notNull(department, "部门id[" + userBuDept.getBuDepartmentId() + "]不存在");

        UserDto user= ThreadLocalUtils.getUser();
        userBuDept.setCreatorId(user.getId());
        userBuDept.setCreator(user.getLastName());
        userBuDept.setUserId(spuUser.getId());
        userBuDept.setUserName(spuUser.getLastname());
        userBuDept.setBuDepartmentName(department.getDepartmentName());
        userBuDept.setBuDepartmentNo(department.getDepartmentNo());
        userBuDept.setEnable(UserBuDeptState.CREATE.ordinal());
        UserBuDept userBuDeptDB = userBuDeptRelService.getByUnique(userBuDept.getBuDepartmentId(),userBuDept.getUserId());
        Assert.isNull(userBuDeptDB,"不允许插入重复数据！！！");

        userBuDeptRelService.insert(userBuDept);

        restResult.setCode(RestResult.NOTICE);
        restResult.setDesc("操作成功");
        return restResult;
    }

    /**
     * 删除采购专员与部门关系
     */
    @RequestMapping(method = RequestMethod.POST, value = "/delete")
    @ResponseBody
    public RestResult delUserBuDeptRel(Integer id) {
        RestResult restResult = new RestResult();
        userBuDeptRelService.delete(id);
        restResult.setCode(RestResult.NOTICE);
        restResult.setDesc("操作成功");
        return restResult;
    }

    /**
     * 启用/禁用 采购专员与部门关系
     */
    @RequestMapping(method = RequestMethod.POST, value = "/update")
    @ResponseBody
    public RestResult updateUserBuDeptRel(Integer id, Integer enable) {
        RestResult restResult = new RestResult();
        Assert.notNull(id, "ID不能为空");
        Assert.notNull(enable, "状态不能为空");
        UserBuDept userBuDept = userBuDeptRelService.getById(id);
        Assert.notNull(userBuDept, "记录id[" + id + "]不存在");
        userBuDeptRelService.updateState(id, enable);
        restResult.setCode(RestResult.NOTICE);
        restResult.setDesc("操作成功");
        return restResult;
    }

    /**
     * 获取当前采购员有权限访问的部门
     * @return 采购员访问的部门
     */
    @RequestMapping(method = RequestMethod.POST,value = "/findDeptByCurrentUser")
    @ResponseBody
    public RestResult findDeptByCurrentUser(){
        RestResult result = new RestResult();
        UserDto userDto = ThreadLocalUtils.getUser();
        if(userDto == null || userDto.getId() == null){
            result.setCode(RestResult.LOGIN);
            return result;
        }
        List<Department> departmentList = userBuDeptRelService.findByCurrentUser(userDto.getId());
        result.setItem(departmentList);
        return result;
    }

    @RequestMapping(value = "/findUser")
    @ResponseBody
    public RestResult findUser(String search,Integer buDeptId){
        RestResult result = new RestResult();
        List<User> userList = userBuDeptRelService.findUser(search,buDeptId);
        result.setItem(userList);
        return result;
    }

    /**
     * 获取当前用户有权限访问的事业部
     * @return 用户访问的部门
     */
    @RequestMapping("/findBuDeptByCurrentUser")
    @ResponseBody
    public RestResult findBuDeptByCurrentUser(){
        RestResult result = new RestResult();
        List<Department> departmentList = userAuthorityService.findBuDeptList();
        result.setItem(departmentList);
        return result;
    }

    /**
     * 获取当前用户有权限获取的部门树
     * @return 部门树
     */
    @RequestMapping("/findDeptNodeByCurrentUser")
    @ResponseBody
    public RestResult findDeptNodeByCurrentUser(){
        RestResult result = new RestResult();
        DepartmentNode departmentNode = userAuthorityService.findDeptNode();
        result.setItem(departmentNode);
        return result;
    }
}  
