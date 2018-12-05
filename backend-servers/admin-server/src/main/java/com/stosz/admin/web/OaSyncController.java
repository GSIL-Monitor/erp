package com.stosz.admin.web;

import com.stosz.admin.service.sync.OaDepartmentSyncService;
import com.stosz.admin.service.sync.OaJobSyncService;
import com.stosz.admin.service.sync.OaUserSyncService;
import com.stosz.plat.common.RestResult;
import com.stosz.plat.web.AbstractController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.annotation.Resource;
import java.util.Date;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * oa信息同步的controller
 *
 * @author xiongchenyang
 * @version [1.0 , 2017/9/7]
 */
@Controller
@RequestMapping("/admin/oaSync")
public class OaSyncController extends AbstractController {

    @Resource
    private OaUserSyncService oaUserSyncService;
    @Resource
    private OaJobSyncService oaJobSyncService;
    @Resource
    private OaDepartmentSyncService oaDepartmentSyncService;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * OA 用户增量同步
     */
    @RequestMapping("/pullAllUser")
    @ResponseBody
    public RestResult userAllSync() {
        RestResult result = new RestResult();
        try {
            oaUserSyncService.pull();
//            String desc = (String) future.get();
            result.setDesc("全量导入用户信息成功!");
            result.setCode(RestResult.NOTICE);
        } catch (Exception e) {
            logger.error("全量导入用户信息出错！", e);
            result.setDesc("全量导入用户信息出错,请联系系统管理员！主要异常信息["+e.getMessage()+"]");
            result.setCode(RestResult.FAIL);
        }
        return result;
    }


    @RequestMapping("/pullAllJob")
    @ResponseBody
    public RestResult jobAllSync() {
        RestResult result = new RestResult();
        Future<Object> future = oaJobSyncService.pull();
        try {
            String desc = (String) future.get();
            result.setDesc(desc);
            result.setCode(RestResult.NOTICE);
        } catch (InterruptedException | ExecutionException | RuntimeException e) {
            logger.info("导入OA职位信息出错！", e);
            result.setDesc("导入OA职位信息出错,请联系系统管理员！");
            result.setCode(RestResult.FAIL);
        }
        return result;
    }


    @RequestMapping("/pullJob")
    @ResponseBody
    public RestResult jobSync() {
        RestResult result = new RestResult();
        Future<Object> future = oaJobSyncService.pull();
        try {
            String desc = (String) future.get();
            result.setDesc(desc);
            result.setCode(RestResult.NOTICE);
        } catch (InterruptedException | ExecutionException | RuntimeException e) {
            logger.info("增量同步职位信息出错！", e);
            result.setDesc("增量同步职位信息出错,请联系系统管理员！");
            result.setCode(RestResult.FAIL);
        }
        return result;
    }

    /**
     * OA部门导入
     */
    @RequestMapping("/pullAllDepartment")
    @ResponseBody
    public RestResult departmentAllSync() {
        RestResult result = new RestResult();
        try {
            oaDepartmentSyncService.pull();
//            String desc = (String) future.get();
            result.setDesc("导入OA部门信息成功!");
            result.setCode(RestResult.NOTICE);
        } catch (Exception e) {
            logger.error("导入OA部门信息出错！", e);
            result.setDesc("导入OA部门信息出错,请联系系统管理员！主要异常信息["+ e.getMessage() +"]");
            result.setCode(RestResult.FAIL);
        }
        return result;
    }


    @RequestMapping("/pullDepartment")
    @ResponseBody
    public RestResult departmentSync() {
        RestResult result = new RestResult();
        Future<Object> future = oaJobSyncService.pull();
        try {
            String desc = (String) future.get();
            result.setDesc(desc);
            result.setCode(RestResult.NOTICE);
        } catch (InterruptedException | ExecutionException | RuntimeException e) {
            logger.info("同步职位信息出错！", e);
            result.setDesc("同步职位信息出错,请联系系统管理员！");
            result.setCode(RestResult.FAIL);
        }
        return result;
    }


}
