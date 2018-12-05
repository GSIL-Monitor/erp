package com.stosz.olderp.ext.service;

import com.stosz.olderp.ext.model.ErpOrderInfo;

import java.util.List;

/**
 * @auther carter
 * create time    2017-12-03
 */
public interface IErpOrderInfoService {
    String url = "/admin/remote/IErpOrderInfoService";

    ErpOrderInfo findById(Long aLong);

    List<ErpOrderInfo> findByIds(List<Integer> ids);

    List<String> findForwardStatus(List<Integer> ids);

    List<Integer> findSettlements();

    int findRecords(Integer idOrder);
}
