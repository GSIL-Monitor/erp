package com.stosz.store.fsm.invalid;

import com.stosz.fsm.handle.IFsmHandler;
import com.stosz.fsm.model.EventModel;
import com.stosz.store.ext.model.Invalid;
import org.springframework.stereotype.Component;

/**
 * @Author:ChenShifeng
 * @Description:
 * @Created Time:
 * @Update Time:2017/12/13 18:27
 */
@Component
public class InvalidCreateAfter extends IFsmHandler<Invalid> {

    @Override
    public void execute(Invalid invalid, EventModel event) {

        logger.debug("新建报废单[{}]", invalid);

    }

}