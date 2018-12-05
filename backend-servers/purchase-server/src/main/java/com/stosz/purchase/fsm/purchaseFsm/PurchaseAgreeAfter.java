package com.stosz.purchase.fsm.purchaseFsm;

import com.stosz.fsm.handle.IFsmHandler;
import com.stosz.fsm.model.EventModel;
import com.stosz.plat.common.MBox;
import com.stosz.purchase.ext.enums.PurchaseItemEvent;
import com.stosz.purchase.ext.model.Purchase;
import com.stosz.purchase.ext.model.PurchaseItem;
import com.stosz.purchase.ext.model.finance.Payable;
import com.stosz.purchase.service.PurchaseItemService;
import com.stosz.purchase.service.PurchaseService;
import com.stosz.purchase.service.SupplierService;
import com.stosz.purchase.service.finance.PayableService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author xiongchenyang
 * @version [1.0 , 2017/11/10]
 */
@Component
public class PurchaseAgreeAfter extends IFsmHandler<Purchase> {

    @Resource
    private PurchaseService purchaseService;

    @Resource
    private PurchaseItemService purchaseItemService;
    @Resource
    private PayableService payableService;
    @Resource
    private SupplierService supplierService;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void execute(Purchase purchase, EventModel event) {
        //财务同意，那么将该采购单对应的所有采购明细单状态变为财务同意
        Integer purchaseId  = purchase.getId();
        // 记录财务审核时间
        purchaseService.updateAuditTime(purchase);
        List<PurchaseItem> purchaseItemList = purchaseItemService.findByPurchaseId(purchaseId);
        logger.info("采购单{}的状态变为财务同意，将对应的所有明细改为财务同意！",purchase) ;
        for (PurchaseItem purchaseItem: purchaseItemList) {
            purchaseItemService.processEvent(purchaseItem, PurchaseItemEvent.agreeByFinance,"采购单状态改为财务同意，所有明细改为财务同意", MBox.getSysUser());
        }

        // 如果采购单不是错货单产生的那么 推送到财务系统
        if((purchase.getSourceType() == null || purchase.getSourceType() != 1)) {
            Payable addPayable = purchaseService.getAddPayAble(purchase, purchaseItemList);
            payableService.addPayable(addPayable);
        }
    }



}
