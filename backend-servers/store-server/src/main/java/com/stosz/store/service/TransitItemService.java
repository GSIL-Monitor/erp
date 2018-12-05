package com.stosz.store.service;


import com.stosz.store.ext.model.TransitItem;
import com.stosz.store.ext.model.TransitStock;
import com.stosz.store.mapper.TransitItemMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author:ChenShifeng
 * @Description: 运单关联sku
 * @Created Time:2017/11/23 15:49
 * @Update Time:
 */
@Service
public class TransitItemService {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    @Resource
    private TransitItemMapper mapper;


    /**
     * 添加
     *
     * @param stock
     */
    public Integer insert(TransitItem stock) {
        return mapper.insert(stock);
    }


    /**
     * 根据运单查询sku
     *
     * @param
     */
    public List<TransitItem> getSkusByTrackingNoOld(String trackingNo) {
        return mapper.getByTrackingNoOld(trackingNo);
    }


    public List<TransitItem> getSkusByTransitId(Integer transitId) {
        return mapper.getSkusByTransitId(transitId);
    }

    public Integer insertBat(List<TransitItem> saveItemList) {
        return mapper.batchInsert(saveItemList);
    }

    public void updateTrack(TransitStock stock) {
        mapper.updateTrack(stock);
    }

    /**
     * 根据调拨单id关联查询sku
     *
     * @param
     */
    public List<TransitItem> getSkusByTransferId(Integer transferId) {
        return mapper.getSkusByTransferId(transferId);
    }

    /**
     * 批量修改部门id 、name
     *
     * @param itemList
     */
    public int updateDeptBatch(List<TransitItem> itemList, Integer deptId, String deptName) {
        return mapper.updateDeptBatch(itemList, deptId, deptName);
    }
}
