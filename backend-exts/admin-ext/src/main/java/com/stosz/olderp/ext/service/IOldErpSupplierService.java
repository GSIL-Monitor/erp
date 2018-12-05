package com.stosz.olderp.ext.service;

import com.stosz.olderp.ext.model.OldErpSupplier;

import java.util.List;

/**
 * @author xiongchenyang
 * @version [1.0 , 2017/11/27]
 */
public interface IOldErpSupplierService {

    String url = "/admin/remote/IOldErpSupplierService";

    List<OldErpSupplier> findAll();
}  
