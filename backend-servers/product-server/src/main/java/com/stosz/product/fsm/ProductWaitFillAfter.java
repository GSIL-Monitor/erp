package com.stosz.product.fsm;

import com.stosz.fsm.handle.IFsmHandler;
import com.stosz.fsm.model.EventModel;
import com.stosz.product.deamon.ProductPushService;
import com.stosz.product.ext.model.Product;
import com.stosz.product.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author xiongchenyang
 * @version [1.0 , 2017/9/27]
 */
@Component
public class ProductWaitFillAfter extends IFsmHandler<Product> {

    @Resource
    private ProductService productService;
    @Resource
    private ProductPushService productPushService;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void execute(Product product, EventModel event) {
        //生成所有的sku
//        productService.generateAllSku(product.getId());
        try {
            //productPushService.pushSkuThing(product.getId());
        } catch (Exception e) {
            logger.error("推送产品SKU{}到老erp失败！", product, e);
            throw new RuntimeException("推送产品SKU到老erp失败！", e);
        }

    }
}
