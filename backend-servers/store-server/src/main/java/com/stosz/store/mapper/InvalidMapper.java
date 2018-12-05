package com.stosz.store.mapper;

import com.stosz.fsm.IFsmDao;
import com.stosz.store.ext.dto.request.SearchInvalidReq;
import com.stosz.store.ext.model.Invalid;
import com.stosz.store.ext.model.TransitItem;
import com.stosz.store.ext.model.TransitStock;
import com.stosz.store.mapper.builder.InvalidBuilder;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @Author:ChenShifeng
 * @Description:InvalidMapper
 * @Created Time:2017/11/23 14:23
 * @Update Time:
 */
@Repository
public interface InvalidMapper extends IFsmDao<Invalid> {
    //-----------------------------------基础数据增删改查------------------------------------//
    @InsertProvider(type = InvalidBuilder.class, method = "insert")
    int insert(Invalid invalid);

    @DeleteProvider(type = InvalidBuilder.class, method = "delete")
    int delete(Integer id);

    @UpdateProvider(type = InvalidBuilder.class, method = "update")
    int update(Invalid invalid);

    @Update( "update invalid SET qty=#{qty} where id=#{id}")
    int updateQty(@Param("id") Integer id, @Param("qty")Integer qty);

    @SelectProvider(type = InvalidBuilder.class, method = "countSearch")
    int count(SearchInvalidReq param);

    @SelectProvider(type = InvalidBuilder.class, method = "getById")
    Invalid getById(Integer id);

    @SelectProvider(type = InvalidBuilder.class, method = "getSearch")
    List<Invalid> find(SearchInvalidReq param);

    @Select("SELECT t.*,DATEDIFF(NOW(),t.create_at) AS storageAge FROM transit_stock t JOIN invalid_item it on t.tracking_no_old=it.tracking_no_old WHERE it.invalid_id=#{id}")
    List<TransitStock> getTransitListByInvalidId(Integer id);

    @Select("SELECT t.* FROM transit_item t JOIN invalid_item it on t.tracking_no_old=it.tracking_no_old WHERE it.invalid_id=#{id}")
    List<TransitItem> getTransitItemListByInvalidId(Integer id);

    @UpdateProvider(type = InvalidBuilder.class, method = "updateState")
    Integer updateState(@Param("id") Integer id, @Param("state") String state, @Param("stateTime") LocalDateTime stateTime);
}
