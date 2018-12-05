package com.stosz.order.fsm;

import com.stosz.fsm.handle.IFsmHandler;
import com.stosz.fsm.model.EventModel;
import com.stosz.order.ext.enums.ChangeTypeEnum;
import com.stosz.order.ext.enums.RecycleGoodsEnum;
import com.stosz.order.ext.model.OrdersRmaBill;
import com.stosz.order.service.AfterSaleService;
import com.stosz.order.service.OrdersRmaBillItemsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * 寄回运单号更新
 *
 * @author tangtao
 */
@Component
public class UpdateTrackingNoAfter extends IFsmHandler<OrdersRmaBill> {

    @Autowired
    private AfterSaleService afterSaleService;
    @Autowired
    private OrdersRmaBillItemsService ordersRmaBillItemsService;

    @Override
    public void execute(OrdersRmaBill ordersRmaBill, EventModel event) {
        //如果是需要寄回的退货单  还需要推送转寄仓
       /* OrdersRmaBill newBill = new OrdersRmaBill();
        newBill.setId(ordersRmaBill.getId());
        newBill.setTrackingNo(ordersRmaBill.getTrackingNo());
        newBill.setUpdateAt(LocalDateTime.now());
        afterSaleService.updateSelective(newBill);*/
        afterSaleService.updateSelective(ordersRmaBill);
        String recycleGoodsEnum = ordersRmaBill.getRecycleGoodsEnum().name();
        if (recycleGoodsEnum.equals(RecycleGoodsEnum.yes.name())) {
            afterSaleService.notifyStockTakeGoods(ordersRmaBill);
        }
    }

}
