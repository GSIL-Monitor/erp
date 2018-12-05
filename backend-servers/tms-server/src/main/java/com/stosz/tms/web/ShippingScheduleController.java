package com.stosz.tms.web;

import com.stosz.plat.common.RestResult;
import com.stosz.plat.common.ThreadLocalUtils;
import com.stosz.plat.common.UserDto;
import com.stosz.plat.web.AbstractController;
import com.stosz.tms.model.ShippingSchedule;
import com.stosz.tms.service.ShippingScheduleService;
import com.stosz.tms.vo.ShippingScheduleListVo;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/tms/shippingSchedule")
public class ShippingScheduleController extends AbstractController {

    @Resource
    private ShippingScheduleService service;

    @RequestMapping("query")
    @ResponseBody
    public RestResult query(ShippingSchedule shippingSchedule) {
        RestResult restResult = new RestResult();
        int count = service.count(shippingSchedule);
        restResult.setTotal(count);
        if (count <= 0) {
            restResult.setCode(RestResult.OK);
            return restResult;
        }
//        final Integer start = shippingSchedule.getStart();
//        final Integer limit = shippingSchedule.getLimit();
//
//        //开始位置
//        shippingSchedule.setStart((start == null || start <= 0) ? 0 : (start -1)* limit);
//        //需要显示的条数
//        shippingSchedule.setLimit((limit == null ) ? 10 : limit);

        final List<ShippingScheduleListVo> scheduleListVos = service.queryList(shippingSchedule);
        restResult.setItem(scheduleListVos);
        restResult.setCode(RestResult.OK);
        return restResult;
    }


    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public RestResult addShippinigStoreRel(ShippingSchedule shippingSchedule) throws ParseException {
        Assert.notNull(shippingSchedule.getShippingWayId(),"物流方式不能为空");
        Assert.notNull(shippingSchedule.getTemplateId(),"模板ID不能为空");
        Assert.notNull(shippingSchedule.getWmsId(),"仓库不能为空");
        Assert.notNull(shippingSchedule.getSorted(),"优先顺序不能为空");
        Assert.notNull(shippingSchedule.getCargoNum(),"所有数量不能为空");
        Assert.notNull(shippingSchedule.getEachCargoNum(),"每次分配数量不能为空");
        Assert.notNull(shippingSchedule.getEachGeneralNum(),"每次分配普货数量不能为空");
        Assert.notNull(shippingSchedule.getEachSpecialNum(),"每次分配特货数量不能为空");
        Assert.notNull(shippingSchedule.getGeneralCargoNum(),"普货数量不能为空");
        Assert.notNull(shippingSchedule.getSpecialCargoNum(),"特货数量不能为空");

        UserDto userDto= ThreadLocalUtils.getUser();
        shippingSchedule.setCreatorId(userDto.getId());
        shippingSchedule.setCreator(userDto.getLastName());
        shippingSchedule.setEnable(1);
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        shippingSchedule.setScheduleDate(df.parse(df.format(new Date())));

        service.add(shippingSchedule);
        RestResult restResult = new RestResult();
        restResult.setCode(RestResult.NOTICE);
        restResult.setDesc("新增物流日程成功");

        return restResult;
    }


    @RequestMapping(value = "/updateEnable",method = RequestMethod.PUT)
    public RestResult updateEnable(
            @ModelAttribute ShippingSchedule shippingSchedule
    ){
        Assert.notNull(shippingSchedule.getId(),"id不能为空");
        Assert.notNull(shippingSchedule.getEnable(),"状态不能为空");
        Assert.isTrue(shippingSchedule.getEnable() == 1 || shippingSchedule.getEnable() == 2,"非法状态值" );

        UserDto userDto=ThreadLocalUtils.getUser();
        shippingSchedule.setModifierId(userDto.getId());
        shippingSchedule.setModifier(userDto.getLastName());

        service.updateEnable(shippingSchedule);
        RestResult restResult = new RestResult();
        restResult.setCode(RestResult.NOTICE);
        restResult.setDesc("更新物流日程状态成功");
        return restResult;
    }

    @RequestMapping(method = RequestMethod.PUT)
    public RestResult update(ShippingSchedule shippingSchedule) {
        Assert.notNull(shippingSchedule.getId(),"id不能为空");
        Assert.notNull(shippingSchedule.getTemplateId(),"模板ID不能为空");
        Assert.notNull(shippingSchedule.getShippingWayId(),"物流方式不能为空");
        Assert.notNull(shippingSchedule.getWmsId(),"仓库不能为空");
        Assert.notNull(shippingSchedule.getSorted(),"优先顺序不能为空");
        Assert.notNull(shippingSchedule.getCargoNum(),"所有数量不能为空");
        Assert.notNull(shippingSchedule.getEachCargoNum(),"每次分配数量不能为空");
        Assert.notNull(shippingSchedule.getEachGeneralNum(),"每次分配普货数量不能为空");
        Assert.notNull(shippingSchedule.getEachSpecialNum(),"每次分配特货数量不能为空");
        Assert.notNull(shippingSchedule.getGeneralCargoNum(),"普货数量不能为空");
        Assert.notNull(shippingSchedule.getSpecialCargoNum(),"特货数量不能为空");

        UserDto userDto=ThreadLocalUtils.getUser();
        shippingSchedule.setModifierId(userDto.getId());
        shippingSchedule.setModifier(userDto.getLastName());

        service.update(shippingSchedule);
        RestResult restResult = new RestResult();
        restResult.setCode(RestResult.NOTICE);
        restResult.setDesc("更新物流日程成功");
        return restResult;
    }


}
