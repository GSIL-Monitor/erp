package com.stosz.purchase.fsm.errorGoodsFsm;

import com.stosz.fsm.handle.IFsmHandler;
import com.stosz.fsm.model.EventModel;
import com.stosz.plat.utils.CollectionUtils;
import com.stosz.purchase.ext.enums.ErrorGoodsItemEvent;
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
 * 取消错货单后需要做的
 * @author xiongchenyang
 * @version [1.0 , 2018/1/8]
 */
@Component
public class GiveUpErrorGoodsAfter extends IFsmHandler<ErrorGoods>{

    @Resource
    private ErrorGoodsItemService errorGoodsItemService;

    @Override
    public void execute(ErrorGoods errorGoods, EventModel event) {
        Integer errorId = errorGoods.getId();
        List<ErrorGoodsItem> errorGoodsItemList = errorGoodsItemService.findByErrorId(errorId);
        Assert.isTrue(CollectionUtils.isNotNullAndEmpty(errorGoodsItemList),"该错货单"+errorId+"没有错货明细！！！");
        for (ErrorGoodsItem errorGoodsItem : errorGoodsItemList){
            errorGoodsItemService.processEvent(errorGoodsItem.getId(), ErrorGoodsItemEvent.giveUp,"错货单取消，所有明细取消！！！",null);
        }
    }
}
