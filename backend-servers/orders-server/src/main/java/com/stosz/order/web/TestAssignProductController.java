package com.stosz.order.web;

import com.google.common.base.Strings;
import com.google.common.collect.Maps;
import com.stosz.admin.ext.model.Department;
import com.stosz.admin.ext.service.IDepartmentService;
import com.stosz.fsm.FsmProxyService;
import com.stosz.fsm.model.IFsmHistory;
import com.stosz.order.ext.enums.OrderEventEnum;
import com.stosz.order.ext.enums.OrderStateEnum;
import com.stosz.order.ext.model.Orders;
import com.stosz.order.ext.mq.OccupyStockModel;
import com.stosz.order.service.OrderService;
import com.stosz.plat.common.RestResult;
import com.stosz.plat.rabbitmq.RabbitMQPublisher;
import com.stosz.plat.web.AbstractController;
import com.stosz.product.ext.service.IProductSkuService;
import com.stosz.store.ext.service.IStockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @auther carter
 * create time    2017-12-26
 * 模拟配货测试后台程序
 */
@Controller("TestAssignProductController")
@RequestMapping("/test")
public class TestAssignProductController extends AbstractController {


    @Autowired
    private FsmProxyService<Orders> ordersFsmProxyService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private RabbitMQPublisher rabbitMQPublisher;

    @Autowired
    private IProductSkuService productSkuService;

    @Autowired
    private IDepartmentService departmentService;

    @Autowired
    private IStockService stockService;


//    @Autowired
//
//    @RequestMapping("/xxx.do")
//    @ResponseBody
//    public RestResult xxx(Integer orderId) {
//
////        productSkuService.
//        return  null;
//
//    }

        @RequestMapping("/orderAuditPass.do")
    @ResponseBody
    public RestResult orderAuditPass(Integer orderId) {

        RestResult restResult = new RestResult();

        if (null == orderId || orderId < 1) {

            restResult.setDesc("订单为正整数");
            return restResult;
        }

        Orders order = orderService.findOrderById(orderId);
        if (null == order || null == order.getId()) {
            restResult.setDesc("订单不存在");
            return restResult;
        }

        List<IFsmHistory> iFsmHistories = ordersFsmProxyService.queryFsmHistory(order);

        Map<String,Object> data = Maps.newHashMap();
        data.put("fsmLog",iFsmHistories);
        restResult.setItem(data);

        if (!Arrays.asList(OrderStateEnum.waitAudit, OrderStateEnum.waitContact).contains(order.getOrderStatusEnum())) {
            restResult.setDesc("只有待审核,待联系的订单才能进入已审核状态");
            return restResult;
        }

        ordersFsmProxyService.processEvent(order, OrderEventEnum.checkValid,"审核通过");

//        boolean occupyStockByOrderSuccess = occupyStockHandler.occupyStockByOrder(order);

        restResult.setDesc("审核通过，触发占用库存");




        return restResult;

    }


    @RequestMapping("/storeNumIncrease.do")
    @ResponseBody
    public RestResult storeNumIncrease(String sku, Integer warehouseId, Integer dept,Integer canAssignQty) {

        RestResult restResult = new RestResult();

        String spuBySku = productSkuService.getSpuBySku(sku);

        if(Strings.isNullOrEmpty(spuBySku))
        {
            restResult.setDesc("sku不存在");
            return restResult;
        }

        Department department = departmentService.get(dept);
        if(null == department || null == department.getId())
        {
            restResult.setDesc("dept不存在");
            return restResult;
        }

        if(null == canAssignQty ||  canAssignQty < 1)
        {
            restResult.setDesc("canAssignQty为正整数");
            return restResult;
        }

//        if (null == warehouseId || warehouseId <1)
//        {
//            restResult.setDesc("warehouseId为正整数");
//            return restResult;
//        }



        OccupyStockModel occupyStockModel = new OccupyStockModel();
        occupyStockModel.setDept(dept);
        occupyStockModel.setSku(sku);
        occupyStockModel.setWarehouseId(warehouseId);
        occupyStockModel.setCanAssignQty(canAssignQty);
        //2,产生一个配货消息到MQ
        rabbitMQPublisher.saveMessage(occupyStockModel.MESSAGE_TYPE, occupyStockModel);

        restResult.setDesc("库存占用信息发送ok");
        return restResult;

    }


}
