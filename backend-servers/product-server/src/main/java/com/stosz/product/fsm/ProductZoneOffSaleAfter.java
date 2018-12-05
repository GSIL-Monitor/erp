package com.stosz.product.fsm;

import com.stosz.fsm.handle.IFsmHandler;
import com.stosz.fsm.model.EventModel;
import com.stosz.plat.common.MBox;
import com.stosz.product.ext.enums.ProductEvent;
import com.stosz.product.ext.enums.ProductState;
import com.stosz.product.ext.enums.ProductZoneState;
import com.stosz.product.ext.model.Product;
import com.stosz.product.ext.model.ProductZone;
import com.stosz.product.service.ProductService;
import com.stosz.product.service.ProductZoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

/**
 * @author liufeng
 * @version [1.0 , 2017/9/22]
 */
@Service
public class ProductZoneOffSaleAfter extends IFsmHandler<ProductZone> {
    @Autowired
    private ProductService productService;
    @Autowired
    private ProductZoneService productZoneService;

    @Override
    public void execute(ProductZone productZone, EventModel event) {
        Product p = productService.getById(productZone.getProductId());
        Assert.notNull(p, "产品区域开始下架时，没有找到对应的产品！产品区域id:" + productZone.getProductId());

        if (p.getStateEnum() != ProductState.offsale && p.getStateEnum() != ProductState.waitFill) {
            List<ProductZone> productZones = productZoneService.findByProductId(p.getId());
            boolean allOffSale = productZones.stream().allMatch(e -> e.getStateEnum() == ProductZoneState.offsale || e.getStateEnum() == ProductZoneState.disappeared);
            if (allOffSale) {
                productService.processEvent(p, ProductEvent.offsale, "此产品对应的所有区域都已经下架，产品也会被设置为下架状态", MBox.getLoginUserName());
            }
        }
    }
}
