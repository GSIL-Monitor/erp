package com.stosz.store.mapper;

import com.stosz.fsm.IFsmDao;
import com.stosz.store.ext.dto.request.SearchTakeStockReq;
import com.stosz.store.ext.model.TakeStock;
import com.stosz.store.ext.model.TransitItem;
import com.stosz.store.ext.model.TransitStock;
import com.stosz.store.mapper.builder.TakeStockBuilder;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @Author:ChenShifeng
 * @Description:TakeStockMapper
 * @Created Time:2017/11/23 14:23
 * @Update Time:
 */
@Repository
public interface TakeStockMapper extends IFsmDao<TakeStock> {
    //-----------------------------------基础数据增删改查------------------------------------//
    @InsertProvider(type = TakeStockBuilder.class, method = "insert")
    int insert(TakeStock takeStock);

    @DeleteProvider(type = TakeStockBuilder.class, method = "delete")
    int delete(Integer id);

    @UpdateProvider(type = TakeStockBuilder.class, method = "update")
    int update(TakeStock takeStock);

    @SelectProvider(type = TakeStockBuilder.class, method = "countSearch")
    int count(SearchTakeStockReq param);

    @SelectProvider(type = TakeStockBuilder.class, method = "getById")
    TakeStock getById(Integer id);

    @SelectProvider(type = TakeStockBuilder.class, method = "getSearch")
    List<TakeStock> find(SearchTakeStockReq param);

    @Select("SELECT t.*,DATEDIFF(NOW(),t.create_at) AS storageAge  FROM transit_stock t JOIN take_stock_item it on t.tracking_no_old=it.tracking_no_old WHERE it.take_stock_id=#{id}")
    List<TransitStock> getTransitListById(@Param("id") Integer id);

    @Select("SELECT t.* FROM transit_item t JOIN take_stock_item it on t.tracking_no_old=it.tracking_no_old WHERE it.take_stock_id=#{id}")
    List<TransitItem> getTransitItemListByTakeStockId(@Param("id") Integer takeStockId);

    @UpdateProvider(type = TakeStockBuilder.class, method = "updateState")
    Integer updateState(@Param("id") Integer id, @Param("state") String state, @Param("stateTime") LocalDateTime stateTime);
}
