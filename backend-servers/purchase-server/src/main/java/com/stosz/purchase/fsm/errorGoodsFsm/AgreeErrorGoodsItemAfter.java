package com.stosz.purchase.fsm.errorGoodsFsm;

import com.stosz.fsm.handle.IFsmHandler;
import com.stosz.fsm.model.EventModel;
import com.stosz.plat.common.MBox;
import com.stosz.purchase.ext.enums.ErrorGoodsEvent;
import com.stosz.purchase.ext.enums.ErrorGoodsItemState;
import com.stosz.purchase.ext.enums.ErrorGoodsState;
import com.stosz.purchase.ext.model.ErrorGoods;
import com.stosz.purchase.ext.model.ErrorGoodsItem;
import com.stosz.purchase.service.ErrorGoodsItemService;
import com.stosz.purchase.service.ErrorGoodsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 同意错货单明细后
 * @author xiongchenyang
 * @version [1.0 , 2018/1/8]
 */
@Component
public class AgreeErrorGoodsItemAfter extends IFsmHandler<ErrorGoodsItem>{

    @Resource
    private ErrorGoodsService errorGoodsService;
    @Resource
    private ErrorGoodsItemService errorGoodsItemService;


    @Override
    public void execute(ErrorGoodsItem errorGoodsItem, EventModel event) {
        Assert.notNull(errorGoodsItem,"要执行的错货单明细不允许为空！");
        Integer errorId = errorGoodsItem.getErrorId();
        ErrorGoodsItem errorGoodsItemDB = errorGoodsItemService.getById(errorGoodsItem.getId());
        errorGoodsItemDB.setAuditor(MBox.getLoginUserName());
        errorGoodsItemDB.setAuditorId(MBox.getLoginUserId());
        errorGoodsItemDB.setAuditorTime(LocalDateTime.now());
        errorGoodsItemService.update(errorGoodsItemDB);
        ErrorGoods errorGoods = errorGoodsService.getById(errorId);
        Assert.notNull(errorGoods,"要修改的错货单不存在！");
        List<ErrorGoodsItem>  errorGoodsItemList = errorGoodsItemService.findByErrorId(errorId);
        Boolean isAllAgree = errorGoodsItemList.stream().allMatch(e->ErrorGoodsItemState.executing.equals(e.getStateEnum()));
        if(isAllAgree){
            errorGoodsService.processEvent(errorGoods, ErrorGoodsEvent.agreeByBusiness,"所有的错货明细都被同意，错货单变为同意",null);

        }

    }
}
