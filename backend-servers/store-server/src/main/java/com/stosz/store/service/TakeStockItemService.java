package com.stosz.store.service;

import com.stosz.store.ext.dto.request.AddTakeStockReq;
import com.stosz.store.ext.model.TakeStockItem;
import com.stosz.store.mapper.TakeStockItemMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Author:ChenShifeng
 * @Description: 盘点 明细
 * @Created Time:2017/11/23 15:49
 * @Update Time:
 */
@Service
public class TakeStockItemService {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Resource
    private TakeStockItemMapper mapper;

    /**
     * 添加
     *
     * @param stock
     */
    public Integer insert(TakeStockItem stock) {
        return mapper.insert(stock);
    }

    public void batchInsert(AddTakeStockReq param){
        mapper.batchInsert(param);
    }
}
