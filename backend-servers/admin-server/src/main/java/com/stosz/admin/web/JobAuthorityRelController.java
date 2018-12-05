package com.stosz.admin.web;

import com.stosz.admin.ext.enums.JobAuthorityRelEnum;
import com.stosz.admin.ext.model.JobAuthorityRel;
import com.stosz.admin.ext.service.IJobAuthorityRelService;
import com.stosz.plat.common.RestResult;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * @author xiongchenyang
 * @version [1.0 , 2017/11/14]
 */
@Controller
@RequestMapping("/admin/jobAuthorityRel")
public class JobAuthorityRelController {

    @Resource
    private IJobAuthorityRelService IJobAuthorityRelService;


    @RequestMapping("/findByJobId")
    @ResponseBody
    public RestResult findByJobId(Integer jobId){
        return IJobAuthorityRelService.findByJobId(jobId);
    }

    @RequestMapping("/insert")
    @ResponseBody
    public RestResult insert( Integer jobId,  Integer authority){
        Assert.notNull(jobId,"职位id不允许为空！");
        Assert.notNull(authority,"权限id不允许为空！");
        JobAuthorityRel jobAuthorityRel = new JobAuthorityRel();
        jobAuthorityRel.setJobId(jobId);
        jobAuthorityRel.setAuthority(JobAuthorityRelEnum.fromId(authority).name());
        RestResult result = new RestResult();
        IJobAuthorityRelService.insert(jobAuthorityRel);
        result.setDesc("添加职位"+jobAuthorityRel.getJobId()+"与权限关系"+jobAuthorityRel.getJobAuthorityRelEnum().display()+"成功！");
        result.setCode(RestResult.NOTICE);
        return result;
    }

    @RequestMapping("/delete")
    @ResponseBody
    public RestResult delete(Integer id){
        RestResult result = new RestResult();
        IJobAuthorityRelService.delete(id);
        result.setDesc("删除成功！");
        result.setCode(RestResult.NOTICE);
        return result;
    }
}  
