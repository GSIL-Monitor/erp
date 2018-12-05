package com.stosz.olderp.ext.service;

import com.stosz.olderp.ext.model.OldErpCurrency;

import java.util.List;

/**
 * @author carter
 * @version [1.0 , 2017/9/11]
 */
public interface IOldErpCurrencyService {
    String url = "/admin/remote/IOldErpCurrencyService";

    List<OldErpCurrency> findAll();

    void insert(OldErpCurrency OldErpCurrency);

    void update(OldErpCurrency OldErpCurrency);

    OldErpCurrency getByUnique(String title);

    OldErpCurrency getById(Integer id);

    OldErpCurrency getByCode(String code);

    void deleteById(Integer id);

}
