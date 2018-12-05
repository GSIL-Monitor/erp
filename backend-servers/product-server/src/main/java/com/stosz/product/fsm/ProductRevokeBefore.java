package com.stosz.product.fsm;

import com.stosz.fsm.handle.IFsmHandler;
import com.stosz.fsm.model.EventModel;
import com.stosz.product.deamon.ProductPushService;
import com.stosz.product.ext.model.Product;
import com.stosz.product.service.ProductService;
import com.stosz.product.service.ProductSkuAttributeValueRelService;
import com.stosz.product.service.ProductSkuService;
import com.stosz.product.sync.service.RevokeProductService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author xiongchenyang
 * @version [1.0 , 2017/10/31]
 */
@Component
public class ProductRevokeBefore extends IFsmHandler<Product> {

    @Resource
    private ProductService productService;
    @Resource
    private ProductSkuService productSkuService;
    @Resource
    private ProductSkuAttributeValueRelService productSkuAttributeValueRelService;
    @Resource
    private RevokeProductService revokeProductService;
    @Resource
    private ProductPushService productPushService;


    @Override
    public void execute(Product product, EventModel event) {
        revokeProductService.revoke(product.getId());
        logger.info("撤回产品{}，删除老erp数据成功！",product);
        try {
            productSkuAttributeValueRelService.deleteByProductId(product.getId());
            logger.info("撤回产品{},删除新erp的sku属性关联表成功！");
            productSkuService.deleteByProduct(product.getId());
            logger.info("撤回产品{}，删除新erp的sku成功！");
        } catch (Exception e) {
            logger.info("撤回产品{}时，删除新erp数据失败！",product,e);
            logger.info("新erp撤回产品{}失败，老erp删除产品信息成功，需要重新将信息推送到老erp！",product);
            productPushService.pushSkuThing(product.getId());
        }
    }
}
