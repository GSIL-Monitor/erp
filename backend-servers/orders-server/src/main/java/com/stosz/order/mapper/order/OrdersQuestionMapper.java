package com.stosz.order.mapper.order;

import com.google.common.base.Joiner;
import com.google.common.base.Strings;
import com.stosz.order.ext.dto.OrdersQuestionDto;
import com.stosz.order.ext.model.OrdersQuestion;
import com.stosz.order.ext.model.OrdersQuestionDO;
import com.stosz.plat.mapper.AbstractBuilder;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.stereotype.Repository;

import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * @auther carter
 * create time    2017-12-06
 */
@Repository
public interface OrdersQuestionMapper {


    @InsertProvider(type = OrdersQuestionMapperBuilder.class, method = "add")
    Integer insert(OrdersQuestion ordersQuestion);


    /**
     * 按照条件查询得到问题件列表数据的总条数
     *
     * @param ordersQuestionDto 查询条件
     * @return 查询得到的总条数
     */
    @SelectProvider(type = OrdersQuestionMapperBuilder.class, method = "countByParam")
    Integer count(OrdersQuestionDto ordersQuestionDto);

    /**
     * 按照条件查询得到问题件列表数据
     *
     * @param ordersQuestionDto 查询条件
     * @return 查询到的结果
     */
    @SelectProvider(type = OrdersQuestionMapperBuilder.class, method = "findByParam")
    List<OrdersQuestionDO> find(OrdersQuestionDto ordersQuestionDto);

    /**
     * 通过运单号查询问题件 TODO
     *
     * @param transNo 运单号
     * @return
     */
    @Select("SELECT * FROM orders_question WHERE trans_no = #{transNo} limit 1")
    OrdersQuestion findByTransNo(@Param("transNo") String transNo);

    @Select("SELECT * FROM orders_question WHERE orders_id = #{ordersId} limit 1")
    OrdersQuestion findByOrdersId(@Param("ordersId") Integer ordersId);

    @Select("SELECT * FROM orders_question WHERE id = #{id}")
    OrdersQuestion findById(@Param("id") Integer id);


    @SelectProvider(type = OrdersQuestionMapperBuilder.class, method = "findByIds")
    List<OrdersQuestionDO> findByIds(@Param("orderQuestionIdArray") String[] orderQuestionIdArray);

    @UpdateProvider(type = OrdersQuestionMapperBuilder.class, method = "updateDealStatus")
    Integer updateDealStatus(String[] ids, @Param("dealStatus") Integer dealStatus, @Param("dealMemo") String dealMemo, @Param("userId") Integer userId, @Param("userName") String userName);


    @UpdateProvider(type = OrdersQuestionMapperBuilder.class, method = "updateSelective")
    Integer updateSelective(@Param("param") OrdersQuestionDO ordersQuestionDO);

    class OrdersQuestionMapperBuilder extends AbstractBuilder<OrdersQuestion> {


        @Override
        public void buildSelectOther(SQL sql) {

        }

        @Override
        public void buildJoin(SQL sql) {

        }

        @Override
        public void buildWhere(SQL sql, OrdersQuestion param) {

        }

        public String add(OrdersQuestion ordersQuestion) {
            SQL sql = new SQL();
            sql.INSERT_INTO(getTableName());
//            valuesExcludeId(sql, getEntityClass());

            List<Pair<String, String>> rs = getColumnFiledSelective(ordersQuestion);
//            List<Pair<String, String>> rs = getColumnField(getEntityClass());
            for (Pair<String, String> pair : rs) {
                if (pair.getRight().equals("id")) {
                    continue;
                }
                if (pair.getRight().equals("dealFirstTime") || pair.getRight().equals("dealLastTime")) {
                    continue;
                } else {
                    sql.VALUES(pair.getLeft(), String.format("#{%s}", pair.getRight()));
                }
            }

            return sql.toString();
        }

        public String findByParam(OrdersQuestionDto ordersQuestionDto) {
            String whereString = getWhereString(ordersQuestionDto);

            StringBuffer stringBuffer = new StringBuffer("select *  from " + getTableName() + whereString);

            stringBuffer.append(" order by id desc ");

            //分页
            int limit = 20;
            if (null != ordersQuestionDto.getPageSize() && ordersQuestionDto.getPageSize() > 0)
                limit = ordersQuestionDto.getPageSize().intValue();
            int start = 0;
            if (null != ordersQuestionDto.getPageIndex() && ordersQuestionDto.getPageIndex() > 0)
                start = (ordersQuestionDto.getPageIndex().intValue() - 1) * limit;


            stringBuffer.append("limit " + start + "," + limit + " ");

            return stringBuffer.toString();
        }

        public String countByParam(@Param("ordersQuestionDto") OrdersQuestionDto ordersQuestionDto) {
            String whereString = getWhereString(ordersQuestionDto);
            return "select count(1) from " + getTableName() + whereString;
        }

        public String findByIds(@Param("orderQuestionIdArray") String[] orderQuestionIdArray) {
            String ids = Joiner.on(",").join(orderQuestionIdArray);
            return "SELECT * FROM orders_question WHERE id IN (" + ids + ")";
        }

        public String updateDealStatus(String[] ids, @Param("dealStatus") Integer dealStatus, @Param("dealMemo") String dealMemo, @Param("userId") Integer userId, @Param("userName") String userName) {

            StringBuffer stringBuffer = new StringBuffer("UPDATE orders_question SET");

            stringBuffer.append(" deal_first_time = CASE");
            stringBuffer.append(" WHEN deal_status_enum = 0 THEN CURRENT_TIMESTAMP");
            stringBuffer.append(" ELSE deal_first_time END");

            stringBuffer.append(",deal_status_enum = #{dealStatus}");
            stringBuffer.append(",deal_memo=#{dealMemo}");
            stringBuffer.append(",deal_user_id=#{userId}");
            stringBuffer.append(",deal_username=#{userName}");
            stringBuffer.append(",deal_last_time = CURRENT_TIMESTAMP");

            String idsStr = Joiner.on(",").join(ids);
            stringBuffer.append(" WHERE id IN (" + idsStr + ")");


            return stringBuffer.toString();
        }

        private String getWhereString(OrdersQuestionDto ordersQuestionDto) {

            StringBuffer stringBuffer = new StringBuffer();


            stringBuffer.append(" where ");

            if (null != ordersQuestionDto.getOrderQuestionStatusEnum())
                stringBuffer.append("deal_status_enum =" + ordersQuestionDto.getOrderQuestionStatusEnum().ordinal() + " and ");

            if (notEmpty(ordersQuestionDto.getOrderId()))
                stringBuffer.append("orders_id =\'" + ordersQuestionDto.getOrderId() + "\' and ");
            if (!Strings.isNullOrEmpty(ordersQuestionDto.getTransNo()))
                stringBuffer.append("trans_no =\'" + ordersQuestionDto.getTransNo() + "\' and ");
            if (!Strings.isNullOrEmpty(ordersQuestionDto.getDealUserId()))
                stringBuffer.append("deal_user_id =\'" + ordersQuestionDto.getDealUserId() + "\' and ");

            if (null != ordersQuestionDto.getZoneId())
                stringBuffer.append("zone_id =" + ordersQuestionDto.getZoneId() + " and ");
            if (null != ordersQuestionDto.getDepartmentId())
                stringBuffer.append("department_id =" + ordersQuestionDto.getDepartmentId() + " and ");
            if (null != ordersQuestionDto.getLogisticId())
                stringBuffer.append("logistic_id =" + ordersQuestionDto.getLogisticId() + " and ");


            if (null != ordersQuestionDto.getInputTimeBegin())
                stringBuffer.append("create_at>=\'").append(ordersQuestionDto.getInputTimeBegin().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))).append("\' and ");
            if (null != ordersQuestionDto.getInputTimeEnd())
                stringBuffer.append("create_at<=\'").append(ordersQuestionDto.getInputTimeEnd().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))).append("\' and ");

            if (null != ordersQuestionDto.getHandleTimeBegin()) {
                stringBuffer.append("(deal_first_time>=\'").append(ordersQuestionDto.getHandleTimeBegin().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))).append("\' or ");
                stringBuffer.append("deal_last_time>=\'").append(ordersQuestionDto.getHandleTimeBegin().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))).append("\') and ");
            }
            if (null != ordersQuestionDto.getHandleTimeEnd()) {
                stringBuffer.append("( deal_first_time<=\'").append(ordersQuestionDto.getHandleTimeEnd().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))).append("\' or ");
                stringBuffer.append("deal_last_time<=\'").append(ordersQuestionDto.getHandleTimeEnd().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))).append("\') and ");
            }


            stringBuffer.append(" 1=1 ");


            return stringBuffer.toString();
        }


    }
}
