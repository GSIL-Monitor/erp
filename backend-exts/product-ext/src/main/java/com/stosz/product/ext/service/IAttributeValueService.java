package com.stosz.product.ext.service;

import com.stosz.plat.common.RestResult;
import com.stosz.product.ext.model.AttributeValue;
import com.stosz.product.ext.model.ProductAttributeValueRel;

import java.util.List;

/**
 * @auther carter
 * create time    2017-11-29
 */
public interface IAttributeValueService {
    String url="/product/remote/IAttributeValueService";

    void addAttributeValue(AttributeValue attributeValue);

    void insertOld(AttributeValue attributeValue);

    void insertList(List<AttributeValue> attributeValueList);

    void addByProduct(AttributeValue attributeValue, Integer productId);

    void delete(Integer attributeValueId);

    void deleteByAttributeId(Integer attributeId);

    void updateAttributeValue(AttributeValue attributeValue);

    RestResult bind(ProductAttributeValueRel param, Integer attributeId);

    RestResult unBind(ProductAttributeValueRel param, Integer attributeId);

    int countProductSkuList(Integer productId, Integer attributeId);

    List<AttributeValue> findAttrValueByProductId(Integer productId);

    AttributeValue getById(Integer id);

    List<AttributeValue> findByIds(List<Integer> ids);

    //    @Cacheable(value = "getByTitleAndAttribute", unless = "#result == null")
    AttributeValue getByTitleAndAttribute(Integer version, String title, Integer attributeId);

    List<AttributeValue> findByAttributeId(Integer productId, Integer attributeId);

    List<AttributeValue> findByProductId(Integer productId);

    List<AttributeValue> findByProductId(Integer productId, boolean ignoreVersion);

    List<AttributeValue> findByAttId(Integer attributeId);

    int countByAttId(Integer attributeId);

    List<AttributeValue> findByAttributeId(int attributeId);

    List<AttributeValue> getValueListBySku(String sku);
}
