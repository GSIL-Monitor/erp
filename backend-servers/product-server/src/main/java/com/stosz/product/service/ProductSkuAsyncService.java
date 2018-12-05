package com.stosz.product.service;

import com.stosz.product.deamon.ProductPushService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author he_guitang
 * @version [1.0 , 2017/11/25]
 *          建站拉取产品的时候,异步生成所有的sku,并推送给老erp
 */
@Service
public class ProductSkuAsyncService {
    @Resource
    private ProductService productService;
    @Resource
    private ProductPushService productPushService;

    /**
     * 异步生成sku
     */
    @Async
    public void asyncGenerateAllSku(Integer productId) {
        productService.generateAllSku(productId);
        productPushService.pushSkuThing(productId);
    }

}
