package com.stosz.product.service;


import com.stosz.plat.common.RestResult;
import com.stosz.product.deamon.ProductPushService;
import com.stosz.product.ext.model.ProductSku;
import com.stosz.product.ext.model.ProductSkuAttributeValueRel;
import com.stosz.product.ext.service.IWmsService;
import com.stosz.product.mapper.ProductSkuMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * 产品sku表
 *
 * @author he_guitang
 * @version [版本号, 1.0]
 */
@Service
public class ProductSkuService {
    @Resource
    private IWmsService wmsService;
    @Resource
    private ProductSkuMapper mapper;
    @Resource
    private ProductService productService;
    @Resource
    private ProductPushService productPushService;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 产品sku增加
     */
    public void insert(ProductSku productSku) {
    	try {
    		mapper.insert(productSku);
    		logger.info("产品sku添加成功: {} ", productSku);
        } catch (DuplicateKeyException e) {
        	logger.info("sku: {} 已经在数据库中存在", productSku.getSku());
        	throw new IllegalArgumentException("sku["+productSku.getSku()+"]已经存在,不能重复生成!");
        } catch (Exception e){
            logger.error(e.getMessage(),e);
        }

        wmsService.insertOrUpdateProduct(productSku);
       
    }
    
    /**
     * 产品sku批量增加
     */
   public void batchInsert(List<ProductSku> productSkuList){
	   	Assert.notNull(productSkuList, "不允许插入空的SKU集合!");
	   	try{
	    	mapper.batchInsert(0, productSkuList);
	    	logger.info("产品sku批量插入成功: {} ",productSkuList);
	   	}catch(DuplicateKeyException e){
	   		logger.info("产品sku: {} 在数据库中已经存在!", productSkuList);
	   		throw new IllegalArgumentException("sku已经存在,不能重复生成!");
	   	}

	   	productSkuList.stream().forEach(productSku ->  wmsService.insertOrUpdateProduct(productSku));

    }

    public int deleteByProduct(Integer productId) {
        int count = mapper.deleteByProduct(productId);
        logger.info("根据产品id: {} 删除产品sku成功,共删除: {} 条!", productId, count);
        return count;
    }
    
    public void insertOld(ProductSku productSku) {
        try {
            Assert.notNull(productSku, "不允许插入空的产品SKU!");
            int i = mapper.insert(productSku);
            Assert.isTrue(i == 1, "插入产品SKU失败！");
            logger.info("插入产品SKU{}成功！", productSku);
        } catch (DuplicateKeyException e) {
//			productSku = productSkuMapper.getBySku(productSku.getSku());
            logger.info("产品sku{}在数据库中已经存在！", productSku);
        }

        wmsService.insertOrUpdateProduct(productSku);

    }

    public ProductSku getBySku(String sku) {
        return mapper.getBySku(sku);
    }

    public List<ProductSku> findByDate(ProductSku param) {
        Assert.notNull(param.getMinCreateAt(), "起始时间不允许为空！");
        Assert.notNull(param.getMaxCreateAt(), "结束时间不允许为空！");
        return mapper.findByDate(param);
    }
    
    public int countByProductId(Integer productId){
    	return mapper.countByProductId(productId);
    }
    
    public int countByDate(LocalDateTime startTime, LocalDateTime endTime) {
        Assert.notNull(startTime, "起始时间不允许为空！");
        Assert.notNull(endTime, "结束时间不允许为空！");
        return mapper.countByDate(startTime, endTime);
    }

    public List<ProductSku> findByProductId(Integer productId) {
        Assert.notNull(productId, "产品id不允许为空！");
        return mapper.findByProductId(productId);
    }


    public void insertList(List<ProductSku> productSkuList) {
        Assert.notNull(productSkuList, "不允许插入空的SKU集合！");
        mapper.insertList(0, productSkuList);

//        productSkuList.stream().forEach(productSku ->  wmsService.insertOrUpdateProduct(productSku));

    }


    public ProductSku getById(Integer id) {
        Assert.notNull(id, "skuID为空,不能查询产品的sku");
        return mapper.getById(id);
    }

    public Set<String> findWmsPushErrSkus() {
        return mapper.findWmsPushErrSkus();
    }


    /**
     * 根据sku获取产品id
     */
    public Set<Integer> findBySkus(List<String> skuList) {
        return mapper.findBySkus(skuList);
    }


    public List<ProductSku> findBySkuList(List<String> skuList) {
        if (skuList == null || skuList.size() == 0) return new ArrayList<>();
        return mapper.findBySkuList(skuList);
    }

    public ProductSku getAttrValueTitleBySku(String sku) {
        return mapper.getAttrValueTitleBySku(sku);
    }

    /**
     * sku生成
     */
    public RestResult productSkuGenerate(Integer productId, List<ProductSkuAttributeValueRel> listParam) {
        RestResult result = productService.productSkuGenerateTrans(productId, listParam);
        productPushService.pushSkuThing(productId);
        return result;
    }

    //==========================================================
    public void updateAttrValTitle(String title, Integer id) {
        mapper.updateAttrValTitle(title, id);
    }

    public int countSum() {
        return mapper.countSum();
    }

    public List<ProductSku> skuList(Integer limit) {
        return mapper.skuList(limit);
    }

    public String getAttValTitle(String sku) {
        return mapper.getAttValTitle(sku);
    }

    public List<ProductSku> findByProductIds(List<Integer> productIds) {
        if (productIds == null || productIds.size() == 0) return null;
        return mapper.findByProductIds(productIds);
    }

}
