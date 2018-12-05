package com.stosz.purchase.fsm.purchaseReturnFsm;

import com.stosz.fsm.FsmProxyService;
import com.stosz.fsm.handle.IFsmHandler;
import com.stosz.fsm.model.EventModel;
import com.stosz.purchase.ext.enums.PurchaseReturnedEvent;
import com.stosz.purchase.ext.enums.PurchaseReturnedItemEvent;
import com.stosz.purchase.ext.enums.ReturnedTypeEnum;
import com.stosz.purchase.ext.model.Purchase;
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
public class FinanceAffirmRefundAfter extends IFsmHandler<PurchaseReturned> {

    @Resource
    private PurchaseReturnedItemService purchaseReturnedItemService;

    @Resource
    private WmsPurchaseService wmsPurchaseService;

    @Resource
    private PurchaseService purchaseService;

    @Resource
    private FsmProxyService<PurchaseReturned> fsmService;

    private final Logger logger = LoggerFactory.getLogger(FinanceAffirmRefundAfter.class);

    @Override
    public void execute(PurchaseReturned purchaseReturned, EventModel event) {

        if (ReturnedTypeEnum.INTHELIBRARY.name().equals(purchaseReturned.getType())) {
            // 查询采购单的供应商
            Purchase purchase = purchaseService.getById(Integer.parseInt(purchaseReturned.getPurchaseNo()));
            purchaseReturned.setSupplierCode(Integer.toString(purchase.getSupplierId()));
            purchaseReturned.setSupplierName(purchase.getSupplierName());
            purchaseReturnedItemService.confirmFinanceRefundItem(purchaseReturned.getId(), PurchaseReturnedItemEvent.financeAffirmRefund);
            // 推送采购退货单到wms
            List<PurchaseReturnedItem> returnedItems = purchaseReturnedItemService.queryListByReturnId(purchaseReturned.getId());
             wmsPurchaseService.subCreatePurchaseReturnPlan(purchaseReturned, returnedItems);
        } else
            purchaseReturnedItemService.confirmFinanceRefundItem(purchaseReturned.getId(), PurchaseReturnedItemEvent.financeAffirmRefundInWay);
    }
}
