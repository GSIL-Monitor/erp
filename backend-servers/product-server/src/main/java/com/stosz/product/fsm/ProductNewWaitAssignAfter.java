package com.stosz.product.fsm;

import com.stosz.fsm.handle.IFsmHandler;
import com.stosz.fsm.model.EventModel;
import com.stosz.product.ext.enums.ProductNewEvent;
import com.stosz.product.ext.enums.ProductNewState;
import com.stosz.product.ext.model.ProductNew;
import com.stosz.product.imageHashMatch.service.HashMatchImageService;
import com.stosz.product.service.ProductNewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author liufeng
 * @version [1.0 , 2017/9/26]
 */
@Component
public class ProductNewWaitAssignAfter extends IFsmHandler<ProductNew> {
    //    @Resource
//    private MatchImageService matchImageService;
    @Resource
    private HashMatchImageService hashMatchImageService;
    @Autowired
    private ProductNewService productNewService;

    @Override
    public void execute(ProductNew productNew, EventModel event) {
        hashMatchImageService.matchImageByCategory(productNew);
        if (productNew.getStateEnum() == ProductNewState.waitImageMatch) {
            productNewService.processEvent(productNew.getId(), productNew.getStateEnum(), ProductNewEvent.match, "图片待匹配通过，状态变为待排重", productNew.getSpu(), null, false);
        }
    }
}
