package com.stosz.purchase.web;

import com.stosz.plat.common.RestResult;
import com.stosz.purchase.ext.model.Platform;
import com.stosz.purchase.service.PlatformService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author xiongchenyang
 * @version [1.0 , 2017/12/28]
 */
@Controller
@RequestMapping("/purchase/base/userAccountRel")
public class UserAccountRelController {

    @Resource
    private PlatformService platformService;


    @ResponseBody
    @RequestMapping("/findAllPlatform")
    public RestResult findList(){
        RestResult result = new RestResult();
        List<Platform>  platformList = platformService.findAll();
        result.setItem(platformList);
        return result;
    }




}  
