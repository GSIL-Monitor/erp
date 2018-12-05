package com.stosz.tms.web;

import com.stosz.plat.common.RestResult;
import com.stosz.plat.common.ThreadLocalUtils;
import com.stosz.plat.common.UserDto;
import com.stosz.plat.web.AbstractController;
import com.stosz.tms.dto.ShippingTrackingNoSectionAddDto;
import com.stosz.tms.dto.ShippingTrackingNumberListAddDto;
import com.stosz.tms.model.ShippingTrackingNoList;
import com.stosz.tms.service.ShippingTrackingNoListService;
import com.stosz.tms.vo.ShippingTrackingGroupCountListVo;
import com.stosz.tms.vo.ShippingTrackingNoCountVo;
import com.stosz.tms.vo.ShippingTrackingNoListListVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/tms/shippingTrackingNoList")
public class ShippingTrackingNoListController extends AbstractController {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Resource
    private ShippingTrackingNoListService service;

    @RequestMapping(value = "query",method = RequestMethod.POST)
    public RestResult queryList(ShippingTrackingNoList shippingTrackingNoList){

        RestResult restResult = new RestResult();
        int count = service.count(shippingTrackingNoList);
        restResult.setTotal(count);
        if (count <= 0) {
            restResult.setCode(RestResult.OK);
            return restResult;
        }

//        final Integer start = shippingTrackingNoList.getStart();
//        final Integer limit = shippingTrackingNoList.getLimit();
//
//        //开始位置
//        shippingTrackingNoList.setStart((start == null || start <= 0) ? 0 : (start -1)* limit);
//        //需要显示的条数
//        shippingTrackingNoList.setLimit((limit == null ) ? 10 : limit);

        final List<ShippingTrackingNoListListVo> shippingTrackingNoListListVos = service.queryList(shippingTrackingNoList);
        restResult.setItem(shippingTrackingNoListListVos);
        restResult.setCode(RestResult.OK);
        return restResult;
    }

    @RequestMapping(value = "/sectionAdd",method = RequestMethod.POST)
    public RestResult  sectionAdd(ShippingTrackingNoSectionAddDto sectionAddDto){
        Assert.notNull(sectionAddDto.getShippingWayId(),"物流线路不能为空");
        Assert.notNull(sectionAddDto.getWmsId(),"仓库不能为空");
        Assert.notNull(sectionAddDto.getProductType(),"类型不能为空");
        Assert.notNull(sectionAddDto.getStartTrackingNo(),"开始运单号不能为空");
        Assert.notNull(sectionAddDto.getEndTrackingNo(),"结束运单号不能为空");

        UserDto userDto= ThreadLocalUtils.getUser();
        sectionAddDto.setCreatorId(userDto.getId());
        sectionAddDto.setCreator(userDto.getLastName());
        service.sectionAdd(sectionAddDto);
        RestResult restResult = new RestResult();
        restResult.setCode(RestResult.NOTICE);
        restResult.setDesc("新增成功");

        return restResult;
    }

    @RequestMapping(value = "/numberListAdd",method = RequestMethod.POST)
    public RestResult  numberListAdd(ShippingTrackingNumberListAddDto numberListAddDto){
        Assert.notNull(numberListAddDto.getShippingWayId(),"物流线路不能为空");
        Assert.notNull(numberListAddDto.getWmsId(),"仓库不能为空");
        Assert.notNull(numberListAddDto.getProductType(),"类型不能为空");
        Assert.notNull(numberListAddDto.getTrackNumbers(),"运单号列表不能为空");


        final List<String> numberList = Arrays.asList(numberListAddDto.getTrackNumbers().split("\n"));

        numberListAddDto.setTrackNumberList(numberList);

        UserDto userDto= ThreadLocalUtils.getUser();
        numberListAddDto.setCreatorId(userDto.getId());
        numberListAddDto.setCreator(userDto.getLastName());
        service.numberListAdd(numberListAddDto);
        RestResult restResult = new RestResult();
        restResult.setCode(RestResult.NOTICE);
        restResult.setDesc("新增成功");

        return restResult;
    }


    /**
     * 运单号失效
     * @param id
     * @return
     */
    @RequestMapping(method = RequestMethod.PUT,value = "/disable/{id}")
    public RestResult disable(
            @PathVariable Integer id
    ){
        Assert.notNull(id,"id不能为空");

        UserDto userDto= ThreadLocalUtils.getUser();
        service.disable(id,userDto.getLastName(),userDto.getId());
        RestResult restResult = new RestResult();
        restResult.setCode(RestResult.NOTICE);
        restResult.setDesc("失效运单号成功");
        return restResult;
    }



    @RequestMapping(method = RequestMethod.GET,value = "/numberCount")
    public RestResult numberCount(){
        final ShippingTrackingNoCountVo noCount = service.getNoCount();
        RestResult restResult = new RestResult();
        restResult.setCode(RestResult.OK);
        restResult.setItem(noCount);
        return restResult;
    }


    @RequestMapping(method = RequestMethod.GET,value = "/shippingGroupCount")
    public RestResult shippingGroupCount(
            @RequestParam(name = "shippingWayId",required = false) Integer shippingWayId
    ){

        final List<ShippingTrackingGroupCountListVo> groupCountList = service.getGroupCountList(shippingWayId);
        RestResult restResult = new RestResult();
        restResult.setCode(RestResult.OK);
        restResult.setItem(groupCountList);

        return restResult;
    }




}
