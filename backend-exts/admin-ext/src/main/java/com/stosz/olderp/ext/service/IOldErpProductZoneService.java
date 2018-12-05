package com.stosz.olderp.ext.service;

import com.stosz.olderp.ext.model.OldErpProductZone;

import java.util.Date;
import java.util.List;

/**
 * 产品区域表的service接口
 *
 * @author xiongchenyang
 * @version [1.0 , 2017/9/5]
 */
public interface IOldErpProductZoneService {

    String url = "/admin/remote/IOldErpProductZoneService";

    void insert(OldErpProductZone oldErpProductZone);

    void delete(Integer id);

    void update(OldErpProductZone oldErpProductZone);

    OldErpProductZone getById(Integer id);

    Integer count();

    List<OldErpProductZone> findByProductId(Integer productId);

    List<OldErpProductZone> getByUnique(Integer productId, Integer departmentId, Integer zoneId);

    List<OldErpProductZone> findByLimit(Integer limit, Integer start);

    List<OldErpProductZone> findByDate(Date startTime, Date endTime);
}  
