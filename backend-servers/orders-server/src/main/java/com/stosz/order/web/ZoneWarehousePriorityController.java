package com.stosz.order.web;


import com.stosz.order.ext.enums.UseableEnum;
import com.stosz.order.ext.model.ZoneWarehousePriority;
import com.stosz.order.service.ZoneWarehousePriorityService;
import com.stosz.plat.common.RestResult;
import com.stosz.plat.common.ThreadLocalUtils;
import com.stosz.plat.common.UserDto;
import com.stosz.plat.web.AbstractController;
import com.stosz.product.ext.service.IZoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author
 */
@RestController
@RequestMapping("/orders/zone_warehouse")
public class ZoneWarehousePriorityController extends AbstractController {

    @Autowired
    private ZoneWarehousePriorityService zoneWarehouseService;

    @Autowired
    private IZoneService zoneService;


    @RequestMapping(value = "find")
    public RestResult find(ZoneWarehousePriority param) {
        if(UseableEnum.other.equals(param.getStatus())){
            param.setStatus(null);
        }
        return zoneWarehouseService.find(param);
    }

    @PostMapping(value = "delete")
    public RestResult delete(@RequestParam("id") Integer id) {
        RestResult result = new RestResult();
        zoneWarehouseService.delete(id);
        result.setDesc("删除成功");
        result.setCode(RestResult.NOTICE);
        return result;
    }

    @PostMapping(value = "add")
    public RestResult add(ZoneWarehousePriority param) {
        RestResult result = new RestResult();
        Assert.notNull(param.getPriority(),"优先级不能为空");
        Assert.notNull(param.getWarehouseId(),"仓库不能为空");
        Assert.notNull(param.getZoneId(),"区域不能为空");
        Assert.notNull(param.getWarehouseName(),"参数异常");
        param.setForbidSku("");
        UserDto userDto = ThreadLocalUtils.getUser();
        param.setCreator(userDto.getLastName());
        param.setCreatorId(userDto.getId());
        zoneWarehouseService.add(param);
        result.setCode(RestResult.NOTICE);
        return result;
    }


    @PostMapping(value = "update")
    public RestResult udpate(ZoneWarehousePriority param) {
        RestResult result = new RestResult();
        Assert.notNull(param.getId(),"请求数据异常");
        Assert.notNull(param.getPriority(),"优先级不能为空");
        Assert.notNull(param.getWarehouseId(),"仓库不能为空");
        Assert.notNull(param.getZoneId(),"区域不能为空");
        Assert.notNull(param.getZoneName(),"区域名称不能为空");
        Assert.notNull(param.getWarehouseName(),"仓库名称不能为空");
        Assert.notNull(param.getStatus(),"状态不能为空");

        UserDto userDto = ThreadLocalUtils.getUser();
        param.setCreator(userDto.getLastName());
        param.setCreatorId(userDto.getId());
        zoneWarehouseService.udpate(param);
        result.setCode(RestResult.NOTICE);
        return result;
    }

    @RequestMapping(value = "enable")
    public RestResult enable(@RequestParam("id") Integer id) {
        RestResult result = new RestResult();
        ZoneWarehousePriority zw = zoneWarehouseService.getById(id);
        Assert.notNull(zw,"找不到该行数据");
        zw.setStatus(UseableEnum.use);
        zoneWarehouseService.udpate(zw);
        result.setItem(zw);
        return result;
    }

    @RequestMapping(value = "disable")
    public RestResult disable(@RequestParam("id") Integer id) {
        RestResult result = new RestResult();
        ZoneWarehousePriority zw = zoneWarehouseService.getById(id);
        Assert.notNull(zw,"找不到该行数据");
        zw.setStatus(UseableEnum.unuse);
        zoneWarehouseService.udpate(zw);
        result.setItem(zw);
        return result;
    }


    @RequestMapping(value = "detail")
    public RestResult detail(@RequestParam("id") Integer id) {
        RestResult result = new RestResult();
        ZoneWarehousePriority zw = zoneWarehouseService.getById(id);
        result.setItem(zw);
        return result;
    }


}
