package com.stosz.purchase.mapper.finance;

import com.stosz.fsm.IFsmDao;
import com.stosz.purchase.ext.model.finance.Payable;
import com.stosz.purchase.mapper.finance.builder.PayableBuilder;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @Author:yangqinghui
 * @Description:TransferMapper
 * @Created Time:2017/11/28 16:53
 * @Update Time:2017/11/28 16:53
 */
@Repository
public interface PayableMapper extends IFsmDao<Payable> {

    //-----------------------------------基础数据增删改查------------------------------------//
    @InsertProvider(type = PayableBuilder.class, method = "insert")
    int insert(Payable payable);

    @DeleteProvider(type = PayableBuilder.class, method = "delete")
    int delete(Integer id);

    @UpdateProvider(type = PayableBuilder.class, method = "update")
    int update(Payable payable);

    @UpdateProvider(type = PayableBuilder.class, method = "updateState")
    Integer updateState(@Param("id") Integer id, @Param("state") String state, @Param("stateTime") LocalDateTime stateTime);

    @SelectProvider(type = PayableBuilder.class, method = "getById")
    Payable getById(Integer id);
    //----------------------------------自定义业务逻辑------------------------------------//



    @Update("update payable set payment_id=null where payment_id=#{paymentId}")
    Integer emptyPayId(@Param("paymentId") Integer paymentId);

    @Delete("delete FROM payable WHERE change_bill_type=#{type} and payment_id=#{paymentId}")
    Integer deleteAjust(@Param("paymentId") Integer paymentId, @Param("type") Integer type);

    @SelectProvider(type = PayableBuilder.class, method = "countByParam")
    Integer countByParam(@Param("payable") Payable payable);

    @SelectProvider(type = PayableBuilder.class, method = "findByParam")
    List<Payable> findByParam(@Param("payable") Payable payable);

    @SelectProvider(type = PayableBuilder.class,method = "findByPaymentIds")
    List<Payable> findByPaymentIds(@Param("paymentIds") Integer[] paymentIds);
}
