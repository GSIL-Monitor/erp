package com.stosz.purchase.mapper;

import com.stosz.purchase.ext.model.PurchaseRequired;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

/**
 * @author xiongchenyang
 * @version [1.0 , 2017/11/7]
 */
@Repository
public interface PurchaseRequiredMapper {
    //---------------------------------------------------基础增删改查---------------------------------------------//
    @InsertProvider(type = PurchaseRequiredBuilder.class, method = "insert")
    int insert(PurchaseRequired purchaseRequired);

    @DeleteProvider(type = PurchaseRequiredBuilder.class, method = "delete")
    int delete(Integer id);

    @UpdateProvider(type = PurchaseRequiredBuilder.class, method = "update")
    int update(PurchaseRequired purchaseRequired);

    @SelectProvider(type = PurchaseRequiredBuilder.class, method = "getById")
    PurchaseRequired getById(Integer id);

    //------------------------------------------------复杂业务逻辑--------------------------------------------//

    /**
     * 根据条件查询当前要展现的spu
     * @param purchaseRequired 查询条件
     * @return 要展现的spu
     */
    @SelectProvider(type = PurchaseRequiredBuilder.class, method = "findSpu")
    List<String> findSpu(PurchaseRequired purchaseRequired);

    @SelectProvider(type = PurchaseRequiredBuilder.class, method = "countSpu")
    int countSpu(PurchaseRequired purchaseRequired);

    /**
     * 根据spu集合查询当前要展现的明细
     * @param spuList  spu集合
     * @return 查询结果
     */
    @SelectProvider(type = PurchaseRequiredBuilder.class, method = "findItem")
    List<PurchaseRequired> findItem(@Param("purchaseRequired") PurchaseRequired purchaseRequired, @Param("spuList") List<String> spuList);


    @SelectProvider(type = PurchaseRequiredBuilder.class, method = "queryList")
    List<PurchaseRequired> queryList(PurchaseRequired purchaseRequired);

    @SelectProvider(type = PurchaseRequiredBuilder.class, method = "queryListCount")
    int queryListCount(PurchaseRequired purchaseRequired);

    @SelectProvider(type = PurchaseRequiredBuilder.class, method = "queryListByParam")
    List<PurchaseRequired> queryListByParam(@Param("buDeptId") Integer buDeptId, @Param("spu") String spu, @Param("sku") String sku);

    @Update("UPDATE purchase_required SET display_at = DATE_ADD(CURRENT_TIMESTAMP,interval 1 day) WHERE bu_dept_id=#{buDeptId} AND SKU=#{sku} AND SPU=#{spu}")
    int updatePurchaseDelay(@Param("buDeptId") Integer buDeptId, @Param("spu") String spu, @Param("sku") String sku);

    @Update("UPDATE purchase_required SET plan_qty=#{qty},change_status=1 WHERE id=#{id}")
    int updatePlanQty(@Param("id") Integer id, @Param("qty") Integer qty);

    @Update("update purchase_required set supplier_id=#{supplierId} where bu_dept_id=#{buDeptId} AND SKU=#{sku}")
    int updateSupplier(PurchaseRequired purchaseRequired);
    
    @Update("update purchase_required set buyer_id=#{buyerId},buyer=#{buyer} where bu_dept_id=#{buDeptId}  AND SPU=#{spu}")
    int updateBuyer(PurchaseRequired purchaseRequired);

    @Update("<script> update purchase_required set order_required_qty=#{orderRequiredQty},stock_qty=#{stockQty},intransit_qty=#{intransitQty},"
            + "purchase_qty=#{purchaseQty},avg_sale_qty=#{avgSaleQty},pending_order_qty=#{pendingOrderQty},buyer_id=#{buyerId},buyer=#{buyer},"
            + "supplier_id=#{supplierId} <if test=\"changeStatus==0\">,plan_qty=#{planQty}</if> <if test=\"requiredAppearedTime!=null\">,required_appeared_time=#{requiredAppearedTime}</if>  where id=#{id} </script>")
    int updatePurchase(PurchaseRequired purchaseRequired);

    @SelectProvider(type = PurchaseRequiredBuilder.class, method = "query")
    List<PurchaseRequired> query(PurchaseRequired purchaseRequired);
    
    @Select("<script> SELECT p.*,s.name supplierName FROM purchase_required p LEFT JOIN supplier s ON p.supplier_id=s.id where (p.bu_dept_id,p.sku) in"
            + "<foreach item=\"item\" index=\"index\" collection=\"set\" open=\"(\" separator=\",\" close=\")\">"
            +"${item}"
            + "</foreach> ORDER BY (CASE WHEN purchase_qty <![CDATA[ <= ]]> 0 THEN 0 ELSE 1 END) DESC,p.update_at ASC</script>")
    List<PurchaseRequired> queryListByDetailSet(@Param("set") Set<String> set);
    
    @Select("<script>SELECT * FROM purchase_required WHERE id IN "+ 
            "<foreach item=\"item\" index=\"index\" collection=\"idList\" open=\"(\" separator=\",\" close=\")\">"
            +"#{item}"
            + "</foreach></script>")
    public List<PurchaseRequired> queryByIdList(@Param("idList") List<Integer> idList);


    @Select("update purchase_required set display_at = DATE_ADD(CURRENT_TIMESTAMP,interval 1 day) ,plan_qty = 0 where id=#{id} ")
    Integer updatePurchaseDelayById(@Param("id") Integer id);


    @Update("update purchase_required set change_status=#{changeStatus} where  id=#{id}")
    int updateChangeStatus(@Param("id") Integer id, @Param("changeStatus") int changeStatus);

    @Select("<script> SELECT *FROM purchase_required WHERE sku IN"
            + "<foreach item=\"item\" index=\"index\" collection=\"skus\" open=\"(\" separator=\",\" close=\")\">"
            +"#{item}"
            + "</foreach>  AND dept_id IN "
            + "<foreach item=\"item\" index=\"index\" collection=\"deptIds\" open=\"(\" separator=\",\" close=\")\">"
            +"#{item}"
            + "</foreach>"
            +"ORDER BY dept_id ASC</script>")
    List<PurchaseRequired> queryBySkuAndDeptId(@Param("skus") Set<String> skus,@Param("deptIds") Set<Integer> deptIds);

}
