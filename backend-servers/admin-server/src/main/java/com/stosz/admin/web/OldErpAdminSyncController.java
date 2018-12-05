package com.stosz.admin.web;

import com.stosz.admin.service.sync.OldErpDepartmentSyncService;
import com.stosz.admin.service.sync.OldErpUserSyncService;
import com.stosz.plat.common.RestResult;
import com.stosz.plat.web.AbstractController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * oa信息同步的controller
 *
 * @author xiongchenyang
 * @version [1.0 , 2017/9/7]
 */
@Controller
@RequestMapping("/admin/oldErpSync")
public class OldErpAdminSyncController extends AbstractController {

    @Resource
    private OldErpUserSyncService oldErpUserSyncService;
    @Resource
    private OldErpDepartmentSyncService oldErpDepartmentSyncService;

    private final Logger logger = LoggerFactory.getLogger(getClass());


    /**
     * 老erp 用户同步
     */
    @RequestMapping("/pullAllUser")
    @ResponseBody
    public RestResult userAllSync() {
        RestResult result = new RestResult();
        Future<Object> future = oldErpUserSyncService.pullAll();
        try {
            String desc = (String) future.get();
            result.setDesc(desc);
            result.setCode(RestResult.NOTICE);
        } catch (InterruptedException | ExecutionException | RuntimeException e) {
            logger.info("同步老erp用户信息出错！", e);
            result.setDesc("同步老erp用户信息出错,请联系系统管理员！");
            result.setCode(RestResult.FAIL);
        }
        return result;
    }


    /**
     * 老erp 部门同步
     */
    @RequestMapping("/pullAllDepartment")
    @ResponseBody
    public RestResult departmentAllSync() {
        RestResult result = new RestResult();
        Future<Object> future = oldErpDepartmentSyncService.sync();
        try {
            String desc = (String) future.get();
            result.setDesc(desc);
            result.setCode(RestResult.NOTICE);
        } catch (InterruptedException | ExecutionException | RuntimeException e) {
            logger.info("同步老erp部门信息出错！", e);
            result.setDesc("同步老erp部门信息出错,请联系系统管理员！");
            result.setCode(RestResult.FAIL);
        }
        return result;
    }

}
