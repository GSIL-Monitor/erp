package com.stosz.admin.web;

import com.stosz.plat.common.MBox;
import com.stosz.plat.common.RestResult;
import com.stosz.plat.enums.SystemEnum;
import com.stosz.plat.service.ProjectConfigService;
import com.stosz.plat.web.CommonsController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @auther carter
 * create time    2017-12-03
 * 上传等公共服务放到admin，不必要每个子系统都有；
 */
@Controller
@RequestMapping(value = "/admin/commons")
public class CommonController extends CommonsController {

    @Autowired
    ProjectConfigService projectConfigService;

    @RequestMapping(value = "/fileHttpPrefix", method = RequestMethod.GET)
    @ResponseBody
    public RestResult fileHttpPrefix(HttpServletRequest request) {
        RestResult rr = new RestResult();
        SystemEnum systemEnum =  MBox.isInside(request)? SystemEnum.image:SystemEnum.imageOutside ;
        rr.setItem(projectConfigService.getSystemHttp(systemEnum));
        return rr;
    }

}
