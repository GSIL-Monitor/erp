package com.stosz.order.fsm;

import com.stosz.fsm.handle.IFsmHandler;
import com.stosz.fsm.model.EventModel;
import com.stosz.order.ext.dto.WmsResponse;
import com.stosz.order.ext.model.OrdersRmaBill;
import com.stosz.order.service.AfterSaleService;
import com.stosz.order.service.OrdersRmaBillItemsService;
import com.stosz.store.ext.service.ITransitStockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.time.LocalDateTime;

/**
 * 通知 转寄仓取消
 */
@Component
public class NotifyStockCancelBefore extends IFsmHandler<OrdersRmaBill> {

    @Autowired
    private ITransitStockService transitStockService;
    @Autowired
    private OrdersRmaBillItemsService ordersRmaBillItemsService;

    @Override
    public void execute(OrdersRmaBill ordersRmaBill, EventModel event) {
        WmsResponse response = transitStockService.notifyStockCancel(ordersRmaBill.getId());
        Assert.isTrue(response.getCode().equals(WmsResponse.OK), response.getDesc());
    }

}
