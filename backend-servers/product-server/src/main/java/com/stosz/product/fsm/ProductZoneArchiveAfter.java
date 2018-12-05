package com.stosz.product.fsm;

import com.stosz.fsm.handle.IFsmHandler;
import com.stosz.fsm.model.EventModel;
import com.stosz.product.deamon.ProductPushService;
import com.stosz.product.ext.enums.ProductEvent;
import com.stosz.product.ext.enums.ProductNewEvent;
import com.stosz.product.ext.enums.ProductNewState;
import com.stosz.product.ext.enums.ProductState;
import com.stosz.product.ext.model.Product;
import com.stosz.product.ext.model.ProductNew;
import com.stosz.product.ext.model.ProductZone;
import com.stosz.product.service.ProductNewService;
import com.stosz.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;

@Service
public class ProductZoneArchiveAfter extends IFsmHandler<ProductZone> {


    @Autowired
    private ProductService productService;

    @Resource
    private ProductNewService productNewService;
    @Resource
    private ProductPushService productPushService;

    @Override
    public void execute(ProductZone productZone, EventModel event) {
        Product p = productService.getById(productZone.getProductId());
        Assert.notNull(p,"产品区域开始建档时，没有找到对应的产品！产品区域id:" + productZone.getProductId());
        if (p.getStateEnum() == ProductState.archiving) {
            productService.processEvent(p, ProductEvent.archive, "产品区域建档后，产品自动变为已上架", null);
        }

        if(productZone.getProductNewId() != null){
            ProductNew productNew = productNewService.get(productZone.getProductNewId());
            if(productNew.getStateEnum() == ProductNewState.checkOk) {
                productNewService.processEvent(productNew.getId(), productNew.getStateEnum(), ProductNewEvent.archive, "建站发起备案", null, null, false);
            }
        }

    }
}
