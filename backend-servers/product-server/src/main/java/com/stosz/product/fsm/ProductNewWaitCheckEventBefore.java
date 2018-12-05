package com.stosz.product.fsm;

import com.stosz.fsm.handle.IFsmHandler;
import com.stosz.fsm.model.EventModel;
import com.stosz.product.ext.enums.ProductNewEvent;
import com.stosz.product.ext.enums.ProductNewState;
import com.stosz.product.ext.model.ProductNew;
import com.stosz.product.service.ProductNewService;
import com.stosz.product.service.ProductService;
import com.stosz.product.service.ProductZoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

/**
 * 新品的带审核状态事件处理的约束条件，即排查人员做排查时的检查
 */
@Component
public class ProductNewWaitCheckEventBefore extends IFsmHandler<ProductNew> {
    @Autowired
    private ProductNewService productNewService;

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductZoneService productZoneService;

    /**
     * 回到带排查时，需要考虑新品对应的项目是不是都已经被建档，被建档的是不允许
     * @param productNew
     * @param event
     */
    @Override
    public void execute(ProductNew productNew, EventModel event) {
        if ( ! event.getSrcState().equals(ProductNewState.waitCheck.name())) {
            return ;
        }

        String eventName = event.getEventName();
        ProductNewEvent e = ProductNewEvent.fromName(eventName);
        if (e == ProductNewEvent.duplicate) {
            Assert.isTrue(productNew.getSpu() != null,"重复产品需要指定spu");
        }



//        if (event.getSrcState().equals(ProductNewState.waitCheck)) {
//            //如果是从 排查通过，被撤回到待排查，则需要检查对应的产品是否已经建档，如果已经建档，则不允许
//            Assert.notNull(productNew.getSpu(),"新品id:{} 没有对应的spu，数据错误！无法撤回!");
//            List<ProductZone> productZoneList = productZoneService.findByProductNewId(productNew.getId());
//            Assert.isTrue(productZoneList!=null && ! productZoneList.isEmpty() , "新品id:" + productNew.getId() + " 没有找到对应的产品区域，数据错误！");
//
//            Optional<ProductZone> opProductZone =  productZoneList.stream().filter(e-> e.getStateEnum()== ProductZoneState.onsale).findAny();
//            //理论上来说，不可能出现这种情况
//            Assert.isTrue( ! opProductZone.isPresent() , "无法撤回，产品已经被建档" );
//            //TODO 还有好多要判断的
//
//        }
    }
}
