package com.stosz.order.mapper.order;

import com.stosz.order.ext.model.OrdersWebInterfaceRecord;
import com.stosz.plat.enums.InterfaceNameEnum;
import com.stosz.plat.enums.InterfaceTypeEnum;
import com.stosz.plat.enums.ResponseResultEnum;
import com.stosz.plat.mapper.AbstractBuilder;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.jdbc.SQL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

/**
 * Created by carter on 2018-01-17. Copyright © 2016 －2017 深圳布谷鸟信息技术有限公司
 * 订单跟外部系统的http交互记录mapper代码
 */

@Repository
public interface OrdersWebInterfaceRecordMapper {

    /**插入数据 */
    @Insert("INSERT INTO orders_web_interface_record (interface_type_enum,interface_name_enum,object_id,request_url,request_param,ext_param,request_time,response_status_code,response_desc,response_time,response_result_enum,request_times,create_at) VALUES(#{interfaceTypeEnum},#{interfaceNameEnum},#{objectId},#{requestUrl},#{requestParam},#{extParam},#{requestTime},#{responseStatusCode},#{responseDesc},#{responseTime},#{responseResultEnum},#{requestTimes},now())")
    void  insert(OrdersWebInterfaceRecord record );

    /**按照ID删除数据 */
    @Delete("DELETE FROM orders_web_interface_record WHERE id=#{id}")
    Long delete(@Param("id") Long id);

    /**按照ID更新数据 */
    @Update("UPDATE orders_web_interface_record SET interface_type_enum=#{interfaceTypeEnum},interface_name_enum=#{interfaceNameEnum},object_id=#{objectId},request_url=#{requestUrl},request_param=#{requestParam},ext_param=#{extParam},request_time=#{requestTime},response_status_code=#{responseStatusCode},response_desc=#{responseDesc},response_time=#{responseTime},response_result_enum=#{responseResultEnum},request_times=#{requestTimes} WHERE id=#{id}")
    Long update(OrdersWebInterfaceRecord record);

    /**按照ID查询数据 */
    @Select("SELECT * FROM orders_web_interface_record WHERE id=#{id} limit 1")
    OrdersWebInterfaceRecord findById(@Param("id") Long id);

    /**混合条件查询 */
    @SelectProvider(type = OrdersWebInterfaceRecordBuilder.class, method = "findByParam")
    List<OrdersWebInterfaceRecord> findByParam(@Param("param")OrdersWebInterfaceRecord param);

    /**查询所有 */
    @Select("SELECT * FROM orders_web_interface_record")    List<OrdersWebInterfaceRecord> findAll();

    /**按照条件查询得到总数 */
    @SelectProvider(type = OrdersWebInterfaceRecordBuilder.class, method = "findCountByParam")
    Integer findCountByParam(@Param("param")OrdersWebInterfaceRecord param);

    @SelectProvider(type = OrdersWebInterfaceRecordBuilder.class,method = "findByCondition")
    List<OrdersWebInterfaceRecord> findByCondition(@Param("interfaceTypeEnum") InterfaceTypeEnum interfaceTypeEnum, @Param("interfaceNameEnum") InterfaceNameEnum interfaceNameEnum, @Param("objectId") Integer objectId, @Param("responseResultEnum") ResponseResultEnum responseResultEnum, @Param("createAtStart") LocalDateTime createAtStart, @Param("createAtEnd") LocalDateTime createAtEnd,  @Param("start")Integer start,  @Param("limit")Integer limit);

    @SelectProvider(type = OrdersWebInterfaceRecordBuilder.class,method = "findRecordByTypeNameAndObjectId")
    OrdersWebInterfaceRecord findRecordByTypeNameAndObjectId(@Param("interfaceTypeEnum") InterfaceTypeEnum interfaceTypeEnum,@Param("interfaceNameEnum") InterfaceNameEnum interfaceNameEnum,@Param("objectId") Integer objectId);

    @Update("UPDATE orders_web_interface_record SET response_status_code=#{responseStatusCode} ,response_desc=#{responseDesc} ,response_result_enum=#{responseResultEnum}, response_time=now()  WHERE id=#{id}")
    void updateResponse(@Param("id") Integer recordId, @Param("responseStatusCode") String responseStatusCode, @Param("responseDesc") String responseDesc,@Param("responseResultEnum") Integer responseResultEnum);

    class OrdersWebInterfaceRecordBuilder extends AbstractBuilder<OrdersWebInterfaceRecord> {
        private static final Logger logger = LoggerFactory.getLogger(OrdersWebInterfaceRecordBuilder.class);
        @Override
        public void buildSelectOther(SQL sql) {

        }

        @Override
        public void buildJoin(SQL sql) {

        }

        @Override
        public void buildWhere(SQL sql, OrdersWebInterfaceRecord param) {

        }

        public String findByParam(@Param("param") OrdersWebInterfaceRecord param) {


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
            logger.info("condition sql:"+s);
            return s;
        }

        public String findCountByParam(@Param("param")OrdersWebInterfaceRecord param) {
            SQL sql = new SQL();
            sql.SELECT("count(1)");
            sql.FROM(getTableNameThis());
            buildWhereByParam(sql, param);
            StringBuilder sb = new StringBuilder();
            return sql.toString() + sb.toString();
        }

        public void buildWhereByParam(SQL sql, OrdersWebInterfaceRecord param) {

            Assert.notNull(param,"查询参数不能为空");
            if (null!=param.getId()  && param.getId().intValue()> 0 )	sql.AND().WHERE("id="+param.getId());
            if (null!=param.getInterfaceTypeEnum() )	sql.AND().WHERE("interface_type_enum="+param.getInterfaceTypeEnum());
            if (null!=param.getInterfaceNameEnum() )	sql.AND().WHERE("interface_name_enum="+param.getInterfaceNameEnum());
            if (null!=param.getObjectId()  && param.getObjectId().intValue()> 0 )	sql.AND().WHERE("object_id="+param.getObjectId());
            if (null!=param.getRequestUrl()  && "" != param.getRequestUrl() )	sql.AND().WHERE("request_url="+param.getRequestUrl());
            if (null!=param.getRequestParam()  && "" != param.getRequestParam() )	sql.AND().WHERE("request_param="+param.getRequestParam());
            if (null!=param.getExtParam()  && "" != param.getExtParam() )	sql.AND().WHERE("ext_param="+param.getExtParam());
            if (null!=param.getRequestTime() )	sql.AND().WHERE("request_time="+param.getRequestTime());
            if (null!=param.getResponseStatusCode()  && "" != param.getResponseStatusCode() )	sql.AND().WHERE("response_status_code="+param.getResponseStatusCode());
            if (null!=param.getResponseDesc()  && "" != param.getResponseDesc() )	sql.AND().WHERE("response_desc="+param.getResponseDesc());
            if (null!=param.getResponseTime() )	sql.AND().WHERE("response_time="+param.getResponseTime());
            if (null!=param.getResponseResultEnum() )	sql.AND().WHERE("response_result_enum="+param.getResponseResultEnum());
            if (null!=param.getRequestTimes()  && param.getRequestTimes().intValue()> 0 )	sql.AND().WHERE("request_times="+param.getRequestTimes());
            if (null!=param.getCreateAt() )	sql.AND().WHERE("create_at="+param.getCreateAt());

            logger.info("条件查询语句：" + sql.toString());


        }


        public String findByCondition(@Param("interfaceTypeEnum") InterfaceTypeEnum interfaceTypeEnum,@Param("interfaceNameEnum")  InterfaceNameEnum interfaceNameEnum,@Param("objectId")  Integer objectId,@Param("responseResultEnum")  ResponseResultEnum responseResultEnum,@Param("createAtStart")  LocalDateTime createAtStart,@Param("createAtEnd")  LocalDateTime createAtEnd,  @Param("start")Integer start,  @Param("limit")Integer limit)
        {
            SQL sql = new SQL().SELECT("*").FROM(getTableName()).WHERE("1=1");

            Optional.ofNullable(interfaceTypeEnum).ifPresent(item -> sql.AND().WHERE("interface_type_enum="+item.ordinal()));
            Optional.ofNullable(interfaceNameEnum).ifPresent(item -> sql.AND().WHERE("interface_name_enum="+item.ordinal()));
            Optional.ofNullable(responseResultEnum).ifPresent(item -> sql.AND().WHERE("response_result_enum="+item.ordinal()));

            Optional.ofNullable(objectId).ifPresent(item -> sql.AND().WHERE("object_id="+item.longValue()));
            Optional.ofNullable(createAtStart).ifPresent(item -> sql.AND().WHERE("create_at>='"+ item.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))+"'"));
            Optional.ofNullable(createAtEnd).ifPresent(item -> sql.AND().WHERE("create_at<='"+item.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))+"'"));

//            if(null==start ||  null==limit) {
//                return sql.toString();
//            }

            return  sql.toString()/*+" limit " + start.intValue()+ ","  + limit.intValue()*/ ;

        }

        public String findRecordByTypeNameAndObjectId(@Param("interfaceTypeEnum") InterfaceTypeEnum interfaceTypeEnum,@Param("interfaceNameEnum") InterfaceNameEnum interfaceNameEnum,@Param("objectId") Integer objectId){
            SQL sql = new SQL().SELECT("*").FROM(getTableName()).WHERE("1=1");

            Optional.ofNullable(interfaceTypeEnum).ifPresent(item -> sql.AND().WHERE("interface_type_enum="+item.ordinal()));
            Optional.ofNullable(interfaceNameEnum).ifPresent(item -> sql.AND().WHERE("interface_name_enum="+item.ordinal()));
            Optional.ofNullable(objectId).ifPresent(item -> sql.AND().WHERE("object_id="+item.longValue()));

            return sql.toString()+" limit 1";


        }


    }

}