package com.stosz.admin.web;

import com.stosz.admin.ext.common.MenuNode;
import com.stosz.admin.ext.model.Job;
import com.stosz.admin.service.admin.JobService;
import com.stosz.admin.service.admin.MenuService;
import com.stosz.plat.common.RestResult;
import com.stosz.plat.web.AbstractController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
@RequestMapping("/admin/job")
public class JobController extends AbstractController {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Resource
    private JobService jobService;

    @Resource
    private MenuService menuService;


    /**
     * 用于跳转到列表页面
     *
     * @return 跳转到页面
     */
    @RequestMapping(method = RequestMethod.GET, value = "")
    @ResponseBody
    public ModelAndView list() {
        return new ModelAndView("/admin/job/job");
    }

    /**
     * 根据职位id获取到该职位的信息
     *
     * @param id 职位id
     * @return 职位信息
     */
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    @ResponseBody
    public RestResult get(@RequestParam(required = true) int id) {
        RestResult result = new RestResult();
        Job entity = jobService.getById(id);
        Assert.notNull(entity, "系统未查询到该职位！");
        result.setItem(entity);
        return result;
    }

    /**
     * 根据条件查询职位，并分页
     *
     * @param job 职位查询条件
     * @return 分页后的职位信息
     */
    @RequestMapping("/query")
    @ResponseBody
    public RestResult query(Job job, HttpServletRequest request, HttpServletResponse response, Model model) {
        RestResult result = new RestResult();
        //获取当前页面需要显示的数据
        Integer start = job.getStart();
        if (start != 0){
            start = start -1;
            job.setStart(start);
        }
        List<Job> productList = jobService.query(job);
        Assert.notNull(productList, "未查询到对应的职位列表！");
        //获取总记录数
        int counts = jobService.count(job);
        result.setCode(RestResult.OK);
        result.setTotal(counts);     //返回总记录数
        result.setItem(productList); //返回当前页面的记录数
        return result;
    }

    @RequestMapping("/getNameBySearch")
    @ResponseBody
    public RestResult getNameBySearch(String search) {
        RestResult result = new RestResult();
        List<Job> list = jobService.findNameBySearch(search);
        result.setItem(list);
        return result;
    }


    /**
     * 更新该职位的权限
     *
     * @param jobId     职位id
     * @param menuId    菜单权限的id
     * @param isChecked 是否选中
     * @return 更新结果
     */
    @RequestMapping("/updateJobMenu")
    @ResponseBody
    public RestResult updateJobMenu(int jobId, int menuId, boolean isChecked) {
        RestResult result = new RestResult();
        //根据isChecked 判断是删除，还是插入，
        jobService.updateJobMenu(jobId, menuId, isChecked);
        result.setCode(RestResult.NOTICE);
        String desc = (isChecked ? "添加" : "取消") + "id:" + menuId + "的菜单权限成功！";
        result.setDesc(desc);
        return result;
    }

    /**
     * 更新该职位的菜单，元素权限
     *
     * @param jobId     职位id
     * @param menuId    菜单权限的id
     * @param elementId 元素权限的id
     * @param isChecked 是否选中
     * @return 更新结果
     */
    @RequestMapping("/updateJobMenuElement")
    @ResponseBody
    public RestResult updateJobMenu(int jobId, int menuId, Integer elementId, boolean isChecked) {
        RestResult result = new RestResult();
        //根据isChecked 判断是删除，还是插入，
        jobService.updateJobMenuElement(jobId, menuId, elementId, isChecked);
        result.setCode(RestResult.NOTICE);
        String desc = (isChecked ? "添加" : "取消") + "权限成功！";
        result.setDesc(desc);
        return result;
    }


    /**
     * 根据职位id获取到对应的菜单树
     * 注： 该接口给获取左侧菜单用的
     *
     * @param jobId 职位id
     * @return 菜单树
     */
    @RequestMapping("/getMenuNodeByJob")
    @ResponseBody
    public RestResult getMenuNodeByJob(Integer jobId) {
        Assert.notNull(jobId, "职位id不允许为空！");
        RestResult result = new RestResult();
        MenuNode menuNode = menuService.getNodeByJobId(jobId);
        Assert.notNull(menuNode, "获取权限菜单失败！");
        result.setItem(menuNode);
        return result;
    }

    /**
     * 该接口给建权的时候用的
     *
     * @param jobId 职位id
     * @return 菜单树
     */
    @RequestMapping("/getPermission")
    @ResponseBody
    public RestResult getPermission(Integer jobId) {
        Assert.notNull(jobId, "职位id不允许为空！");
        RestResult result = new RestResult();
        MenuNode menuNode = menuService.getPermission(jobId);
        Assert.notNull(menuNode, "获取权限菜单失败！");
        result.setItem(menuNode);
        return result;
    }
}
