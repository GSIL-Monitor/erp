package com.stosz.order.mapper.order;

import com.stosz.order.ext.model.OrdersItem;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrdersItemsMapper {



    @InsertProvider(type =  OrdersItemsBuilder.class,method = "insert")
    int insert( OrdersItem param);

    @DeleteProvider(type = OrdersItemsBuilder.class, method = "delete")
    int delete(@Param("id") Integer id);

    @UpdateProvider(type = OrdersItemsBuilder.class, method = "update")
    int update( OrdersItem param);

    @SelectProvider(type = OrdersItemsBuilder.class, method = "find")
    List< OrdersItem> find(OrdersItem param);

    @SelectProvider(type = OrdersItemsBuilder.class, method = "count")
    int count( OrdersItem param);

    @SelectProvider(type = OrdersItemsBuilder.class, method = "getById")
    OrdersItem getById(@Param("id") Integer id);

    @SelectProvider(type = OrdersItemsBuilder.class, method = "getByOrderId")
    List<OrdersItem> getByOrderId(@Param("id") Integer id);

    @SelectProvider(type = OrdersItemsBuilder.class, method = "findByOrderIds")
    List<OrdersItem> findByOrderIds(@Param("orderIds") List<Integer> orderIds);

    @Insert("<script>"
            +"insert into orders_item(orders_id,spu,sku,inner_title,attr_title_array,qty,activity_id,single_amount,total_amount,product_id,product_title,foreign_title,foreign_total_amount,attr_name_array,attr_value_array,item_status_enum,product_image_url,create_at,update_at,creator_id,creator) "
            + "values "
            + " <foreach collection =\"list\" item=\"o\" index= \"index\" separator =\",\"> "
            + "(#{o.ordersId},#{o.spu},#{o.sku},#{o.innerTitle},#{o.attrTitleArray},#{o.qty},#{o.activityId},#{o.singleAmount},#{o.totalAmount},#{o.productId},#{o.productTitle},#{o.foreignTitle},#{o.foreignTotalAmount},#{o.attrNameArray},#{o.attrValueArray},#{o.itemStatusEnum},#{o.productImageUrl},now(),now(),#{o.creatorId},#{o.creator})"
            + "</foreach> "
            + "</script>")
    Integer batchInsert(List<OrdersItem> ordersAdditionList);
    
    @SelectProvider(type = OrdersItemsBuilder.class, method = "getByOrderIds")
    List<OrdersItem> getByOrderIds(@Param("orderIds") List<Integer> orderIds);
    /**
     * 
     * 根据订单ID查询商品条目信息
     * @param orderId 
     * @date 2017年11月29日
     * @author liusl
     */
    @Delete("delete from orders_item where orders_id=#{orderId}")
    void deleteByOrderId(Integer orderId);


    /**
     * 更新订单项的库存占用信息
     * @param id  订单项id
     * @param warehouseId 仓库id
     * @param qty  占用库存数量
     * @return  更新结果
     */
    @Update("UPDATE orders_item SET warehouse_id=#{warehouseId} ,occupy_qty=#{qty},occupy_stock_time=now() WHERE id=#{id} ")
    Integer setOrderItemsOccupy(@Param("id") Integer id,@Param("warehouseId") Integer warehouseId,@Param("qty") Integer qty);

    /**
     * 根据运单集合查询订单项数据
     * @param trackingNos
     * @return
     */
    @Select("<script>SELECT * FROM orders_item item INNER JOIN orders o ON item.orders_id = o.id where o.tracking_no in " +
            " <foreach item=\"item\" index=\"index\" collection=\"list\" open=\"(\" separator=\",\" close=\")\">" +
            " #{item} " +
            " </foreach>" +
            "</script>")
    List<OrdersItem> queryOrdersItems(List<String> trackingNos);
}
