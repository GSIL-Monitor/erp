package com.stosz.tms.web;

import com.google.common.collect.Lists;
import com.stosz.plat.common.RestResult;
import com.stosz.plat.common.ResultBean;
import com.stosz.plat.utils.ExcelUtil;
import com.stosz.plat.web.AbstractController;
import com.stosz.tms.dto.ShippingParcelMatchingDto;
import com.stosz.tms.model.ShippingParcel;
import com.stosz.tms.model.ShippingZoneStoreRel;
import com.stosz.tms.service.ShippingParcelService;
import com.stosz.tms.vo.ShippingParcelDetailVo;
import com.stosz.tms.vo.ShippingParcelListVo;
import com.stosz.tms.vo.ShippingParcelMatchingExportVo;
import com.stosz.tms.vo.ShippingParcelTaskListVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;


@RestController
@RequestMapping("/tms/shippingParcel")
public class ShippingParcelController extends AbstractController {

    @Resource
    private ShippingParcelService service;

    @RequestMapping(value = "query",method = RequestMethod.POST)
    public RestResult queryList(ShippingParcel shippingParcel){

        if (!StringUtils.isEmpty(shippingParcel.getShippingWayIdStr())){
            final List<String> shippingWayIdStrList = Arrays.asList(shippingParcel.getShippingWayIdStr().split(","));
            final List<Integer> shippingWayIdList  = Lists.newArrayList();
            shippingWayIdStrList.forEach(e -> {
                shippingWayIdList.add(Integer.valueOf(e));
            });
            shippingParcel.setShippingWayIdList(shippingWayIdList);
        }


        RestResult restResult = new RestResult();
        int count = service.count(shippingParcel);
        restResult.setTotal(count);
        if (count <= 0) {
            restResult.setCode(RestResult.OK);
            return restResult;
        }
//
//        final Integer start = shippingParcel.getStart();
//        final Integer limit = shippingParcel.getLimit();
//
//        //开始位置
//        shippingParcel.setStart((start == null || start <= 0) ? 0 : (start -1)* limit);
//        //需要显示的条数
//        shippingParcel.setLimit((limit == null ) ? 10 : limit);

        final List<ShippingParcelListVo> parcelListVos = service.queryList(shippingParcel);
        restResult.setItem(parcelListVos);
        restResult.setCode(RestResult.OK);
        return restResult;
    }

    @RequestMapping(value = "/taskList",method = RequestMethod.GET)
    public RestResult selectTaskList(
            @RequestParam(name = "id",required = true) Integer id
    ){
        final List<ShippingParcelTaskListVo> taskListVos = service.selectTaskList(id);
        RestResult restResult = new RestResult();
        restResult.setItem(taskListVos);
        restResult.setCode(RestResult.OK);
        return restResult;
    }

    @RequestMapping(value = "/detail",method = RequestMethod.GET)
    public RestResult selectDetail(
            @RequestParam(name = "id",required = true) Integer id
    ){
        final ShippingParcelDetailVo parcelDetailVo = service.selectDetail(id);
        RestResult restResult = new RestResult();
        restResult.setItem(parcelDetailVo);
        restResult.setCode(RestResult.OK);
        return restResult;
    }


    @RequestMapping(value = "/matchingExport",method = RequestMethod.POST)
    public RestResult matchingExport(ShippingParcelMatchingDto matchingDto, HttpServletResponse response) throws Exception {
        Assert.notNull(matchingDto.getDataType(),"数据类型不能为空");
        Assert.notNull(matchingDto.getDataList(),"匹配数据不能为空");
        Assert.isTrue(matchingDto.getDataType() == 1 || matchingDto.getDataType() == 2 ,"数据类型错误");

        final List<ShippingParcelMatchingExportVo> matchingExportVos = service.matchingExport(matchingDto);

        String[] headers = {"运号单","订单号","产品名","数量","sku","仓库","总数量","订单状态","物流线路","物流状态","物流归类状态","是否结款","下单时间","签收时间","发货时间"};
        String[] fileds = {"trackNo","orderNo","productTitle","orderDetailQty","sku","wmsName",
                "goodsQty","state","shippingName","trackStatus","classifyStatus","isSettlemented","createAt","receiveTime"};

        ExcelUtil.exportExcelAndDownload(response,"订单列表","订单信息",headers,fileds,matchingExportVos);
        return null;
    }


    @RequestMapping(value = "/onceAgainAssignShipping/{parcelId}",method = RequestMethod.PUT)
    public RestResult onceAgainAssignShipping(
            @PathVariable Integer parcelId
    ){
        final ResultBean<String> resultBean = service.onceAgainAssignShipping(parcelId);

        RestResult restResult = new RestResult();

        if (resultBean.getCode() == ResultBean.OK){
            restResult.setCode(RestResult.NOTICE);
            restResult.setDesc("");
        }else {
            restResult.setCode(RestResult.FAIL);
            restResult.setDesc(resultBean.getDesc());
        }
        return restResult;
    }

    @RequestMapping(value = "/updateMemo",method = RequestMethod.PUT)
    public RestResult update(
            @ModelAttribute ShippingParcel shippingParcel) {
        Assert.notNull(shippingParcel.getId(),"ID不能为空");
        Assert.notNull(shippingParcel.getMemo(),"物流备注不能为空");

       Assert.isTrue( service.updateSelective(shippingParcel) > 0,"更新包裹物流备注失败" );
        RestResult restResult = new RestResult();
        restResult.setCode(RestResult.NOTICE);
        restResult.setDesc("修改成功");
        return restResult;
    }

    @RequestMapping(value = "/batchAssignShipping",method = RequestMethod.POST)
    public RestResult batchAssignShipping(
        @RequestBody List<Integer> idList
    ) throws InterruptedException {

        Integer succeedCount = 0 ;
        Integer failCount = 0;


        for (Integer id:idList
             ) {
            final ResultBean<String> resultBean = service.onceAgainAssignShipping(id);

            RestResult restResult = new RestResult();

            if (resultBean.getCode() == ResultBean.OK)
                succeedCount += 1;
            else
                failCount += 1;
        }


        RestResult restResult = new RestResult();
        restResult.setCode(RestResult.NOTICE);
        restResult.setDesc("批量重新指派完成,成功数量:"+succeedCount+",失败数量:"+failCount);
        return restResult;

    }

}

