package com.stosz.purchase.fsm.errorGoodsFsm;

import com.stosz.fsm.handle.IFsmHandler;
import com.stosz.fsm.model.EventModel;
import com.stosz.purchase.ext.enums.ErrorGoodsEvent;
import com.stosz.purchase.ext.enums.ErrorGoodsItemState;
import com.stosz.purchase.ext.model.ErrorGoods;
import com.stosz.purchase.ext.model.ErrorGoodsItem;
import com.stosz.purchase.service.ErrorGoodsItemService;
import com.stosz.purchase.service.ErrorGoodsService;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.List;

/**
 * 错货明细不同意之后
 * @author xiongchenyang
 * @version [1.0 , 2018/1/8]
 */
@Component
public class DisAgreeErrorGoodsItemAfter extends IFsmHandler<ErrorGoodsItem>{

    @Resource
    private ErrorGoodsService errorGoodsService;
    @Resource
    private ErrorGoodsItemService errorGoodsItemService;

    @Override
    public void execute(ErrorGoodsItem errorGoodsItem, EventModel event) {
        Assert.notNull(errorGoodsItem,"要执行的错货单明细不允许为空！");
        Integer errorId = errorGoodsItem.getErrorId();
        ErrorGoods errorGoods = errorGoodsService.getById(errorId);
        Assert.notNull(errorGoods,"要修改的错货单不存在！");
        errorGoodsService.processEvent(errorGoods, ErrorGoodsEvent.disagreeByBusiness,"有一条错货明细不被同意，错货单变为不同意",null);

    }
}
