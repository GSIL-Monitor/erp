package com.stosz.admin.mapper.olderp;

import com.stosz.olderp.ext.model.OldErpStock;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 * 老erp库存信息的mapper
 *
 * @author xiongchenyang
 * @version [1.0 , 2017/9/8]
 */
@Repository
public interface OldErpStockMapper {

    @Select("select wp.id_product as product_id, wp.id_department as department_id,w.id_zone as zone_id, SUM(wp.quantity) as stock from erp_warehouse_product wp LEFT JOIN erp_warehouse w ON wp.id_warehouse = w.id_warehouse where wp.id_department !=0 AND w.id_zone is not NULL GROUP BY wp.id_product,wp.id_department, w.id_zone ORDER BY w.id_zone limit #{limit} offset #{start}")
    List<OldErpStock> findByLimit(@Param("limit") Integer limit, @Param("start") Integer start);

    @Select("select wp.id_product as product_id, wp.id_department as department_id,w.id_zone as zone_id, SUM(wp.quantity) as stock from erp_warehouse_product wp LEFT JOIN erp_warehouse w ON wp.id_warehouse = w.id_warehouse where wp.id_department !=0 AND w.id_zone is not NULL GROUP BY wp.id_product,wp.id_department, w.id_zone ORDER BY w.id_zone limit #{limit} offset #{start}")
    List<OldErpStock> findByDate(Date startTime, Date endTime);

    @SelectProvider(type = OldErpStockBuilder.class, method = "findOrderDateByParam")
    List<OldErpStock> findOrderDateByParam(@Param("param") OldErpStock param);

    @Select("select count(1) from erp_order")
    int countOrderAt();

    @SelectProvider(type = OldErpStockBuilder.class, method = "findStockByParam")
    List<OldErpStock> findStockByParam(@Param("param") OldErpStock oldErpStock);

    @Select("select sum(quantity) from erp_warehouse_product wp left join erp_warehouse w on wp.id_warehouse = w.id_warehouse where wp.id_product = #{productId} and wp.id_department = #{departmentId} and w.id_zone  = #{zoneId}")
    Integer getByUnique(@Param("productId") Integer productId, @Param("departmentId") Integer departmentId, @Param("zoneId") Integer zoneId);

    @Select("select _this.created_at as last_order_at from erp_order _this left join erp_order_item oi on oi.id_order = _this.id_order where oi.id_product= #{productId} and _this.id_department = #{departmentId} and _this.id_zone = #{zoneId} order by _this.created_at desc limit 1")
    LocalDateTime getOrderDateByUnique(@Param("productId") Integer productId, @Param("departmentId") Integer departmentId, @Param("zoneId") Integer zoneId);

    @SelectProvider(type = OldErpStockBuilder.class, method = "findHasStock")
    List<OldErpStock> findHasStock(@Param("param") OldErpStock param);

    @SelectProvider(type = OldErpStockBuilder.class, method = "findHasLastOrderAtByProductId")
    List<OldErpStock> findHasLastOrderAtByProductId(@Param("productId") Integer productId);


    @Select("select count(1) from erp_warehouse_product where id_product = #{productId} and(quantity != 0 or road_num !=0 or qty_preout!=0 )")
    Integer  getHasStockCountByProductId(@Param("productId") Integer productId);

    @Select("select count(1) from erp_order_item where id_product = #{productId}")
    Integer getHasOrderCountByProductId(@Param("productId") Integer productId);
}
