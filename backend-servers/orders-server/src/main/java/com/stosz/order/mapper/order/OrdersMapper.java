package com.stosz.order.mapper.order;

import com.google.common.base.Joiner;
import com.google.common.base.Strings;
import com.stosz.admin.ext.enums.JobAuthorityRelEnum;
import com.stosz.fsm.IFsmDao;
import com.stosz.order.ext.dto.*;
import com.stosz.order.ext.enums.OrderStateEnum;
import com.stosz.order.ext.enums.WarehouseTypeEnum;
import com.stosz.order.ext.model.*;
import com.stosz.plat.mapper.AbstractBuilder;
import com.stosz.plat.utils.CollectionUtils;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.jdbc.SQL;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Repository
public interface OrdersMapper extends IFsmDao {

    String Base_Column_List = " merchant_enum, merchant_order_no, order_type_enum, zone_id, zone_code, zone_name, \n" +
            "    currency_code, order_exchange_rate, order_amount, confirm_amount, goods_qty, ip, \n" +
            "    ip_name, payment_method, pay_state, memo, purchaser_at, get_code, input_code, order_status_enum, \n" +
            "    seo_department_id, department_user_info, bu_department_id, black_fields, repeat_info, \n" +
            "    code_type, ip_order_qty, seo_user_id, seo_user_name, create_at, update_at, creator_id, \n" +
            "    creator, old_id, state_time, order_title, domain, warehouse_id, warehouse_name, tracking_no, \n" +
            "    logistics_name, logistics_id, tracking_memo, tracking_status, tracking_status_show, \n" +
            "    warehouse_memo, warehouse_type_enum, order_inner_title, amount_show, invalid_reason_type, \n" +
            "    audit_time, assign_product_time, combo_id, combo_name, cancel_reason_enum, customer_message ";

    String Base_Column_Insert = "#{o.merchantEnum}, #{o.merchantOrderNo},#{o.orderTypeEnum}, #{o.zoneId}, #{o.zoneCode}, #{o.zoneName}, #{o.currencyCode}, #{o.orderExchangeRate}, \n" +
            "      #{o.orderAmount}, #{o.confirmAmount}, #{o.goodsQty}, #{o.ip}, #{o.ipName}, #{o.paymentMethod}, #{o.payState}, #{o.memo}, #{o.purchaserAt}, #{o.getCode}, #{o.inputCode}, #{o.orderStatusEnum}, #{o.seoDepartmentId}, #{o.departmentUserInfo}, #{o.buDepartmentId}, #{o.blackFields}, #{o.repeatInfo}, #{o.codeType}, #{o.ipOrderQty}, #{o.seoUserId}, #{o.seoUserName}, #{o.createAt}, #{o.updateAt}, #{o.creatorId}, #{o.creator}, #{o.oldId}, #{o.stateTime}, #{o.orderTitle}, #{o.domain}, #{o.warehouseId}, #{o.warehouseName}, #{o.trackingNo}, #{o.logisticsName}, #{o.logisticsId}, #{o.trackingMemo}, #{o.trackingStatus}, #{o.trackingStatusShow}, #{o.warehouseMemo}, #{o.warehouseTypeEnum}, #{o.orderInnerTitle}, #{o.amountShow}, #{o.invalidReasonType}, #{o.auditTime}, #{o.assignProductTime}, #{o.comboId}, #{o.comboName}, #{o.cancelReasonEnum}, #{o.customerMessage}";

    @UpdateProvider(type = OrdersBuilder.class, method = "updateOrderState")
    @Override
    Integer updateState(@Param("id") Integer id, @Param("state") String state, @Param("stateTime") LocalDateTime stateTime);

    @InsertProvider(type = OrdersBuilder.class, method = "insert")
    int insert(Orders param);

    @DeleteProvider(type = OrdersBuilder.class, method = "delete")
    int delete(@Param("id") Integer id);

    @UpdateProvider(type = OrdersBuilder.class, method = "update")
    int update(Orders param);

    @Update("UPDATE orders set order_title=#{title} where id=#{id} ")
    int updateTitle(@Param("title") String title, @Param("id") Long id);

    @SelectProvider(type = OrdersBuilder.class, method = "find")
    List<Orders> find(Orders param);

    @SelectProvider(type = OrdersBuilder.class, method = "count")
    int count(Orders param);

    @SelectProvider(type = OrdersBuilder.class, method = "getById")
    Orders getById(@Param("id") Integer id);

    @Select("<script>" +
            "SELECT * FROM orders WHERE old_id in " +
            "<foreach item=\"item\" index=\"index\" collection=\"list\"\n" +
            "     open=\"(\" separator=\",\" close=\")\">\n" +
            "        #{item}\n" +
            "  </foreach>" +
            "</script>")
    List<Orders> findByOldIds(List<Integer> ids);

    @SelectProvider(type = OrdersBuilder.class, method = "findBySearchParam")
    List<OrderDO> findBySearchParam(OrderSearchParam param);

    @SelectProvider(type = OrdersBuilder.class, method = "countBySearchParam")
    Integer countBySearchParam(OrderSearchParam param);

    @Select("SELECT IFNULL(MAX(old_id),0) from orders")
    Integer getMaxOldId();

    @Select("SELECT * FROM orders WHERE tracking_no=#{trackingNo}")
    Orders findByTrackingNo(@Param("trackingNo") String trackingNo);

    @Select("select * from orders o where o.ip = #{ip} and o.create_at >= #{beginAt} and o.create_at <= #{endAt}")
    List<Orders> findByIpAfterBetweenCreateAt(@Param("ip") String ip, @Param("beginAt") LocalDateTime beginAt, @Param("endAt") LocalDateTime endAt);

    @Update("UPDATE orders set memo=#{memo} where id=#{id} ")
    int updateMemoByOrderId(@Param("id") Integer id, @Param("memo") String memo);

    @Update("UPDATE orders set repeat_info=#{repeatInfo} where id=#{id} ")
    int updateRepeatInfoByOrderId(@Param("id") Integer id, @Param("repeatInfo") String repeatInfo);

    @Update("<script>" +
            "    update orders \n" +
            "    set  ip_order_qty = #{ipOrderQty}\n" +
            "    where id in\n" +
            "    <foreach collection=\"idList\" index=\"index\" item=\"item\" \n" +
            "        separator=\",\" open=\"(\" close=\")\">\n" +
            "        #{item}\n" +
            "    </foreach>" +
            "</script>")
    int updateIpIOrderQtyByOrderId(@Param("idList") List<Integer> idList, @Param("ipOrderQty") Integer ipOrderQty);

    @Update("UPDATE orders set invalid_reason_type=#{invalidReasonType}, audit_time=#{auditTime}, memo=#{memo} where id=#{id} ")
    int updateForAuditByOrderId(@Param("id") Integer id, @Param("invalidReasonType") Integer invalidReasonType, @Param("auditTime") LocalDateTime auditTime, @Param("memo") String memo);

    @SelectProvider(type = OrdersBuilder.class, method = "findOrderByOrderIds")
    List<Orders> findOrderByOrderIds(@Param("orderIds") List<Integer> ids);

    @SelectProvider(type = OrdersBuilder.class, method = "findOrderByMerchantOrderNos")
    List<Orders> findOrderByMerchantOrderNos(@Param("merchantOrderNos") List<String> merchantOrderNos);

    @Select("SELECT * FROM orders WHERE ip=#{ip} and domain=#{domain} and create_at >= #{beginAt} and create_at <= #{endAt}")
    List<Orders> findByIpAndDomainBetweenCreateAt(@Param("ip") String ip, @Param("domain") String domain, @Param("beginAt") LocalDateTime beginAt, @Param("endAt") LocalDateTime endAt);

    /**
     * 通过运单号查询订单
     *
     * @param ids
     * @return
     */
    @SelectProvider(type = OrdersBuilder.class, method = "findOrderByTrackingIds")
    List<Orders> findOrderByTrackingIds(@Param("trackingIds") List<String> ids);


    @Insert("<script>"
            + "insert into orders( "
            + Base_Column_List
            + " ) "
            + "values "
            + " <foreach collection =\"list\" item=\"o\" index= \"index\" separator =\",\"> "
            + "( "
            + Base_Column_Insert
            + " )"
            + "</foreach> "
            + "</script>")
    Integer batchInsert(List<Orders> items);

    @Select("SELECT * FROM orders WHERE old_id=#{oldId}")
    Orders getByOldId(@Param("oldId") Integer idOrder);

    @Select("SELECT * FROM orders WHERE merchant_order_no=#{orderNo}")
    Orders getOrderByOrderNo(String orderNo);

    @SelectProvider(type = OrdersBuilder.class, method = "findBySkuAndDept")
    List<Orders> findBySkuAndDept(@Param("sku") String sku, @Param("dept") Integer dept);

    @Select("SELECT * FROM orders_item where orders_id=#{orderId}")
    List<OrdersItem> findOrdersItemByOrderId(@Param("orderId") Integer orderId);

    @Update("UPDATE orders SET warehouse_id=#{warehouseId},warehouse_name=#{warehouseName},warehouse_type_enum=#{warehouseTypeEnum},warehouse_memo=#{warehouseMemo},warehouse_code=#{warehouseSysCode} WHERE id=#{id}")
    Integer updateOrderWarehouseInfo(@Param("warehouseId") Integer warehouseId, @Param("warehouseName") String warehouseName, @Param("warehouseTypeEnum") WarehouseTypeEnum warehouseTypeEnum, @Param("warehouseMemo") String warehouseMemo,@Param("warehouseSysCode") String warehouseSysCode,  @Param("id") Integer orderId);

    @SelectProvider(type = OrdersBuilder.class, method = "findSkuNeedDetail")
    List<DeptAssignQtyDto> findSkuNeedDetail(@Param("zoneIdSet") Set<Integer> zoneIdSet, @Param("sku") String sku, @Param("orderStateEnums") HashSet<OrderStateEnum> orderStateEnums, @Param("deptSet") Set<Integer> deptSet);

    @SelectProvider(type = OrdersBuilder.class, method = "querySumByParam")
    public Integer querySumByParam(Map<String, Object> param);

    @Update("UPDATE orders set logistics_id=#{shippingWayId},logistics_name=#{shippingName}, tracking_no=#{trackingNo} where id=#{orderId}")
    void updateOrderLogisticsInfo(TransportRequest transportRequest);

    @Select("<script> " +
            "select o.id,o.merchant_order_no,o.zone_id ,o.purchaser_at,l.province,l.city, l.area,o.zone_name,o.ip,o.ip_name,o.ip_order_qty,o.domain,o.black_fields,o.order_status_enum,o.payment_method,o.pay_state," +
            "l.first_name,l.last_name,l.country, l.customer_id, l.email,l.telphone,o.code_type,o.get_code,o.input_code,o.order_amount,o.amount_show,o.seo_user_name,o.goods_qty,o.order_title,l.address,l.zipcode," +
            "o.customer_message,o.memo,o.warehouse_id,o.warehouse_name,o.tracking_no,o.tracking_status_show,o.logistics_name,o.logistics_id,o.create_at,o.repeat_info,o.black_fields from orders o" +
            " INNER JOIN orders_link l on l.orders_id = o.id where o.id in  " +
            " <foreach item=\"item\" index=\"index\" collection=\"list\" open=\"(\" separator=\",\" close=\")\">" +
            " #{item} " +
            " </foreach>" +
            "</script>")
    List<OrderDO> queryOrdersByOrderIds(List<String> orderIds);

    @Select("<script> " +
            "select o.id,o.merchant_order_no,o.zone_id ,o.purchaser_at,o.province,o.city, l.area,o.zone_name,o.ip,o.ip_name,o.ip_order_qty,o.domain,o.black_fields,o.order_status_enum,o.payment_method,o.pay_state," +
            "l.first_name,l.last_name,l.country, l.customer_id, l.email,l.telphone,o.code_type,o.get_code,o.input_code,o.order_amount,o.amount_show,o.seo_user_name,o.goods_qty,o.order_title,l.address,l.zipcode," +
            "o.customer_message,o.memo,o.warehouse_id,o.warehouse_name,o.tracking_no,o.tracking_status_show,o.logistics_name,o.logistics_id,o.create_at,o.repeat_info,o.black_fields from orders o" +
            " INNER JOIN orders_link l on l.orders_id = o.id where o.id in  " +
            " <foreach item=\"item\" index=\"index\" collection=\"orderIds\" open=\"(\" separator=\",\" close=\")\">" +
            " #{item} " +
            " </foreach>" +
            "</script>")
    List<CombOrderItem> queryOrderByOrderIds(List<String> orderIds);

    @Select("<script> " +
            "select o.id as orderIdOld,o.bu_department_id deptId,o.department_user_info as deptName,o.order_inner_title as innerName,group_concat(i.sku) as sku,o.order_status_enum as status,o.tracking_no as trackingNoOld" +
            " from orders o INNER JOIN orders_item i on o.id=i.orders_id" +
            " where o.tracking_no in  " +
            " <foreach item=\"item\" index=\"index\" collection=\"trackingNos\" open=\"(\" separator=\",\" close=\")\">" +
            " #{item} " +
            " </foreach>" +
            " GROUP BY o.id " +
            "</script>")
    List<TransitStockDTO> queryOrdersByTrackingNos(@Param("trackingNos") List<String> trackingNos);

    @SelectProvider(type = OrdersBuilder.class, method = "findOrderSkuQtyDetail")
    List<DeptOrderQtyTimeDto> findOrderSkuQtyDetail(@Param("zoneIdSet") Set<Integer> zoneIdSet, @Param("sku") String sku, @Param("orderStateEnums") HashSet<OrderStateEnum> orderStateEnums,@Param("deptSet") Set<Integer> deptSet);

    @Update("")
    Integer updateTmsInfo(Integer shippingWayId, String shippingName, String trackingNo, Integer orderId);

    @Select("SELECT * FROM orders_addition where orders_id=#{ordersId} limit 1 ")
    OrdersAddition getOrderAddition(@Param("ordersId") Integer ordersId);


    class OrdersBuilder extends AbstractBuilder<Orders> {

        private org.slf4j.Logger logger = LoggerFactory.getLogger(OrdersBuilder.class);

        @Override
        public void buildSelectOther(SQL sql) {

        }

        @Override
        public void buildJoin(SQL sql) {

        }

        @Override
        public void buildWhere(SQL sql, Orders param) {

        }

        public String findOrderSkuQtyDetail(@Param("zoneIdSet") Set<Integer> zoneIdSet, @Param("sku") String sku, @Param("orderStateEnums") HashSet<OrderStateEnum> orderStateEnums,@Param("deptSet") Set<Integer> deptSet) {

            SQL sql = new SQL()
                    .SELECT(" o.seo_department_id as dept, o.id as orderId, item.qty as qty, item.create_at as createAt ")
                    .FROM("orders o ")
                    .JOIN("orders_item item ON item.orders_id = o.id ")
                    .WHERE("item.sku ='" + sku + "'");
            if (CollectionUtils.isNotNullAndEmpty(zoneIdSet)) {
                sql.
                        AND()
                        .WHERE("o.zone_id   in " + zoneIdSet.stream().map(item -> String.valueOf(item)).collect(Collectors.joining(",", "(", ")")));
            }

            if (CollectionUtils.isNotNullAndEmpty(deptSet)) {
                sql.
                        AND()
                        .WHERE("o.seo_department_id   in " + deptSet.stream().map(item -> String.valueOf(item)).collect(Collectors.joining(",", "(", ")")));
            }

            sql.AND()
                    .WHERE("o.order_status_enum in " + orderStateEnums.stream().map(item -> String.valueOf(item.ordinal())).collect(Collectors.joining(",", "(", ")")))
                    .ORDER_BY("item.create_at asc ")
                    .toString();

            return sql.toString();

        }


        public String findSkuNeedDetail(@Param("zoneIdSet") Set<Integer> zoneIdSet, @Param("sku") String sku, @Param("orderStateEnums") HashSet<OrderStateEnum> orderStateEnums,@Param("deptSet") Set<Integer> deptSet) {

            SQL sql = new SQL()
                    .SELECT(" o.seo_department_id as deptId , sum(item.qty) as qty")
                    .FROM("orders o ")
                    .JOIN("orders_item item ON item.orders_id = o.id ")
                    .WHERE("item.sku ='" + sku + "'");

            if (CollectionUtils.isNotNullAndEmpty(zoneIdSet)) {
                sql.
                        AND()
                        .WHERE("o.zone_id   in " + zoneIdSet.stream().map(item -> String.valueOf(item)).collect(Collectors.joining(",", "(", ")")));
            }


            if (CollectionUtils.isNotNullAndEmpty(deptSet))
            {
                sql.
                        AND()
                        .WHERE("o.seo_department_id   in " + deptSet.stream().map(item -> String.valueOf(item)).collect(Collectors.joining(",", "(", ")")));
            }

            sql
                    .AND()
                    .WHERE("o.order_status_enum in " + orderStateEnums.stream().map(item -> String.valueOf(item.ordinal())).collect(Collectors.joining(",", "(", ")")))
                    .GROUP_BY("o.seo_department_id ");

            return sql.toString();
        }


        public String findBySkuAndDept(@Param("sku") String sku, @Param("dept") Integer dept) {

            SQL sql = new SQL()
                    .SELECT("o.*")
                    .FROM(getTableName() + " o ")
                    .LEFT_OUTER_JOIN("orders_item oi ON oi.orders_id = o.id ")
                    .WHERE("o.seo_department_id=#{dept} ")
                    .AND()
                    .WHERE("oi.sku = #{sku}")
                    .AND()
                    .WHERE("o.order_status_enum in(" + OrderStateEnum.auditPass.ordinal() + "," + OrderStateEnum.waitPurchase.ordinal() + ")")
                    .ORDER_BY("o.create_at asc ");

            return sql.toString();
        }


        public String updateOrderState(@Param("id") Integer id, @Param("state") String state, @Param("stateTime") LocalDateTime stateTime) {
            return "UPDATE orders set order_status_enum=" + OrderStateEnum.fromName(state).ordinal() + " , state_time=#{stateTime} where id=#{id}";
        }

        public String querySumByParam(Map<String, Object> param) {

            SQL sql = new SQL()
                    .SELECT("SUM(i.qty)")
                    .FROM("orders_item i")
                    .INNER_JOIN(getTableName() + " o ON o.id = i.orders_id");
            if (notEmpty(param.get("sku"))) sql.AND().WHERE("i.sku =#{sku}");
            if (notEmpty(param.get("states"))) sql.AND().WHERE("o.order_status_enum IN (" + param.get("states") + ")");
            if (notEmpty(param.get("deptId"))) sql.AND().WHERE("o.seo_department_id =#{deptId}");
            if (notEmpty(param.get("startTime"))) sql.AND().WHERE("o.purchaser_at >=#{startTime}");
            if (notEmpty(param.get("endtTime"))) sql.AND().WHERE("o.purchaser_at <=#{endtTime}");

            return sql.toString();
        }


        /**
         * 查询订单信息
         *
         * @param trackingNos    运单号
         * @param orderIds       订单号
         * @param orderStartTime 下单开始时间
         * @param orderEndTime   下单结束时间
         * @return
         * @date 2017年11月24日
         * @author liusl
         */
        public String queryOrderByParams(List<String> trackingNos, List<String> orderIds, LocalDateTime orderStartTime, LocalDateTime orderEndTime) {

            StringBuilder sb = new StringBuilder();

            sb.append(" select * from orders o where 1=1 ");

            if (trackingNos != null && trackingNos.size() != 0) {
                String trackings = Joiner.on(",").join(trackingNos);
                sb.append(" and o.tracking_no in (" + trackings + ") ");
            }

            if (orderIds != null && orderIds.size() != 0) {
                String ids = Joiner.on(",").join(orderIds);
                sb.append(" and o.id in (" + ids + ") ");
            }

            return sb.toString();

        }

        // TODO: 2017/11/23  索引优化

        /**
         * 订单列表查找
         *
         * @param param
         * @return
         */
        public String findBySearchParam(OrderSearchParam param) {

            StringBuilder sb = new StringBuilder();

            //订单-订单联系人  ====》 一对一关系
            sb.append(" select o.id,o.merchant_order_no,o.zone_name,o.ip,o.ip_name,o.ip_order_qty,o.domain,o.black_fields,o.order_status_enum,o.payment_method,o.pay_state, " +
                    " o.code_type,o.get_code,o.input_code,o.order_amount,o.amount_show,o.department_user_info,o.seo_user_name," +
                    " o.goods_qty,o.order_title,o.customer_message,o.memo," +
                    " o.warehouse_id,o.warehouse_name,o.tracking_no,o.tracking_status_show,o.logistics_name,o.logistics_id,o.create_at, " +
                    " o.repeat_info,o.black_fields " +
                    " from orders o ");
//                    .append(" INNER JOIN orders_link l on l.orders_id = o.id ");
//                    .append(" LEFT JOIN orders_link l on l.orders_id = o.id ");

            sb.append(" where 1=1 ");


            //被限制查看的地区
            if (param.getZoneIds() != null && param.getZoneIds().size() != 0) {
                String ids = Joiner.on(",").join(param.getZoneIds());
                sb.append(" and o.zone_id in (" + ids + ") ");
            }

            if (param.getAuthorityType() != null && param.getAuthorityType() == JobAuthorityRelEnum.all.ordinal()) {//公司权限
                if (param.getSearchDepts() != null) {//有搜索部门时，将搜索部门添加到约束
                    String ids = Joiner.on(",").join(param.getSearchDepts());
                    if (!StringUtils.hasText(ids)) {
                        ids = "-1";
                    }
                    sb.append(" and o.seo_department_id in (" + ids + ") ");
                } else {//没有搜索时则没有约束

                }
            } else if (param.getAuthorityType() != null && param.getAuthorityType() == JobAuthorityRelEnum.myDepartment.ordinal()) {//部门权限
                if (param.getSearchDepts() != null && param.getSearchDepts().size() != 0) {//有搜索部门约束时，取搜索部门权限与数据权限部门的交集
                    param.getSearchDepts().retainAll(param.getAuthorityDepts() == null ? new ArrayList<>() : param.getAuthorityDepts());
                    String ids = Joiner.on(",").join(param.getSearchDepts());
                    if (!StringUtils.hasText(ids)) {
                        ids = "-1";
                    }
                    sb.append(" and o.seo_department_id in (" + ids + ") ");
                } else {//没有搜索部门约束时，取数据权限
                    param.setSearchDepts(param.getAuthorityDepts() == null ? new ArrayList<>() : param.getAuthorityDepts());
                    String ids = Joiner.on(",").join(param.getSearchDepts());
                    if (!StringUtils.hasText(ids)) {
                        ids = "-1";
                    }
                    sb.append(" and o.seo_department_id in (" + ids + ") ");
                }
            } else if (param.getAuthorityType() != null && param.getAuthorityType() == JobAuthorityRelEnum.myself.ordinal()) {//个人权限
                if (param.getSearchDepts() != null && param.getSearchDepts().size() != 0) {//有搜索部门约束时
                    param.getSearchDepts().retainAll(param.getAuthorityDepts() == null ? new ArrayList<>() : param.getAuthorityDepts());
                    String ids = Joiner.on(",").join(param.getSearchDepts());
                    if (!StringUtils.hasText(ids)) {
                        ids = "-1";
                    }
                    sb.append(" and o.seo_department_id in (" + ids + ") ");
                } else {//没有搜索部门约束时
                    param.setAuthorityDepts(param.getAuthorityDepts() == null ? new ArrayList<>() : param.getAuthorityDepts());
                    String ids = Joiner.on(",").join(param.getAuthorityDepts());
                    if (!StringUtils.hasText(ids)) {
                        ids = "-1";
                    }
                    sb.append(" and ( o.seo_user_id = #{userId} and o.seo_department_id = #{deptId} or o.seo_department_id in (" + ids + ")  ) ");
                }
            } else {
                throw new RuntimeException("权限异常，请联系管理员");
            }

            //数据权限，有指定部门的权限


            //批量查询
            if (!Strings.isNullOrEmpty(param.getTelBatch())) {
                String ids = Arrays.stream(param.getTelBatch().split(",")).map(s -> "'" + s.trim() + "'")
                        .collect(Collectors.joining(", "));
//                sb.append(" and l.telphone in ( ");
//                sb.append(ids);
//                sb.append(" ) ");

                sb.append(" and o.id in (select DISTINCT l.orders_id from orders_link l where l.telphone in (" + ids + ") ) ");

            }
            if (!Strings.isNullOrEmpty(param.getMerchantOrderNoBatch())) {
                sb.append(" and o.merchant_order_no in ( ");
                sb.append(param.getMerchantOrderNoBatch());
                sb.append(" ) ");
            }
            if (!Strings.isNullOrEmpty(param.getOrderIdBatch())) {
                sb.append(" and o.id in (");
                sb.append(param.getOrderIdBatch());
                sb.append(" ) ");
            }
            if (!Strings.isNullOrEmpty(param.getTrackingNoBatch())) {
                String ids = Arrays.stream(param.getTrackingNoBatch().split(",")).map(s -> "'" + s.trim() + "'")
                        .collect(Collectors.joining(","));
                sb.append(" and o.tracking_no in ( ");
                sb.append(ids);
                sb.append(" ) ");
            }


            //订单表的数据搜索
            if (!Strings.isNullOrEmpty(param.getCustomerName())) {
                sb.append(" and o.id in (select DISTINCT l.orders_id from orders_link l where concat(l.first_name, l.last_name) = #{customerName} ) ");
            }


            if (param.getOrderId() != null) {
                sb.append(" and o.id = #{orderId} ");
            }
            if (param.getMerchantOrderNo() != null) {
                sb.append(" and o.merchant_order_no = #{merchantOrderNo} ");
            }
            if (param.getCodeType() != null) {
                sb.append(" and o.code_type = #{codeType} ");
            }
            if (param.getPayMethod() != null) {
                sb.append(" and o.payment_method = #{payMethod} ");
            }
            if (param.getOrderState() != null) {
                sb.append(" and o.order_status_enum = #{orderState} ");
            }
            if (param.getZoneId() != null) {
                sb.append(" and o.zone_id = #{zoneId} ");
            }
//            if(param.getBuDepartmentId() != null) {
//                sb.append(" and o.bu_department_id = #{buDepartmentId} ");
//            }
            if (param.getWarehouseId() != null) {
                sb.append(" and o.warehouse_id = #{warehouseId} ");
            }
            if (!Strings.isNullOrEmpty(param.getTrackingNo())) {
                sb.append(" and o.tracking_no = #{trackingNo} ");
            }
            if (param.getLogisticId() != null) {
                sb.append(" and o.logistics_id = #{logisticId} ");
            }

            //关键字搜索
            //手机号，模糊搜索
            if (!Strings.isNullOrEmpty(param.getTel())) {
                param.setTel("%" + param.getTel() + "%");
                sb.append(" and o.id in (select DISTINCT l.orders_id from orders_link l where l.telphone like #{tel} ) ");
            }
            //邮箱，精确搜索
            if (!Strings.isNullOrEmpty(param.getEmail())) {
                sb.append(" and o.id in (select DISTINCT l.orders_id from orders_link l where l.email = #{email} ) ");
            }
            //域名，精确搜索
            if (!Strings.isNullOrEmpty(param.getDomain())) {
                sb.append(" and o.domain = #{domain} ");
            }
            //ip，精确搜索
            if (!Strings.isNullOrEmpty(param.getIp())) {
                sb.append(" and o.ip = #{ip} ");
            }
            //地址，模糊搜索
            if (!Strings.isNullOrEmpty(param.getAddress())) {
                param.setAddress("%" + param.getAddress() + "%");
                sb.append(" and o.id in (select DISTINCT l.orders_id from orders_link l where l.address like #{address} ) ");
            }

            /**
             * 需要在业务层拼接，显示订单项属性
             */
            //订单行数据sku或spu搜索 订单-订单行 ===》 一对多关系
            if (!Strings.isNullOrEmpty(param.getSku()) && !Strings.isNullOrEmpty(param.getSpu())) {
                sb.append(" and o.id in (select DISTINCT oi.orders_id from orders_item oi where sku = #{sku} and spu = #{spu}) ");
            } else if (!Strings.isNullOrEmpty(param.getSku())) {
                sb.append(" and o.id in (select DISTINCT oi.orders_id from orders_item oi where sku = #{sku}) ");
            } else if (!Strings.isNullOrEmpty(param.getSpu())) {
                sb.append(" and o.id in (select DISTINCT oi.orders_id from orders_item oi where spu = #{spu}) ");
            }
            if (!Strings.isNullOrEmpty(param.getTitle())) {
                param.setTitle("%" + param.getTitle() + "%");
                sb.append(" and o.id in (select DISTINCT oi.orders_id from orders_item oi where  oi.product_title like #{title} ) ");
            }

            //默认查询三个月，暂不启用！
//            if(param.getTimeRegionBegin() != null){
//                sb.append(" and o.create_at >= #{timeRegionBegin} ");
//            }
//            if(param.getTimeRegionEnd() != null){
//                sb.append(" and o.create_at <= #{timeRegionEnd} ");
//            }

            if (param.getMinShowTime() != null) {
                sb.append(" and o.create_at >= #{minShowTime} ");
            }
            if (param.getMaxShowTime() != null) {
//                LocalDateTime endTime = param.getMaxShowTime();
//                param.setMaxShowTime(endTime.plusHours(23).plusMinutes(59).plusHours(59));
                sb.append(" and o.create_at <= #{maxShowTime} ");
            }
            //该字段没有时，默认降序
            if (Strings.isNullOrEmpty(param.getSortOrder())) {
                sb.append(" order by o.create_at desc  ");
            } else if ("asc".equals(param.getSortOrder())) {
                sb.append(" order by o.create_at asc ");
            } else {
                sb.append(" order by o.create_at desc  ");
            }

            sb.append(" limit "+Optional.ofNullable(param.getStart()).orElse(0) +"," + Optional.ofNullable(param.getLimit()).orElse(20));


            logger.info("订单查询sql:{}", sb.toString());


            return sb.toString();

        }


        public String countBySearchParam(OrderSearchParam param) {

            StringBuilder sb = new StringBuilder();


            //订单-订单联系人  ====》 一对一关系
            sb.append(" select count(1) " +
                    " from orders o ")
                    .append(" LEFT JOIN orders_link l on l.orders_id = o.id ");

            sb.append(" where 1=1 ");


//            if(param.getDepartmentIds() != null && param.getDepartmentIds().size() != 0){
//                String ids = Joiner.on(",").join(param.getDepartmentIds());
//                sb.append(" and o.bu_department_id in (" + ids + ") ");
//            }else {//为空，表示没有权限，上层可感知到，应该在上层直接返回，无需进入dao层
//                sb.append(" and o.bu_department_id in (-1) ");
//            }

//            if(param.getZoneIds() != null && param.getZoneIds().size() != 0){
//                String ids = Joiner.on(",").join(param.getZoneIds());
//                sb.append(" and o.zone_id in (" + ids + ") ");
//            }else {//为空，表示没有权限，上层可感知到，应该在上层直接返回，无需进入dao层
//                sb.append(" and o.zone_id in (-1) ");
//            }

            //订单表的数据搜索
            if (!Strings.isNullOrEmpty(param.getTitle())) {
                param.setTitle("%" + param.getTitle() + "%");
                sb.append(" and o.order_title = #{title} ");
            }
            if (!Strings.isNullOrEmpty(param.getCustomerName())) {
                sb.append(" and concat(l.first_name, l.last_name) = #{customerName} ");
            }
            if (param.getOrderId() != null) {
                sb.append(" and o.id = #{orderId} ");
            }
            if (param.getCodeType() != null) {
                sb.append(" and o.code_type = #{codeType} ");
            }
            if (param.getPayMethod() != null) {
                sb.append(" and o.payment_method = #{payMethod} ");
            }
            if (param.getOrderState() != null) {
                sb.append(" and o.order_status_enum = #{state} ");
            }
            //不再使用zone_code查询
//            if(!Strings.isNullOrEmpty(param.getZoneCode())) {
//                sb.append(" and zone_code = #{zoneCode} ");
//            }
            if (param.getZoneId() != null) {
                sb.append(" and zone_id = #{zoneId} ");
            }
            if (param.getWarehouseId() != null) {
                sb.append(" and o.warehouse_id = #{warehouseId} ");
            }
            if (!Strings.isNullOrEmpty(param.getTrackingNo())) {
                sb.append(" and o.tracking_no = #{trackingNo} ");
            }
            if (param.getLogisticId() != null) {
                sb.append(" and o.logistics_id = #{logisticId} ");
            }

            //关键字搜索
            //手机号，模糊搜索
            if (!Strings.isNullOrEmpty(param.getTel())) {
                param.setTel("%" + param.getTel() + "%");
                sb.append(" and l.telphone like #{telphone} ");
            }
            //邮箱，精确搜索
            if (!Strings.isNullOrEmpty(param.getEmail())) {
                sb.append(" and l.email = #{email} ");
            }
            //域名，精确搜索
            if (!Strings.isNullOrEmpty(param.getDomain())) {
                sb.append(" and o.domain = #{domain} ");
            }
            //ip，精确搜索
            if (!Strings.isNullOrEmpty(param.getIp())) {
                sb.append(" and o.ip = #{ip} ");
            }
            //地址，模糊搜索
            if (!Strings.isNullOrEmpty(param.getAddress())) {
                param.setAddress("%" + param.getAddress() + "%");
                sb.append(" and l.address like #{address} ");
            }

            /**
             * 需要在业务层拼接，显示订单项属性
             */
            //订单行数据sku或spu搜索 订单-订单行 ===》 一对多关系
            if (!Strings.isNullOrEmpty(param.getSku()) && !Strings.isNullOrEmpty(param.getSpu())) {
                sb.append(" and o.id in (select DISTINCT oi.orders_id from orders_item oi where sku = #{sku} and spu = #{spu}) ");
            } else if (!Strings.isNullOrEmpty(param.getSku())) {
                sb.append(" and o.id in (select DISTINCT oi.orders_id from orders_item oi where sku = #{sku}) ");
            } else if (!Strings.isNullOrEmpty(param.getSpu())) {
                sb.append(" and o.id in (select DISTINCT oi.orders_id from orders_item oi where spu = #{spu}) ");
            }

            if (param.getMinShowTime() != null) {
                sb.append(" and o.create_at >= #{minShowTime} ");
            }
            if (param.getMaxShowTime() != null) {
                sb.append(" and o.create_at <= #{maxShowTime} ");
            }

            return sb.toString();

        }


        public String findOrderByOrderIds(Map map) {
            Object o = map.get("orderIds");
            List<Integer> orderIds = null;
            if (o != null) {
                orderIds = (List<Integer>) o;
            }
            StringBuilder sb = new StringBuilder();
            String ids;
            if (orderIds == null || orderIds.size() == 0) {
                ids = "null";
            } else {
                ids = Joiner.on(",").join(orderIds);
            }
            sb.append("select * from orders i where i.id in ( " + ids + ")");
            return sb.toString();
        }

        public String findOrderByMerchantOrderNos(Map map) {
            Object o = map.get("merchantOrderNos");
            List<String> merchantOrderNos = null;
            if (o != null) {
                merchantOrderNos = (List<String>) o;
            }
            StringBuilder sb = new StringBuilder();
            String ids;
            if (merchantOrderNos == null || merchantOrderNos.size() == 0) {
                ids = "null";
            } else {
                ids = Joiner.on(",").join(merchantOrderNos);
            }
            sb.append("select * from orders i where i.merchant_order_no in ( " + ids + ")");
            return sb.toString();
        }


        public String findOrderByTrackingIds(Map map) {
            Object o = map.get("trackingIds");
            List<String> trackingIds = null;
            if (o != null) {
                trackingIds = (List<String>) o;
            }
            StringBuilder sb = new StringBuilder();
            String ids;
            if (trackingIds == null || trackingIds.size() == 0) {
                ids = "null";
            } else {
                ids = trackingIds.stream().map(s -> "'" + s + "'")
                        .collect(Collectors.joining(", "));
            }
            sb.append("select * from orders i where i.tracking_no in ( " + ids + ")");
            return sb.toString();
        }


    }

    @Select("SELECT * FROM orders WHERE id=#{Id}")
    Orders findOrderById(Integer Id);

    @Update("update orders set tracking_status_show=#{trackingStatusShow} where id=#{id}")
    void updateOrderLogisticsStatus(Orders order);

    @Select("<script>SELECT * FROM orders WHERE tracking_no in " +
            " <foreach item=\"item\" index=\"index\" collection=\"trackingNos\" open=\"(\" separator=\",\" close=\")\">" +
            " #{item} " +
            " </foreach>" +
            "</script>")
    List<Orders> queryOrderByTrackingNos(List<String> trackingNos);

    @SelectProvider(type = OrdersBuilder.class, method = "queryOrderByParams")
    List<Orders> queryOrderByParams(List<String> trackingNos, List<String> orderIds);

    /**
     * 更新订单状态
     *
     * @param orderId
     * @param status
     * @return
     * @date 2017年11月25日
     * @author liusl
     */
    @Update("Update orders set order_status_enum =#{status} where id=#{orderId}")
    Integer updateOrderStatus(Integer orderId, Integer status);

    /**
     * 更新订单的数量与修改人
     *
     * @param param
     * @date 2017年11月29日
     * @author liusl
     */

    @Update("<script>Update orders set confirm_amount=#{confirmAmount},amount_show=#{amountShow} "
            + "<if test=\"memo != null and memo != ''\">,memo=#{memo}</if>"
            + " where id=#{orderId} </script>")
    void updateOrderByOrderId(Map<String, Object> param);


}
