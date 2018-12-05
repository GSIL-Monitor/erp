package com.stosz.purchase.mapper;

import com.stosz.fsm.IFsmDao;
import com.stosz.purchase.ext.model.PurchaseReturnedItem;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PurchaseReturnedItemMapper extends IFsmDao<PurchaseReturnedItem> {

    @Insert("<script>INSERT INTO purchase_returned_item(returned_id,purchase_item_id,dept_id,dept_no,dept_name,spu,sku, purchase_qty,"
            + "purchase_price,returned_qty,creator_id,creator,create_at,state, state_time) VALUES"
            + "<foreach item=\"item\" index=\"index\" collection=\"list\"  separator=\",\" >"
            + "(#{item.returnedId},#{item.purchaseItemId},#{item.deptId},#{item.deptNo},#{item.deptName},#{item.spu},#{item.sku},#{item.purchaseQty}"
            + ",#{item.purchasePrice},#{item.returnedQty},#{item.creatorId},#{item.creator},now(),#{item.state},#{item.stateTime})"
            + "</foreach></script>")
    public int addList(List<PurchaseReturnedItem> items);

    @InsertProvider(type = PurchaseReturnItemBuilder.class, method = "insert")
    public int add(PurchaseReturnedItem purchaseReturnedItem);

    @Select("select * from purchase_returned_item where returned_id=#{returnId}")
    public List<PurchaseReturnedItem> queryListByReturnId(Integer returnId);

    @UpdateProvider(type = PurchaseReturnItemBuilder.class, method = "updateState")
    Integer updateState(@Param("id") Integer id, @Param("state") String state, @Param("stateTime") LocalDateTime stateTime);

    @Select("select * from purchase_returned_item where returned_id = #{returnedId} and sku = #{sku}")
    List<PurchaseReturnedItem>  findByNoAndSku(@Param("returnedId") Integer returnedId, @Param("sku") String sku);

    @UpdateProvider(type = PurchaseReturnItemBuilder.class,method = "update")
    int update(PurchaseReturnedItem purchaseReturnedItem);

    @SelectProvider(type = PurchaseReturnItemBuilder.class,method = "getById")
    PurchaseReturnedItem getById(Integer id);
}
