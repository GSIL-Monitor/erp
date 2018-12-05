package com.stosz.store.fsm.transfer;

import com.stosz.fsm.handle.IFsmHandler;
import com.stosz.fsm.model.EventModel;
import com.stosz.store.ext.model.Transfer;
import com.stosz.store.service.TransferItemService;
import com.stosz.store.service.TransferService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @Author:yangqinghui
 * @Description:
 * @Created Time:
 * @Update Time:2017/12/13 18:27
 */
@Component
public class OutStoreApproveAfter extends IFsmHandler<Transfer> {

    @Override
    public void execute(Transfer transfer, EventModel event) {
        logger.debug("调拨单{}出库部门审核通过", transfer);
    }
}