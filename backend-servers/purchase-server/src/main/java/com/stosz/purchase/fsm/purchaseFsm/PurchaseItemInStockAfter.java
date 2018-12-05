package com.stosz.purchase.fsm.purchaseFsm;

import com.stosz.fsm.handle.IFsmHandler;
import com.stosz.fsm.model.EventModel;
import com.stosz.purchase.ext.enums.PurchaseEvent;
import com.stosz.purchase.ext.enums.PurchaseItemState;
import com.stosz.purchase.ext.model.Purchase;
import com.stosz.purchase.ext.model.PurchaseIn;
import com.stosz.purchase.ext.model.PurchaseItem;
import com.stosz.purchase.service.PurchaseItemService;
import com.stosz.purchase.service.PurchaseService;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author xiongchenyang
 * @version [1.0 , 2017/11/10]
 */
@Component
public class PurchaseItemInStockAfter extends IFsmHandler<PurchaseItem> {

    @Resource
    private PurchaseService purchaseService;
    @Resource
    private PurchaseItemService purchaseItemService;

    @Override
    public void execute(PurchaseItem purchaseItem, EventModel event) {
        Integer purchaseId = purchaseItem.getPurchaseId();
        Assert.notNull(purchaseId,"该采购明细【"+purchaseItem+"】存在问题，没有采购单id");
        Purchase purchase = purchaseService.getById(purchaseId);
        Assert.notNull(purchase,"入库时获取采购明细【"+purchaseItem+"】的采购单失败！");
        List<PurchaseItem> purchaseItemList = purchaseItemService.findByPurchaseId(purchaseId);
        Assert.notNull(purchaseItemList,"根据采购单id【"+purchaseId+"】获取对应的采购明细失败！");
        //如果所有明细状态都是已完成，那么将对应的采购单状态改为已完成
        boolean allAgree = purchaseItemList.stream().allMatch(e -> e.getStateEnum() == PurchaseItemState.completed);
        if(allAgree){
            purchaseService.processEvent(purchase, PurchaseEvent.inStock,"该采购单的所有明细全部入库完成，将采购单状态修改为已完成",null);
        }

    }

}
