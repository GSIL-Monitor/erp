package com.stosz.olderp.ext.service;

import com.stosz.olderp.ext.model.OldErpAttributeValue;

import java.util.List;

/**
 * 老erp的属性值service的接口
 *
 * @author xiongchenyang
 * @version [1.0 , 2017/8/31]
 */
public interface IOldErpAttributeValueService {
    String url = "/admin/remote/IOldErpAttributeValueService";
    void insert(OldErpAttributeValue oldErpAttributeValue);

    void delete(Integer id);

    void update(OldErpAttributeValue oldErpAttributeValue);

    OldErpAttributeValue getById(Integer id);

    int count();

    List<OldErpAttributeValue> findByLimit(Integer limit, Integer start);

    OldErpAttributeValue getByUnique(Integer productId, Integer attributeId, String title);

    List<OldErpAttributeValue> findByAttributeId(Integer productId, Integer attributeId);

    /**
     * 查询查重产品属性值
     */
    List<OldErpAttributeValue> findCheckByAttributeId(Integer productId, Integer attributeId);

    /**
     * 根据id组合获取属性值
     */
    List<OldErpAttributeValue> findByAttributeValue(String attributeValues);


}  
