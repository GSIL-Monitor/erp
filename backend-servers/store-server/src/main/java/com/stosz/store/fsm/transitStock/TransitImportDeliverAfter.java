package com.stosz.store.fsm.transitStock;

import com.google.common.collect.Lists;
import com.stosz.fsm.handle.IFsmHandler;
import com.stosz.fsm.model.EventModel;
import com.stosz.order.ext.service.IOrderService;
import com.stosz.plat.utils.CollectionUtils;
import com.stosz.store.ext.dto.request.StockChangeReq;
import com.stosz.store.ext.enums.OrderHandleEnum;
import com.stosz.store.ext.enums.TransitStateEnum;
import com.stosz.store.ext.model.TransferItem;
import com.stosz.store.ext.model.TransitItem;
import com.stosz.store.ext.model.TransitStock;
import com.stosz.store.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author:ChenShifeng
 * @Description: 发货导入
 * @Created Time:
 * @Update Time:2017/12/13 18:27
 */
@Component
public class TransitImportDeliverAfter extends IFsmHandler<TransitStock> {

    @Autowired
    private TransitStockService transitStockService;

    @Autowired
    private IOrderService orderService;

    @Autowired
    private TransferItemService transferItemService;

    @Autowired
    private TransitItemService transitItemService;

    @Autowired
    private TransferService transferService;

    @Autowired
    private StockServiceImpl stockServiceImpl;

    @Override
    public void execute(TransitStock stock, EventModel event) {

        logger.debug("发货导入一个包裹[{}]", stock);

        //1.更新新运单
        transitStockService.update(stock);

        if (TransitStateEnum.fromName(stock.getHistoryState()) == TransitStateEnum.transferring) {
            this.checkTransferOver(stock);

        } else {
            //1、sku库存出库
            this.changeStock(stock);
            // 2、通知订单系统 已发货
            orderService.updateOrderStatusByTransit(stock.getOrderIdNew().toString(), stock.getTrackingNoNew(), stock.getLogisticsId(), stock.getLogisticsNameNew());
        }
    }

    private void changeStock(TransitStock stock) {
        //1、出库

        List<TransitItem> itemList=transitItemService.getSkusByTransitId(stock.getId());

        List<StockChangeReq> reqList = Lists.newLinkedList();
        for (TransitItem item : itemList) {
            StockChangeReq req = new StockChangeReq();
            req.setWmsId(stock.getWmsId());
            req.setDeptId(stock.getDeptId());
            req.setDeptName(stock.getDeptName());
            req.setSku(item.getSku());
            req.setChangeQty(item.getQty());
            req.setType(OrderHandleEnum.order_delivered.name());
            req.setVoucherNo(stock.getRmaId().toString());
            req.setSpu(item.getSpu());
            req.setAmount(item.getTotalAmount());
            req.setChangeAt(item.getCreateAt());
            reqList.add(req);
        }
        stockServiceImpl.orderChangeStockQty(reqList);
    }

    /**
     * 调拨出库
     *
     * @param stock
     */
    private void checkTransferOver(TransitStock stock) {
        List<TransferItem> itemList = transferItemService.findByTrack(stock.getTrackingNoOld());
        if (CollectionUtils.isNullOrEmpty(itemList)) {
            return;
        }
        transferService.transferOutTransit(itemList.get(0).getTranId());
    }

}