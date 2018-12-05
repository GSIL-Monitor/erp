package com.stosz.store.fsm.transfer;

import com.google.common.collect.Lists;
import com.stosz.fsm.handle.IFsmHandler;
import com.stosz.fsm.model.EventModel;
import com.stosz.store.ext.dto.request.StockChangeReq;
import com.stosz.store.ext.enums.TransitStockEventEnum;
import com.stosz.store.ext.model.*;
import com.stosz.store.service.*;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ChenShifeng
 * @version [1.0 , 2017/11/10]
 */
@Component
public class TransferInTransitAfter extends IFsmHandler<Transfer> {

    @Resource
    private StockService stockService;
    @Resource
    private TransferItemService transferItemService;
    @Resource
    private StockServiceImpl stockServiceImpl;
    @Resource
    private TransitItemService transitItemService;

    @Override
    public void execute(Transfer transfer, EventModel event) {
        logger.debug("调拨单{} 已入转寄仓", transfer);
        //1.解锁sku  包裹级别自行处理
        this.changePack(transfer);

    }

    /**
     * 解锁sku
     *
     * @param transfer
     */
    private void changePack(Transfer transfer) {
        List<TransferItem> transferItems = transferItemService.findByTranId(transfer.getId());
        List<TransitItem> itemList = Lists.newArrayList();
        for (TransferItem transferItem : transferItems) {
            itemList.addAll(transitItemService.getSkusByTrackingNoOld(transferItem.getTrackingNo()));
        }
        this.changeLockPackSku(transfer, itemList);
    }

    private void changeLockPackSku(Transfer transfer, List<TransitItem> itemList) {
        //获取当前时间点sku的成本价
        BigDecimal skuCost = null;
        for (TransitItem item : itemList) {

            //todo 待放开
            skuCost = new BigDecimal(1);//settlementMonthService.getSkuCost(transfer.getOutWmsId(), transferItem.getOutDeptId(), item.getSku());

            StockChangeReq stockChangeReq = new StockChangeReq();
            stockChangeReq.setWmsId(transfer.getInWmsId());
            stockChangeReq.setDeptId(item.getDeptId());
            stockChangeReq.setSku(item.getSku());
            stockChangeReq.setType("transfer_in_stock");
            stockChangeReq.setChangeQty(item.getQty());

            stockChangeReq.setVoucherNo(transfer.getId().toString());
            stockChangeReq.setDeptName(item.getDeptName());
            stockChangeReq.setAmount(skuCost);
            stockChangeReq.setSpu(item.getSpu());
            stockChangeReq.setChangeAt(transfer.getCreateAt());
            ChangeStock changeStock = stockServiceImpl.transferChangeStockQty(stockChangeReq);
            stockService.updateStockData(changeStock);
        }
    }
}
