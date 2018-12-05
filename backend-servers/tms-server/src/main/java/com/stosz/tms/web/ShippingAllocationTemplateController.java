package com.stosz.tms.web;

import com.stosz.plat.common.RestResult;
import com.stosz.plat.common.ThreadLocalUtils;
import com.stosz.plat.common.UserDto;
import com.stosz.plat.utils.StringUtil;
import com.stosz.plat.web.AbstractController;
import com.stosz.tms.model.ShippingAllocationTemplate;
import com.stosz.tms.model.ShippingWay;
import com.stosz.tms.model.ShippingZoneStoreRel;
import com.stosz.tms.service.ShippingAllocationTemplateService;
import com.stosz.tms.vo.ShippingAllocationTemplateVo;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/tms/allocationTemplate")
public class ShippingAllocationTemplateController extends AbstractController {

    @Resource
    private ShippingAllocationTemplateService service;

    @RequestMapping("query")
    @ResponseBody
    public RestResult query(ShippingAllocationTemplate allocationTemplate) {
        RestResult restResult = new RestResult();
        int count = service.count(allocationTemplate);
        restResult.setTotal(count);
        if (count <= 0) {
            restResult.setCode(RestResult.OK);
            return restResult;
        }
//        final Integer start = allocationTemplate.getStart();
//        final Integer limit = allocationTemplate.getLimit();
//
//        //开始位置
//        allocationTemplate.setStart((start == null || start <= 0) ? 0 : (start -1)* limit);
//        //需要显示的条数
//        allocationTemplate.setLimit((limit == null ) ? 10 : limit);

        final List<ShippingAllocationTemplateVo> allocationTemplateVos = service.queryList(allocationTemplate);
        restResult.setItem(allocationTemplateVos);
        restResult.setCode(RestResult.OK);
        return restResult;
    }



    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public RestResult addShippinigStoreRel(ShippingAllocationTemplate allocationTemplate){
        Assert.notNull(allocationTemplate.getShippingWayId(),"物流线路不能为空");
        Assert.notNull(allocationTemplate.getWmsId(),"仓库不能为空");
        Assert.notNull(allocationTemplate.getSorted(),"优先顺序不能为空");
        Assert.notNull(allocationTemplate.getCargoNum(),"所有数量不能为空");
        Assert.notNull(allocationTemplate.getEachCargoNum(),"每次分配数量不能为空");
        Assert.notNull(allocationTemplate.getEachGeneralNum(),"每次分配普货数量不能为空");
        Assert.notNull(allocationTemplate.getEachSpecialNum(),"每次分配特货数量不能为空");
        Assert.notNull(allocationTemplate.getGeneralCargoNum(),"普货数量不能为空");
        Assert.notNull(allocationTemplate.getSpecialCargoNum(),"特货数量不能为空");

        UserDto userDto= ThreadLocalUtils.getUser();
        allocationTemplate.setCreatorId(userDto.getId());
        allocationTemplate.setCreator(userDto.getLastName());
        allocationTemplate.setEnable(1);

        service.add(allocationTemplate);
        RestResult restResult = new RestResult();
        restResult.setCode(RestResult.NOTICE);
        restResult.setDesc("新增物流配额成功");

        return restResult;
    }


    @RequestMapping(value = "/updateEnable",method = RequestMethod.PUT)
    public RestResult updateEnable(
            @ModelAttribute ShippingAllocationTemplate allocationTemplate
    ){
        Assert.notNull(allocationTemplate.getId(),"id不能为空");
        Assert.notNull(allocationTemplate.getEnable(),"状态不能为空");
        Assert.isTrue(allocationTemplate.getEnable() == 1 || allocationTemplate.getEnable() == 2,"非法状态值" );

        UserDto userDto=ThreadLocalUtils.getUser();
        allocationTemplate.setModifierId(userDto.getId());
        allocationTemplate.setModifier(userDto.getLastName());

        service.updateEnable(allocationTemplate);
        RestResult restResult = new RestResult();
        restResult.setCode(RestResult.NOTICE);
        restResult.setDesc("更新物流配额状态成功");
        return restResult;
    }

    @RequestMapping(method = RequestMethod.PUT)
    public RestResult update(ShippingAllocationTemplate allocationTemplate) {
        Assert.notNull(allocationTemplate.getId(),"id不能为空");
        Assert.notNull(allocationTemplate.getShippingWayId(),"物流线路不能为空");
        Assert.notNull(allocationTemplate.getWmsId(),"仓库不能为空");
        Assert.notNull(allocationTemplate.getSorted(),"优先顺序不能为空");
        Assert.notNull(allocationTemplate.getCargoNum(),"所有数量不能为空");
        Assert.notNull(allocationTemplate.getEachCargoNum(),"每次分配数量不能为空");
        Assert.notNull(allocationTemplate.getEachGeneralNum(),"每次分配普货数量不能为空");
        Assert.notNull(allocationTemplate.getEachSpecialNum(),"每次分配特货数量不能为空");
        Assert.notNull(allocationTemplate.getGeneralCargoNum(),"普货数量不能为空");
        Assert.notNull(allocationTemplate.getSpecialCargoNum(),"特货数量不能为空");

        UserDto userDto=ThreadLocalUtils.getUser();
        allocationTemplate.setModifierId(userDto.getId());
        allocationTemplate.setModifier(userDto.getLastName());

        service.update(allocationTemplate);
        RestResult restResult = new RestResult();
        restResult.setCode(RestResult.NOTICE);
        restResult.setDesc("更新物流配额成功");
        return restResult;
    }
}
