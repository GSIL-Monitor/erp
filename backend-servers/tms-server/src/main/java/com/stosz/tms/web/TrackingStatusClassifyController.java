package com.stosz.tms.web;

import com.stosz.plat.common.RestResult;
import com.stosz.plat.common.ThreadLocalUtils;
import com.stosz.plat.common.UserDto;
import com.stosz.plat.web.AbstractController;
import com.stosz.tms.dto.TrackingStatusClassifyAddDto;
import com.stosz.tms.model.ShippingStoreRel;
import com.stosz.tms.model.ShippingZoneStoreRel;
import com.stosz.tms.model.TrackStatusClassify;
import com.stosz.tms.service.TrackingStatusClassifyService;
import com.stosz.tms.vo.ShippingZoneStoreRelationListVo;
import com.stosz.tms.vo.TrackStatusClassifyListVo;
import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/tms/tackingStatusClassify")
public class TrackingStatusClassifyController extends AbstractController {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Resource
    private TrackingStatusClassifyService service;

    @RequestMapping(value = "query",method = RequestMethod.POST)
    public RestResult queryList(TrackStatusClassify trackStatusClassify){

        RestResult restResult = new RestResult();
        int count = service.count(trackStatusClassify);
        restResult.setTotal(count);
        if (count <= 0) {
            restResult.setCode(RestResult.OK);
            return restResult;
        }

//        final Integer start = trackStatusClassify.getStart();
//        final Integer limit = trackStatusClassify.getLimit();
//
//        //开始位置
//        trackStatusClassify.setStart((start == null || start <= 0) ? 0 : (start -1)* limit);
//        //需要显示的条数
//        trackStatusClassify.setLimit((limit == null ) ? 10 : limit);

        final List<TrackStatusClassifyListVo> trackStatusClassifyListVos = service.queryList(trackStatusClassify);
        restResult.setItem(trackStatusClassifyListVos);
        restResult.setCode(RestResult.OK);
        return restResult;
    }

    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public RestResult add(TrackingStatusClassifyAddDto statusClassifyAddDto ){
        Assert.notNull(statusClassifyAddDto.getTrackApiId(),"轨迹接口不能为空");
        Assert.notNull(statusClassifyAddDto.getStatusClassifys(),"状态数据不能为空");

        UserDto userDto= ThreadLocalUtils.getUser();
        statusClassifyAddDto.setCreatorId(userDto.getId());
        statusClassifyAddDto.setCreator(userDto.getLastName());
        service.add(statusClassifyAddDto);
        RestResult restResult = new RestResult();
        restResult.setItem(statusClassifyAddDto);
        restResult.setCode(RestResult.NOTICE);
        return restResult;
    }


    @RequestMapping(value = "/updateEnable",method = RequestMethod.PUT)
    public RestResult updateEnable(
            @ModelAttribute TrackStatusClassify statusClassify
    ){
        Assert.notNull(statusClassify.getId(),"id不能为空");
        Assert.notNull(statusClassify.getEnable(),"状态不能为空");
        Assert.isTrue(statusClassify.getEnable() == 1 || statusClassify.getEnable() == 0,"非法状态值" );

        UserDto userDto=ThreadLocalUtils.getUser();
        statusClassify.setModifierId(userDto.getId());
        statusClassify.setModifier(userDto.getLastName());

        service.updateEnable(statusClassify);
        RestResult restResult = new RestResult();
        restResult.setCode(RestResult.NOTICE);
        restResult.setDesc("更新状态成功");
        return restResult;
    }



    @RequestMapping(method = RequestMethod.PUT)
    public RestResult update(TrackStatusClassify statusClassify) {
        Assert.notNull(statusClassify.getId(),"ID不能为空");
        Assert.notNull(statusClassify.getTrackApiId(),"轨迹接口不能为空");
        Assert.notNull(statusClassify.getClassifyCode(),"归类状态不能为空");
        Assert.notNull(statusClassify.getShippingStatus(),"物流公司状态不能为空");

        UserDto userDto=ThreadLocalUtils.getUser();
        statusClassify.setModifierId(userDto.getId());
        statusClassify.setModifier(userDto.getLastName());
        service.update(statusClassify);

        RestResult restResult = new RestResult();
        restResult.setCode(RestResult.NOTICE);
        restResult.setDesc("修改成功");
        return restResult;
    }


}
