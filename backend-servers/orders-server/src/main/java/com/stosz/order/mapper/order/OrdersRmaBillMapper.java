package com.stosz.order.mapper.order;

import com.google.common.base.Joiner;
import com.google.common.base.Strings;
import com.stosz.fsm.IFsmDao;
import com.stosz.order.ext.dto.OrdersBillSearchParam;
import com.stosz.order.ext.dto.OrdersChangeExport;
import com.stosz.order.ext.enums.*;
import com.stosz.order.ext.model.OrdersRmaBill;
import com.stosz.order.ext.model.OrdersRmaBillDO;
import com.stosz.plat.mapper.AbstractBuilder;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Repository
public interface OrdersRmaBillMapper extends IFsmDao {

    @UpdateProvider(type = OrdersRmaBillBuilder.class, method = "updateOrderState")
    Integer updateState(@Param("id") Integer id, @Param("state") String state, @Param("stateTime") LocalDateTime stateTime);

    @InsertProvider(type = OrdersRmaBillBuilder.class, method = "insert")
    int insert(OrdersRmaBill param);

    @DeleteProvider(type = OrdersRmaBillBuilder.class, method = "delete")
    int delete(@Param("id") Integer id);

    @UpdateProvider(type = OrdersRmaBillBuilder.class, method = "update")
    int update(OrdersRmaBill param);

    @SelectProvider(type = OrdersRmaBillBuilder.class, method = "count")
    int count(OrdersRmaBill param);

    @SelectProvider(type = OrdersRmaBillBuilder.class, method = "getById")
    OrdersRmaBill getById(@Param("id") Integer id);

    @SelectProvider(type = OrdersRmaBillBuilder.class, method = "findBySearchParam")
    List<OrdersRmaBill> findBySearchParam(OrdersBillSearchParam param);

    @SelectProvider(type = OrdersRmaBillBuilder.class, method = "findBySearchParamWithOrdersLink")
    List<OrdersRmaBillDO> findBySearchParamWithOrdersLink(OrdersBillSearchParam param);


    @SelectProvider(type = OrdersRmaBillBuilder.class, method = "countBySearchParam")
    Integer countBySearchParam(OrdersBillSearchParam param);

    @Select("select evidence_image_json_array  from orders_rma_bill b where b.id =#{0}")
    String findPhotos(String rmaId);

    @Select("select *  from orders_rma_bill b where b.tracking_no =#{0}")
    OrdersRmaBill queryRmaBillByTrackingNo(String trackingNo);

    @SelectProvider(type = OrdersRmaBillBuilder.class, method = "updateSelective")
    Integer updateSelective(OrdersRmaBill ordersRmaBill);

    @Select("select *  from orders_rma_bill b where b.orders_id =#{0} and (b.rma_status_enum!=3 and b.rma_status_enum!=5) and b.change_type_enum=1")
    OrdersRmaBill findOrdersRmaBillByOrderId(Integer id);


    @Select("select *  from orders_rma_bill b where b.orders_id =#{orderId} and b.rma_status_enum != #{state} ")
    List<OrdersRmaBill> findOrdersRmaBillByOrderIdAndNotInState(@Param("orderId") Integer orderId, @Param("state") Integer state);

    /**
     * 根据退货/拒收申请单号查询申请单信息
     *
     * @param orderChangeId
     * @return
     * @date 2017年11月28日
     * @author liusl
     */
    @Select("SELECT d.* from orders_rma_bill d where d.id=#{0}")
    OrdersRmaBill findOrdersChangeByOrderchangeId(Integer orderChangeId);

    /**
     * 根据订单号查询退换货信息
     *
     * @param changeIds
     * @return
     * @date 2017年11月28日
     * @author liusl
     */
    @Select("<script>select d.* from orders_rma_bill d "
            + "where d.id in  "
            + "<foreach item=\"item\" index=\"index\" collection=\"array\" open=\"(\" separator=\",\" close=\")\"> "
            + "#{item} "
            + "</foreach>"
            + "</script>")
    List<OrdersRmaBill> findOrdersChangeByOrderchangeIds(Integer[] changeIds);

    /**
     * 找出能够匹配到的订单 在订单表中有记录，但是在退换货中没有记录
     *
     * @param paramMap
     * @return
     * @date 2017年11月28日
     * @author liusl
     */
    @Select("<script>select o.id as orderId, o.tracking_no as orderTrack,c.id as changeId,c.apply_status_enum as applyStatus from orders o left join orders_rma_bill c on c.merchant_order_no=o.merchant_order_no "
            + "where o.tracking_no in  "
            + "<foreach item=\"item\" index=\"index\" collection=\"trackingInfos\" open=\"(\" separator=\",\" close=\")\"> "
            + "#{item.trackingNo} "
            + "</foreach>"
            + "</script>")
    List<OrdersChangeExport> findOrderChangeByTarchNo(Map<String, Object> paramMap);

    @Select("<script>" +
            "SELECT * FROM orders_rma_bill WHERE orders_id in " +
            "<foreach item=\"item\" index=\"index\" collection=\"list\"\n" +
            "     open=\"(\" separator=\",\" close=\")\">\n" +
            "        #{item}\n" +
            "  </foreach>" +
            "</script>")
    List<OrdersRmaBill> findByOrderIds(List<Integer> orderIds);

    @Select("select count(1) from orders_rma_bill b where b.id=#{id} ")
    Integer countOrdersRejectByOrderId(@Param("id") Integer id);

    @Select("<script> " +
            " select orders_id as orderId from  orders_rma_bill l where l.rma_status_enum !=5 and change_type_enum=0 and l.orders_id in" +
            " <foreach collection=\"list\" item=\"item\" " +
            "  index=\"index\" open=\"(\" close=\")\" separator=\",\"> " +
            "  #{item}" +
            " </foreach>" +
            " </script>")
    List<Integer> queryOrderByOrderIds(List<Integer> orderId);

    @Insert("<script>"
            + "INSERT INTO orders_rma_bill (order_tracking_no,merchant_order_no,tracking_no,warehouse_id,warehouse_name,logistics_id,logistic_name,order_amount,left_symbol,right_symbol,                        seo_department_id,department_user_info,web_url,change_type_enum,change_reason_enum,change_reason_other_memo,change_way_enum,recycle_goods_enum,rma_source_enum,rma_status_enum," +
            "purchase_at,pay_method_enum,refund_amount,apply_user_id,apply_user_name,apply_time,question_memo,currency_name,orders_id,create_at,creator_id,creator,refund_amount_show,zone_id,apply_memo " +
            ") values"
            + " <foreach collection =\"list\" item=\"o\" index= \"index\" separator =\",\"> "
            + "(#{o.orderTrackingNo},#{o.merchantOrderNo},#{o.trackingNo},#{o.warehouseId},#{o.warehouseName},#{o.logisticsId},#{o.logisticName},#{o.orderAmount},#{o.leftSymbol},#{o.rightSymbol}," +
            "  #{o.seoDepartmentId},#{o.departmentUserInfo},#{o.webUrl},#{o.changeTypeEnum},#{o.changeReasonEnum},#{o.changeReasonOtherMemo},#{o.changeWayEnum},#{o.recycleGoodsEnum},#{o.rmaSourceEnum},#{o.rmaStatusEnum}," +
            "  #{o.purchaseAt},#{o.payMethodEnum},#{o.refundAmount},#{o.applyUserId},#{o.applyUserName},now(),#{o.questionMemo},#{o.currencyName},#{o.ordersId},now(),#{o.creatorId},#{o.creator},#{o.refundAmountShow},#{o.zoneId},#{o.applyMemo})"
            + "</foreach> "
            + "</script>")
    Integer batchInsert(List<OrdersRmaBill> ordersRmaBills);


    class OrdersRmaBillBuilder extends AbstractBuilder<OrdersRmaBill> {

        @Override
        public void buildSelectOther(SQL sql) {

        }

        @Override
        public void buildJoin(SQL sql) {

        }

        @Override
        public void buildWhere(SQL sql, OrdersRmaBill param) {

        }

        private String selectSql = " select id, merchant_order_no, tracking_no, logistics_id, logistic_name, order_amount, seo_department_id, department_user_info, web_url, change_type_enum, " +
                "change_reason_enum, change_way_enum, recycle_goods_enum, rma_source_enum, rma_status_enum, pay_method_enum, refund_amount, apply_user_id, apply_user_name, " +
                "apply_time, apply_memo, question_memo, check_user_id, check_user_name, check_time, check_memo, c.orders_id, change_at, create_at, update_at, creator_id, " +
                " creator, refund_amount_show, state_time, refund_id, customer_get_account ,purchase_at,refund_id,warehouse_name,left_symbol,right_symbol,customer_get_account,currency_name,order_tracking_no"
                + " from orders_rma_bill c ";

        private String selectSqlWithOrdersLink = " select c.id, merchant_order_no, tracking_no, logistics_id, logistic_name, order_amount, seo_department_id, department_user_info, web_url, change_type_enum, " +
                "change_reason_enum, change_way_enum, recycle_goods_enum, rma_source_enum, rma_status_enum, pay_method_enum, refund_amount, apply_user_id, apply_user_name, " +
                "apply_time, apply_memo, question_memo, check_user_id, check_user_name, check_time, check_memo, c.orders_id, c.change_at, c.create_at, c.update_at, c.creator_id, " +
                " c.creator, refund_amount_show, c.state_time, refund_id, customer_get_account ,purchase_at,refund_id,warehouse_name,left_symbol,right_symbol,customer_get_account,currency_name,order_tracking_no"
                + ",l.first_name,l.telphone,l.address "
                + " from orders_rma_bill c left join orders_link l on l.orders_id = c.orders_id ";

        public String updateOrderState(@Param("id") Integer id, @Param("state") String state, @Param("stateTime") LocalDateTime stateTime) {
            return "UPDATE orders_rma_bill set rma_status_enum=" + OrdersRmaStateEnum.fromName(state).ordinal() + " , state_time=#{stateTime} where id=#{id}";
        }

        /**
         * 退换货订单列表查找
         *
         * @param param
         * @return
         */
        public String findBySearchParam(OrdersBillSearchParam param) {

            //分页默认值
            if (param.getStart() == null) {
                param.setStart(0);
            }
            if (param.getLimit() == null) {
                param.setLimit(20);
            }


            StringBuilder sb = new StringBuilder();
            sb.append(selectSql);
            sb.append(" where 1=1 ");

            setWhereSql(sb, param);

            return sb.toString();

        }

        public String findBySearchParamWithOrdersLink(OrdersBillSearchParam param) {

            //分页默认值
            if (param.getStart() == null) {
                param.setStart(0);
            }
            if (param.getLimit() == null) {
                param.setLimit(20);
            }


            StringBuilder sb = new StringBuilder();
            sb.append(selectSqlWithOrdersLink);
            sb.append(" where 1=1 ");

            setWhereSql(sb, param);

            return sb.toString();

        }

        private void setWhereSql(StringBuilder sb, OrdersBillSearchParam param) {
            //权限相关(个人)
            Integer creatorId = param.getCreatorId();
            if (creatorId != null) {
                sb.append(" creator_id=#{creatorId} ");
            }
            //先让退货订单有数据，后面在处理这个
            if (param.getDepartmentIds() != null && param.getDepartmentIds().size() != 0) {
                String ids = Joiner.on(",").join(param.getDepartmentIds());
                sb.append(" and c.seo_department_id in (" + ids + ") ");
            }

            if (param.getZoneIds() != null && param.getZoneIds().size() != 0) {
                String ids = Joiner.on(",").join(param.getZoneIds());
                sb.append(" and c.zone_id in (" + ids + ") ");
            }

            //退换货的数据搜索
            String merchantOrderNo = param.getMerchantOrderNo();
            if (!Strings.isNullOrEmpty(merchantOrderNo)) {
                sb.append(" and c.merchant_order_no = #{merchantOrderNo} ");
            }
            String rmaId = param.getRmaId();
            if (!Strings.isNullOrEmpty(rmaId)) {
                sb.append(" and c.id = #{rmaId} ");
            }
            String orderId = param.getOrderId();
            if (!Strings.isNullOrEmpty(orderId)) {
                sb.append(" and c.orders_id = #{orderId} ");
            }
            //站点来源
            String webUrl = param.getWebUrl();
            if (!Strings.isNullOrEmpty(webUrl)) {
                sb.append(" and c.web_url = #{webUrl} ");
            }
            //退货单状态
            OrdersRmaStateEnum rmaStatusEnum = param.getRmaStatusEnum();
            if (rmaStatusEnum != null) {
                sb.append(" and c.rma_status_enum = #{rmaStatusEnum} ");
            }
            //类型
            ChangeTypeEnum changeTypeEnum = param.getChangeTypeEnum();
            if (changeTypeEnum != null) {
                sb.append(" and c.change_type_enum = #{changeTypeEnum} ");
            }
            //是否回收
            RecycleGoodsEnum recycleGoodsEnum = param.getRecycleGoodsEnum();
            if (recycleGoodsEnum != null) {
                sb.append(" and c.recycle_goods_enum = #{recycleGoodsEnum} ");
            }
            //售后来源
            RmaSourceEnum rmaSourceEnum = param.getRmaSourceEnum();
            if (rmaSourceEnum != null) {
                sb.append(" and c.rma_source_enum = #{rmaSourceEnum} ");
            }
            //退货原因
            ChangeReasonEnum changeReasonEnum = param.getChangeReasonEnum();
            if (changeReasonEnum != null) {
                sb.append(" and c.change_reason_enum = #{changeReasonEnum} ");
            }
            //申请开始时间
            LocalDateTime applyStartTime = param.getApplyStartTime();
            if (applyStartTime != null) {
                sb.append(" and c.apply_time >= #{applyStartTime} ");
            }
            //申请结束时间
            LocalDateTime endStartTime = param.getApplyEndTime();
            if (endStartTime != null) {
                sb.append(" and c.apply_time <= #{applyEndTime} ");
            }
            //该字段没有时，默认降序
            if (Strings.isNullOrEmpty(param.getSortOrder())) {
                sb.append(" order by c.create_at desc  ");
            } else if ("asc".equals(param.getSortOrder())) {
                sb.append(" order by c.create_at asc ");
            } else if ("desc".equals(param.getSortOrder())) {
                sb.append(" order by c.create_at desc  ");
            }
            sb.append(" limit #{start}, #{limit} ");
        }


        public String countBySearchParam(OrdersBillSearchParam param) {

            StringBuilder sb = new StringBuilder();


            //订单-订单联系人  ====》 一对一关系
            sb.append(" select count(1) " +
                    " from orders_rma_bill c");

            sb.append(" where 1=1 ");

            //权限相关(个人)
            Integer creatorId = param.getCreatorId();
            if (creatorId != null) {
                sb.append(" creator_id=#{creatorId} ");
            }
            //先让退货订单有数据，后面在处理这个
            if (param.getDepartmentIds() != null && param.getDepartmentIds().size() != 0) {
                String ids = Joiner.on(",").join(param.getDepartmentIds());
                sb.append(" and c.seo_department_id in (" + ids + ") ");
            }

            if (param.getZoneIds() != null && param.getZoneIds().size() != 0) {
                String ids = Joiner.on(",").join(param.getZoneIds());
                sb.append(" and c.zone_id in (" + ids + ") ");
            }
            //退换货的数据搜索
            String merchantOrderNo = param.getMerchantOrderNo();
            if (!Strings.isNullOrEmpty(merchantOrderNo)) {
                sb.append(" and c.merchant_order_no = #{merchantOrderNo} ");
            }
            String rmaId = param.getRmaId();
            if (!Strings.isNullOrEmpty(rmaId)) {
                sb.append(" and c.id = #{rmaId} ");
            }
            String orderId = param.getOrderId();
            if (!Strings.isNullOrEmpty(orderId)) {
                sb.append(" and c.orders_id = #{orderId} ");
            }
            //站点来源
            String webUrl = param.getWebUrl();
            if (!Strings.isNullOrEmpty(webUrl)) {
                sb.append(" and c.web_url = #{webUrl} ");
            }
            //退货单状态
            OrdersRmaStateEnum rmaStatusEnum = param.getRmaStatusEnum();
            if (rmaStatusEnum != null) {
                sb.append(" and c.rma_status_enum = #{rmaStatusEnum} ");
            }
            //类型
            ChangeTypeEnum changeTypeEnum = param.getChangeTypeEnum();
            if (changeTypeEnum != null) {
                sb.append(" and c.change_type_enum = #{changeTypeEnum} ");
            }
            //是否回收
            RecycleGoodsEnum recycleGoodsEnum = param.getRecycleGoodsEnum();
            if (recycleGoodsEnum != null) {
                sb.append(" and c.recycle_goods_enum = #{recycleGoodsEnum} ");
            }
            //售后来源
            RmaSourceEnum rmaSourceEnum = param.getRmaSourceEnum();
            if (rmaSourceEnum != null) {
                sb.append(" and c.rma_source_enum = #{rmaSourceEnum} ");
            }
            //退货原因
            ChangeReasonEnum changeReasonEnum = param.getChangeReasonEnum();
            if (changeReasonEnum != null) {
                sb.append(" and c.change_reason_enum = #{changeReasonEnum} ");
            }
            //申请开始时间
            LocalDateTime applyStartTime = param.getApplyStartTime();
            if (applyStartTime != null) {
                sb.append(" and c.apply_time >= #{applyStartTime} ");
            }
            //申请结束时间
            LocalDateTime endStartTime = param.getApplyEndTime();
            if (endStartTime != null) {
                sb.append(" and c.apply_time <= #{applyEndTime} ");
            }

            return sb.toString();

        }

    }

    @Update("update orders_rma_bill set change_reason=#{changeReason},change_from=#{changeFrom} WHERE id=#{changeId} and change_type_enum = #{changeType}")
    void updateOrderChange(OrdersChangeExport update);

    @Update("update orders_rma_bill set recycle_goods=#{recycleGoods},return_amount=#{returnAmount},memo=#{memo} WHERE tracking_no=#{trackingNo} and id = #{id}")
    void updateChange(OrdersRmaBill ordersChange);

    @Update("UPDATE orders_rma_bill SET tracking_no = #{trackingNo} WHERE id = #{id}")
    Integer updateTrackingNo(@Param("id") Integer id, @Param("trackingNo") String trackingNo);

    @Select("SELECT d.* from orders_rma_bill d where d.merchant_order_no=#{merchantOrderNo}")
    OrdersRmaBill findByMerchantOrderNo(String merchantOrderNo);

    @Select("SELECT d.* from orders_rma_bill d where d.orders_id=#{orderId}")
    List<OrdersRmaBill> findByOrderId(@Param("orderId") Integer orderId);

}
