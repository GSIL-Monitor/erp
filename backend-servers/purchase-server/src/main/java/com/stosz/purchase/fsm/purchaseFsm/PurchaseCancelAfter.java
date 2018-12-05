package com.stosz.purchase.fsm.purchaseFsm;

import com.stosz.fsm.handle.IFsmHandler;
import com.stosz.fsm.model.EventModel;
import com.stosz.plat.common.MBox;
import com.stosz.purchase.ext.enums.PurchaseItemEvent;
import com.stosz.purchase.ext.model.Purchase;
import com.stosz.purchase.ext.model.PurchaseItem;
import com.stosz.purchase.service.PurchaseItemService;
import com.stosz.store.ext.dto.request.StockChangeReq;
import com.stosz.store.ext.enums.PurchaseHandleEnum;
import com.stosz.store.ext.service.IStockService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author xiongchenyang
 * @version [1.0 , 2017/11/10]
 */
@Component
public class PurchaseCancelAfter extends IFsmHandler<Purchase> {

    @Resource
    private PurchaseItemService purchaseItemService;
    @Resource
    private IStockService iStockService;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void execute(Purchase purchase, EventModel event) {
        //当采购员点击取消时，将所有采购明细变为取消
        Integer purchaseId = purchase.getId();
        List<PurchaseItem> purchaseItemList = purchaseItemService.findByPurchaseId(purchaseId);
        // 取消采购单的时候，需要通知仓库修改可用库存数
        List<StockChangeReq> stockChangeReqList = new ArrayList<>();
        for (PurchaseItem purchaseItem : purchaseItemList) {
            StockChangeReq stockChangeReq = new StockChangeReq();
            stockChangeReq.setChangeQty(purchaseItem.getQuantity());
            stockChangeReq.setType(PurchaseHandleEnum.ready_purchase.name());
            stockChangeReq.setDeptId(purchaseItem.getDeptId());
            stockChangeReq.setWmsId(purchase.getWmsId());
            stockChangeReq.setSku(purchaseItem.getSku());
            stockChangeReq.setDeptName(purchaseItem.getDeptName());
            stockChangeReq.setSpu(purchaseItem.getSpu());
            stockChangeReq.setAmount(purchaseItem.getAmount());
            stockChangeReq.setVoucherNo(purchase.getPurchaseNo());
            stockChangeReq.setChangeAt(LocalDateTime.now());
            stockChangeReqList.add(stockChangeReq);
        }
        iStockService.purchaseChangeStockQty(stockChangeReqList);


        logger.info("采购单{}的状态变为取消，将对应的所有明细改为取消！", purchase);
        for (PurchaseItem purchaseItem : purchaseItemList) {
            purchaseItemService.processEvent(purchaseItem, PurchaseItemEvent.giveUp, "采购单状态变为取消，将所有采购明细取消", MBox.getSysUser());
        }
    }
}
