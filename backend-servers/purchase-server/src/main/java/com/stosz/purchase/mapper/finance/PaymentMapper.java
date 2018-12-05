package com.stosz.purchase.mapper.finance;

import com.stosz.purchase.ext.model.finance.Payment;
import com.stosz.purchase.mapper.finance.builder.PaymentBuilder;
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
public interface PaymentMapper {

    //-----------------------------------基础数据增删改查------------------------------------//
    @InsertProvider(type = PaymentBuilder.class, method = "insert")
    int insert(Payment payment);

    @DeleteProvider(type = PaymentBuilder.class, method = "delete")
    int delete(Integer id);

    @UpdateProvider(type = PaymentBuilder.class, method = "update")
    int update(Payment payment);

    @SelectProvider(type = PaymentBuilder.class, method = "getById")
    Payment getById(Integer id);

    @SelectProvider(type = PaymentBuilder.class, method = "count")
    Integer count(Payment payment);
    //----------------------------------自定义业务逻辑------------------------------------//
    @SelectProvider(type = PaymentBuilder.class, method = "query")
    List<Payment> query(Payment payment);

    @Select("<script>"
            + "SELECT * FROM payment WHERE id in "
            + " <foreach collection =\"ids\" item=\"item\" index= \"index\" open=\"(\" close=\")\" separator =\",\"> "
            + " #{item}"
            + "</foreach> "
            + "</script>")
    List<Payment> findByIds(@Param("ids") Integer[] ids);

    @Delete("<script>"
            + "delete FROM payment WHERE id in "
            + " <foreach collection =\"ids\" item=\"item\" index= \"index\" open=\"(\" close=\")\" separator =\",\"> "
            + " #{item}"
            + "</foreach> "
            + "</script>")
    Integer deleteByIds(@Param("ids") List<Integer> ids);
}
