package com.stosz.admin.web;

import com.stosz.admin.ext.common.DepartmentNode;
import com.stosz.admin.ext.model.Department;
import com.stosz.admin.service.admin.DepartmentService;
import com.stosz.plat.common.RestResult;
import com.stosz.plat.web.AbstractController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by XCY on 2017/8/17.
 * desc:
 */
@Controller
@RequestMapping("/admin/department")
public class DepartmentController extends AbstractController {
    private static final String TAG = "DepartmentController";

    @Resource
    private DepartmentService departmentService;

    @RequestMapping("")
    public String list(Model model, HttpServletRequest request) {
        return "/admin/department/department";
    }


    @RequestMapping("/list")
    @ResponseBody
    public RestResult list() {
        RestResult result = new RestResult();
        DepartmentNode departmentNode = departmentService.getNode();
        Assert.notNull(departmentNode, "获取部门组织结构失败！");
        result.setItem(departmentNode);
        return result;
    }

    @RequestMapping("/getDepartmentNodeByNo")
    @ResponseBody
    public RestResult getDepartmentNodeByNo(String no) {
        RestResult result = new RestResult();
        DepartmentNode departmentNode = departmentService.getNodeByNo(no);
        Assert.notNull(departmentNode, "获取部门信息失败！");
        result.setItem(departmentNode);
        return result;
    }

    @RequestMapping("/query")
    @ResponseBody
    public RestResult query() {
        RestResult result = new RestResult();
        List<Department> departmentList = departmentService.findAll();
        Assert.notNull(departmentList, "获取部门信息失败！");
        result.setItem(departmentList);
        return result;
    }

    @RequestMapping("/getTopDepartment")
    @ResponseBody
    public RestResult getTopDepartment(){
        RestResult result = new RestResult();
        List<Department> departmentList = departmentService.getTopDepartment();
        Assert.notNull(departmentList, "获取部门信息失败！");
        result.setItem(departmentList);
        return result;
    }
}  
