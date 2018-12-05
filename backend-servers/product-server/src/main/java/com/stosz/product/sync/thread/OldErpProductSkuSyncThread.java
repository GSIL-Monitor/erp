package com.stosz.product.sync.thread;

import com.stosz.olderp.ext.model.OldErpAttribute;
import com.stosz.olderp.ext.model.OldErpAttributeValue;
import com.stosz.olderp.ext.model.OldErpProductSku;
import com.stosz.olderp.ext.service.IOldErpAttributeService;
import com.stosz.olderp.ext.service.IOldErpAttributeValueService;
import com.stosz.olderp.ext.service.IOldErpProductSkuService;
import com.stosz.product.ext.model.Attribute;
import com.stosz.product.ext.model.AttributeValue;
import com.stosz.product.ext.model.ProductSku;
import com.stosz.product.ext.model.ProductSkuAttributeValueRel;
import com.stosz.product.ext.service.IAttributeValueService;
import com.stosz.product.service.AttributeService;
import com.stosz.product.service.ProductSkuAttributeValueRelService;
import com.stosz.product.service.ProductSkuService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Future;

/**
 * @author xiongchenyang
 * @version [1.0 , 2017/9/11]
 */
@Component
public class OldErpProductSkuSyncThread {

    @Resource
    private AttributeService attributeService;
    @Resource
    private IAttributeValueService IAttributeValueService;
    @Resource
    private IOldErpProductSkuService iOldErpProductSkuService;
    @Resource
    private ProductSkuService productSkuService;
    @Resource
    private ProductSkuAttributeValueRelService productSkuAttributeValueRelService;
    @Resource
    private IOldErpAttributeService iOldErpAttributeService;
    @Resource
    private IOldErpAttributeValueService iOldErpAttributeValueService;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Async("productSkuPool")
    @Transactional(value = "pcTxManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Future<Integer> pull(List<OldErpProductSku> oldErpProductSkuList) {
        Integer success = 0;
        List<ProductSku> productSkuList = new ArrayList<>();
        List<ProductSkuAttributeValueRel> productSkuAttributeValueRelList = new ArrayList<>();
        for (OldErpProductSku lst : oldErpProductSkuList) {
            //数据迁移,将老erp的数据写到新erp中
            ProductSku ps = new ProductSku();
            ps.setId(lst.getId());
            ps.setProductId(lst.getIdProduct());
            ps.setSpu(lst.getModel());
            ps.setSku(lst.getSku());
            ps.setBarcode(lst.getBarcode());
            productSkuList.add(ps);
            if (lst.getOptionValue() != null) {
                String[] arr = lst.getOptionValue().split(",");
                List<String> optionValues = Arrays.asList(arr);

                List<OldErpProductSku> skuList = iOldErpProductSkuService.getByAttValueId(optionValues);
                for (OldErpProductSku sku : skuList) {
                    //数据迁移,将老erp的数据写到新erp中
                    ProductSkuAttributeValueRel rel = new ProductSkuAttributeValueRel();
                    rel.setSku(lst.getSku());
                    rel.setProductId(lst.getIdProduct());
                    rel.setAttributeId(sku.getAttributeId());
                    rel.setAttributeValueId(sku.getAttributeValueId());
                    productSkuAttributeValueRelList.add(rel);
                }
            }

            success++;
        }
        productSkuService.insertList(productSkuList);
        if (!productSkuAttributeValueRelList.isEmpty()) {
            productSkuAttributeValueRelService.insertList(productSkuAttributeValueRelList);
        }
        return new AsyncResult<>(success);
    }

    @Async("productSkuPool")
    public Future<Integer> push(List<ProductSku> productSkuList) {
        int success = 0;
        for (ProductSku productSku : productSkuList) {
            String sku = productSku.getSku();
            Integer productId = productSku.getProductId();
            OldErpProductSku oldErpProductSku = new OldErpProductSku();
            oldErpProductSku.setIdProduct(productId);
            oldErpProductSku.setSku(sku);
            oldErpProductSku.setModel(productSku.getSpu());
            oldErpProductSku.setBarcode(productSku.getBarcode());
            Assert.notNull(sku, "产品sku不能为空！");
            List<ProductSkuAttributeValueRel> attributeTitles = productSkuAttributeValueRelService.findBySku(sku);
            StringBuilder sb = new StringBuilder();
            if (attributeTitles != null && !attributeTitles.isEmpty()) {
                for (ProductSkuAttributeValueRel productSkuAttributeValueRel : attributeTitles) {
                    String attributeTitle = productSkuAttributeValueRel.getAttributeTitle();
                    String attributeValueTitle = productSkuAttributeValueRel.getAttributeValueTitle();
                    OldErpAttribute oldErpAttribute = iOldErpAttributeService.getByUnique(productId, attributeTitle);
                    if (oldErpAttribute != null) {
                        OldErpAttributeValue oldErpAttributeValue = iOldErpAttributeValueService.getByUnique(productId, oldErpAttribute.getId(), attributeValueTitle);
                        if (oldErpAttributeValue != null) {
                            sb.append(oldErpAttributeValue.getId()).append(",");
                        }
                    }
                }
            }
            if (sb.length() > 0) {
                sb.deleteCharAt(sb.length() - 1);
                oldErpProductSku.setOptionValue(sb.toString());
            }
            OldErpProductSku oldErpProductSkuDB = iOldErpProductSkuService.getBySku(sku);
            if (oldErpProductSkuDB == null) {
                iOldErpProductSkuService.insert(oldErpProductSku);
            } else {
                iOldErpProductSkuService.update(oldErpProductSku);
            }
            success++;
        }
        return new AsyncResult<>(success);
    }

    /**
     * 以产品为维度跑同步时用的，现在没用
     */
    @Async("productSkuPool")
    public Future<Integer> pullSku(Integer productId) {
        List<OldErpProductSku> oldErpProductSkuList = iOldErpProductSkuService.findByProductId(productId);
        Integer success = 0;
        List<ProductSku> productSkuList = new ArrayList<>();
        List<ProductSkuAttributeValueRel> productSkuAttributeValueRelList = new ArrayList<>();
        for (OldErpProductSku lst : oldErpProductSkuList) {
            //数据迁移,将老erp的数据写到新erp中
            ProductSku ps = new ProductSku();
            ps.setProductId(lst.getIdProduct());
            ps.setSpu(lst.getModel());
            ps.setSku(lst.getSku());
            ps.setBarcode(lst.getBarcode());
            productSkuList.add(ps);
            if (productSkuList.size() > 2000) {
                productSkuService.insertList(productSkuList);
                productSkuList.clear();
            }
            if (lst.getOptionValue() != null) {
                String[] arr = lst.getOptionValue().split(",");
                List<String> optionValues = Arrays.asList(arr);

                List<OldErpProductSku> skuList = iOldErpProductSkuService.getByAttValueId(optionValues);
                for (OldErpProductSku sku : skuList) {
                    //通过属性名获取属性id
                    Attribute attr = attributeService.getByTitle(0, sku.getAttributeTitle());
                    if (attr == null) {
                        logger.info("老erp存在的属性名{}，在新erp不存在，跳过这一条！", sku.getAttributeTitle());
                        continue;
                    }
                    //通过属性值title和属性id获取属性值id
                    AttributeValue attrValue = IAttributeValueService.getByTitleAndAttribute(0, sku.getAttributeValueTitle(), attr.getId());
                    if (attrValue == null) {
                        logger.info("老erp存在的属性值名{}，在新erp不存在，跳过这一条！", sku.getAttributeValueTitle());
                        continue;
                    }
                    //数据迁移,将老erp的数据写到新erp中
                    ProductSkuAttributeValueRel rel = new ProductSkuAttributeValueRel();
                    rel.setSku(lst.getSku());
                    rel.setProductId(lst.getIdProduct());
                    rel.setAttributeId(attr.getId());
                    rel.setAttributeValueId(attrValue.getId());
                    productSkuAttributeValueRelList.add(rel);
                }
            }
            if (productSkuList.size() >= 2000) {
                productSkuAttributeValueRelService.insertList(productSkuAttributeValueRelList);
                productSkuAttributeValueRelList.clear();
            }
            success++;
        }
        if (productSkuList.size() > 0) {
            productSkuService.insertList(productSkuList);
        }
        if (productSkuAttributeValueRelList.size() > 0) {
            productSkuAttributeValueRelService.insertList(productSkuAttributeValueRelList);
        }
        return new AsyncResult<>(success);
    }

}  
