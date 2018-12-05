package com.stosz.purchase.mapper;

import com.stosz.fsm.IFsmDao;
import com.stosz.purchase.ext.common.PurchaseSkuDto;
import com.stosz.purchase.ext.model.Purchase;
import com.stosz.purchase.ext.model.PurchaseItem;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

/**
 * 采购详情的mapper
 *
 * @author xiongchenyang
 * @version [1.0 , 2017/11/9]
 */
@Repository
public interface PurchaseItemMapper extends IFsmDao<PurchaseItem> {

    // -----------------------------------基础数据增删改查------------------------------------//

    @InsertProvider(type = PurchaseItemBuilder.class, method = "insert")
    int insert(PurchaseItem purchaseItem);

    @DeleteProvider(type = PurchaseItemBuilder.class, method = "delete")
    int delete(Integer id);

    @UpdateProvider(type = PurchaseItemBuilder.class, method = "updateSelective")
    int update(PurchaseItem purchaseItem);

    @UpdateProvider(type = PurchaseItemBuilder.class, method = "updateState")
    Integer updateState(@Param("id") Integer id, @Param("state") String state, @Param("stateTime") LocalDateTime stateTime);

    @SelectProvider(type = PurchaseItemBuilder.class, method = "getById")
    PurchaseItem getById(Integer id);

    // -----------------------------------复杂业务逻辑------------------------------------//

    @Select("select * from purchase_item where purchase_id = #{purchaseId}")
    List<PurchaseItem> findByPurchaseId(@Param("purchaseId") Integer purchaseId);

    @Select("select distinct spu from purchase_item where purchase_id = #{purchaseId}")
    List<String> getPurchaseSpu(@Param("purchaseId") Integer purchaseId);

    @SelectProvider(type = PurchaseItemBuilder.class, method = "findPageList")
    List<PurchaseItem> findPageList(PurchaseItem purchaseItem);

    @SelectProvider(type = PurchaseItemBuilder.class, method = "findPageListCount")
    int findPageListCount(PurchaseItem purchaseItem);

    @Select("select sku,sum(purchase_qty) as total_purchase_qty, sum(avg_sale_qty) as total_avg_sale_qty , sum(pending_order_qty) as total_pending_order_qty,sum(quantity) as total_quantity,sum(instock_qty) as total_instock_qty,sum(intransit_cancle_qty) as total_intransit_qty, price  from purchase_item where purchase_id = #{purchaseId} GROUP BY sku")
    List<PurchaseSkuDto> findSkuDtoByPurchaseId(@Param("purchaseId") Integer purchaseId);

    @Select("select * from purchase_item where sku = #{sku} and purchase_id = #{purchaseId}")
    List<PurchaseItem> findBySku(@Param("sku") String sku, @Param("purchaseId") Integer purchaseId);

    @SelectProvider(type = PurchaseItemBuilder.class, method = "queryReturnedList")
    List<PurchaseItem> queryReturnedList(Integer purchaseId);

    @Update("update purchase_item set intransit_cancle_qty=intransit_cancle_qty+#{addCancleQty} where id=#{id}")
    int updateCancleqtyById(@Param("id") Integer id, @Param("addCancleQty") Integer addCancleQty);

    @Update("update purchase_item set instock_qty=instock_qty+#{addCancleQty} where id=#{id}")
    int updateInstockQtyById(@Param("id") Integer id, @Param("addCancleQty") Integer addCancleQty);

    @Select("<script>select * from purchase_item where id in"
            + "<foreach item=\"item\" index=\"index\" collection=\"ids\" open=\"(\" separator=\",\" close=\")\">" + "#{item}" + "</foreach></script>")
    List<PurchaseItem> queryByIds(@Param("ids") List<Integer> ids);

    @SelectProvider(type=PurchaseItemBuilder.class,method="getListByIds")
    List<PurchaseItem> getListByIds(@Param("ids") List<Integer> ids);

    @Select("select * from purchase_item where purchase_id = #{purchaseId} and sku=#{sku}")
    List<PurchaseItem> findByPurIdAndSku(@Param("purchaseId") Integer purchaseId,@Param("sku") String sku);

    @Select("select * from purchase_item where purchase_id = #{purchaseId} and sku=#{sku} and dept_id=#{deptId}")
    PurchaseItem findOne(@Param("purchaseId") Integer purchaseId,@Param("sku") String sku,@Param("deptId") Integer deptId);

    @SelectProvider(type = PurchaseItemBuilder.class,method = "findSpu")
    List<PurchaseItem> findSpu(@Param("purchases") List<Purchase> purchases);

    @Select("select distinct sku from purchase_item where purchase_id = #{purchaseId}")
    Set<String> findSkuByPurchaseId(@Param("purchaseId") Integer purchaseId);
}
