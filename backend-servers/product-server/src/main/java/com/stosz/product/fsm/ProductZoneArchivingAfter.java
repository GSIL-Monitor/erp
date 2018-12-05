package com.stosz.product.fsm;

import com.stosz.fsm.handle.IFsmHandler;
import com.stosz.fsm.model.EventModel;
import com.stosz.product.deamon.ProductPushService;
import com.stosz.product.ext.enums.ProductEvent;
import com.stosz.product.ext.enums.ProductState;
import com.stosz.product.ext.model.Product;
import com.stosz.product.ext.model.ProductZone;
import com.stosz.product.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;

@Service
public class ProductZoneArchivingAfter extends IFsmHandler<ProductZone> {


    @Autowired
    private ProductService productService;
    @Resource
    private ProductPushService productPushService;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void execute(ProductZone productZone, EventModel event) {
        Product p = productService.getById(productZone.getProductId());
        Assert.notNull(p,"产品区域开始建档时，没有找到对应的产品！产品区域id:" + productZone.getProductId());
        if (p.getStateEnum() == ProductState.disappeared || p.getStateEnum() == ProductState.offsale) {
            productService.processEvent(p, ProductEvent.archiving, "重新备案，将产品设置为建档中", null);
        }
        //变为建档中的初始状态
        productPushService.pushProductThing(p.getId());
    }
}
