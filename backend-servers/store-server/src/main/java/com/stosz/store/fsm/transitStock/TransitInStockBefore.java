package com.stosz.store.fsm.transitStock;

import com.google.common.collect.Lists;
import com.stosz.fsm.handle.IFsmHandler;
import com.stosz.fsm.model.EventModel;
import com.stosz.order.ext.service.IOrderService;
import com.stosz.plat.utils.CollectionUtils;
import com.stosz.store.ext.dto.request.StockChangeReq;
import com.stosz.store.ext.enums.OrderHandleEnum;
import com.stosz.store.ext.enums.TransferHandleEnum;
import com.stosz.store.ext.enums.TransitStateEnum;
import com.stosz.store.ext.model.ChangeStock;
import com.stosz.store.ext.model.TransferItem;
import com.stosz.store.ext.model.TransitItem;
import com.stosz.store.ext.model.TransitStock;
import com.stosz.store.service.*;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author:ChenShifeng
 * @Description: 转寄入库
 * @Created Time:
 * @Update Time:2017/12/13 18:27
 */
@Component
public class TransitInStockBefore extends IFsmHandler<TransitStock> {

    @Autowired
    private IOrderService orderService;

    @Autowired
    private TransitStockService transitStockService;

    @Autowired
    private TransferItemService transferItemService;

    @Autowired
    private TransferService transferService;

    @Autowired
    private TransitItemService transitItemService;

    @Autowired
    private StockServiceImpl stockServiceImpl;

    @Resource
    private StockService stockService;

    @Override
    public void execute(TransitStock stock, EventModel event) {

        logger.info("转寄入库一个包裹[{}]", stock);
        //更新仓库
        transitStockService.update(stock);

        switch (TransitStateEnum.fromName(stock.getState())) {
            case wait_inTransit:
                //1、sku库存入库
                this.changeStock(stock, OrderHandleEnum.order_in_stock.name());
                // 2、通知订单系统 入库成功
                orderService.updateOrderRmaStatus(stock.getRmaId(), stock.getStorageLocal(), stock.getId());
                break;
            case transferring:
                this.changeStockByTransfer(stock, TransferHandleEnum.transfer_cancel.name());
                break;
            case delivered:
                this.checkTransferOver(stock);
                break;
        }

    }

    /**
     * @param stock
     * @param type  库存操作事件
     */
    private void changeStock(TransitStock stock, String type) {
        //1、入库

        List<TransitItem> itemList = transitItemService.getSkusByTransitId(stock.getId());

        List<StockChangeReq> reqList = Lists.newLinkedList();
        for (TransitItem item : itemList) {
            StockChangeReq req = new StockChangeReq();
            req.setWmsId(stock.getWmsId());
            req.setDeptId(stock.getDeptId());
            req.setDeptName(stock.getDeptName());
            req.setSku(item.getSku());
            req.setChangeQty(item.getQty());
            req.setType(type);
            req.setVoucherNo(stock.getRmaId().toString());
            req.setSpu(item.getSpu());
            req.setAmount(item.getTotalAmount());
            req.setChangeAt(item.getCreateAt());
            reqList.add(req);
        }
        stockServiceImpl.orderChangeStockQty(reqList);
    }

    /**
     *  调拨取消
     * @param stock
     * @param type  库存操作事件
     */
    private void changeStockByTransfer(TransitStock stock, String type) {
        //1、库存取消

        List<TransitItem> itemList = transitItemService.getSkusByTransitId(stock.getId());

        for (TransitItem item : itemList) {
            StockChangeReq req = new StockChangeReq();
            req.setWmsId(stock.getWmsId());
            req.setDeptId(stock.getDeptId());
            req.setDeptName(stock.getDeptName());
            req.setSku(item.getSku());
            req.setChangeQty(item.getQty());
            req.setType(type);
            req.setVoucherNo(stock.getRmaId().toString());
            req.setSpu(item.getSpu());
            req.setAmount(item.getTotalAmount());
            req.setChangeAt(item.getCreateAt());

            ChangeStock changeStock = stockServiceImpl.transferChangeStockQty(req);
            stockService.updateStockData(changeStock);
        }

    }

    /**
     * 调拨入库
     *
     * @param stock
     */
    private void checkTransferOver(TransitStock stock) {
        List<TransferItem> itemList = transferItemService.findByTrack(stock.getTrackingNoOld());
        if (CollectionUtils.isNullOrEmpty(itemList)) {
            return;
        }
        transferService.transferInTransit(itemList.get(0).getTranId());
    }


}