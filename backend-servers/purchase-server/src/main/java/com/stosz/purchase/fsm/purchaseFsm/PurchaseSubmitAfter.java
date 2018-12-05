package com.stosz.purchase.fsm.purchaseFsm;

import com.stosz.fsm.handle.IFsmHandler;
import com.stosz.fsm.model.EventModel;
import com.stosz.purchase.ext.enums.PurchaseItemEvent;
import com.stosz.purchase.ext.model.Purchase;
import com.stosz.purchase.ext.model.PurchaseItem;
import com.stosz.purchase.service.PurchaseItemService;
import com.stosz.purchase.service.PurchaseService;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author xiongchenyang
 * @version [1.0 , 2017/11/10]
 */
@Component
public class PurchaseSubmitAfter extends IFsmHandler<Purchase> {

    @Resource
    private PurchaseService purchaseService;
    @Resource
    private PurchaseItemService purchaseItemService;

    @Override
    public void execute(Purchase purchase, EventModel event) {
        //记录采购单提交的时间
        purchaseService.updateSubmitTime(purchase);
        //将该采购单的所有明细全部变为待业务审核状态。
        List<PurchaseItem> purchaseItemList = purchaseItemService.findByPurchaseId(purchase.getId());
        Assert.notNull(purchaseItemList,"提交采购单时获取采购明细出错！");
        for (PurchaseItem purchaseItem: purchaseItemList) {
            purchaseItemService.processEvent(purchaseItem, PurchaseItemEvent.submit,"提交采购单，同时将采购明细提交！",null);
        }
    }

}
