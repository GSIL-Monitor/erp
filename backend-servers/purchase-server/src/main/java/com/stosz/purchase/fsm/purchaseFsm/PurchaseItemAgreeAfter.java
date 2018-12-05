package com.stosz.purchase.fsm.purchaseFsm;

import com.stosz.fsm.handle.IFsmHandler;
import com.stosz.fsm.model.EventModel;
import com.stosz.purchase.ext.enums.PurchaseEvent;
import com.stosz.purchase.ext.enums.PurchaseItemState;
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
public class PurchaseItemAgreeAfter extends IFsmHandler<PurchaseItem> {

    @Resource
    private PurchaseItemService purchaseItemService;
    @Resource
    private PurchaseService purchaseService;

    @Override
    public void execute(PurchaseItem purchaseItem, EventModel event) {
        // 判断该采购明细对应的所有采购单是否都为业务同意状态，如果是，那么更改采购单状态为业务同意
        Integer purchaseId = purchaseItem.getPurchaseId();
        Assert.notNull(purchaseId,"该采购明细【"+purchaseItem+"】存在问题，没有采购单id");
        Purchase purchase = purchaseService.getById(purchaseId);
        Assert.notNull(purchase,"将采购明细"+purchaseItem+"状态修改为业务同意时出错，没有获取到对应的采购单!");
        List<PurchaseItem> purchaseItemList = purchaseItemService.findByPurchaseId(purchaseId);
        Assert.notNull(purchaseItemList,"根据采购单id【"+purchaseId+"】获取对应的采购明细失败！");
        //如果所有明细状态都是待财务审批，那么将对应的采购单状态改为待财务审批
        boolean allAgree = purchaseItemList.stream().allMatch(e -> e.getStateEnum() == PurchaseItemState.waitFinanceApprove);
        if(allAgree){
            purchaseService.processEvent(purchase, PurchaseEvent.agreeByBusiness,"该采购单的所有明细都由业务审核通过，将采购单状态修改为审核通过",null);
        }
    }

}
