package com.stosz.purchase.fsm.purchaseFsm;

import com.stosz.fsm.handle.IFsmHandler;
import com.stosz.fsm.model.EventModel;
import com.stosz.purchase.ext.enums.PurchaseEvent;
import com.stosz.purchase.ext.model.Purchase;
import com.stosz.purchase.ext.model.PurchaseItem;
import com.stosz.purchase.service.PurchaseService;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import javax.annotation.Resource;

/**
 * @author xiongchenyang
 * @version [1.0 , 2017/11/10]
 */
@Component
public class PurchaseItemDisagreeAfter extends IFsmHandler<PurchaseItem> {

    @Resource
    private PurchaseService purchaseService;

    @Override
    public void execute(PurchaseItem purchaseItem, EventModel event) {

        // 将该采购明细对应的采购单变为业务不同意状态
        Integer purchaseId = purchaseItem.getPurchaseId();
        Purchase purchase = purchaseService.getById(purchaseId);
        Assert.notNull(purchase,"将采购明细【"+purchaseItem+"】修改为业务不同意状态时出错，没有找到对应的采购单！！！");
        purchaseService.processEvent(purchase, PurchaseEvent.disagreeByBusiness,"采购明细业务不同意，将采购单状态变为业务不同意",null);

    }

}
