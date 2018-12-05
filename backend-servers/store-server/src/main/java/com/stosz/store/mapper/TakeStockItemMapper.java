package com.stosz.store.mapper;

import com.stosz.store.ext.dto.request.AddTakeStockReq;
import com.stosz.store.ext.model.TakeStockItem;
import com.stosz.store.mapper.builder.TakeStockItemBuilder;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author:ChenShifeng
 * @Description:TakeStockMapper
 * @Created Time:2017/11/23 14:23
 * @Update Time:
 */
@Repository
public interface TakeStockItemMapper {
    //-----------------------------------基础数据增删改查------------------------------------//
    @InsertProvider(type = TakeStockItemBuilder.class, method = "insert")
    int insert(TakeStockItem item);

    @DeleteProvider(type = TakeStockItemBuilder.class, method = "delete")
    int delete(Integer id);

    @UpdateProvider(type = TakeStockItemBuilder.class, method = "update")
    int update(TakeStockItem item);

    @InsertProvider(type = TakeStockItemBuilder.class, method = "insertBat")
    void batchInsert(AddTakeStockReq param);
}
