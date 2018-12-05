package com.stosz.order.fsm;

import com.stosz.fsm.handle.IFsmHandler;
import com.stosz.fsm.model.EventModel;
import com.stosz.order.ext.model.Orders;
import com.stosz.order.service.OrderService;
import com.stosz.tms.dto.OrderStateInfo;
import com.stosz.tms.service.ITransportFacadeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @auther carter
 * create time    2017-11-15
 *
 * 转寄仓发货导入成功
 *
 */
@Component
public class ForwardImportOkAfter extends IFsmHandler<Orders> {

    @Resource
    private ITransportFacadeService transportFacadeService;

    @Autowired
    private OrderService orderService;


    @Override
    public void execute(Orders orders, EventModel event) {

        //通知物流开始抓取轨迹
        OrderStateInfo orderStateInfo = new OrderStateInfo();
        orderStateInfo.setOrderId(orders.getId());
        orderStateInfo.setOrderStateEnum(com.stosz.tms.enums.OrderStateEnum.deliver);
        orderStateInfo.setShippingWayCode(orders.getLogisticsName());
        orderStateInfo.setShippingWayName(orders.getLogisticsName());
        orderStateInfo.setTrackNo(orders.getTrackingNo());
        orderStateInfo.setWeight(orders.getWeight());
        transportFacadeService.notifyLogisticsIsFetch(orderStateInfo);

        //发送状态通知到建站系统
        orderService.publishOrderStateMessage(orders, com.stosz.order.ext.enums.OrderStateEnum.fromName(event.getDstState()));




    }
}
