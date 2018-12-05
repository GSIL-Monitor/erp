package com.stosz.olderp.ext.service;

import com.stosz.olderp.ext.model.ErpOrderShipping;

import java.util.List;

/**
 * @auther carter
 * create time    2017-12-03
 */
public interface IErpOrderShippingService {
    String url = "/admin/remote/IErpOrderShippingService";

    List<ErpOrderShipping> findByOrderId(Integer idOrder_olderp);

    List<ErpOrderShipping> findByOrderIds(List<Integer> idOrders);
}
