package com.stosz.store.mapper;

import com.stosz.fsm.IFsmDao;
import com.stosz.store.ext.dto.response.MatchPackRes;
import com.stosz.store.ext.model.TransferItem;
import com.stosz.store.ext.model.TransitStock;
import com.stosz.store.mapper.builder.TransitStockBuilder;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @Author:ChenShifeng
 * @Description:TransitStockMapper
 * @Created Time:2017/11/23 14:23
 * @Update Time:
 */
@Repository
public interface TransitStockMapper extends IFsmDao<TransitStock> {
    //-----------------------------------基础数据增删改查------------------------------------//
    @InsertProvider(type = TransitStockBuilder.class, method = "insert")
    int insert(TransitStock transitStock);

    @DeleteProvider(type = TransitStockBuilder.class, method = "delete")
    int delete(Integer id);

    @UpdateProvider(type = TransitStockBuilder.class, method = "updateAll")
    int update(TransitStock transitStock);

    @Update("UPDATE transit_stock SET tracking_no_old = #{trackingNoOld} WHERE rma_id = #{rmaId} ")
    int updateByRmaId(TransitStock stock);

    @UpdateProvider(type = TransitStockBuilder.class, method = "updateTransitStateById")
    int updateTransitStateById(TransitStock transitStock);

    @SelectProvider(type = TransitStockBuilder.class, method = "count")
    int count(TransitStock transitStock);

    @Select("SELECT count(1) FROM transit_stock WHERE tracking_no_old=#{track} AND state = #{state}")
    int countByState(@Param("track") String track, @Param("state") String state);

    @Select("SELECT count(1) FROM transit_stock WHERE order_id_new=#{OrderNew} AND tracking_no_new=#{trackNew}")
    int countByOrderNew(@Param("OrderNew") String OrderNew, @Param("trackNew") String trackNew);

    @SelectProvider(type = TransitStockBuilder.class, method = "getById")
    TransitStock getById(Integer id);

    @Select("SELECT * FROM transit_stock WHERE tracking_no_old=#{trackNo}")
    TransitStock getByTrackNoOld(String trackNo);

    @Select("SELECT * FROM transit_stock WHERE order_id_new=#{ordersNew}")
    TransitStock getByOrdersNew(String ordersNew);

    @SelectProvider(type = TransitStockBuilder.class, method = "find")
    List<TransitStock> find(TransitStock transitStock);

    @SelectProvider(type = TransitStockBuilder.class, method = "getTransitList")
    List<TransitStock> getTransitList(TransitStock transitStock);

    @Select("select * from transit_stock ts join transfer_item tf on ts.tracking_no_old=tf.tracking_no where tf.tran_id=#{transferId} ")
    List<TransitStock> getTransitByTransferId(Integer transferId);

    @Select("SELECT id,wms_id,stock_name,tracking_no_old,order_id_old,qtys,skus,storage_local FROM (SELECT count(it.id) AS skuCount,GROUP_CONCAT(it.sku) AS skus,GROUP_CONCAT(it.qty) AS qtys,t.* FROM transit_stock t LEFT JOIN transit_item it ON t.id = it.transit_id WHERE t.state = #{state} AND t.wms_id = #{wmsId} AND t.dept_id = #{deptId} GROUP BY t.id HAVING	skuCount = #{skuCount} ORDER BY	create_at) a")
    List<MatchPackRes> getMatchPackList(@Param("skuCount") Integer skuCount, @Param("state") String state, @Param("wmsId") Integer wmsId, @Param("deptId") Integer deptId);

    @UpdateProvider(type = TransitStockBuilder.class, method = "updateState")
    Integer updateState(@Param("id") Integer id, @Param("state") String state, @Param("stateTime") LocalDateTime stateTime);

    @Select("<script>"
            + "select count(1) from transit_stock "
            + " where state=#{state} "
            + " and tracking_no_old in "
            + " <foreach collection =\"list\" item=\"item\" index= \"index\" open=\"(\" close=\")\" separator =\",\"> "
            + " #{item.trackingNo}"
            + "</foreach> "
            + "</script>")
    int countByInStock(@Param("list") List<TransferItem> itemList,@Param("state") String state);

     @Delete("delete from transit_stock  where rma_id=#{rmaId} and state=#{state}")
    int delByRmaId(@Param("rmaId") Integer rmaId,@Param("state") String state);
}
