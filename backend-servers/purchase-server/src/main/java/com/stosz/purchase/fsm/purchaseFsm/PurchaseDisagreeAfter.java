package com.stosz.purchase.fsm.purchaseFsm;

import com.stosz.fsm.handle.IFsmHandler;
import com.stosz.fsm.model.EventModel;
import com.stosz.plat.common.MBox;
import com.stosz.purchase.ext.enums.PurchaseItemEvent;
import com.stosz.purchase.ext.model.Purchase;
import com.stosz.purchase.ext.model.PurchaseItem;
import com.stosz.purchase.service.PurchaseItemService;
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
public class PurchaseDisagreeAfter extends IFsmHandler<Purchase> {

    @Resource
    private PurchaseItemService purchaseItemService;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void execute(Purchase purchase, EventModel event) {
        //TODO 将该采购单对应的所有采购明细变为财务不同意状态
        Integer purchaseId  = purchase.getId();
        List<PurchaseItem> purchaseItemList = purchaseItemService.findByPurchaseId(purchaseId);
        logger.info("采购单{}的状态变为财务不同意，将对应的所有明细改为财务不同意！",purchase) ;
        for (PurchaseItem purchaseItem: purchaseItemList) {
            purchaseItemService.processEvent(purchaseItem, PurchaseItemEvent.disagreeByFinance,"采购单状态变为财务不同意，将所有采购明细变为财务不同意", MBox.getSysUser());
        }

    }
}
