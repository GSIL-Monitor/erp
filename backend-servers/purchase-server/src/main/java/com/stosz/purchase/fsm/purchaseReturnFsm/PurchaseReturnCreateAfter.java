package com.stosz.purchase.fsm.purchaseReturnFsm;

import com.stosz.fsm.handle.IFsmHandler;
import com.stosz.fsm.model.EventModel;
import com.stosz.purchase.ext.enums.ReturnedTypeEnum;
import com.stosz.purchase.ext.model.PurchaseReturned;
import com.stosz.purchase.ext.model.PurchaseReturnedItem;
import com.stosz.purchase.service.*;
import com.stosz.store.ext.enums.PurchaseHandleEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author:yangqinghui
 * @Description:
 * @Created Time:2017/12/21 20:44
 */
@Component
public class PurchaseReturnCreateAfter extends IFsmHandler<PurchaseReturned> {

    @Resource
    private PurchaseReturnedItemService purchaseReturnedItemService;

    @Resource
    private PurchaseItemService purchaseItemService;

    @Resource
    private PurchaseService purchaseService;

    @Resource
    private SupplierService supplierService;

    @Resource
    private PurchaseReturnedService purchaseReturnedService;

   /* @Resource
    private IFinanceService financeService;*/

    private final Logger logger = LoggerFactory.getLogger(PurchaseReturnCreateAfter.class);

    @Override
    public void execute(PurchaseReturned purchaseReturned, EventModel event) {

        List<PurchaseReturnedItem> purchaseReturnedItems = purchaseReturnedItemService.queryListByReturnId(purchaseReturned.getId());
        if (ReturnedTypeEnum.INTRANSIT.ordinal() == purchaseReturned.getType()) {// 在途退货
            // 修改采购采购明细在途采退数 减少库存的在途数 如果支付金额与成本金额相等推到财务系统
            for (PurchaseReturnedItem purchaseReturnedItem : purchaseReturnedItems)
                purchaseItemService.updateCancleQty(purchaseReturnedItem.getPurchaseItemId(), purchaseReturnedItem.getReturnedQty());

            purchaseReturnedService.changeReturnLockStock(purchaseReturned, purchaseReturnedItems, PurchaseHandleEnum.intransit_minus.name());

        }
    }
}
