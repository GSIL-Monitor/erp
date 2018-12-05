package com.stosz.order.mapper.order;

import com.stosz.fsm.IFsmDao;
import com.stosz.order.ext.model.OrderRmaItemReturnQty;
import com.stosz.order.ext.model.OrdersRmaBillItem;
import com.stosz.plat.mapper.AbstractBuilder;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * @author wangqian
 * 黑名单
 */


@Repository
public interface OrdersRmaBillItemMapper  extends IFsmDao {

    @InsertProvider(type = OrdersRmaBillItemBuilder.class,method = "insert")
    int insert(OrdersRmaBillItem item);

    @DeleteProvider(type = OrdersRmaBillItemBuilder.class, method = "delete")
    int delete(@Param("id") Integer id);

    @UpdateProvider(type = OrdersRmaBillItemBuilder.class, method = "update")
    int update(OrdersRmaBillItem param);

    @SelectProvider(type = OrdersRmaBillItemBuilder.class, method = "find")
    List<OrdersRmaBillItem> find(OrdersRmaBillItem param);

    @SelectProvider(type = OrdersRmaBillItemBuilder.class, method = "count")
    int count(OrdersRmaBillItem param);

    @SelectProvider(type = OrdersRmaBillItemBuilder.class, method = "getById")
    OrdersRmaBillItem getById(@Param("id") Integer id);

    @Select("select * from orders_rma_bill_item i where i.orders_rma_bill_id = #{rmaId}")
    List<OrdersRmaBillItem> findByRmaId(@Param("rmaId") Integer rmaId);

    @Select("select * from orders_rma_bill_item i where i.orders_id = #{orderId}")
    List<OrdersRmaBillItem> findByOrderId(@Param("orderId") Integer orderId);

    @Select("<script>" +
            " select * from orders_rma_bill_item i where i.orders_rma_bill_id in " +
            " <foreach item=\"item\" index=\"index\" collection=\"rmaIds\" open=\"(\" separator=\",\" close=\")\">" +
            " #{item} " +
            " </foreach>" +
            "</script>")
    List<OrdersRmaBillItem> findByRmaIds(@Param("rmaIds") List<Integer> rmaIds);


//    @Select("select * from orders_rma_bill_item i where i.orders_rma_bill_id = #{rmaId} and  rma_status_enum = #{status}")
//    List<OrdersRmaBillItem> findHasReturnByRmaId(@Param("rmaId") Integer rmaId, @Param("status") Integer status);


        @Insert("<script>"
            +"INSERT INTO orders_rma_bill_item (orders_rma_bill_id, spu, sku, attr_title_array, product_id, product_title,inner_title, product_img_url,orders_item_id,"
                    +" orders_item_qty, orders_item_return_qty, orders_item_apply_qty, single_amount, total_amount, detection_result, storage_location, in_storage_no, "
                    +" in_qty, create_at, creator_id, creator) values"
                    + " <foreach collection =\"list\" item=\"o\" index= \"index\" separator =\",\"> "
                    + "(#{o.ordersRmaBillId},#{o.spu},#{o.sku},#{o.attrTitleArray},#{o.productId},#{o.productTitle},#{o.innerTitle},#{o.productImgUrl},#{o.ordersItemId},"
                    +" #{o.ordersItemQty},#{o.ordersItemReturnQty},#{o.ordersItemApplyQty},#{o.singleAmount},#{o.totalAmount},#{o.detectionResult},#{o.storageLocation},#{o.inStorageNo},"
                    +" #{o.inQty},now(),#{o.creatorId},#{o.creator})"
                    + "</foreach> "
                    + "</script>")
    Integer batchInsert(List<OrdersRmaBillItem> items);

    @Update("update orders_rma_bill_item set in_qty=orders_item_apply_qty, storage_location=#{storageLocation} where orders_rma_bill_id=#{rmaId}")
    Integer updateRmaBillItemByRmaId(@Param("rmaId") Integer rmaId,@Param("storageLocation") String storageLocation);


    class OrdersRmaBillItemBuilder  extends AbstractBuilder<OrdersRmaBillItem> {

        @Override
        public void buildSelectOther(SQL sql) {

        }

        @Override
        public void buildJoin(SQL sql) {

        }

        @Override
        public void buildWhere(SQL sql, OrdersRmaBillItem param) {

        }

        public String getOrdersRmaItemByRmaId() {
            SQL sql = new SQL();
            sql.SELECT(_this("*"));
            buildSelectOther(sql);
            sql.FROM(getTableNameThis());
            buildJoin(sql);
            sql.WHERE(_this("orders_rma_bill_id=#{rmaId}"));
            return sql.toString();
        }


    }
    @SelectProvider(type = OrdersRmaBillItemBuilder.class, method = "getOrdersRmaItemByRmaId")
    List<OrdersRmaBillItem> getOrdersRmaItemByRmaId(@Param("rmaId") Integer rmaId);



}
