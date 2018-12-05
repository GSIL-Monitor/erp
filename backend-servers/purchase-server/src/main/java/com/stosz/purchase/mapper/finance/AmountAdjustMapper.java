package com.stosz.purchase.mapper.finance;

import com.stosz.purchase.ext.model.finance.AmountAdjust;
import com.stosz.purchase.mapper.finance.builder.AmountAdjustBuilder;
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
public interface AmountAdjustMapper {

    //-----------------------------------基础数据增删改查------------------------------------//
    @InsertProvider(type = AmountAdjustBuilder.class, method = "insert")
    int insert(AmountAdjust amountAdjust);

    @DeleteProvider(type = AmountAdjustBuilder.class, method = "delete")
    int delete(Integer id);

    @UpdateProvider(type = AmountAdjustBuilder.class, method = "update")
    int update(AmountAdjust amountAdjust);

    @SelectProvider(type = AmountAdjustBuilder.class, method = "getById")
    AmountAdjust getById(Integer id);

    @SelectProvider(type = AmountAdjustBuilder.class, method = "count")
    int count(AmountAdjust adjust);

    @SelectProvider(type = AmountAdjustBuilder.class, method = "query")
    List<AmountAdjust> query(AmountAdjust adjust);
    //----------------------------------自定义业务逻辑------------------------------------//

    @Delete("delete FROM amount_adjust WHERE payment_id=#{paymentId}")
    Integer deleteByPayId(@Param("paymentId") Integer paymentId);
}
