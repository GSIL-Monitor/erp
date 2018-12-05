package com.stosz.store.fsm.takeStock;

import com.stosz.fsm.handle.IFsmHandler;
import com.stosz.fsm.model.EventModel;
import com.stosz.plat.common.ThreadLocalUtils;
import com.stosz.plat.common.UserDto;
import com.stosz.store.ext.model.TakeStock;
import com.stosz.store.service.TakeStockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.LocalDateTime;

/**
 * @Author:ChenShifeng
 * @Description:
 * @Created Time:
 * @Update Time:2017/12/13 18:27
 */
@Component
public class TakeStockPassFirstAfter extends IFsmHandler<TakeStock> {

    @Autowired
    private TakeStockService takeStockService;

    @Override
    public void execute(TakeStock takeStock, EventModel event) {

        logger.debug("盘点单初审[{}]", takeStock);
        //1.更新记录
        UserDto userDto = ThreadLocalUtils.getUser();
        takeStock.setApprover(userDto.getLastName());
        takeStock.setUpdateAt(LocalDateTime.now());
        //1.更新记录
        takeStockService.update(takeStock);
    }

}