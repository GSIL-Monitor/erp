package com.stosz.store.fsm.transfer;

import com.stosz.fsm.handle.IFsmHandler;
import com.stosz.fsm.model.EventModel;
import com.stosz.store.ext.enums.TransferTypeEnum;
import com.stosz.store.ext.enums.TransitStockEventEnum;
import com.stosz.store.ext.model.Transfer;
import com.stosz.store.ext.model.TransferItem;
import com.stosz.store.service.TransferItemService;
import com.stosz.store.service.TransferService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author xiongchenyang
 * @version [1.0 , 2017/11/10]
 */
@Component
public class ApproveFailAfter extends IFsmHandler<Transfer> {

    @Resource
    private TransferService transferService;
    @Resource
    private TransferItemService transferItemService;

    @Override
    public void execute(Transfer transfer, EventModel event) {

        logger.debug("调拨单{}审核不通过", transfer);
        List<TransferItem> transferItems = transferItemService.findByTranId(transfer.getId());

        switch (TransferTypeEnum.fromId(transfer.getType())) {
            case sameTransit:
            case transit2Normal:
            case transit2Transit:
                transferService.changePack(transferItems, TransitStockEventEnum.inStock);
                break;
            case sameNormal:
            case normal2Normal:
                this.changeSku(transferItems, transfer);
                break;
            default:
                break;
        }


    }

    private void changeSku(List<TransferItem> transferItems, Transfer transfer) {
        if (transferItems.size() > 0) {
            transferService.updateStock(transferItems, transfer, "transfer_cancel");
        }
    }

}
