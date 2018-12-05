package com.stosz.product.sync.service;

import com.stosz.olderp.ext.model.OldErpAttribute;
import com.stosz.olderp.ext.model.OldErpAttributeValue;
import com.stosz.olderp.ext.model.OldErpProductSku;
import com.stosz.olderp.ext.service.IOldErpAttributeService;
import com.stosz.olderp.ext.service.IOldErpAttributeValueService;
import com.stosz.olderp.ext.service.IOldErpProductSkuService;
import com.stosz.olderp.ext.service.IOldErpStockService;
import com.stosz.plat.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author xiongchenyang
 * @version [1.0 , 2017/10/30]
 */
@Service
public class RevokeProductService {

    @Resource
    private IOldErpStockService iOldErpStockService;
    @Resource
    private IOldErpProductSkuService iOldErpProductSkuService;
    @Resource
    private IOldErpAttributeValueService iOldErpAttributeValueService;
    @Resource
    private IOldErpAttributeService iOldErpAttributeService;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    public void revoke(Integer productId){
        Integer orderCount = iOldErpStockService.getHasOrderCountByProductId(productId);
        Assert.isTrue(orderCount == null || orderCount <=0,"该产品有订单，不允许撤回！");

        Integer stockCount = iOldErpStockService.getHasStockCountByProductId(productId);
        Assert.isTrue(stockCount == null || stockCount <= 0 ,"该产品有库存，不允许撤回");
        List<OldErpProductSku> oldErpProductSkuList = iOldErpProductSkuService.findByProductId(productId);
        logger.info("产品id为{}的产品被撤回，即将删除对应sku！sku的集合为{}",productId,oldErpProductSkuList);
        if(oldErpProductSkuList == null || oldErpProductSkuList.size()==0){
            logger.info("产品id为{}的产品没有sku结合！",productId);
            return ;
        }
        for (OldErpProductSku oldErpProductSku: oldErpProductSkuList) {
            String optionValue = oldErpProductSku.getOptionValue();
            if(StringUtils.isNotBlank(optionValue)){
                List<OldErpAttributeValue> oldErpAttributeValueList = iOldErpAttributeValueService.findByAttributeValue(optionValue);
                if(oldErpAttributeValueList.size()>0){
                    for (OldErpAttributeValue oldErpAttributeValue : oldErpAttributeValueList) {
                        Integer optionId = oldErpAttributeValue.getOptionId();
                        OldErpAttribute oldErpAttribute = iOldErpAttributeService.getById(optionId);
                        if(oldErpAttribute!= null){
                            iOldErpAttributeService.delete(optionId);
                            logger.info("撤回产品id为{}的产品时，删除属性{}",productId,oldErpAttribute);
                        }
                        iOldErpAttributeValueService.delete(oldErpAttributeValue.getId());
                        logger.info("撤回产品id为{}的产品时，删除属性值{}",productId,oldErpAttributeValue);
                    }
                }
            }
        }
        iOldErpProductSkuService.deleteByProductId(productId);
        logger.info("撤回产品id为{}的产品时，删除该产品的sku成功！");
    }
}  
