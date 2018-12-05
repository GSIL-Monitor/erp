package com.stosz.order.fsm;

import com.stosz.fsm.handle.IFsmHandler;
import com.stosz.fsm.model.EventModel;
import com.stosz.order.ext.model.OrdersRmaBill;
import com.stosz.order.service.outsystem.wms.WmsOrderService;
import com.stosz.plat.common.MBox;
import com.stosz.plat.wms.bean.OrderCancelRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 
 * 客户取消
 * @author liusl
 */
@Component
public  class ChangeCancelAfter extends IFsmHandler<OrdersRmaBill> {

    @Autowired
    private WmsOrderService wmsOrderService;

    @Override
    public void execute(OrdersRmaBill ordersChange, EventModel event) {
        Integer changeTypeEnum = ordersChange.getChangeTypeEnum().ordinal();
        //退货
        if (changeTypeEnum == 0) {
            //TODO 接口待联调给出
            OrderCancelRequest orderCancelRequest = new  OrderCancelRequest();
            orderCancelRequest.setOrderId(String.valueOf(ordersChange.getMerchantOrderNo()));
            orderCancelRequest.setOrderType("XTRK");
            orderCancelRequest.setGoodsOwner(MBox.BGN_COMPANY_NO);
            logger.info("调用仓储WMS取消订单接口 请求参数 request-->", orderCancelRequest.toString());
            wmsOrderService.subCancelOrder(orderCancelRequest);
        } else {
            //换货 应付款撤销？

        }
    }
}
