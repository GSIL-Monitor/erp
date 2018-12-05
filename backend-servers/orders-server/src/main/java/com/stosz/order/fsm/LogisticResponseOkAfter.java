package com.stosz.order.fsm;

import com.stosz.fsm.handle.IFsmHandler;
import com.stosz.fsm.model.EventModel;
import com.stosz.order.ext.enums.WarehouseTypeEnum;
import com.stosz.order.ext.model.OrdersLink;
import com.stosz.order.ext.model.Orders;
import com.stosz.order.ext.model.OrdersAddition;
import com.stosz.order.ext.model.OrdersItem;
import com.stosz.order.service.OrderService;
import com.stosz.order.service.OrdersLinkService;
import com.stosz.order.service.outsystem.wms.WmsOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @auther carter
 * create time    2017-11-15
 *
 * 这里处理注意 订单是否已经配货，如果配货，需要发wms，否则不做处理
 *
 */
@Component
public class LogisticResponseOkAfter extends IFsmHandler<Orders> {

    @Autowired
    private OrdersLinkService ordersLinkService;


    @Autowired
    private OrderService orderService;
    @Autowired
    private WmsOrderService wmsOrderService;

    @Override
    public void execute(Orders orders, EventModel event) {

//             <!--<Event Name="wmsOutCallback" Description="推送wms" DstState="deliver" After="pushWmsAfter"/>-->
//            <!--<Event Name="pushForward" Description="推送转寄仓" DstState="deliver" After="pushForwardAfter"/>


        WarehouseTypeEnum warehouseTypeEnum =orders.getWarehouseTypeEnum();

        switch (warehouseTypeEnum)
        {
            case normal:
                //推送wms
                OrdersLink ordersLink = ordersLinkService.getByOrderId(orders.getId());
                OrdersAddition ordersAddition = orderService.getOrderAddition(orders.getId());
                List<OrdersItem> ordersItemList = orderService.findOrdersItemByOrderId(orders.getId());
                wmsOrderService.subCreateSaleOrder(orders,ordersAddition,ordersItemList, ordersLink);
                break;
            case forward:
                //转寄仓 todo
                break;
        }


    }
}
