package com.stosz.order.web;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.stosz.order.ext.dto.DeptAssignQtyDto;
import com.stosz.order.ext.dto.DeptOrderQtyTimeDto;
import com.stosz.order.service.OrderService;
import com.stosz.order.service.ZoneWarehousePriorityService;
import com.stosz.plat.common.RestResult;
import com.stosz.product.ext.service.IProductSkuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @auther carter
 * create time    2017-12-26
 */
@Controller("TestController")
@RequestMapping("/test")
public class TestController {


    @Autowired
    private OrderService orderService;
    @Autowired
    private ZoneWarehousePriorityService zoneWarehousePriorityService;

    @Autowired
    private IProductSkuService productSkuService;

    @RequestMapping("/purchaseSet.do")
    @ResponseBody
    public RestResult purchaseSet(String sku,Integer wmsId, Integer purchaseQty)
    {

        RestResult restResult = new RestResult();
        TempResult tempResult = new TempResult();


        List<DeptAssignQtyDto> deptAssignQtyDtoList = Lists.newLinkedList();
        if (null == wmsId || wmsId<1)   {
             restResult.setItem(tempResult);
             restResult.setDesc("wmsId不能为空");
            return restResult;
        }
        if (Strings.isNullOrEmpty(sku)) {
            restResult.setItem(tempResult);
            restResult.setDesc("sku不能为空");
            return restResult;
        }
        if (null == purchaseQty || purchaseQty<1)
        {
            restResult.setItem(tempResult);
            restResult.setDesc("purchaseQty不能为空");
            return restResult;
        }
        //1,得到skp，仓库对应的区域集合；
        String spu = productSkuService.getSpuBySku(sku);
        if (Strings.isNullOrEmpty(spu)) {
            restResult.setItem(tempResult);
            restResult.setDesc("sku拿不到spu");
            return restResult;
        }

        tempResult.setSpu(spu);

//        Set<Integer> zoneIdSet =  zoneWarehousePriorityService.findZoneSetByWarehouseIdAndSpu(wmsId,spu);
//        //2,通过区域，sku ，订单状态为 已审核，待采购  找到对应的订单需求总数
////        deptAssignQtyDtoList = orderService.findSkuNeedDetail(zoneIdSet,sku, Sets.newHashSet(OrderStateEnum.auditPass,OrderStateEnum.waitPurchase), deptSet);
//        tempResult.setNeed(deptAssignQtyDtoList);
//        tempResult.setResult(tempResult.getNeed());
//        //3,如果都满足，直接全部分配；
//        Integer needTotal =  deptAssignQtyDtoList.stream().mapToInt(DeptAssignQtyDto::getQty).sum();
//
//
//        //否则，按照订单项的时间来，时间在前的先分配；
//
////        List<DeptOrderQtyTimeDto> deptOrderQtyTimeDtoList = orderService.findOrderSkuQtyDetail(zoneIdSet,sku, Sets.newHashSet(OrderStateEnum.auditPass,OrderStateEnum.waitPurchase), deptSet);
//        tempResult.setNeedDetail(deptOrderQtyTimeDtoList);
//
//        if(purchaseQty>=needTotal)
//        {
//
//            restResult.setItem(tempResult);
//            restResult.setDesc("分配ok");
//            return restResult;
//        }
//
//
//
//        tempResult.setNeedDetail(deptOrderQtyTimeDtoList);
//        AtomicInteger leftNum = new AtomicInteger(purchaseQty) ;
//        for (DeptOrderQtyTimeDto  deptOrderQtyTimeDto: deptOrderQtyTimeDtoList)
//        {
//
//            if(leftNum.addAndGet(-1*deptOrderQtyTimeDto.getQty())>=0)
//            {
//                deptOrderQtyTimeDto.setAssign(true);
//            }else
//            {
//                deptOrderQtyTimeDto.setAssign(true);
//                deptOrderQtyTimeDto.setAssignQty(deptOrderQtyTimeDto.getQty()+leftNum.get());
//                break;
//            }
//
//        }
//
//        Map<Integer, List<DeptOrderQtyTimeDto>> collect = deptOrderQtyTimeDtoList.stream()
//                .filter(item -> item.isAssign())
//                .collect(Collectors.groupingBy(DeptOrderQtyTimeDto::getDept));
//
//
//        deptAssignQtyDtoList = collect.entrySet().stream().map(entry->{
//            DeptAssignQtyDto deptAssignQtyDtoTemp = new DeptAssignQtyDto();
//            deptAssignQtyDtoTemp.setDeptId(entry.getKey());
//            deptAssignQtyDtoTemp.setQty(entry.getValue().stream().mapToInt(DeptOrderQtyTimeDto::getAssignQty).sum());
//
//            return deptAssignQtyDtoTemp;
//
//        }).collect(Collectors.toList());


        tempResult.setResult(deptAssignQtyDtoList);
        restResult.setItem(tempResult);
        restResult.setDesc("分配ok");
        return restResult;

    }


    class TempResult{

       private List<DeptAssignQtyDto> need = Lists.newLinkedList();
       private List<DeptOrderQtyTimeDto> needDetail = Lists.newLinkedList();
       private List<DeptAssignQtyDto>   result = Lists.newLinkedList();
       private String spu="";

        public List<DeptAssignQtyDto> getNeed() {
            return need;
        }

        public void setNeed(List<DeptAssignQtyDto> need) {
            this.need = need;
        }

        public List<DeptOrderQtyTimeDto> getNeedDetail() {
            return needDetail;
        }

        public void setNeedDetail(List<DeptOrderQtyTimeDto> needDetail) {
            this.needDetail = needDetail;
        }

        public List<DeptAssignQtyDto> getResult() {
            return result;
        }

        public void setResult(List<DeptAssignQtyDto> result) {
            this.result = result;
        }

        public String getSpu() {
            return spu;
        }

        public void setSpu(String spu) {
            this.spu = spu;
        }
    }



}
