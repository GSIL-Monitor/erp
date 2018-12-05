package com.stosz.store.fsm.transfer;

import com.stosz.fsm.handle.IFsmHandler;
import com.stosz.fsm.model.EventModel;
import com.stosz.store.ext.model.Transfer;
import org.springframework.stereotype.Component;

/**
 * @author
 * @version [1.0 , 2017/11/10]
 */
@Component
public class TransferCreateAfter extends IFsmHandler<Transfer> {

    @Override
    public void execute(Transfer transfer, EventModel event) {
        logger.debug("将新建的调拨单{}置位草稿状态", transfer);
    }
}
