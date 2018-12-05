package com.stosz.store.fsm.transfer;

import com.stosz.fsm.handle.IFsmHandler;
import com.stosz.fsm.model.EventModel;
import com.stosz.store.ext.dto.request.StockChangeReq;
import com.stosz.store.ext.model.ChangeStock;
import com.stosz.store.ext.model.Transfer;
import com.stosz.store.ext.model.TransferItem;
import com.stosz.store.ext.service.IStockService;
import com.stosz.store.service.StockService;
import com.stosz.store.service.TransferItemService;
import org.springframework.stereotype.Component;

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
public class OutStoreAfter extends IFsmHandler<Transfer> {

    @Resource
    private IStockService iStockService;
    @Resource
    private TransferItemService transferItemService;
    @Resource
    private StockService stockService;

    @Override
    public void execute(Transfer transfer, EventModel event) {
        logger.debug("调拨单{}出库,更新库存", transfer);
        List<TransferItem> transferItems = transferItemService.findByTranId(transfer.getId());
        for (TransferItem transferItem : transferItems) {

            StockChangeReq stockChangeReq = new StockChangeReq();
            stockChangeReq.setWmsId(transfer.getOutWmsId());
            stockChangeReq.setDeptId(transferItem.getOutDeptId());
            stockChangeReq.setSku(transferItem.getSku());
            stockChangeReq.setType("transfer_out_stock");
            stockChangeReq.setChangeQty(transferItem.getFactOutQty());
            stockChangeReq.setDeptName(transferItem.getOutDeptName());

            stockChangeReq.setVoucherNo(transfer.getId().toString());
            stockChangeReq.setAmount(transferItem.getCost().multiply(new BigDecimal(transferItem.getFactOutQty())));
            stockChangeReq.setSpu(transferItem.getSpu());
            stockChangeReq.setChangeAt(transfer.getCreateAt());

            ChangeStock changeStock = iStockService.transferChangeStockQty(stockChangeReq);
            stockService.updateStockData(changeStock);

            StockChangeReq inStockChangeReq = new StockChangeReq();
            inStockChangeReq.setWmsId(transfer.getInWmsId());
            inStockChangeReq.setDeptId(transferItem.getInDeptId());
            inStockChangeReq.setSku(transferItem.getSku());

            inStockChangeReq.setVoucherNo(transfer.getId().toString());
            inStockChangeReq.setAmount(transferItem.getCost().multiply(new BigDecimal(transferItem.getFactOutQty())));
            inStockChangeReq.setSpu(transferItem.getSpu());
            inStockChangeReq.setChangeAt(transfer.getCreateAt());

            inStockChangeReq.setType("transfer_intransit");
            inStockChangeReq.setChangeQty(transferItem.getFactOutQty());
            inStockChangeReq.setDeptName(transferItem.getInDeptName());

            ChangeStock inChangeStock = iStockService.transferChangeStockQty(inStockChangeReq);
            stockService.updateStockData(inChangeStock);
        }
    }
}