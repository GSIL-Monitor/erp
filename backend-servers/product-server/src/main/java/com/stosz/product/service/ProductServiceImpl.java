package com.stosz.product.service;

import com.stosz.plat.enums.LabelTypeEnum;
import com.stosz.product.ext.model.*;
import com.stosz.product.ext.service.IAttributeValueService;
import com.stosz.product.ext.service.IProductService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author xiongchenyang
 * @version [1.0 , 2017/11/13]
 */
@Service
public class ProductServiceImpl implements IProductService {


    @Resource
    private ProductService productService;
    @Resource
    private IAttributeValueService iAttributeValueService;
    @Resource
    private AttributeService attributeService;
    @Resource
    private AttributeValueService attributeValueService;
    @Resource
    private ProductSkuService productSkuService;
    @Resource
    private ProductSkuAttributeValueRelService productSkuAttributeValueRelService;
    @Resource
    private PartnerService partnerService;
    @Resource
    private LabelObjectService labelObjectService;

    @Override
    public Product getBySpu(String spu) {
        if (spu == null || "".equals(spu)) {

            return null;
        }
        Product product = productService.getBySpu(spu);
        return product;
    }

    /**
     * 根据sku获取属性信息
     */
    @Override
    public List<AttributeValue> getValueListBySku(String sku) {
        return iAttributeValueService.getValueListBySku(sku);
    }


    @Override
    public Product getProductInfoById(Integer id) {
        if (id == null) return  null;
        Product product = productService.getById(id);
        if (product == null) return null;
        List<Attribute> attributes = attributeService.queryByProductId(id);
        if (attributes == null || attributes.size() == 0){
            return product;
        }
        List<AttributeValue> attributeValues = attributeValueService.findAttrValueByProductId(id);
        Map<Integer, List<AttributeValue>> map = attributeValues.stream().collect(Collectors.groupingBy(AttributeValue::getAttributeId));
        for (Attribute attribute : attributes){
            attribute.setAttributeValueList(map.get(attribute.getId()));
        }
        product.setAttributeList(attributes);
        return product;
    }

    @Override
    public List<Product> findProductBySpuList(Set<String> spuList) {
        return productService.findProductBySupList(spuList);
    }

    @Override
    public Product getById(Integer productId) {
        return productService.getById(productId);
    }

    @Override
    public List<Product> getByIds(List<Integer> productIds) {
        return productService.getByIds(productIds);
    }

    @Override
    public Product getProductById(Integer productId) {
        return productService.getById(productId);
    }

    @Override
    public List<Product> findByWebDirectory(String webDirectory) {
        List<Product> list = productService.findByWebDirectory(webDirectory);
        if (list == null || list.size() == 0){
            return null;
        }
        List<Integer> productIds = list.stream().map(Product::getId).collect(Collectors.toList());
        List<ProductSku> productSkuList = productSkuService.findByProductIds(productIds);
        Map<Integer, List<ProductSku>> skuMap = productSkuList.stream().collect(Collectors.groupingBy(ProductSku::getProductId));
        for (Product product : list){
//            List<ProductSku> productSkuList = productSkuService.findByProductId(product.getId());
//            product.setSkuList(productSkuList);
            product.setSkuList(skuMap.get(product.getId()));
        }
        return list;
    }

    @Override
    public Product getProductAssemble(Integer id) {
        if (id == null) return null;
        Product product = productService.getById(id);
        List<ProductSku> productSkuList = productSkuService.findByProductId(id);
        for (ProductSku pk : productSkuList) {
            List<ProductSkuAttributeValueRel> relList = productSkuAttributeValueRelService.findBySku(pk.getSku());
            List<Integer> attrValIds = relList.stream().map(ProductSkuAttributeValueRel::getAttributeValueId).collect(Collectors.toList());
            List<AttributeValue> attrVal = attributeValueService.findByIds(attrValIds);
            pk.setAttributeValues(attrVal);
        }
        product.setSkuList(productSkuList);
        return product;
    }

    @Override
    public Map<String, Product> findBySkuList(List<String> skuList) {
        List<Product> products = productService.findBySkuList(skuList);
        Map<String, Product> skuMap = products.stream().collect(Collectors.toMap(Product::getSku, Function.identity()));
        return skuMap;
    }

    @Override
    public List<Product> findBySpuSet(Set<String> spuList) {
        if (spuList == null || spuList.size() == 0) return null;
        List<Product> productList = productService.findProductBySupList(spuList);
        if (productList == null || productList.size() == 0) return null;
        List<Integer> productIds = productList.stream().map(Product::getId).collect(Collectors.toList());
        List<ProductSku> productSkuList = productSkuService.findByProductIds(productIds);
        Map<Integer, List<ProductSku>> skuMap = productSkuList.stream().collect(Collectors.groupingBy(ProductSku::getProductId));
        for (Product product : productList){
            product.setSkuList(skuMap.get(product.getId()));
        }
        return productList;
    }    
    @Override
    public Map<String, List<LabelObject>> queryLabelBySkuList(List<String> skuList) {
        if (skuList == null || skuList.size() == 0) return new HashMap<>();
        List<ProductSku> productSkuList = productSkuService.findBySkuList(skuList);
        List<Integer> productIds = productSkuList.stream().map(ProductSku::getProductId).collect(Collectors.toList());
        Map<Integer, List<LabelObject>> map = labelObjectService.queryListLabelObject(productIds, LabelTypeEnum.Product.name());
        Map<String, List<LabelObject>> resultMap = new HashMap<>();
        for (ProductSku productSku : productSkuList){
            if (map.get(productSku.getId()) != null && map.get(productSku.getId()).size() != 0 ){
                List<LabelObject> list = map.get(productSku.getId());
                resultMap.put(productSku.getSku(), list);
            }
        }
        return resultMap;
    }
}
