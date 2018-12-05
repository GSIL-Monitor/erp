package com.stosz.order.service;

import com.google.common.collect.Lists;
import com.stosz.order.ext.model.ZoneWarehousePriority;
import com.stosz.order.mapper.order.ZoneWarehousePriorityMapper;
import com.stosz.plat.common.RestResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * @auther carter
 * create time    2017-11-09
 */
@Service
public class ZoneWarehousePriorityService {

    private static final Logger logger = LoggerFactory.getLogger(ZoneWarehousePriorityService.class);

    @Autowired
    private ZoneWarehousePriorityMapper zoneWarehousePriorityMapper;


    public int insertBatch(List<ZoneWarehousePriority> list){
        return zoneWarehousePriorityMapper.insertBatch(list);
    }

    public RestResult find(ZoneWarehousePriority param){
        RestResult rs = new RestResult();
        int count = zoneWarehousePriorityMapper.findCountByParam(param);
        rs.setTotal(count);
        if (count == 0) {
            return rs;
        }
        List<ZoneWarehousePriority> lst = zoneWarehousePriorityMapper.findByParam(param);
        rs.setItem(lst);
        rs.setDesc("区域仓库查询成功");
        return rs;
    }

    public List<ZoneWarehousePriority> findAll(){
        return zoneWarehousePriorityMapper.findAll();
    }


    public int add(ZoneWarehousePriority param){
        try{
            zoneWarehousePriorityMapper.insert(param);
         } catch (DuplicateKeyException e) {
            throw new IllegalArgumentException("区域仓库["+param.getZoneName()+ " " + param.getWarehouseName()+"]已存在");
          }
        logger.info("添加区域仓库成功: {}", param);
        return 1;
    }

    public int delete(Integer id){
        ZoneWarehousePriority zwp = zoneWarehousePriorityMapper.getById(id);
        Assert.notNull(zwp,"该行数据不存在");
        zoneWarehousePriorityMapper.delete(id);
        logger.info("添加区域仓库成功: {}", zwp);
        return 1;
    }

    public int udpate(ZoneWarehousePriority param){
        try{
            zoneWarehousePriorityMapper.update(param);
        } catch (DuplicateKeyException e) {
            throw new IllegalArgumentException("区域仓库["+param.getZoneName()+ " " + param.getWarehouseName()+"]已存在");
        }
        logger.info("修改区域仓库成功: {}", param);
        return 1;
    }

    /**
     *  根据区域编号和仓库编号查询
     * @param zoneId
     * @param warehouseId
     * @return
     */
    public ZoneWarehousePriority getByZoneIdAndWarehouseId(Integer zoneId, Integer warehouseId){
        return zoneWarehousePriorityMapper.getByZoneIdAndWarehouseId(zoneId,warehouseId);
    }

    public ZoneWarehousePriority getById(Integer id){
        return zoneWarehousePriorityMapper.getById(id);
    }

    /**
     * 通过区域id查询到仓库id
     * @param zoneId 区域id
     * @return
     */
    public List<ZoneWarehousePriority> findByZoneId(Integer zoneId) {

      return Optional.of(zoneWarehousePriorityMapper.findByZoneId(zoneId)).orElse(Lists.newLinkedList());
    }

    /**
     * 找到wmsid， spu  可以允许配货的区域；
     * @param wmsId  仓库id
     * @param spu   spu
     * @return
     */
    public Set<Integer> findZoneSetByWarehouseIdAndSpu(Integer wmsId, String spu) {
        return zoneWarehousePriorityMapper.findZoneSetByWarehouseIdAndSpu(wmsId,spu);
    }
}
