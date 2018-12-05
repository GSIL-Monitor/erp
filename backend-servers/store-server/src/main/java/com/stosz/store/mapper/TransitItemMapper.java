package com.stosz.store.mapper;

import com.stosz.store.ext.model.TransitItem;
import com.stosz.store.ext.model.TransitStock;
import com.stosz.store.mapper.builder.TransitItemBuilder;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author:ChenShifeng
 * @Description:TransitItemMapper
 * @Created Time:2017/11/23 14:23
 * @Update Time:
 */
@Repository
public interface TransitItemMapper {
    //-----------------------------------基础数据增删改查------------------------------------//
    @InsertProvider(type = TransitItemBuilder.class, method = "insert")
    int insert(TransitItem transit);

    @Insert("<script>"
            + "insert into transit_item(transit_id,order_id_old,tracking_no_old,dept_id,spu,sku,attr_title_array,"
            + " qty,single_amount,total_amount,product_title,foreign_title,foreign_total_amount,create_at,rma_id,"
            + " dept_name) "
            + " values "
            + " <foreach collection =\"list\" item=\"o\" index= \"index\" separator =\",\"> "
            + "(#{o.transitId},#{o.orderIdOld},#{o.trackingNoOld},#{o.deptId},#{o.spu},#{o.sku},#{o.attrTitleArray},"
            + "#{o.qty},#{o.singleAmount},#{o.totalAmount},#{o.productTitle},#{o.foreignTitle},#{o.foreignTotalAmount},"
            + "now(),#{o.rmaId},#{o.deptName})"
            + "</foreach> "
            + "</script>")
    Integer batchInsert(List<TransitItem> saveItemList);

    @DeleteProvider(type = TransitItemBuilder.class, method = "delete")
    int delete(Integer id);

    @UpdateProvider(type = TransitItemBuilder.class, method = "update")
    int update(TransitItem transit);

    @Update("UPDATE transit_item SET tracking_no_old = #{trackingNoOld} WHERE rma_id = #{rmaId} ")
    int updateTrack(TransitStock stock);

    @SelectProvider(type = TransitItemBuilder.class, method = "getById")
    TransitItem getById(Integer id);

    @SelectProvider(type = TransitItemBuilder.class, method = "find")
    TransitItem find(TransitItem transit);

    /**
     * 根据运单查询sku
     *
     * @param
     */
    @Select("select * from transit_item  where tracking_no_old = #{trackingNoOld} ")
    List<TransitItem> getByTrackingNoOld(@Param("trackingNoOld") String trackingNoOld);

    @Select("select * from transit_item  where transit_id = #{transitId} ")
    List<TransitItem> getSkusByTransitId(@Param("transitId") Integer transitId);

    @Select("select * from transit_item ts join transfer_item tf on ts.tracking_no_old=tf.tracking_no where tf.tran_id=#{transferId} ")
    List<TransitItem> getSkusByTransferId(Integer transferId);

    @Update("<script>" +
            "    update transit_item \n" +
            "    set  dept_id = #{deptId},dept_name=#{deptName}\n" +
            "    where id in\n" +
            "    <foreach collection=\"itemList\" index=\"index\" item=\"item\" \n" +
            "        separator=\",\" open=\"(\" close=\")\">\n" +
            "        #{item.id}\n" +
            "    </foreach>" +
            "</script>")
    int updateDeptBatch(@Param("itemList")List<TransitItem> itemList,@Param("deptId") Integer deptId, @Param("deptName")String deptName);

}
