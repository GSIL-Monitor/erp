package com.stosz.order.service.outsystem.wms;

import com.stosz.order.ext.dto.WmsResponse;
import com.stosz.order.ext.model.OrdersLink;
import com.stosz.order.ext.model.Orders;
import com.stosz.order.ext.model.OrdersAddition;
import com.stosz.order.ext.model.OrdersItem;
import com.stosz.plat.wms.bean.OrderCancelRequest;

import java.util.List;

/**
 * 
 * 新增销售订单
 * @author liusl
 */
public interface IWmsOrderService {
    String url="/admin/remote/IWmsOrderService";

    /**
     * 
     * 新增销售订单配货后通知WMS
     * @return
     * @date 2017年11月24日
     * @author liusl
     */
    void subCreateSaleOrder(Orders orders, OrdersAddition ordersAddition, List<OrdersItem> ordersItems, OrdersLink ordersLink);
    /**
     * 
     * 取消订单
     * @return 
     * @date 2017年11月24日
     * @author liusl
     */
    WmsResponse subCancelOrder(OrderCancelRequest orderCancelRequest);
}
