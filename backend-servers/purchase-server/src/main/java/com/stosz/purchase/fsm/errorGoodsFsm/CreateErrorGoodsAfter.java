package com.stosz.purchase.fsm.errorGoodsFsm;

import com.stosz.fsm.handle.IFsmHandler;
import com.stosz.fsm.model.EventModel;
import com.stosz.purchase.ext.model.ErrorGoods;
import com.stosz.purchase.ext.model.ErrorGoodsItem;
import com.stosz.purchase.ext.model.PurchaseItem;
import com.stosz.purchase.ext.model.PurchaseReturnedItem;
import com.stosz.purchase.service.PurchaseService;
import com.stosz.store.ext.dto.request.StockChangeReq;
import com.stosz.store.ext.enums.PurchaseHandleEnum;
import com.stosz.store.ext.service.IStockService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author xiongchenyang
 * @version [1.0 , 2018/1/12]
 */
@Component
public class CreateErrorGoodsAfter extends IFsmHandler<ErrorGoods> {

    @Resource
    private IStockService iStockService;
    @Resource
    private PurchaseService purchaseService;

    @Override
    public void execute(ErrorGoods errorGoods, EventModel event) {
        //锁库存
        List<StockChangeReq> stockChangeReqList = new ArrayList<>();
        List<ErrorGoodsItem> errorGoodsItemList = errorGoods.getErrorGoodsItemList();
        for(ErrorGoodsItem errorGoodsItem : errorGoodsItemList){
            StockChangeReq purchaseStockChangeReq = new StockChangeReq();
            StockChangeReq returnStockChangeReq = new StockChangeReq();

            purchaseStockChangeReq.setChangeQty(errorGoodsItem.getQuantity());
            returnStockChangeReq.setChangeQty(errorGoodsItem.getQuantity());

            purchaseStockChangeReq.setType(PurchaseHandleEnum.ready_purchase.name());
            returnStockChangeReq.setType(PurchaseHandleEnum.intransit_minus.name());

            purchaseStockChangeReq.setDeptId(errorGoodsItem.getDeptId());
            returnStockChangeReq.setDeptId(errorGoodsItem.getDeptId());

            purchaseStockChangeReq.setWmsId(errorGoods.getWmsId());
            returnStockChangeReq.setWmsId(errorGoods.getWmsId());

            purchaseStockChangeReq.setSku(errorGoodsItem.getRealSku());
            returnStockChangeReq.setSku(errorGoodsItem.getOriginalSku());

            purchaseStockChangeReq.setDeptName(errorGoodsItem.getDeptName());
            returnStockChangeReq.setDeptName(errorGoodsItem.getDeptName());

            purchaseStockChangeReq.setSpu(errorGoodsItem.getSpu());
            returnStockChangeReq.setSpu(errorGoodsItem.getSpu());

            purchaseStockChangeReq.setAmount(errorGoodsItem.getAmount());
            returnStockChangeReq.setAmount(errorGoodsItem.getAmount());

            purchaseStockChangeReq.setVoucherNo(errorGoods.getNo());
            returnStockChangeReq.setVoucherNo(errorGoods.getNo());

            purchaseStockChangeReq.setChangeAt(LocalDateTime.now());
            returnStockChangeReq.setChangeAt(LocalDateTime.now());

            stockChangeReqList.add(purchaseStockChangeReq);
            stockChangeReqList.add(returnStockChangeReq);
        }
        iStockService.purchaseChangeStockQty(stockChangeReqList);

    }
}
