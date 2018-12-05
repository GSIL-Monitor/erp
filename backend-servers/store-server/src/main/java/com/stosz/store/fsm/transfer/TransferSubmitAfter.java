package com.stosz.store.fsm.transfer;

import com.stosz.fsm.FsmProxyService;
import com.stosz.fsm.handle.IFsmHandler;
import com.stosz.fsm.model.EventModel;
import com.stosz.store.ext.dto.request.StockChangeReq;
import com.stosz.store.ext.enums.TransferTypeEnum;
import com.stosz.store.ext.enums.TransitStateEnum;
import com.stosz.store.ext.enums.TransitStockEventEnum;
import com.stosz.store.ext.model.*;
import com.stosz.store.service.*;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author yangqinghui
 * @version [1.0 , 2017/11/10]
 */
@Component
public class TransferSubmitAfter extends IFsmHandler<Transfer> {

    @Resource
    private SettlementMonthService settlementMonthService;
    @Resource
    private StockService stockService;
    @Resource
    private TransferItemService transferItemService;
    @Resource
    private StockServiceImpl stockServiceImpl;
    @Resource
    private TransitStockService transitStockService;
    @Resource
    private TransitItemService transitItemService;
    @Resource
    private FsmProxyService<TransitStock> transitStockFsmProxyService;

    @Override
    public void execute(Transfer transfer, EventModel event) {
        logger.debug("调拨单{}提交", transfer);

        List<TransferItem> transferItems = transferItemService.findByTranId(transfer.getId());

        switch (TransferTypeEnum.fromId(transfer.getType())) {

            case normal2Normal:
                this.normal2Normal(transfer, transferItems);
                break;
            case sameNormal:
                this.lockSku(transfer, transferItems);
                break;
            case transit2Normal:
            case transit2Transit:
            case sameTransit:
                this.changePack(transfer, transferItems);
                break;
            default:
                break;
        }
    }

    /**
     * 包裹变为调拨中
     *
     * @param transferItems
     */
    private void changePack(Transfer transfer, List<TransferItem> transferItems) {
        Assert.notEmpty(transferItems,"无调拨单明细");
        List<TransitItem> itemList = new ArrayList<>();
        for (TransferItem transferItem : transferItems) {
            if (StringUtils.hasText(transferItem.getTrackingNo())) {
                TransitStock stock = transitStockService.findByTrackNoOld(transferItem.getTrackingNo());
                stock.setHistoryState(TransitStateEnum.transferring.name());
                stock.setState(TransitStateEnum.transferring.name());
                transitStockService.update(stock);
//            transitStockFsmProxyService.processEvent(stock, TransitStockEventEnum.transfer, TransitStockEventEnum.transfer.display());
                itemList.addAll(transitItemService.getSkusByTrackingNoOld(transferItem.getTrackingNo()));
            }
        }
        this.lockPackSku(transfer, itemList, transferItems.get(0));
    }

    private void lockPackSku(Transfer transfer, List<TransitItem> itemList, TransferItem transferItem) {
        Assert.notEmpty(itemList,"包裹无sku明细");
        for (TransitItem item : itemList) {
            StockChangeReq stockChangeReq = new StockChangeReq();
            stockChangeReq.setWmsId(transfer.getOutWmsId());
            stockChangeReq.setDeptId(transferItem.getOutDeptId());
            stockChangeReq.setSku(item.getSku());
            stockChangeReq.setSpu(item.getSpu());
            stockChangeReq.setType("transfer_apply");
            stockChangeReq.setChangeQty(item.getQty());
            stockChangeReq.setDeptName(transferItem.getOutDeptName());

            stockChangeReq.setVoucherNo(transfer.getId().toString());
            ChangeStock changeStock = stockServiceImpl.transferChangeStockQty(stockChangeReq);
            stockService.updateStockData(changeStock);
        }
    }


    /**
     * 如果是普通仓或者转寄仓就直接锁库存
     **/
    private void lockSku(Transfer transfer, List<TransferItem> transferItems) {
        for (TransferItem transferItem : transferItems) {
            StockChangeReq stockChangeReq = new StockChangeReq();
            stockChangeReq.setWmsId(transfer.getOutWmsId());
            stockChangeReq.setDeptId(transferItem.getOutDeptId());
            stockChangeReq.setSku(transferItem.getSku());
            stockChangeReq.setSpu(transferItem.getSpu());
            stockChangeReq.setType("transfer_apply");
            stockChangeReq.setChangeQty(transferItem.getExpectedQty());
            stockChangeReq.setDeptNo(transferItem.getOutDeptNo());
            stockChangeReq.setDeptName(transferItem.getOutDeptName());

            stockChangeReq.setVoucherNo(transfer.getId().toString());
            ChangeStock changeStock = stockServiceImpl.transferChangeStockQty(stockChangeReq);
            stockService.updateStockData(changeStock);
        }
    }

    /**
     * 如果是普通仓调拨需要分配部门
     **/
    private void normal2Normal(Transfer transfer, List<TransferItem> transferItems) {
        for (TransferItem transferItem : transferItems) {
            /**查询该库存在该sku的部门**/
            Stock stock = new Stock();
            stock.setWmsId(transfer.getOutWmsId());
            stock.setSku(transferItem.getSku());
            List<Stock> stocks = stockService.queryQty(stock);
            /**确定从哪些部门调拨**/
            Integer expectedQty = transferItem.getExpectedQty();
            for (Stock stock1 : stocks) {

                StockChangeReq stockChangeReq = new StockChangeReq();
                stockChangeReq.setWmsId(stock1.getWmsId());
                stockChangeReq.setDeptId(stock1.getDeptId());
                stockChangeReq.setDeptName(stock1.getDeptName());
                stockChangeReq.setSpu(stock1.getSpu());
                stockChangeReq.setSku(stock1.getSku());
                stockChangeReq.setVoucherNo(transfer.getId().toString());
                stockChangeReq.setType("transfer_apply");

                TransferItem transferItemNew = new TransferItem();
                transferItemNew.setTranId(transfer.getId());
                transferItemNew.setOutDeptId(stock1.getDeptId());
                transferItemNew.setInDeptId(stock1.getDeptId());
                transferItemNew.setSku(stock1.getSku());
                transferItemNew.setSpu(stock1.getSpu());
                transferItemNew.setOutDeptNo(stock1.getDeptNo());
                transferItemNew.setInDeptNo(stock1.getDeptNo());
                transferItemNew.setOutDeptName(stock1.getDeptName());
                transferItemNew.setInDeptName(stock1.getDeptName());
                /**获取当月月结信息将成本价保存在调拨明细里**/
                BigDecimal skuCost = BigDecimal.ONE;//todo 待放开 settlementMonthService.getSkuCost(stock1.getWmsId(), stock1.getDeptId(), stock1.getSku());
                Assert.notNull(skuCost, "未获取当前sku成本价");
                transferItemNew.setCost(skuCost);
                /**订单提交锁库存**/
                if (stock1.getUsableQty() > 0) {
                    int newExpectedQty = expectedQty - stock1.getUsableQty();

                    if (newExpectedQty > 0) {
                        transferItemNew.setExpectedQty(stock1.getUsableQty());
                        stockChangeReq.setChangeQty(stock1.getUsableQty());
                        transferItemService.insert(transferItemNew);
                        ChangeStock changeStock = stockServiceImpl.transferChangeStockQty(stockChangeReq);
                        stockService.updateStockData(changeStock);
                        expectedQty = newExpectedQty;
                    } else {
                        transferItemNew.setExpectedQty(expectedQty);
                        stockChangeReq.setChangeQty(expectedQty);
                        expectedQty = newExpectedQty;
                        transferItemService.insert(transferItemNew);
                        ChangeStock changeStock = stockServiceImpl.transferChangeStockQty(stockChangeReq);
                        stockService.updateStockData(changeStock);
                        break;
                    }
                }
            }
            Assert.isTrue(expectedQty <= 0, "库存不足");
        }
        transferItemService.delete(transferItems);
    }
}
