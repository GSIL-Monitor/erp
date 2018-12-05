package com.stosz.store.fsm.transfer;

import com.google.common.collect.Lists;
import com.stosz.fsm.handle.IFsmHandler;
import com.stosz.fsm.model.EventModel;
import com.stosz.plat.utils.StringUtils;
import com.stosz.store.ext.dto.request.StockChangeReq;
import com.stosz.store.ext.model.ChangeStock;
import com.stosz.store.ext.model.Transfer;
import com.stosz.store.ext.model.TransferItem;
import com.stosz.store.ext.model.TransitItem;
import com.stosz.store.ext.service.IStockService;
import com.stosz.store.service.*;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;

/**
 * @Author:ChenShifeng
 * @Description: 转寄仓出库
 * @Created Time:
 * @Update Time:2017/12/13 18:27
 */
@Component
public class OutTransitAfter extends IFsmHandler<Transfer> {

    @Resource
    private TransferItemService transferItemService;
    @Resource
    private StockService stockService;
    @Resource
    private SettlementMonthService settlementMonthService;
    @Resource
    private TransitItemService transitItemService;
    @Resource
    private StockServiceImpl stockServiceImpl;

    @Override
    public void execute(Transfer transfer, EventModel event) {
        logger.debug("调拨单{}出库,更新库存", transfer);

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
            if (StringUtils.isNotBlank(transferItem.getTrackingNo())) {
                itemList.addAll(transitItemService.getSkusByTrackingNoOld(transferItem.getTrackingNo()));
            }
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
            stockChangeReq.setWmsId(transfer.getOutWmsId());
            stockChangeReq.setDeptId(item.getDeptId());
            stockChangeReq.setSku(item.getSku());
            stockChangeReq.setType("transfer_out_stock");
            stockChangeReq.setChangeQty(item.getQty());

            stockChangeReq.setVoucherNo(transfer.getId().toString());
            stockChangeReq.setDeptName(item.getDeptName());
            stockChangeReq.setAmount(skuCost);
            stockChangeReq.setSpu(item.getSpu());
            stockChangeReq.setChangeAt(transfer.getCreateAt());
            ChangeStock changeStock = stockServiceImpl.transferChangeStockQty(stockChangeReq);
            stockService.updateStockData(changeStock);


            StockChangeReq inStockChangeReq = new StockChangeReq();
            inStockChangeReq.setWmsId(transfer.getInWmsId());
            inStockChangeReq.setDeptId(item.getDeptId());
            inStockChangeReq.setSku(item.getSku());
            inStockChangeReq.setType("transfer_intransit");
            inStockChangeReq.setChangeQty(item.getQty());
            inStockChangeReq.setDeptName(item.getDeptName());
            inStockChangeReq.setVoucherNo(transfer.getId().toString());
            inStockChangeReq.setAmount(skuCost);
            inStockChangeReq.setSpu(item.getSpu());
            inStockChangeReq.setChangeAt(transfer.getCreateAt());

            ChangeStock inChangeStock = stockServiceImpl.transferChangeStockQty(inStockChangeReq);
            stockService.updateStockData(inChangeStock);
        }
    }
}