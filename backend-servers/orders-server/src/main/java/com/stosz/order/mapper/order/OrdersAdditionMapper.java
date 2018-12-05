package com.stosz.order.mapper.order;

import com.stosz.order.ext.model.OrdersAddition;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrdersAdditionMapper {

    @InsertProvider(type =  OrdersAdditionBuilder.class,method = "insert")
    int insert( OrdersAddition param);

    @DeleteProvider(type = OrdersAdditionBuilder.class, method = "delete")
    int delete(@Param("id") Integer id);

    @UpdateProvider(type = OrdersAdditionBuilder.class, method = "update")
    int update( OrdersAddition param);

    @SelectProvider(type = OrdersAdditionBuilder.class, method = "find")
    List< OrdersAddition> find( OrdersAddition param);

    @SelectProvider(type = OrdersAdditionBuilder.class, method = "count")
    int count( OrdersAddition param);

    @SelectProvider(type = OrdersAdditionBuilder.class, method = "getById")
    OrdersAddition getById(@Param("id") Integer id);

    @SelectProvider(type = OrdersAdditionBuilder.class, method = "getByOrderId")
    OrdersAddition getByOrderId(@Param("orderId") Integer orderId);

    @Insert("<script>"
            +"insert into orders_addition(browser,browse_second,equipment,user_agent,order_from,orders_id,ip,ip_name,black_level_enum,black_type_enum,web_url,web_domain,create_at,update_at,creator_id,creator) "
            + "values "
            + " <foreach collection =\"list\" item=\"o\" index= \"index\" separator =\",\"> "
            + "(#{o.browser},#{o.browseSecond},#{o.equipment},#{o.userAgent},#{o.orderFrom},#{o.ordersId},#{o.ip},#{o.ipName},#{o.blackLevelEnum},#{o.blackTypeEnum},#{o.webUrl},#{o.webDomain},now(),now(),#{o.creatorId},#{o.creator})"
            + "</foreach> "
            + "</script>")
    Integer batchInsert(List<OrdersAddition> ordersAdditionList);


    @Select("<script> " +
            " select * from orders_addition where orders_id in" +
            " <foreach collection=\"list\" item=\"item\" " +
            "  index=\"index\" open=\"(\" close=\")\" separator=\",\"> " +
            "  #{item}" +
            " </foreach>" +
            " </script>")
    List<OrdersAddition> queryByOrderIds(List<Integer> orderIds);
}
