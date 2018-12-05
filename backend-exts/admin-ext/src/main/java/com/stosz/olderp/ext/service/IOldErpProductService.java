package com.stosz.olderp.ext.service;

import com.stosz.olderp.ext.model.OldErpProduct;

import java.util.List;

/**
 * 老erp 产品的service接口
 * @author xiongchenyang
 * @version [1.0 , 2017/8/29]
 */
public interface IOldErpProductService {
    String url = "/admin/remote/IOldErpProductService";
    void insert(OldErpProduct oldErpProduct);

    void updateStatus(OldErpProduct oldErpProduct);

    void update(OldErpProduct oldErpProduct);

    OldErpProduct getById(Integer id);

    List<OldErpProduct> findByLimit(Integer limit, Integer start);

    List<Integer> findAllId();

    Integer count();

    Integer getTotalStockById(Integer productId);

    List<OldErpProduct> findCheckById(Integer productId);
}
