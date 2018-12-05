package com.stosz.olderp.ext.service;

import com.stosz.olderp.ext.model.OldErpCheckProduct;

import java.util.List;

/**
 * 查重新品的service
 *
 * @author xiongchenyang
 * @version [1.0 , 2017/10/11]
 */
public interface IOldErpCheckProductService {
    String url = "/admin/remote/IOldErpCheckProductService";
    List<OldErpCheckProduct> findAll();
}
