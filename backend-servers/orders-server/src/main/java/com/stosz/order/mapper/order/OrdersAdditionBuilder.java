package com.stosz.order.mapper.order;

import com.stosz.order.ext.model.OrdersAddition;
import com.stosz.plat.mapper.AbstractBuilder;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

/**
 * @author wangqian
 * 2017年11月6日
 * 订单附加项
 */
public class OrdersAdditionBuilder  extends AbstractBuilder<OrdersAddition> {

    @Override
    public void buildSelectOther(SQL sql) {

    }

    @Override
    public void buildJoin(SQL sql) {

    }

    @Override
    public void buildWhere(SQL sql, OrdersAddition param) {

    }


    public String getByOrderId(@Param("orderId") Integer orderId){
        StringBuilder sb = new StringBuilder();
        sb.append("select * from orders_addition a where a.orders_id = #{orderId} limit 1 ");
        return sb.toString();
    }
}
