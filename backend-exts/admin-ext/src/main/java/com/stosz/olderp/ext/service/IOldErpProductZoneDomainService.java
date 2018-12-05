package com.stosz.olderp.ext.service;

import com.stosz.olderp.ext.model.OldErpProductZoneDomain;

import java.util.List;

/**
 * @author xiongchenyang
 * @version [1.0 , 2017/9/19]
 */
public interface IOldErpProductZoneDomainService {
    String url = "/admin/remote/IOldErpProductZoneDomainService";
    OldErpProductZoneDomain getByUnique(Integer productId, Integer departmentId, Integer zoneId);


    List<OldErpProductZoneDomain> findByLimit(Integer limit, Integer start);

    int count();
}  
