package com.stosz.store.mapper;


import com.stosz.store.ext.model.Stock;
import com.stosz.store.mapper.builder.StockBuilder;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author:yangqinghui
 * @Description:StockMapper
 * @Created Time:2017/11/22 19:38
 * @Update Time:
 */
@Repository
public interface StockMapper {

    //-----------------------------------基础数据增删改查------------------------------------//
    @InsertProvider(type = StockBuilder.class, method = "insert")
    int insert(Stock stock);

    @DeleteProvider(type = StockBuilder.class, method = "delete")
    int delete(Integer id);

    @UpdateProvider(type = StockBuilder.class, method = "update")
    int update(Stock stock);

    @SelectProvider(type = StockBuilder.class, method = "getById")
    Stock getById(Integer id);

    @SelectProvider(type = StockBuilder.class, method = "find")
    Stock find(Stock stock);

    @SelectProvider(type = StockBuilder.class, method = "count")
    int count(Stock stock);

    //----------------------------------自定义逻辑------------------------------------//
    @SelectProvider(type = StockBuilder.class, method = "query")
    List<Stock> queryStock(Stock stock);

    @UpdateProvider(type = StockBuilder.class, method = "updateVersion")
    int updateStock(Stock stock);

    @Select("select sum(usable_qty) from stock where wms_id<>#{wmsId} and dept_id=#{deptId} and sku=#{sku}")
    Integer getOtherQty(@Param("wmsId") Integer wmsId, @Param("deptId") Integer deptId, @Param("sku") String sku);
}
