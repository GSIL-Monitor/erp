package com.stosz.product.ext.service;

import com.stosz.plat.common.RestResult;
import com.stosz.product.ext.model.Zone;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @auther carter
 * create time    2017-11-29
 */
public interface IZoneService {
    String url =  "/product/remote/IZoneService";

    @Transactional(value = "pcTxManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    void insert(Zone zone);

    void insertOld(Zone zone);

    void insertList(List<Zone> zoneList);

    @Transactional(value = "pcTxManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    void delete(Integer id);

    @Transactional(value = "pcTxManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    void update(Zone zone);

    //	@Cacheable(value = "getZoneById", unless = "#result == null")
    Zone getCacheById(Integer id);

    Zone getById(Integer id);

    Map<Integer,Zone> findByIds(List<Integer> ids);

    List<Zone> queryByIds(List<Integer> ids);

    RestResult titleLike(String title);

    RestResult find(Zone zone);

    List<Zone> findStintZone();

    List<Zone> findByCountryId(Integer countryId);

    List<Zone> findByParentId(Integer parentId);

    Zone load(Integer id);

    Zone getByCode(String code);

    Zone getByUnique(Integer countryId, String title);

    List<Zone> findAll();

    /**
     * 根据区域ID查询国家二字码
     */
    String getCountryCodeById(Integer id);

}
