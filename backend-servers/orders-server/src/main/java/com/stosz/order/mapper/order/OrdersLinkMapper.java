package com.stosz.order.mapper.order;

import com.stosz.order.ext.dto.OrderLinkDTO;
import com.stosz.order.ext.model.OrdersLink;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface OrdersLinkMapper {

    @InsertProvider(type = OrdersLinkBuilder.class,method = "insert")
    int insert(OrdersLink param);

    @DeleteProvider(type = OrdersLinkBuilder.class, method = "delete")
    int delete(@Param("id") Integer id);

    @UpdateProvider(type = OrdersLinkBuilder.class, method = "update")
    int update(OrdersLink param);

    @SelectProvider(type = OrdersLinkBuilder.class, method = "find")
    List<OrdersLink> find(OrdersLink param);

    @SelectProvider(type = OrdersLinkBuilder.class, method = "count")
    int count(OrdersLink param);

    @SelectProvider(type = OrdersLinkBuilder.class, method = "getById")
    OrdersLink getById(@Param("id") Integer id);

    @SelectProvider(type = OrdersLinkBuilder.class, method = "getByOrderId")
    OrdersLink getByOrderId(@Param("id") Integer id);

    @Select("<script> " +
            " select l.* from orders_link l, orders_item i where l.orders_id = i.orders_id and l.telphone = #{tel} and i.sku in " +
            " <foreach collection=\"skuList\" item=\"sku\" " +
            "  index=\"index\" open=\"(\" close=\")\" separator=\",\"> " +
            "  #{sku}" +
            " </foreach>" +
            " and l.create_at &gt;= #{beginAt} and l.create_at   &lt;= #{endAt}" +
             " </script>")
    List<OrdersLink> findByTelAndSkuListBetweenCreateAt(Map map);


    @Select("SELECT IFNULL(MAX(old_id),0) FROM orders_link")
    Integer getNewMaxId();

    @Insert("<script>"
            +"insert into orders_link(first_name,last_name,country,telphone,email,province,city,area,address,zipcode,black_level_enum,black_type_enum,orders_id,customer_id,create_at,update_at,creator_id,creator,old_id) "
            + "values "
            + " <foreach collection =\"list\" item=\"o\" index= \"index\" separator =\",\"> "
            + "(#{o.firstName},#{o.lastName},#{o.country},#{o.telphone},#{o.email},#{o.province},#{o.city},#{o.area},#{o.address},#{o.zipcode},#{o.blackLevelEnum},#{o.blackTypeEnum},#{o.ordersId},#{o.customerId},now(),now(),#{o.creatorId},#{o.creator},#{o.oldId})"
            + "</foreach> "
            + "</script>")
    Integer batchInsert(List<OrdersLink> ordersLinkList);


    /**
     * 通过手机号查询订单联系人
     * @param tels
     * @return
     */
    @SelectProvider(type = OrdersLinkBuilder.class, method = "findOrderLinkByTels")
    List<OrdersLink> findOrderLinkByTels(@Param("tels") List<String>tels);
    /**
     * 
     * 更新联系人信息
     * @param orderLinkDTO 
     * @date 2017年11月29日
     * @author liusl
     */
    @Update("update orders_link SET first_name = #{firstName},last_name = #{lastName},telphone = #{telphone},email = #{email},province = #{province},city = #{city},area = #{area},address = #{address},zipcode = #{zipcode} where id=#{linkId}")
    void updateOrderLink(OrderLinkDTO orderLinkDTO);


    @Select("<script> " +
            " select * from  orders_link l where l.orders_id in" +
            " <foreach collection=\"list\" item=\"item\" " +
            "  index=\"index\" open=\"(\" close=\")\" separator=\",\"> " +
            "  #{item}" +
            " </foreach>" +
            " </script>")
    List<OrdersLink> findByOrderIdList(List<Integer> orderIds);


    /**
     * 通过手机号查询订单联系人
     * @param tel
     * @return
     */
    @Select("select * from orders_link l where l.telphone = #{tel} order by id desc")
    List<OrdersLink> findByTel(@Param("tel") String tel);

}
