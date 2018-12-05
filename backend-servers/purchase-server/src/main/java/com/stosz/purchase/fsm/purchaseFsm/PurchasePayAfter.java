package com.stosz.purchase.fsm.purchaseFsm;

import com.stosz.fsm.handle.IFsmHandler;
import com.stosz.fsm.model.EventModel;
import com.stosz.purchase.ext.common.PurchaseDto;
import com.stosz.purchase.ext.enums.PurchaseItemEvent;
import com.stosz.purchase.ext.model.Purchase;
import com.stosz.purchase.ext.model.PurchaseItem;
import com.stosz.purchase.service.PurchaseItemService;
import com.stosz.purchase.service.PurchaseService;
import com.stosz.purchase.service.WmsPurchaseService;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author xiongchenyang
 * @version [1.0 , 2017/11/10]
 */
@Component
public class PurchasePayAfter extends IFsmHandler<Purchase> {

    @Resource
    private PurchaseService purchaseService;

    @Resource
    private PurchaseItemService purchaseItemService;

    @Resource
    private WmsPurchaseService wmsPurchaseService;

    @Override
    public void execute(Purchase purchase, EventModel event) {
        // 将该采购单对应的所有采购明细变为付款，触发付款事件，同时将该采购单推送到wms
        Integer purchaseId = purchase.getId();
        // 记录采购单支付时间和支付信息
        purchaseService.updatePayTime(purchase);
        List<PurchaseItem> purchaseItemList = purchaseItemService.findByPurchaseId(purchaseId);
        Assert.notNull(purchaseItemList,"采购单【"+purchase+"】付款时出错，找不到该采购单对应的明细！");
        for (PurchaseItem purchaseItem: purchaseItemList) {
            purchaseItemService.processEvent(purchaseItem, PurchaseItemEvent.pay,"采购单付款，将所有明细付款",null);
        }
        PurchaseDto purchaseDto = new PurchaseDto();
        purchaseDto.setPurchase(purchase);
        purchaseDto.setItems(purchaseItemList);
        Boolean result = wmsPurchaseService.subCreatePurchaseOrder(purchaseDto);
        if(!result){
            logger.error("将采购单信息{}推送到wms失败！",purchaseDto);
            throw  new RuntimeException("采购单信息推送到wms失败！");
        }
        logger.info("将采购单信息{}推送到wms成功！",purchaseDto);
    }

}
