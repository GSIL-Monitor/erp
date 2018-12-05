package com.stosz.purchase.fsm.purchaseFsm;

import com.stosz.fsm.handle.IFsmHandler;
import com.stosz.fsm.model.EventModel;
import com.stosz.plat.utils.CollectionUtils;
import com.stosz.purchase.ext.model.OrderNotifyRequired;
import com.stosz.purchase.ext.model.Purchase;
import com.stosz.purchase.ext.model.PurchaseItem;
import com.stosz.purchase.ext.service.IOrderRequiredService;
import com.stosz.purchase.service.PurchaseItemService;
import com.stosz.store.ext.dto.request.StockChangeReq;
import com.stosz.store.ext.enums.PurchaseHandleEnum;
import com.stosz.store.ext.service.IStockService;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author xiongchenyang
 * @version [1.0 , 2017/11/10]
 */
@Component
public class PurchaseCreateAfter extends IFsmHandler<Purchase> {

    @Resource
    private IOrderRequiredService iOrderRequiredService;
    @Resource
    private PurchaseItemService purchaseItemService;
    @Resource
    private IStockService iStockService;

    @Override
    public void execute(Purchase purchase, EventModel event) {
        //修改在途数，发起需求计算

        logger.error("修改采购单状态！！！！！！！！！！");
        Integer purchaseId = purchase.getId();
        List<PurchaseItem> purchaseItemList = purchaseItemService.findByPurchaseId(purchaseId);
        Assert.isTrue(CollectionUtils.isNotNullAndEmpty(purchaseItemList),"获取采购单【"+purchaseId+"】的明细失败！");
        if(purchase.getSourceType() == null ||(purchase.getSourceType() != null && purchase.getSourceType() != 1)){
            List<StockChangeReq> stockChangeReqList = new ArrayList<>();
            for(PurchaseItem purchaseItem : purchaseItemList){
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
        }
        for (PurchaseItem purchaseItem: purchaseItemList
             ) {
            Integer deptId = purchaseItem.getDeptId();
            String spu = purchaseItem.getSpu();
            String sku = purchaseItem.getSku();
            OrderNotifyRequired orderNotifyRequired = new OrderNotifyRequired();
            orderNotifyRequired.setDeptId(deptId);
            orderNotifyRequired.setSku(sku);
            orderNotifyRequired.setSpu(spu);
            orderNotifyRequired.setType(2);
            iOrderRequiredService.notifyOrderRequired(orderNotifyRequired);
        }
        logger.error("发起采购需求计算");

    }
}
