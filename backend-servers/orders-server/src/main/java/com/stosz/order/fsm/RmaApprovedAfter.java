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

/**
 * 退货审核通过
 *
 * @author liusl
 */
@Component
public class RmaApprovedAfter extends IFsmHandler<OrdersRmaBill> {

    @Autowired
    private AfterSaleService afterSaleService;

    @Override
    public void execute(OrdersRmaBill ordersRmaBill, EventModel event) {
        //如果是需要寄回的退货单  还需要推送转寄仓
        String recycleGoodsEnum = ordersRmaBill.getRecycleGoodsEnum().name();
        if (recycleGoodsEnum.equals(RecycleGoodsEnum.yes.name())) {
            afterSaleService.notifyStockTakeGoods(ordersRmaBill);
        }
        afterSaleService.updateSelective(ordersRmaBill);
    }

}
