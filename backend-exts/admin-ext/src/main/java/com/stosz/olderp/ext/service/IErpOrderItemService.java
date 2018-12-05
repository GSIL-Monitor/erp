package com.stosz.olderp.ext.service;

import com.stosz.olderp.ext.model.ErpOrderItem;

import java.util.List;

/**
 * @auther carter
 * create time    2017-12-03
 */
public interface IErpOrderItemService {
    String url = "/admin/remote/IErpOrderItemService";

    List<ErpOrderItem> findByParam(Integer idOrder_olderp);

    List<ErpOrderItem> findByIds(List<Integer> ids);
}
