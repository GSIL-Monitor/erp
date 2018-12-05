package com.stosz.store.service;

import com.stosz.store.ext.model.TransferItem;
import com.stosz.store.mapper.TransferItemMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author:yangqinghui
 * @Description:
 * @Created Time:2017/11/23 17:56
 * @Update Time:
 */
@Service
public class TransferItemService {

    @Resource
    private TransferItemMapper transferItemMapper;

    /**
     * 添加调拨单
     * @param transferItem
     */
    public void insert(TransferItem transferItem) {
        transferItemMapper.insert(transferItem);
    }

    /**
     * 根据Tranid查询所有sku
     * @param tranId
     */
    public List<TransferItem> findByTranId(Integer tranId) {
        return transferItemMapper.findByTranId(tranId);
    }

    /**
     * 根据Tranid  and trackingNO is null 查询所有sku
     * @param tranId
     */
    public List<TransferItem> findByTranIdAndNullTrack(Integer tranId) {
        return transferItemMapper.findByTranIdAndNullTrack(tranId);
    }

    /**
     * 根据sku  transferId 查询单条
     * @param sku
     */
    public TransferItem findBySkuAndTranId(Integer tranId, String sku) {
        return transferItemMapper.findBySkuAndTranId(tranId,sku);
    }

    public Integer update(TransferItem transferItem) {
        return transferItemMapper.update(transferItem);
    }

    public void delete(List<TransferItem> transfers) {
        int sub=0;
        for (TransferItem transferItem:transfers){
            int delete = transferItemMapper.delete(transferItem.getId());
            sub=sub+delete;
        }
        Assert.isTrue(sub==transfers.size(),"调拨明细未清除完整");
    }
    /**
     * 根据track查询所有sku
     * @param trackingNoOld
     */
    public List<TransferItem> findByTrack(String trackingNoOld) {
        return transferItemMapper.findByTrack(trackingNoOld);
    }

    public Integer insertBat(List<TransferItem> saveItemList) {
        return transferItemMapper.batchInsert(saveItemList);
    }


}
