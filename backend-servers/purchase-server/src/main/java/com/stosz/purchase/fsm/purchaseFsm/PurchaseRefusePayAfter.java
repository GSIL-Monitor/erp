package com.stosz.purchase.fsm.purchaseFsm;

import com.stosz.fsm.handle.IFsmHandler;
import com.stosz.fsm.model.EventModel;
import com.stosz.purchase.ext.enums.PurchaseItemEvent;
import com.stosz.purchase.ext.model.Purchase;
import com.stosz.purchase.ext.model.PurchaseItem;
import com.stosz.purchase.service.PurchaseItemService;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author xiongchenyang
 * @version [1.0 , 2017/11/10]
 */
@Component
public class PurchaseRefusePayAfter extends IFsmHandler<Purchase> {

    @Resource
    private PurchaseItemService purchaseItemService;

    @Override
    public void execute(Purchase purchase, EventModel event) {

        //TODO 将该采购单对应的所有采购明细变为拒绝付款，触发拒绝付款事件。
        Integer purchaseId = purchase.getId();
        List<PurchaseItem> purchaseItemList = purchaseItemService.findByPurchaseId(purchaseId);
        Assert.notNull(purchaseItemList,"采购单【"+purchase+"】拒绝付款时出错，找不到该采购单对应的明细！");
        for (PurchaseItem purchaseItem: purchaseItemList) {
            purchaseItemService.processEvent(purchaseItem, PurchaseItemEvent.refusePay,"采购单拒绝付款，将所有明细拒绝付款",null);
        }


    }

}
