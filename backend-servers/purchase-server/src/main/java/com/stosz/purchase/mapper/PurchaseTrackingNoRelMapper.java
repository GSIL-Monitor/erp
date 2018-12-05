package com.stosz.purchase.mapper;

import com.stosz.purchase.ext.model.Purchase;
import com.stosz.purchase.ext.model.PurchaseTrackingNo;
import com.stosz.purchase.ext.model.PurchaseTrackingNoRel;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 采购运单的实体
 * @author xiongchenyang
 * @version [1.0 , 2018/1/2]
 */
@Repository
public interface PurchaseTrackingNoRelMapper {

    //-----------------------------------------------增删改查-------------------------------//
    @InsertProvider(type = PurchaseTrackingNoRelBuilder.class, method = "insert")
    int insert(PurchaseTrackingNoRel purchaseTrackingNoRel);
    @DeleteProvider(type = PurchaseTrackingNoRelBuilder.class,method = "delete")
    int delete(Integer id);
    @UpdateProvider(type = PurchaseTrackingNoRelBuilder.class, method = "update")
    int update(PurchaseTrackingNoRel purchaseTrackingNoRel);
    @SelectProvider(type = PurchaseTrackingNoRelBuilder.class, method = "getById")
    PurchaseTrackingNoRel getById(Integer id);

    //--------------------------------------------复杂业务逻辑-----------------------------//
    @Select("select * from purchase_tracking_no_rel where purchase_no = #{purchaseNo}")
    List<PurchaseTrackingNoRel>  findByPurchaseNo(@Param("purchaseNo") String purchaseNo);
    @Select("select * from purchase_tracking_no_rel where tracking_no = #{trackingNo}")
    List<PurchaseTrackingNoRel> findByTrackingNo(@Param("trackingNo") String trackingNo);
    @SelectProvider(type = PurchaseTrackingNoRelBuilder.class, method = "getTrackingNoByPurchaseNo")
    List<PurchaseTrackingNoRel> getTrackingNoByPurchaseNo(@Param("purchaseNo") List<Purchase> purchaseNo);
    @Select("select group_concat(tracking_no) from purchase_tracking_no_rel where purchase_no = #{purchaseNo}")
    String getTrackingNo(@Param("purchaseNo") String purchaseNo);

    @Select("select * from purchase_tracking_no_rel where tracking_no = #{purchaseTrackingNo.trackingNo} and purchase_no =#{purchaseTrackingNo.purchaseNo}")
    PurchaseTrackingNoRel getByUnique(@Param("purchaseTrackingNo") PurchaseTrackingNo purchaseTrackingNo);

    @Delete("delete from purchase_tracking_no_rel where tracking_no = #{purchaseTrackingNo.trackingNo} and purchase_no =#{purchaseTrackingNo.purchaseNo}")
    int deleteByUnique(@Param("purchaseTrackingNo") PurchaseTrackingNo purchaseTrackingNo);

}  
