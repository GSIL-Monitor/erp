package com.stosz.purchase.mapper.finance;

import com.stosz.purchase.ext.model.finance.PaymentItem;
import com.stosz.purchase.mapper.finance.builder.PaymentItemBuilder;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author:yangqinghui
 * @Description:TransferMapper
 * @Created Time:2017/11/28 16:53
 * @Update Time:2017/11/28 16:53
 */
@Repository
public interface PaymentItemMapper {

    //-----------------------------------基础数据增删改查------------------------------------//
    @InsertProvider(type = PaymentItemBuilder.class, method = "insert")
    int insert(PaymentItem paymentItem);

    @DeleteProvider(type = PaymentItemBuilder.class, method = "delete")
    int delete(Integer id);

    @UpdateProvider(type = PaymentItemBuilder.class, method = "update")
    int update(PaymentItem paymentItem);

    @SelectProvider(type = PaymentItemBuilder.class, method = "getById")
    PaymentItem getById(Integer id);

    @SelectProvider(type = PaymentItemBuilder.class, method = "count")
    Integer count(PaymentItem payment);
    //----------------------------------自定义业务逻辑------------------------------------//
    @SelectProvider(type = PaymentItemBuilder.class, method = "query")
    List<PaymentItem> query(PaymentItem payment);

    @Select("select * from payment_item where payment_id=#{paymentId}")
    List<PaymentItem> getListByPaymentId(Integer paymentId);

    @Delete("delete from payment_item where goal_bill_no=#{goalBillNo} and goal_bill_type=#{goalBillType}")
    int deleteByNoAndType(@Param("goalBillNo") String goalBillNo, @Param("goalBillType") Integer goalBillType);

    @Select("select * from payment_item where goal_bill_no=#{goalBillNo} and goal_bill_type=#{goalBillType}")
    List<PaymentItem> getListByNoAndType(@Param("goalBillNo") String goalBillNo, @Param("goalBillType") Integer goalBillType);

    @Select("select * from payment_item where change_bill_no=#{changeNo}")
    List<PaymentItem> getListByChangeNo(String changeNo);

    @Update("update payment_item set payment_id=null where payment_id=#{paymentId}")
    Integer emptyPayId(@Param("paymentId") Integer paymentId);

    @Delete("delete FROM payment_item WHERE change_bill_type=#{type} and payment_id=#{paymentId}")
    Integer deleteAjust(@Param("paymentId") Integer paymentId, @Param("type") Integer type);

    @Select("select * from payment_item where change_bill_item=#{itemNo} and change_bill_type=#{changeBillType}")
    PaymentItem getOneByChange(@Param("itemNo") String itemNo, @Param("changeBillType") Integer changeBillType);
}
