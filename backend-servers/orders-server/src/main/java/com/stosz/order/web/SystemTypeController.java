package com.stosz.order.web;

import com.stosz.order.ext.model.SystemType;
import com.stosz.order.service.SystemTypeService;
import com.stosz.plat.common.RestResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * @auther carter
 * create time    2017-10-31
 */
@Controller("SystemTypeController")
@RequestMapping("/orders/system_type/")
public class SystemTypeController {


    @Autowired
    private SystemTypeService systemTypeService;

    @RequestMapping(method = RequestMethod.GET, value = "/list")
    @ResponseBody
    public ModelAndView list() {
        return new ModelAndView("/orders/system_type/list");
    }


    @RequestMapping(value = "/find")
    @ResponseBody
    public RestResult find(SystemType systemType) {
        RestResult restResult = new RestResult();
        List<SystemType> systemTypeList = systemTypeService.findByParam(systemType);
        Integer total = systemTypeService.findCountByParam(systemType);
        restResult.setCode("200");
        restResult.setDesc("查询成功");
        restResult.setItem(systemTypeList);
        restResult.setTotal(total);

        return restResult;
    }

    @RequestMapping("/update")
    @ResponseBody
    public RestResult update(SystemType systemType) {
        RestResult restResult = new RestResult();
        systemTypeService.update(systemType);
        restResult.setCode(RestResult.NOTICE);
        return restResult;
    }


    @RequestMapping("/insert")
    @ResponseBody
    public RestResult insert(SystemType systemType) {
        RestResult restResult = new RestResult();
        systemTypeService.insert(systemType);
        restResult.setCode(RestResult.NOTICE);
        return restResult;
    }


    @RequestMapping("/delete")
    @ResponseBody
    public RestResult delete(@RequestParam Long id) {
        RestResult restResult = new RestResult();
        systemTypeService.delete(id);
        restResult.setCode(RestResult.NOTICE);
        return restResult;
    }




}
