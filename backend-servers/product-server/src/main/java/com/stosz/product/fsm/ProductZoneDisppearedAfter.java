package com.stosz.product.fsm;

import com.stosz.fsm.handle.IFsmHandler;
import com.stosz.fsm.model.EventModel;
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

@Service
public class ProductZoneDisppearedAfter extends IFsmHandler<ProductZone> {


    @Autowired
    private ProductService productService;

    @Autowired
    private ProductZoneService productZoneService;

    @Override
    public void execute(ProductZone productZone, EventModel event) {
        Product p = productService.getById(productZone.getProductId());
        Assert.notNull(p,"产品区域开始建档时，没有找到对应的产品！产品区域id:" + productZone.getProductId());
        if (p.getStateEnum() != ProductState.disappeared && p.getStateEnum() != ProductState.waitFill) {

            List<ProductZone> productZones = productZoneService.findByProductId(p.getId());
            boolean allDisppeared = productZones.stream().allMatch(e -> e.getStateEnum() == ProductZoneState.disappeared);
            if (allDisppeared) {
                if (p.getTotalStock() == 0) {
                    productService.processEvent(p, ProductEvent.cancel,"此产品对应的所有区域都已经销档，并且此产品没有了库存，产品也会被设置为销档状态!",null);
                }else{
                    productService.processLog(p,"log" , "此产品对应的所有区域都已经销档，但产品还有库存:" + p.getTotalStock() + "，产品暂时不做销档" , null);
                }
            }
        }


    }
}
