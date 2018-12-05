package com.stosz.order.mapper.order;

import com.stosz.order.ext.model.OrdersWebInterfaceRecordDetail;
import com.stosz.plat.mapper.AbstractBuilder;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.jdbc.SQL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import java.util.List;

/**
 * Created by carter on 2018-01-17. Copyright © 2016 －2017 深圳布谷鸟信息技术有限公司
 * mapper代码
 */

@Repository
public interface OrdersWebInterfaceRecordDetailMapper {

    /**
     * 插入数据
     */
    @Insert("INSERT INTO orders_web_interface_record_detail (record_id,interface_name_enum,object_id,request_url,request_param,request_time,response_status_code,response_desc,response_time,response_result_enum,create_at) VALUES(#{recordId},#{interfaceNameEnum},#{objectId},#{requestUrl},#{requestParam},#{requestTime},#{responseStatusCode},#{responseDesc},#{responseTime},#{responseResultEnum},now())")
    void insert(OrdersWebInterfaceRecordDetail record);

    /**
     * 按照ID删除数据
     */
    @Delete("DELETE FROM orders_web_interface_record_detail WHERE id=#{id}")
    Long delete(@Param("id") Long id);

    /**
     * 按照ID更新数据
     */
    @Update("UPDATE orders_web_interface_record_detail SET record_id=#{recordId},interface_name_enum=#{interfaceNameEnum},object_id=#{objectId},request_url=#{requestUrl},request_param=#{requestParam},request_time=#{requestTime},response_status_code=#{responseStatusCode},response_desc=#{responseDesc},response_time=#{responseTime},response_result_enum=#{responseResultEnum} WHERE id=#{id}")
    Long update(OrdersWebInterfaceRecordDetail record);

    /**
     * 按照ID查询数据
     */
    @Select("SELECT * FROM orders_web_interface_record_detail WHERE id=#{id} limit 1")
    OrdersWebInterfaceRecordDetail findById(@Param("id") Long id);

    /**
     * 混合条件查询
     */
    @SelectProvider(type = OrdersWebInterfaceRecordDetailBuilder.class, method = "findByParam")
    List<OrdersWebInterfaceRecordDetail> findByParam(@Param("param") OrdersWebInterfaceRecordDetail param);

    /**
     * 查询所有
     */
    @Select("SELECT * FROM orders_web_interface_record_detail")
    List<OrdersWebInterfaceRecordDetail> findAll();

    /**
     * 按照条件查询得到总数
     */
    @SelectProvider(type = OrdersWebInterfaceRecordDetailBuilder.class, method = "findCountByParam")
    Integer findCountByParam(@Param("param") OrdersWebInterfaceRecordDetail param);

    @Select("SELECT * FROM orders_web_interface_record_detail WHERE record_id=#{recordId}")
    List<OrdersWebInterfaceRecordDetail> findDetailListById(@Param("recordId") Integer recordId);

    @Update("UPDATE orders_web_interface_record_detail SET response_status_code=#{responseStatusCode} ,response_desc=#{responseDesc} ,response_result_enum=#{responseResultEnum}, response_time=now()  WHERE  id=#{id}")
    void updateResponse(@Param("id") Integer recordDetailId, @Param("responseStatusCode") String responseStatusCode, @Param("responseDesc") String responseDesc,@Param("responseResultEnum") Integer responseResultEnum);


    class OrdersWebInterfaceRecordDetailBuilder extends AbstractBuilder<OrdersWebInterfaceRecordDetail> {
        private static final Logger logger = LoggerFactory.getLogger(OrdersWebInterfaceRecordDetailBuilder.class);

        @Override
        public void buildSelectOther(SQL sql) {

        }

        @Override
        public void buildJoin(SQL sql) {

        }

        @Override
        public void buildWhere(SQL sql, OrdersWebInterfaceRecordDetail param) {

        }

        public String findByParam(@Param("param") OrdersWebInterfaceRecordDetail param) {


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
            logger.info("condition sql:" + s);
            return s;
        }

        public String findCountByParam(@Param("param") OrdersWebInterfaceRecordDetail param) {
            SQL sql = new SQL();
            sql.SELECT("count(1)");
            sql.FROM(getTableNameThis());
            buildWhereByParam(sql, param);
            StringBuilder sb = new StringBuilder();
            return sql.toString() + sb.toString();
        }

        public void buildWhereByParam(SQL sql, OrdersWebInterfaceRecordDetail param) {

            Assert.notNull(param, "查询参数不能为空");
            if (null != param.getId() && param.getId().intValue() > 0) sql.AND().WHERE("id=" + param.getId());
            if (null != param.getRecordId() && param.getRecordId().intValue() > 0)
                sql.AND().WHERE("record_id=" + param.getRecordId());
            if (null != param.getInterfaceNameEnum())
                sql.AND().WHERE("interface_name_enum=" + param.getInterfaceNameEnum());
            if (null != param.getObjectId() && param.getObjectId().intValue() > 0)
                sql.AND().WHERE("object_id=" + param.getObjectId());
            if (null != param.getRequestUrl() && "" != param.getRequestUrl())
                sql.AND().WHERE("request_url=" + param.getRequestUrl());
            if (null != param.getRequestParam() && "" != param.getRequestParam())
                sql.AND().WHERE("request_param=" + param.getRequestParam());
            if (null != param.getRequestTime()) sql.AND().WHERE("request_time=" + param.getRequestTime());
            if (null != param.getResponseStatusCode() && "" != param.getResponseStatusCode())
                sql.AND().WHERE("response_status_code=" + param.getResponseStatusCode());
            if (null != param.getResponseDesc() && "" != param.getResponseDesc())
                sql.AND().WHERE("response_desc=" + param.getResponseDesc());
            if (null != param.getResponseTime()) sql.AND().WHERE("response_time=" + param.getResponseTime());
            if (null != param.getResponseResultEnum())
                sql.AND().WHERE("response_result_enum=" + param.getResponseResultEnum());
            if (null != param.getCreateAt()) sql.AND().WHERE("create_at=" + param.getCreateAt());

            logger.info("条件查询语句：" + sql.toString());


        }

    }

}