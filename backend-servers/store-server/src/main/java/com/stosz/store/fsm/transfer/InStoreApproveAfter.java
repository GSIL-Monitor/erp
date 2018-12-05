package com.stosz.store.fsm.transfer;

import com.google.common.collect.Lists;
import com.stosz.fsm.FsmProxyService;
import com.stosz.fsm.handle.IFsmHandler;
import com.stosz.fsm.model.EventModel;
import com.stosz.plat.utils.BeanUtil;
import com.stosz.store.ext.dto.request.StockChangeReq;
import com.stosz.store.ext.enums.TransferTypeEnum;
import com.stosz.store.ext.enums.TransitStockEventEnum;
import com.stosz.store.ext.model.*;
import com.stosz.store.ext.service.IStockService;
import com.stosz.store.service.*;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;

/**
 * @Author:yangqinghui
 * @Description:
 * @Created Time:
 * @Update Time:2017/12/13 18:27
 */
@Component
public class InStoreApproveAfter extends IFsmHandler<Transfer> {

    @Resource
    private StockService stockService;
    @Resource
    private TransferService transferService;
    @Resource
    private TransferItemService transferItemService;
    @Resource
    private IStockService iStockService;
    @Resource
    private TransitStockService transitStockService;
    @Resource
    private FsmProxyService<TransitStock> transitStockFsmProxyService;
    @Resource
    private SettlementMonthService settlementMonthService;
    @Resource
    private TransitItemService transitItemService;

    @Override
    public void execute(Transfer transfer, EventModel event) {
        logger.debug("调入部门审核通过调拨单[{}]", transfer);
        transferService.update(transfer);

        List<TransferItem> itemList = transferItemService.findByTranId(transfer.getId());
        switch (TransferTypeEnum.fromId(transfer.getType())) {
            case sameTransit:
                this.changePack(transfer, itemList);
                break;
            case sameNormal:
                this.changeSku(transfer, itemList);
                break;
            default:
                break;
        }
    }

    /**
     * 修改包裹归属部门
     */
    private void changePack(Transfer transfer, List<TransferItem> itemList) {
        Assert.isTrue(itemList.size() > 0, "无调拨明细存在");
        List<TransitItem> items = Lists.newArrayList();
        for (TransferItem item : itemList) {
            if (StringUtils.hasText(item.getTrackingNo())) {
                TransitStock stock = transitStockService.findByTrackNoOld(item.getTrackingNo());
                stock.setDeptId(item.getInDeptId());
                stock.setDeptName(item.getInDeptName());
                transitStockService.update(stock);
                transitStockFsmProxyService.processEvent(stock, TransitStockEventEnum.transferDept, TransitStockEventEnum.transferDept.display());
                items.addAll(transitItemService.getSkusByTrackingNoOld(item.getTrackingNo()));
            }
        }
        this.changeLockPackSku(transfer, items, itemList.get(0));
    }

    private void changeLockPackSku(Transfer transfer, List<TransitItem> itemList, TransferItem transferItem) {

        //获取当前时间点sku的成本价
        BigDecimal skuCost = null;
        for (TransitItem item : itemList) {
            //todo 待放开
            skuCost = new BigDecimal(1);//settlementMonthService.getSkuCost(transfer.getOutWmsId(), transferItem.getOutDeptId(), item.getSku());

            StockChangeReq stockChangeReq = new StockChangeReq();
            stockChangeReq.setWmsId(transfer.getOutWmsId());
            stockChangeReq.setDeptId(transfer.getOutDeptId());
            stockChangeReq.setSku(item.getSku());
            stockChangeReq.setType("transfer_out_stock");
            stockChangeReq.setChangeQty(item.getQty());
            stockChangeReq.setDeptName(transferItem.getOutDeptName());

            stockChangeReq.setVoucherNo(transfer.getId().toString());
            stockChangeReq.setAmount(skuCost);
            stockChangeReq.setSpu(item.getSpu());
            stockChangeReq.setChangeAt(transfer.getCreateAt());

            ChangeStock changeStock = iStockService.transferChangeStockQty(stockChangeReq);
            stockService.updateStockData(changeStock);

            StockChangeReq inStockChangeReq = new StockChangeReq();
            BeanUtil.copy(stockChangeReq, inStockChangeReq);
            inStockChangeReq.setDeptId(transferItem.getInDeptId());
            inStockChangeReq.setDeptName(transferItem.getInDeptName());
            inStockChangeReq.setType("one_store_in");
            ChangeStock inChangeStock = iStockService.transferChangeStockQty(inStockChangeReq);
            stockService.updateStockData(inChangeStock);

        }
        //修改明细部门
        transitItemService.updateDeptBatch(itemList, transferItem.getInDeptId(), transferItem.getInDeptName());
    }

    private void changeSku(Transfer transfer, List<TransferItem> transferItems) {
        Assert.isTrue(transferItems.size() > 0, "无调拨明细存在");
        TransferItem transferItem = transferItems.get(0);
        transferItem.setFactInQty(transferItem.getFactOutQty());
        transferItem.setFactOutQty(transferItem.getExpectedQty());
        transferItemService.update(transferItem);

        StockChangeReq stockChangeReq = new StockChangeReq();
        stockChangeReq.setWmsId(transfer.getOutWmsId());
        stockChangeReq.setDeptId(transferItem.getOutDeptId());
        stockChangeReq.setSku(transferItem.getSku());
        stockChangeReq.setType("transfer_out_stock");
        stockChangeReq.setChangeQty(transferItem.getExpectedQty());
        stockChangeReq.setDeptNo(transferItem.getOutDeptNo());
        stockChangeReq.setDeptName(transferItem.getOutDeptName());

        stockChangeReq.setVoucherNo(transfer.getId().toString());
        stockChangeReq.setAmount(transferItem.getCost().multiply(new BigDecimal(transferItem.getFactOutQty())));
        stockChangeReq.setSpu(transferItem.getSpu());
        stockChangeReq.setChangeAt(transfer.getCreateAt());

        ChangeStock changeStock = iStockService.transferChangeStockQty(stockChangeReq);
        stockService.updateStockData(changeStock);

        StockChangeReq inStockChangeReq = new StockChangeReq();
        BeanUtil.copy(stockChangeReq, inStockChangeReq);
        inStockChangeReq.setDeptId(transferItem.getInDeptId());
        inStockChangeReq.setDeptNo(transferItem.getInDeptNo());
        inStockChangeReq.setDeptName(transferItem.getInDeptName());
        inStockChangeReq.setType("one_store_in");
        ChangeStock inChangeStock = iStockService.transferChangeStockQty(inStockChangeReq);
        stockService.updateStockData(inChangeStock);
    }
}