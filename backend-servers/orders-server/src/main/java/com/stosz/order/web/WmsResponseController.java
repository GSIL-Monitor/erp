package com.stosz.order.web;

import com.stosz.order.service.OrderService;
import com.stosz.order.service.OrdersWebInterfaceRecordService;
import com.stosz.plat.common.RestCallBackResult;
import com.stosz.plat.enums.ResponseResultEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

/**
 * @auther liusl
 * create time    2017-12-12
 * 处理wms的异步响应信息
 */
@RestController
@RequestMapping("/orders/wms/api/")
public class WmsResponseController{

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrdersWebInterfaceRecordService ordersWebInterfaceRecordService;


    @RequestMapping(value = "order_out")
    public RestCallBackResult orderWmsOut(@RequestParam(value = "result_data") String result_data,@RequestParam(value = "key") String key){

        RestCallBackResult restCallBackResult = orderService.orderWmsOut(result_data, key);
        //处理响应
        ordersWebInterfaceRecordService.saveResponse(restCallBackResult.getRecordDetailId(),restCallBackResult.getBody(),
                Optional.of(restCallBackResult.getIsSuccess()).map(item->RestCallBackResult.OK.equalsIgnoreCase(item)? ResponseResultEnum.success: ResponseResultEnum.fail).get());

        return restCallBackResult;

    }
}
