package com.stosz.tms.web;

import com.stosz.plat.common.RestResult;
import com.stosz.plat.common.ThreadLocalUtils;
import com.stosz.plat.common.UserDto;
import com.stosz.plat.utils.StringUtil;
import com.stosz.plat.web.AbstractController;
import com.stosz.tms.mapper.ShippingStoreRelationMapper;
import com.stosz.tms.model.ShippingStoreRel;
import com.stosz.tms.model.ShippingWay;
import com.stosz.tms.service.ShippingWayStoreRelationService;
import com.stosz.tms.vo.ShippingStoreRelationListVo;
import com.stosz.tms.vo.ShippingWayStoreRelLableListVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/tms/shippingStoreRel")
public class ShippingWayStoreRelationController extends AbstractController {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Resource
    private ShippingWayStoreRelationService service;


    @RequestMapping(value = "query",method = RequestMethod.POST)
    public RestResult queryList(ShippingStoreRel shippingStoreRel){


        RestResult restResult = new RestResult();
        int count = service.count(shippingStoreRel);
        restResult.setTotal(count);
        if (count <= 0) {
            restResult.setCode(RestResult.OK);
            return restResult;
        }
//
//        final Integer start = shippingStoreRel.getStart();
//        final Integer limit = shippingStoreRel.getLimit();
//
//        //开始位置
//        shippingStoreRel.setStart((start == null || start <= 0) ? 0 : (start -1)* limit);
//        //需要显示的条数
//        shippingStoreRel.setLimit((limit == null ) ? 10 : limit);

        final List<ShippingStoreRelationListVo> storeRelationListVos = service.query(shippingStoreRel);
        restResult.setItem(storeRelationListVos);
        restResult.setCode(RestResult.OK);
        return restResult;

    }


    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public RestResult addShippinigStoreRel( @RequestBody ShippingStoreRel shippingStoreRel, HttpServletRequest request){
        Assert.notNull(shippingStoreRel.getShippingWayId(),"物流线路不能为空");
        Assert.notNull(shippingStoreRel.getWmsId(),"仓库不能为空");

        if (shippingStoreRel.getExpressSheetType() == 0) {// 不区分货物类型
            shippingStoreRel.setAllowedProductType(0);
            shippingStoreRel.setShippingGeneralName(shippingStoreRel.getAliasName());
            shippingStoreRel.setShippingGeneralCode(shippingStoreRel.getAliasCode());
        } else if (shippingStoreRel.getExpressSheetType() == 1) {
            if ((shippingStoreRel.getGeneral() != null && shippingStoreRel.getGeneral() == 1) && ( shippingStoreRel.getSpecia() != null && shippingStoreRel.getSpecia() == 1)) {
                shippingStoreRel.setAllowedProductType(0);
            } else if (shippingStoreRel.getGeneral() != null && shippingStoreRel.getGeneral() == 1) {
                shippingStoreRel.setAllowedProductType(1);
            } else if (  shippingStoreRel.getSpecia() != null && shippingStoreRel.getSpecia() == 1) {
                shippingStoreRel.setAllowedProductType(2);
            }
        }

        UserDto userDto= ThreadLocalUtils.getUser();
        shippingStoreRel.setCreatorId(userDto.getId());
        shippingStoreRel.setCreator(userDto.getLastName());


        shippingStoreRel.setEnable(0);

        service.add(shippingStoreRel);
        RestResult restResult = new RestResult();
        restResult.setCode(RestResult.NOTICE);
        restResult.setDesc("新增物流仓库关系成功");

        return restResult;
    }


    @RequestMapping(value = "/updateEnable",method = RequestMethod.PUT)
    public RestResult updateEnable(
            @ModelAttribute ShippingStoreRel shippingStoreRel
    ){
        Assert.notNull(shippingStoreRel.getId(),"id不能为空");
        Assert.notNull(shippingStoreRel.getEnable(),"状态不能为空");
        Assert.isTrue(shippingStoreRel.getEnable() == 1 || shippingStoreRel.getEnable() == 0,"非法状态值" );

        UserDto userDto=ThreadLocalUtils.getUser();
        shippingStoreRel.setModifierId(userDto.getId());
        shippingStoreRel.setModifier(userDto.getLastName());

        service.updateEnable(shippingStoreRel);
        RestResult restResult = new RestResult();
        restResult.setCode(RestResult.NOTICE);
        restResult.setDesc("更新状态成功");
        return restResult;
    }



    @RequestMapping(method = RequestMethod.PUT)
    public RestResult update(@RequestBody ShippingStoreRel shippingStoreRel) {
        Assert.notNull(shippingStoreRel.getId(),"ID不能为空");
        Assert.notNull(shippingStoreRel.getShippingWayId(),"物流线路不能为空");
        Assert.notNull(shippingStoreRel.getWmsId(),"仓库不能为空");

        if (shippingStoreRel.getExpressSheetType() == 0) {// 不区分货物类型
            shippingStoreRel.setAllowedProductType(0);
            shippingStoreRel.setShippingGeneralName(shippingStoreRel.getAliasName());
            shippingStoreRel.setShippingGeneralCode(shippingStoreRel.getAliasCode());
        } else if (shippingStoreRel.getExpressSheetType() == 1) {
            if ((shippingStoreRel.getGeneral() != null && shippingStoreRel.getGeneral() == 1) && ( shippingStoreRel.getSpecia() != null && shippingStoreRel.getSpecia() == 1)) {
                shippingStoreRel.setAllowedProductType(0);
            } else if (shippingStoreRel.getGeneral() != null && shippingStoreRel.getGeneral() == 1) {
                shippingStoreRel.setAllowedProductType(1);
            } else if (  shippingStoreRel.getSpecia() != null && shippingStoreRel.getSpecia() == 1) {
                shippingStoreRel.setAllowedProductType(2);
            }
        }
        UserDto userDto=ThreadLocalUtils.getUser();
        shippingStoreRel.setModifierId(userDto.getId());
        shippingStoreRel.setModifier(userDto.getLastName());

        service.update(shippingStoreRel);
        RestResult restResult = new RestResult();
        restResult.setCode(RestResult.NOTICE);
        restResult.setDesc("修改成功");
        return restResult;
    }


    @RequestMapping(value = "/getLableRelList",method = RequestMethod.GET)
    public RestResult getLableRelList(
           @RequestParam(name = "shippingWayStoreRelId",required = false) Integer shippingWayStoreRelId
    ){
        final List<ShippingWayStoreRelLableListVo> lableRelList = service.getLableRelList(shippingWayStoreRelId);


        RestResult restResult = new RestResult();
        restResult.setCode(RestResult.OK);
        restResult.setItem(lableRelList);
        return restResult;
    }

}
