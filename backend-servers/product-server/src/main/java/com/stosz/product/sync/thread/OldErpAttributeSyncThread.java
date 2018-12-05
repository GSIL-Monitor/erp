package com.stosz.product.sync.thread;

import com.stosz.olderp.ext.model.OldErpAttribute;
import com.stosz.olderp.ext.service.IOldErpAttributeService;
import com.stosz.product.ext.model.Attribute;
import com.stosz.product.ext.model.CategoryAttributeRel;
import com.stosz.product.ext.model.ProductAttributeRel;
import com.stosz.product.service.AttributeService;
import com.stosz.product.service.CategoryAttributeRelService;
import com.stosz.product.service.ProductAttributeRelService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * @author xiongchenyang
 * @version [1.0 , 2017/9/9]
 */
@Component
public class OldErpAttributeSyncThread {
    @Resource
    private IOldErpAttributeService iOldErpAttributeService;
    @Resource
    private AttributeService attributeService;
    @Resource
    private ProductAttributeRelService productAttributeRelService;
    @Resource
    private OldErpAttributeValueSyncThread oldErpAttributeValueSyncThread;
    @Resource
    private CategoryAttributeRelService categoryAttributeRelService;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Async("attributePool")
    @Transactional(value = "pcTxManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Future<Integer> pull(List<OldErpAttribute> oldErpAttributes) {
        int success = 0;
        List<Attribute> attributeList = new ArrayList<>();
        List<ProductAttributeRel> productAttributeRelList = new ArrayList<>();
        for (OldErpAttribute oldErpAttribute : oldErpAttributes) {
            Attribute attribute = new Attribute();
            attribute.setId(oldErpAttribute.getId());
            attribute.setVersion(0 - oldErpAttribute.getId());
            ProductAttributeRel productAttributeRel = new ProductAttributeRel();
            productAttributeRel.setId(oldErpAttribute.getId());
            String title = oldErpAttribute.getTitle();
            attribute.setTitle(title);
            productAttributeRel.setProductId(oldErpAttribute.getIdProduct());
            productAttributeRel.setAttributeId(oldErpAttribute.getId());
            productAttributeRel.setCreatorId(0);
            productAttributeRel.setCreator("系统");
            attributeList.add(attribute);
            productAttributeRelList.add(productAttributeRel);
            success++;
        }
        attributeService.insertList(attributeList);
        productAttributeRelService.insertList(productAttributeRelList);
        return new AsyncResult<>(success);
    }


    @Async("attributePool")
    public Future<Integer> push(List<ProductAttributeRel> productAttributeRels) {
        int i = 0;
        for (ProductAttributeRel productAttributeRel : productAttributeRels) {
            OldErpAttribute oldErpAttribute = new OldErpAttribute();
            Integer productId = productAttributeRel.getProductId();
            String title = productAttributeRel.getAttributeTitle();
            oldErpAttribute.setTitle(title);
            oldErpAttribute.setIdProduct(productId);
            int count = iOldErpAttributeService.countByTitle(productId, title);
            if (count == 0) {
                iOldErpAttributeService.insert(oldErpAttribute);
                i++;
            }
        }
        return new AsyncResult<>(i);
    }


    /**
     * 以产品为维度来跑数据时用到的，目前已经废弃
     */
    @Async("attributePool")
    public Future<Integer> pullAttribute(Integer productId, Integer categoryId) throws ExecutionException, InterruptedException {
        List<OldErpAttribute> oldErpAttributeList = iOldErpAttributeService.findByProductId(productId);
        if (oldErpAttributeList == null || oldErpAttributeList.isEmpty()) {
            logger.info("产品id为{}的产品没有对应的属性！");
            return new AsyncResult<>(0);
        }
        int count = 0;
        List<CategoryAttributeRel> categoryAttributeRelList = new ArrayList<>();
        List<Future<Integer>> futureList = new ArrayList<>();
        for (OldErpAttribute oldErpAttribute : oldErpAttributeList) {
            Attribute attribute = new Attribute();
            Integer oldAttributeId = oldErpAttribute.getId();
            attribute.setVersion(0);
            ProductAttributeRel productAttributeRel = new ProductAttributeRel();
            String title = oldErpAttribute.getTitle();
            if (title != null) {
                title = oldErpAttribute.getTitle().trim();
            } else {
                title = "";
            }
            attribute.setTitle(title);
            productAttributeRel.setProductId(oldErpAttribute.getIdProduct());
            productAttributeRel.setCreatorId(0);
            productAttributeRel.setCreator("系统");
            try {
                attributeService.insertOld(attribute);
            } catch (DuplicateKeyException e) {
                attribute = attributeService.getByTitle(attribute.getVersion(), attribute.getTitle());
                logger.info("已经存在----属性{}在数据库中已经存在！", attribute);
            }
            Integer attributeId = attribute.getId();
            productAttributeRel.setAttributeId(attribute.getId());
            try {
                productAttributeRelService.insertOld(productAttributeRel);
            } catch (Exception e) {
                productAttributeRel = productAttributeRelService.getByAttribute(attributeId, productId);
                logger.info("已经存在----产品属性{}在数据库中已经存在！", productAttributeRel);
            }
            Integer productAttributeRelId = productAttributeRel.getId();
            //同步该属性对应的分类属性关系表
            CategoryAttributeRel categoryAttributeRel = new CategoryAttributeRel();
            categoryAttributeRel.setAttributeId(attributeId);
            categoryAttributeRel.setCategoryId(categoryId);
            categoryAttributeRelList.add(categoryAttributeRel);
            if (categoryAttributeRelList.size() >= 2000) {
                categoryAttributeRelService.insertList(categoryAttributeRelList);
                categoryAttributeRelList.clear();
            }
            //同步该属性下的产品属性值
            Future<Integer> future = oldErpAttributeValueSyncThread.pullAttributeValue(attributeId, productAttributeRelId, productId, oldAttributeId);
            futureList.add(future);
            count++;
        }
        if (!categoryAttributeRelList.isEmpty()) {
            categoryAttributeRelService.insertList(categoryAttributeRelList);
        }
        for (Future<Integer> future : futureList) {
            Integer i = future.get();
            logger.info("同步产品id为{}的属性值成功,属性值总数{}！", productId, i);
        }
        logger.info("同步产品id为{} 的属性成功！", productId);
        return new AsyncResult<>(count);
    }

}  
