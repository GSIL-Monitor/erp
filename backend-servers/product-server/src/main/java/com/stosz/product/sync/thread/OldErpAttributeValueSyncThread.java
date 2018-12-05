package com.stosz.product.sync.thread;

import com.stosz.olderp.ext.model.OldErpAttribute;
import com.stosz.olderp.ext.model.OldErpAttributeValue;
import com.stosz.olderp.ext.service.IOldErpAttributeService;
import com.stosz.olderp.ext.service.IOldErpAttributeValueService;
import com.stosz.product.ext.model.AttributeValue;
import com.stosz.product.ext.model.ProductAttributeValueRel;
import com.stosz.product.ext.service.IAttributeValueService;
import com.stosz.product.service.ProductAttributeValueRelService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;

/**
 * 老erp产品属性值同步的多线程方法
 *
 * @author xiongchenyang
 * @version [1.0 , 2017/9/9]
 */
@Component
public class OldErpAttributeValueSyncThread {

    @Resource
    private IAttributeValueService IAttributeValueService;
    @Resource
    private ProductAttributeValueRelService productAttributeValueRelService;
    @Resource
    private IOldErpAttributeService iOldErpAttributeService;
    @Resource
    private IOldErpAttributeValueService iOldErpAttributeValueService;


    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Async("attributeValuePool")
    @Transactional(value = "pcTxManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Future<Integer> pull(List<OldErpAttributeValue> oldErpAttributeValueList) {
        int i = 0;//成功数
        Assert.notNull(oldErpAttributeValueList, "属性值同步，属性值集合不允许为空！");
        List<AttributeValue> attributeValueList = new ArrayList<>();
        List<ProductAttributeValueRel> productAttributeValueRelList = new ArrayList<>();
        for (OldErpAttributeValue oldErpAttributeValue : oldErpAttributeValueList) {
            AttributeValue attributeValue = new AttributeValue();
            attributeValue.setId(oldErpAttributeValue.getId());
            attributeValue.setAttributeId(oldErpAttributeValue.getOptionId());
            attributeValue.setTitle(oldErpAttributeValue.getTitle());
            attributeValue.setVersion(0 - oldErpAttributeValue.getId());
            attributeValueList.add(attributeValue);

            ProductAttributeValueRel productAttributeValueRel = new ProductAttributeValueRel();
            productAttributeValueRel.setId(oldErpAttributeValue.getId());
            productAttributeValueRel.setAttributeValueId(oldErpAttributeValue.getId());
            productAttributeValueRel.setProductAttributeId(oldErpAttributeValue.getOptionId());
            productAttributeValueRel.setProductId(oldErpAttributeValue.getProductId());
            productAttributeValueRel.setCreator("系统");
            productAttributeValueRel.setCreatorId(0);
            productAttributeValueRel.setUsable(true);
            productAttributeValueRelList.add(productAttributeValueRel);
            i++;
        }
        IAttributeValueService.insertList(attributeValueList);
        productAttributeValueRelService.insertList(productAttributeValueRelList);
        return new AsyncResult<>(i);
    }

    @Async("attributeValuePool")
    public Future<Integer> push(List<ProductAttributeValueRel> productAttributeValueRelList) {
        Integer i = 0;//成功记录数
        for (ProductAttributeValueRel productAttributeValueRel : productAttributeValueRelList) {
            String optionTitle = productAttributeValueRel.getOptionTitle();
            Integer productId = productAttributeValueRel.getProductId();
            String title = productAttributeValueRel.getTitle();
            OldErpAttribute oldErpAttribute = iOldErpAttributeService.getByUnique(productId, optionTitle);
            if (oldErpAttribute == null) {
                logger.info("老erp找不到与{}属性值匹配的属性，放弃该条记录！", productAttributeValueRel);
                break;
            }
            OldErpAttributeValue oldErpAttributeValue = new OldErpAttributeValue();
            oldErpAttributeValue.setProductId(productId);
            oldErpAttributeValue.setOptionId(oldErpAttribute.getId());
            oldErpAttributeValue.setTitle(title);
            iOldErpAttributeValueService.insert(oldErpAttributeValue);
            i++;
            logger.info("push属性值{}到老erp成功！", oldErpAttributeValue);
        }
        return new AsyncResult<>(i);
    }


    /**
     * 以产品为维度跑同步，现在已经废弃
     */
    @Async("attributeValuePool")
    public Future<Integer> pullAttributeValue(Integer attributeId, Integer productAttributeRel, Integer productId, Integer oldAttributeId) {
        List<OldErpAttributeValue> oldErpAttributeValueList = iOldErpAttributeValueService.findByAttributeId(productId, oldAttributeId);
        if (oldErpAttributeValueList == null || oldErpAttributeValueList.isEmpty()) {
            logger.info("产品id为{}，老erp属性id为{}，没有对应的属性值！", productId, oldAttributeId);
            return new AsyncResult<>(0);
        }
        int i = 0;
        List<ProductAttributeValueRel> productAttributeValueRelList = new ArrayList<>();
        for (OldErpAttributeValue oldErpAttributeValue : oldErpAttributeValueList) {
            AttributeValue attributeValue = new AttributeValue();
            attributeValue.setAttributeId(attributeId);
            attributeValue.setTitle(oldErpAttributeValue.getTitle().trim());
            attributeValue.setVersion(0);
            try {
                IAttributeValueService.insertOld(attributeValue);
            } catch (DuplicateKeyException e) {
                attributeValue = IAttributeValueService.getByTitleAndAttribute(attributeValue.getVersion(), attributeValue.getTitle(), attributeValue.getAttributeId());
                logger.info("属性值{}在数据库中已经存在！", attributeValue);
            }
            Integer attributeValueId = attributeValue.getId();
            ProductAttributeValueRel productAttributeValueRel = new ProductAttributeValueRel();
            productAttributeValueRel.setAttributeValueId(attributeValueId);
            productAttributeValueRel.setProductAttributeId(productAttributeRel);
            productAttributeValueRel.setProductId(productId);
            productAttributeValueRel.setCreator("系统");
            productAttributeValueRel.setCreatorId(0);
            productAttributeValueRel.setUsable(true);
            productAttributeValueRelList.add(productAttributeValueRel);
            if (productAttributeValueRelList.size() >= 2000) {
                productAttributeValueRelService.insertList(productAttributeValueRelList);
                productAttributeValueRelList.clear();
            }
            i++;
        }
        if (!productAttributeValueRelList.isEmpty()) {
            productAttributeValueRelService.insertList(productAttributeValueRelList);
        }
        logger.info("产品id为{}，老erp属性id为{}的属性值同步完成", productId, oldAttributeId);
        return new AsyncResult<>(i);
    }
}
