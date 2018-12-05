package com.stosz.order.fsm;

import com.stosz.fsm.handle.IFsmHandler;
import com.stosz.fsm.model.EventModel;
import com.stosz.order.ext.model.OrdersRmaBill;

/**
 * 
 * 退换货申请不通过
 * @author liusl
 */
//@Component
public  class ChangeRejectAfter extends IFsmHandler<OrdersRmaBill> {

    @Override
    public void execute(OrdersRmaBill ordersRmaBill, EventModel event) {
      //TODO 
    }
}
