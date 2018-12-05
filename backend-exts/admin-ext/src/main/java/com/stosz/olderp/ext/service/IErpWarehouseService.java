package com.stosz.olderp.ext.service;

import com.stosz.olderp.ext.model.ErpWarehouse;

import java.util.List;


/**
 * @auther carter
 * create time    2017-12-03
 */
public interface IErpWarehouseService {
    String url = "/admin/remote/IErpWarehouseService";

    ErpWarehouse findById(Integer idWarehouse);

    List<ErpWarehouse> findByIds(List<Integer> ids);

    List<ErpWarehouse> findAll();

}
