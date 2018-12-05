package com.stosz.purchase.mapper;

import com.stosz.purchase.ext.model.PurchaseTrackingNo;
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
public interface PurchaseTrackingNoMapper {

    //-----------------------------------------------增删改查-------------------------------//
    @InsertProvider(type = PurchaseTrackingNoBuilder.class, method = "insert")
    int insert(PurchaseTrackingNo purchaseTrackingNo);
    @DeleteProvider(type = PurchaseTrackingNoBuilder.class,method = "delete")
    int delete(Integer id);
    @UpdateProvider(type = PurchaseTrackingNoBuilder.class, method = "update")
    int update(PurchaseTrackingNo purchaseTrackingNo);
    @UpdateProvider(type = PurchaseTrackingNoBuilder.class, method = "updateSelective")
    int updateSelect(PurchaseTrackingNo purchaseTrackingNo);
    @SelectProvider(type = PurchaseTrackingNoBuilder.class, method = "getById")
    PurchaseTrackingNo getById(Integer id);

    //--------------------------------------------复杂业务逻辑-----------------------------//
    @Select("select tn.*,tnr.purchase_no from purchase_tracking_no tn left join purchase_tracking_no_rel tnr on tn.tracking_no = tnr.tracking_no where tnr.purchase_no = #{purchaseNo}")
    List<PurchaseTrackingNo>  findByPurchaseNo(@Param("purchaseNo") String purchaseNo);
    @Select("select * from purchase_tracking_no where tracking_no = #{trackingNo}")
    PurchaseTrackingNo getByTrackingNo(@Param("trackingNo") String trackingNo);

}  
