package com.stosz.olderp.ext.service;

import com.stosz.olderp.ext.model.ErpDomain;

import java.util.List;

/**
 * @auther carter
 * create time    2017-12-03
 */
public interface IErpDomainService {
    String url = "/admin/remote/IErpDomainService";

    ErpDomain findById(Long aLong);

    List<ErpDomain> findByIds(List<Integer> ids);
}
