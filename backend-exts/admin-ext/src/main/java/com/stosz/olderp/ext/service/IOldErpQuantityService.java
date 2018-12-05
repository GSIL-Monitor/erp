package com.stosz.olderp.ext.service;

//import com.stosz.product.ext.model.Product;

/**
 * 老erp库存信息同步的service
 *
 * @author xiongchenyang
 * @version [1.0 , 2017/9/6]
 */
public interface IOldErpQuantityService {

    String url = "/admin/remote/IOldErpQuantityService";

//    List<Product> findByLimit(Integer limit, Integer start);
//
//    List<Product> findByDate(Date startTime, Date endTime);

    int count();

}
