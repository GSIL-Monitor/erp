package com.stosz.purchase.fsm.purchaseReturnFsm;

import com.stosz.fsm.FsmProxyService;
import com.stosz.fsm.handle.IFsmHandler;
import com.stosz.fsm.model.EventModel;
import com.stosz.purchase.ext.enums.PurchaseReturnItemState;
import com.stosz.purchase.ext.enums.PurchaseReturnedEvent;
import com.stosz.purchase.ext.model.PurchaseReturned;
import com.stosz.purchase.ext.model.PurchaseReturnedItem;
import com.stosz.purchase.service.PurchaseReturnedItemService;
import com.stosz.purchase.service.PurchaseReturnedService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author xiongchenyang
 * @version [1.0 , 2017/11/29]
 */
@Component
public class PurchaseReturnedDeliveryStockAfter extends IFsmHandler<PurchaseReturnedItem> {

    @Resource
    private PurchaseReturnedService purchaseReturnedService;
    @Resource
    private PurchaseReturnedItemService purchaseReturnedItemService;

    @Resource
    private FsmProxyService<PurchaseReturned> fsmService;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void execute(PurchaseReturnedItem purchaseReturnedItem, EventModel event) {
        // 查询是否所有兄弟明细都出库了，如果都出库了那么调用退货单的完成时间
        Integer returnedId = purchaseReturnedItem.getReturnedId();
        PurchaseReturned purchaseReturned = purchaseReturnedService.getById(returnedId);
        Assert.notNull(purchaseReturned,"采购退货单id【"+returnedId+"】找不到对应的采购的退货单");
        List<PurchaseReturnedItem> purchaseReturnedItemList = purchaseReturnedItemService.queryListByReturnId(returnedId);
        Assert.notNull(purchaseReturnedItemList,"采购退货单【"+returnedId+"】找不到对应的明细！");
        boolean allDelivery = purchaseReturnedItemList.stream().allMatch(e -> e.getState().equals(PurchaseReturnItemState.completed.name()));
        if(allDelivery){
            fsmService.processEvent(purchaseReturned, PurchaseReturnedEvent.deliveryStock,"所有采购退货明细完成！", LocalDateTime.now(),null);
        }
    }
}
