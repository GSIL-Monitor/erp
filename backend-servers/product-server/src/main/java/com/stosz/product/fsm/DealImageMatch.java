package com.stosz.product.fsm;

import com.stosz.fsm.handle.IFsmHandler;
import com.stosz.fsm.model.EventModel;
import com.stosz.product.ext.model.ProductNew;
import com.stosz.product.service.ProductNewSiftImageService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author liufeng
 * @version [1.0 , 2017/9/26]
 */
@Component
public class DealImageMatch extends IFsmHandler<ProductNew> {

    @Resource
    private ProductNewSiftImageService productNewSiftImageService;


    @Override
    public void execute(ProductNew productNew, EventModel event) {
        productNewSiftImageService.deleteByProductNewId(productNew.getId());
    }
}
