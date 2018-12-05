package com.stosz.olderp.ext.service;

import com.stosz.olderp.ext.model.OldErpCountry;

import java.util.List;

/**
 * @author xiongchenyang
 * @version [1.0 , 2017/9/11]
 */
public interface IOldErpCountryService {
    String url = "/admin/remote/IOldErpCountryService";
    List<OldErpCountry> findAll();

    void insert(OldErpCountry oldErpCountry);

    void update(OldErpCountry oldErpCountry);

    OldErpCountry getByUnique(String title);

    void deleteById(Integer id);

    OldErpCountry getById(Integer id);
}
