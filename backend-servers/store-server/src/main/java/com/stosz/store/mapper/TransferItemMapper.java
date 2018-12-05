package com.stosz.store.mapper;

import com.stosz.store.ext.model.TransferItem;
import com.stosz.store.mapper.builder.TransferItemBuilder;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author:yangqinghui
 * @Description:TransferItemMapper
 * @Created Time:2017/11/28 16:53
 * @Update Time:2017/11/28 16:53
 */
@Repository
public interface TransferItemMapper {

    //-----------------------------------基础数据增删改查------------------------------------//
    @InsertProvider(type = TransferItemBuilder.class, method = "insert")
    int insert(TransferItem transferItem);

    @DeleteProvider(type = TransferItemBuilder.class, method = "delete")
    int delete(Integer id);

    @UpdateProvider(type = TransferItemBuilder.class, method = "update")
    int update(TransferItem transferItem);

    @SelectProvider(type = TransferItemBuilder.class, method = "getById")
    TransferItem getById(Integer id);

    //----------------------------------自定义业务逻辑------------------------------------//
    @SelectProvider(type = TransferItemBuilder.class, method = "findByTranId")
    List<TransferItem> findByTranId(Integer transId);

    @SelectProvider(type = TransferItemBuilder.class, method = "findByTranIdAndNullTrack")
    List<TransferItem> findByTranIdAndNullTrack(Integer transId);

    @Select("select * from transfer_item where tran_id=#{tranId} and sku=#{sku}")
    TransferItem findBySkuAndTranId(@Param("tranId") Integer tranId, @Param("sku")String sku);

    @Select("select * from transfer_item where tran_id=" +
            "(select tran_id from transfer_item where tracking_no=#{trackingNoOld} order by create_at desc limit 1)")
    List<TransferItem> findByTrack(String trackingNoOld);

    @Insert("<script>"
            + "insert into transfer_item(tran_id,in_dept_id,out_dept_id,in_dept_no,in_dept_name,out_dept_no,out_dept_name,"
            + " tracking_no,spu,sku,cost,expected_qty,fact_out_qty,fact_in_qty,create_at,update_at) "
            + " values "
            + " <foreach collection =\"list\" item=\"o\" index= \"index\" separator =\",\"> "
            + "(#{o.tranId},#{o.inDeptId},#{o.outDeptId},#{o.inDeptNo},#{o.inDeptName},#{o.outDeptNo},#{o.outDeptName},"
            + "#{o.trackingNo},#{o.spu},#{o.sku},#{o.cost},#{o.expectedQty},#{o.factOutQty},#{o.factInQty},"
            + "now(),now())"
            + "</foreach> "
            + "</script>")
    Integer batchInsert(List<TransferItem> saveItemList);
}
