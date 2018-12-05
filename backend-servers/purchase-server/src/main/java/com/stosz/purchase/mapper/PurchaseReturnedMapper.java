package com.stosz.purchase.mapper;

import com.stosz.fsm.IFsmDao;
import com.stosz.purchase.ext.model.PurchaseReturned;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PurchaseReturnedMapper extends IFsmDao<PurchaseReturned> {

    @InsertProvider(type = PurchaseReturnedBuilder.class, method = "insert")
    public int addReturned(PurchaseReturned purchaseReturned);

    @Update("update purchase_returned set returned_no=#{returnedNo} where id=#{id}")
    public int updateReturned(PurchaseReturned purchaseReturned);

    @SelectProvider(type = PurchaseReturnedBuilder.class, method = "query")
    public List<PurchaseReturned> queryList(PurchaseReturned purchaseReturned);

    @SelectProvider(type = PurchaseReturnedBuilder.class, method = "count")
    public int queryCount(PurchaseReturned purchaseReturned);

    @SelectProvider(type = PurchaseReturnedBuilder.class, method = "getById")
    public PurchaseReturned getById(Integer id);

    @UpdateProvider(type = PurchaseReturnedBuilder.class, method = "updateState")
    Integer updateState(@Param("id") Integer id, @Param("state") String state, @Param("stateTime") LocalDateTime stateTime);
    
    @Update("UPDATE purchase_returned  SET refund_user_id=#{refundUserId},refund_user=#{refundUser},refund_time=#{refundTime} WHERE id=#{id}")
    public int updateRefund(PurchaseReturned purchaseReturned);

    @Select("select * from purchase_returned where returned_no = #{returnedNo}")
    PurchaseReturned getByNo(String returnedNo);

    @Select("select * from purchase_returned where purchase_no = #{purchaseNo}")
    List<PurchaseReturned> findByPurchaseNo(String purchaseNo);
}
