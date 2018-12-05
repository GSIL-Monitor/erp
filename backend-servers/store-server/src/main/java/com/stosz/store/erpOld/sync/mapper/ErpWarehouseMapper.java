package com.stosz.store.erpOld.sync.mapper;


import com.stosz.old.erp.ext.model.OldErpWarehouse;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author:ChenShifeng
 * @Description:ErpWarehouseMapper
 * @Created Time:2017/11/23 14:23
 * @Update Time:
 */
@Repository
public interface ErpWarehouseMapper {
    //-----------------------------------基础数据增删改查------------------------------------//
    @InsertProvider(type = ErpWarehouseBuilder.class, method = "insert")
    int insert(OldErpWarehouse warehouse);

    @DeleteProvider(type = ErpWarehouseBuilder.class, method = "delete")
    int delete(Integer id);

    @UpdateProvider(type = ErpWarehouseBuilder.class, method = "update")
    int update(OldErpWarehouse warehouse);

    @Select("select w.*,z.title AS zoneName from erp_warehouse w LEFT JOIN erp_zone z on w.id_zone=z.id_zone limit #{start},#{limit}")
    List<OldErpWarehouse> findLimit(@Param("start") int start, @Param("limit") int fetch_count);

    @SelectProvider(type = ErpWarehouseBuilder.class, method = "count")
    int getOldMaxId();
}
