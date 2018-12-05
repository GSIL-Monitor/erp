package com.stosz.olderp.ext.service;

import com.stosz.olderp.ext.model.OldErpOrder;
import com.stosz.olderp.ext.model.OldErpOrderLink;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @auther carter
 * create time    2017-11-24
 */
public interface IOldErpOrderService {

    String url = "/admin/remote/IOldErpOrderService";
    List<OldErpOrderLink> findOldErpOrderLinkInc(Integer offset, Integer limit, LocalDateTime beginTime, LocalDateTime endTime);

    Integer countOldErpOrderLinkInc(LocalDateTime beginTime, LocalDateTime endTime);

    List<OldErpOrderLink> findOldErpOrderCustomer(Integer offset, Integer limit);

    Integer countOldErpOrderCustomer();

    List<OldErpOrderLink> findRiskOldErpOrderLinkByTel(String tel, Integer offset, Integer limit);

    List<OldErpOrder> fetchByIdRegion(int idMin, int idMax);

    int getMaxId();


    List<OldErpOrder> findOldErpOrder(Integer offset, Integer limit, LocalDateTime beginTime, LocalDateTime endTime);

    Integer countOldErpOrder(LocalDateTime beginTime, LocalDateTime endTime);
}
