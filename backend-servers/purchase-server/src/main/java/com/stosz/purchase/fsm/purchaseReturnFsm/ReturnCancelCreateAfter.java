package com.stosz.purchase.fsm.purchaseReturnFsm;

import com.stosz.fsm.handle.IFsmHandler;
import com.stosz.fsm.model.EventModel;
import com.stosz.plat.wms.enums.WMSOrderType;
import com.stosz.purchase.ext.enums.PurchaseReturnState;
import com.stosz.purchase.ext.enums.ReturnedTypeEnum;
import com.stosz.purchase.ext.model.PurchaseReturned;
import com.stosz.purchase.ext.model.PurchaseReturnedItem;
import com.stosz.purchase.service.PurchaseItemService;
import com.stosz.purchase.service.PurchaseReturnedItemService;
import com.stosz.purchase.service.PurchaseReturnedService;
import com.stosz.purchase.service.WmsPurchaseService;
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
public class ReturnCancelCreateAfter extends IFsmHandler<PurchaseReturned> {

    @Resource
    private PurchaseReturnedItemService purchaseReturnedItemService;

    @Resource
    private PurchaseItemService purchaseItemService;

    @Resource
    private WmsPurchaseService wmsPurchaseService;

    @Resource
    private PurchaseReturnedService purchaseReturnedService;

    private final Logger logger = LoggerFactory.getLogger(ReturnCancelCreateAfter.class);

    @Override
    public void execute(PurchaseReturned purchaseReturned, EventModel event) {

        List<PurchaseReturnedItem> purchaseReturnedItems = purchaseReturnedItemService.queryListByReturnId(purchaseReturned.getId());
        // 如果是取消在库采退
        if (ReturnedTypeEnum.INTHELIBRARY.name().equals(purchaseReturned.getType())) {
            //更改库存
            purchaseReturnedService.changeReturnLockStock(purchaseReturned, purchaseReturnedItems, PurchaseHandleEnum.return_fail.name());
        } else {// 取消在途采退
            // 采购明细的采退数量+取消采退数
            purchaseItemService.modifyPurhcaseCancleQty(purchaseReturnedItems);
            //更新库存
            purchaseReturnedService.changeReturnLockStock(purchaseReturned, purchaseReturnedItems, PurchaseHandleEnum.intransit_add.name());
        }
        if (!PurchaseReturnState.stayRefund.equals(purchaseReturned.getState()))
            wmsPurchaseService.subCancelOrder(purchaseReturned.getReturnedNo(), WMSOrderType.purchaseReturnOrder);
    }
}
