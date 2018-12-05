package com.stosz.product.fsm;

import com.stosz.fsm.handle.IFsmHandler;
import com.stosz.fsm.model.EventModel;
import com.stosz.product.ext.enums.ProductNewState;
import com.stosz.product.ext.model.ProductNew;
import com.stosz.product.ext.model.ProductNewZone;
import com.stosz.product.service.ProductNewZoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.List;

/**
 * 新品的草稿状态提交前的检查
 */
@Component
public class ProductNewDraftSubmitBefore extends IFsmHandler<ProductNew> {

    @Autowired
    private ProductNewZoneService productNewZoneService;

    /**
     * 草稿状态提交之前检查有没有设置区域
     * @param productNew
     * @param event
     */
    @Override
    public void execute(ProductNew productNew, EventModel event) {
        if (! event.getSrcState().equals(ProductNewState.draft.name())) {
            return;
        }
        List<ProductNewZone> zones = productNewZoneService.findByProductNewId(productNew.getId());
        Assert.notEmpty(zones,"新品ID:" + productNew.getId() + "尚未添加区域，不允许提交！");
    }
}
