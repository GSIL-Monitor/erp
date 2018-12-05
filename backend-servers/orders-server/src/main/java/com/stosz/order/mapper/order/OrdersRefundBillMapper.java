package com.stosz.order.mapper.order;

import com.google.common.base.Joiner;
import com.stosz.fsm.IFsmDao;
import com.stosz.order.ext.dto.OrdersRefundDTO;
import com.stosz.order.ext.enums.OrdersRefundStatusEnum;
import com.stosz.order.ext.model.OrdersRefundBill;
import com.stosz.order.ext.model.OrdersRefundBillDO;
import com.stosz.plat.mapper.AbstractBuilder;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @auther tangtao
 * create time  2018/1/3
 */
@Repository
public interface OrdersRefundBillMapper extends IFsmDao {

    @SelectProvider(type = OrdersRefundBuilder.class, method = "modify")
    Integer modify(OrdersRefundDTO ordersRefundDTO);

    @InsertProvider(type = OrdersRefundBuilder.class, method = "insertSelective")
    int insert(OrdersRefundBill ordersRefundBill);

    @Override
    @UpdateProvider(type = OrdersRefundBuilder.class, method = "updateRefundState")
    Integer updateState(@Param("id") Integer id, @Param("state") String state, @Param("stateTime") LocalDateTime stateTime);

    @SelectProvider(type = OrdersRefundBuilder.class, method = "updateSelective")
    Integer updateSelective(OrdersRefundBill ordersRefundBill);

    @SelectProvider(type = OrdersRefundBuilder.class, method = "findByParam")
    List<OrdersRefundBillDO> findByParam(OrdersRefundDTO param);

    @SelectProvider(type = OrdersRefundBuilder.class, method = "countByParam")
    Integer count(OrdersRefundDTO ordersRefundDTO);

    @SelectProvider(type = OrdersRefundBuilder.class, method = "getById")
    OrdersRefundBill findById(Integer id);

    class OrdersRefundBuilder extends AbstractBuilder<OrdersRefundBill> {

        @Override
        public void buildSelectOther(SQL sql) {

        }

        @Override
        public void buildJoin(SQL sql) {

        }

        @Override
        public void buildWhere(SQL sql, OrdersRefundBill param) {

        }

        public String updateRefundState(@Param("id") Integer id, @Param("state") String state, @Param("stateTime") LocalDateTime stateTime) {
            SQL sql = new SQL();
            sql.UPDATE(getTableName());
            sql.SET("refund_status_enum=" + OrdersRefundStatusEnum.fromName(state).ordinal());
            sql.SET("state_time=#{stateTime}");
            sql.WHERE("id=#{id}");
            return sql.toString();
        }

        public String modify(OrdersRefundDTO ordersRefundDTO) {

            SQL sql = new SQL();
            sql.UPDATE(getTableName());
//            sql.SET("refund_method_enum=#{refundMethodEnum}");
            sql.SET("refund_amount=#{refundAmount}");
            sql.SET("update_at=CURRENT_TIMESTAMP");

            sql.WHERE("id=#{id}");
            return sql.toString();
        }

        public String findByParam(OrdersRefundDTO param) {
            SQL sql = new SQL();
            sql.SELECT("*");
            sql.FROM(getTableName());
            buildWhereByParam(sql, param);

            StringBuilder sb = new StringBuilder();
            if (notEmpty(param.getStart())) {
                sb.append(" order by id desc limit ").append(param.getStart()).append(",").append(param.getLimit());
            } else {
                sb.append(" order by id desc limit ").append(param.getLimit());
            }
            return sql.toString() + sb.toString();
        }

        public String countByParam(OrdersRefundDTO param) {
            SQL sql = new SQL();
            sql.SELECT("COUNT(id)");
            sql.FROM(getTableName());
            buildWhereByParam(sql, param);
            return sql.toString();
        }

        private void buildWhereByParam(SQL sql, OrdersRefundDTO param) {
            Assert.notNull(param, "查询参数不能为空");
            if (notEmpty(param.getId())) sql.AND().WHERE("id=#{id}");
            if (notEmpty(param.getOrdersId())) sql.AND().WHERE("orders_id=#{ordersId}");
            if (notEmpty(param.getSiteFrom())) sql.AND().WHERE("site_from=#{siteFrom}");
            if (notEmpty(param.getRefundStatusEnum())) sql.AND().WHERE("refund_status_enum=#{refundStatusEnum}");
            if (notEmpty(param.getRefundMethodEnum())) sql.AND().WHERE("refund_method_enum=#{refundMethodEnum}");
            if (notEmpty(param.getRefundTypeEnum())) sql.AND().WHERE("refund_type_enum=#{refundTypeEnum}");


            if (notEmpty(param.getAuditTimeBegin())) sql.AND().WHERE("audit_time>=#{auditTimeBegin}");
            if (notEmpty(param.getAuditTimeEnd())) sql.AND().WHERE("audit_time<=#{auditTimeEnd}");

            if (notEmpty(param.getCreateAtBegin())) sql.AND().WHERE("create_at>=#{createAtBegin}");
            if (notEmpty(param.getCreateAtEnd())) sql.AND().WHERE("create_at<=#{createAtEnd}");

            // 权限相关
            if (notEmpty(param.getCreatorId())) sql.AND().WHERE("creator_id=#{creatorId}");

            if (notEmpty(param.getDepartmentIds())) {
                String departmentIds = "<foreach collection=\"departmentIds\" index=\"index\" item=\"item\" \n" +
                        "        separator=\",\" open=\"(\" close=\")\">\n" +
//                        "        #{item}\n" +
                        "    </foreach>";

                String ids = Joiner.on(",").join(param.getDepartmentIds());
                sql.AND().WHERE("seo_department_id IN (" + ids + ")");
            }

            if (notEmpty(param.getZoneIds())) {
                String ids = Joiner.on(",").join(param.getZoneIds());
                sql.AND().WHERE("zone_id IN (" + ids + ")");
            }
        }
    }
}
