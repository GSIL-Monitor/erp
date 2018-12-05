package com.stosz.order.mapper.order;

import com.google.common.base.Joiner;
import com.stosz.order.ext.model.OrderLogistic;
import com.stosz.plat.mapper.AbstractBuilder;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Map;

/**
 * Created by carter on 2017-11-16. Copyright © 2016 －2017 深圳布谷鸟信息技术有限公司
 * mapper代码
 */

@Repository
public interface OrderLogisticMapper {

	/**insert default method */

	@Insert("INSERT INTO order_logistic (orders_id,tracking_no,reject_status_enum,tracking_status,product_id,product_title,sku,spu,tracking_memo,logistics_id,create_at,update_at,creator_id,creator) VALUES(#{orderId},#{trackingNo},#{rejectStatusEnum},#{trackingStatus},#{productId},#{productTitle},#{sku},#{spu},#{trackingMemo},#{logisticsId},now(),now(),#{creatorId},#{creator})")

	void  insert(OrderLogistic record );

	@Delete("DELETE FROM order_logistic WHERE id=#{id}")
    Long delete(@Param("id") Long id);

	  @Update("UPDATE order_logistic SET orders_id=#{orderId},tracking_no=#{trackingNo},reject_status_enum=#{rejectStatusEnum},tracking_status=#{trackingStatus},product_id=#{productId},product_title=#{productTitle},sku=#{sku},spu=#{spu},tracking_memo=#{trackingMemo},logistics_id=#{logisticsId}, update_at = now(),creator_id=#{creatorId},creator=#{creator} WHERE id=#{id}")
    Long update(OrderLogistic record);

	@Select("SELECT * FROM order_logistic WHERE id=#{id}")
    OrderLogistic findById(@Param("id") Long id);

    @Select("SELECT * FROM order_logistic WHERE tracking_no=#{transNo}")
    OrderLogistic findByTransNo(@Param("transNo") String tracking_no);

//    @Select("<script>\n" +
//            "select * from order_logistic ol \n" +
//            "where ol.orders_id  in\n" +
//            "<foreach item=\"item\" index=\"index\" collection=\"list\"\n" +
//            "     open=\"(\" separator=\",\" close=\")\">\n" +
//            "       #{item}\n" +
//            "</foreach>\n" +
//            "</script>")

    @SelectProvider(type = OrderLogisticBuilder.class, method = "findByOrderIds")
    List<OrderLogistic> findByOrderIds(@Param("orderIds") List<Integer> orderIds);

    @SelectProvider(type = OrderLogisticBuilder.class, method = "findByParam")
    List<OrderLogistic> findByParam(@Param("param")OrderLogistic param);


    @Select("SELECT * FROM order_logistic")
    List<OrderLogistic> findAll();


    @SelectProvider(type = OrderLogisticBuilder.class, method = "findCountByParam")
    Integer findCountByParam(@Param("param")OrderLogistic param);

    @Insert("<script>"
            +"insert into order_logistic(orders_id,tracking_no,reject_status_enum,tracking_status,product_id,product_title,sku,spu,tracking_memo,logistics_id,create_at,update_at,creator_id,creator) "
            + "values "
            + " <foreach collection =\"list\" item=\"o\" index= \"index\" separator =\",\"> "
            + "(#{o.orderId},#{o.trackingNo},#{o.rejectStatusEnum},#{o.trackingStatus},#{o.productId},#{o.productTitle},#{o.sku},#{o.spu},#{o.trackingMemo},#{o.logisticsId},now(),now(),#{o.creatorId},#{o.creator})"
            + "</foreach> "
            + "</script>")
    Integer batchInsert(List<OrderLogistic> orderLogisticList);


    class OrderLogisticBuilder extends AbstractBuilder<OrderLogistic> {
        @Override
        public void buildSelectOther(SQL sql) {

        }

        @Override
        public void buildJoin(SQL sql) {

        }

        @Override
        public void buildWhere(SQL sql, OrderLogistic param) {

        }

        public String findByOrderIds(Map<String, Object> param){
            List<Integer> orderIds = (List<Integer>) param.get("orderIds");
            StringBuilder sb = new StringBuilder();
            sb.append("select * from order_logistic ol where ol.orders_id  in (" );
            if(orderIds != null && orderIds.size() != 0){
                String ids = Joiner.on(",").join(orderIds);
                sb.append(ids);
            }else{
                sb.append("null");
            }
            sb.append(" )  ");
            return sb.toString();
        }

        public String findByParam(@Param("param") OrderLogistic param) {


            SQL sql = new SQL();
            sql.SELECT(_this("*"));
            sql.FROM(getTableNameThis());
            buildWhereByParam(sql, param);
            StringBuilder sb = new StringBuilder();
            if (notEmpty(param.getStart())) {
                sb.append(" order by id desc limit ").append(param.getStart()).append(",").append(param.getLimit());
            } else {
                sb.append(" order by id desc limit ").append(param.getLimit());
            }
            String s = sql.toString() + sb.toString();
            return s;
        }

        public String findCountByParam(@Param("param")OrderLogistic param) {
            SQL sql = new SQL();
            sql.SELECT("count(1)");
            sql.FROM(getTableNameThis());
            buildWhereByParam(sql, param);
            StringBuilder sb = new StringBuilder();
            return sql.toString() + sb.toString();
        }

        public void buildWhereByParam(SQL sql, OrderLogistic param) {

            Assert.notNull(param,"查询参数不能为空");
		if (null!=param.getId()  && param.getId().intValue()> 0 )	sql.AND().WHERE("id="+param.getId());
		if (null!=param.getOrderId()  && param.getOrderId().intValue()> 0 )	sql.AND().WHERE("orders_id="+param.getOrderId());
		if (null!=param.getTrackingNo()  && "" != param.getTrackingNo() )	sql.AND().WHERE("tracking_no="+param.getTrackingNo());
		if (null!=param.getRejectStatusEnum()  && param.getRejectStatusEnum().intValue()> 0 )	sql.AND().WHERE("reject_status_enum="+param.getRejectStatusEnum());
		if (null!=param.getTrackingStatus()  && param.getTrackingStatus().intValue()> 0 )	sql.AND().WHERE("tracking_status="+param.getTrackingStatus());
		if (null!=param.getProductId()  && param.getProductId().intValue()> 0 )	sql.AND().WHERE("product_id="+param.getProductId());
		if (null!=param.getProductTitle()  && "" != param.getProductTitle() )	sql.AND().WHERE("product_title="+param.getProductTitle());
		if (null!=param.getSku()  && "" != param.getSku() )	sql.AND().WHERE("sku="+param.getSku());
		if (null!=param.getSpu()  && "" != param.getSpu() )	sql.AND().WHERE("spu="+param.getSpu());
		if (null!=param.getTrackingMemo()  && "" != param.getTrackingMemo() )	sql.AND().WHERE("tracking_memo="+param.getTrackingMemo());
		if (null!=param.getLogisticsId()  && param.getLogisticsId().intValue()> 0 )	sql.AND().WHERE("logistics_id="+param.getLogisticsId());
		if (null!=param.getCreateAt() )	sql.AND().WHERE("create_at="+param.getCreateAt());
		if (null!=param.getUpdateAt() )	sql.AND().WHERE("update_at="+param.getUpdateAt());
		if (null!=param.getCreatorId()  && param.getCreatorId().intValue()> 0 )	sql.AND().WHERE("creator_id="+param.getCreatorId());
		if (null!=param.getCreator()  && "" != param.getCreator() )	sql.AND().WHERE("creator="+param.getCreator());



        }

    }

}