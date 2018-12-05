package com.stosz.admin.mapper.olderp;

import com.stosz.olderp.ext.model.ErpZoneWarehouse;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ErpZoneWarehouseMapper {

    /**
     * 查找老ERP区域仓库，通过时间增量查询
     * @param offset offset 分段查询起始位置
     * @param limit 分段查询条数
     * @param beginTime 指定的开始时间
     * @param endTime 指定的结束时间
     * @return
     */
    @Select("select zw_id,id_warehouse,id_zone,level,status,create_time,create_user_id  " +
            " from erp_zone_warehouse " +
            " where create_time > #{beginTime} " +
            " and create_time <= #{endTime} " +
            " order by id_warehouse asc " +
            " limit #{limit} offset #{offset}")
    List<ErpZoneWarehouse> findErpWarehouse(@Param("offset") Integer offset, @Param("limit") Integer limit,
                                            @Param("beginTime") LocalDateTime beginTime, @Param("endTime") LocalDateTime endTime);




    /**
     * 从指定的开始时间到指定的结束时间的记录数
     * @param beginTime
     * @param endTime
     * @return
     */
    @Select(" select count(1) from erp_zone_warehouse " +
            " where create_time > #{beginTime} " +
            " and create_time <= #{endTime}")
    Integer countErpWarehouse(@Param("beginTime") LocalDateTime beginTime, @Param("endTime") LocalDateTime endTime);
}
