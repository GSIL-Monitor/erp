package com.stosz.olderp.ext.service;

import com.stosz.olderp.ext.model.OldErpZone;

import java.util.List;

/**
 * @author xiongchenyang
 * @version [1.0 , 2017/9/12]
 */
public interface IOldErpZoneService {
    String url = "/admin/remote/IOldErpZoneService";
    void insert(OldErpZone oldErpZone);

    void update(OldErpZone oldErpZone);

    OldErpZone getById(Integer id);

    List<OldErpZone> findAll();

    void deleteByIdZone(Integer id);
}
