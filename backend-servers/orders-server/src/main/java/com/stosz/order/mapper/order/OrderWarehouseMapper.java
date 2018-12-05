package com.stosz.order.mapper.order;

import com.stosz.order.ext.model.OrderWarehouse;
import com.stosz.plat.mapper.AbstractBuilder;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import java.util.List;

/**
 * Created by carter on 2017-11-16. Copyright © 2016 －2017 深圳布谷鸟信息技术有限公司
 * 订单仓库信息mapper代码
 */

@Repository
public interface OrderWarehouseMapper {

	/**insert default method */

	@Insert("INSERT INTO order_warehouse (orders_id,warehouse_id,warehouse_memo,warehouse_type_enum,create_at,update_at,creator_id,creator) VALUES(#{orderId},#{warehouseId},#{warehouseMemo},#{warehouseTypeEnum},now(),now(),#{creatorId},#{creator})")

	void  insert(OrderWarehouse record );

	@Delete("DELETE FROM order_warehouse WHERE id=#{id}")
    Long delete(@Param("id") Long id);

	  @Update("UPDATE order_warehouse SET orders_id=#{orderId},warehouse_id=#{warehouseId},warehouse_memo=#{warehouseMemo}, update_at = now(),creator_id=#{creatorId},creator=#{creator} WHERE id=#{id}")
    Long update(OrderWarehouse record);

	@Select("SELECT * FROM order_warehouse WHERE id=#{id}")
    OrderWarehouse findById(@Param("id") Integer id);

	    @SelectProvider(type = OrderWarehouseBuilder.class, method = "findByParam")
    List<OrderWarehouse> findByParam(@Param("param")OrderWarehouse param);


    @Select("SELECT * FROM order_warehouse")
    List<OrderWarehouse> findAll();


    @SelectProvider(type = OrderWarehouseBuilder.class, method = "findCountByParam")
    Integer findCountByParam(@Param("param")OrderWarehouse param);

    @Insert("<script>"
            +"insert into order_warehouse(orders_id,warehouse_id,warehouse_memo,create_at,update_at,creator_id,creator) "
            + "values "
            + " <foreach collection =\"list\" item=\"o\" index= \"index\" separator =\",\"> "
            + "(#{o.orderId},#{o.warehouseId},#{o.warehouseMemo},now(),now(),#{o.creatorId},#{o.creator})"
            + "</foreach> "
            + "</script>")
    Integer batchInsert(List<OrderWarehouse> orderWarehouseList);


    class OrderWarehouseBuilder extends AbstractBuilder<OrderWarehouse> {
        @Override
        public void buildSelectOther(SQL sql) {

        }

        @Override
        public void buildJoin(SQL sql) {

        }

        @Override
        public void buildWhere(SQL sql, OrderWarehouse param) {

        }

        public String findByParam(@Param("param") OrderWarehouse param) {


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

        public String findCountByParam(@Param("param")OrderWarehouse param) {
            SQL sql = new SQL();
            sql.SELECT("count(1)");
            sql.FROM(getTableNameThis());
            buildWhereByParam(sql, param);
            StringBuilder sb = new StringBuilder();
            return sql.toString() + sb.toString();
        }

        public void buildWhereByParam(SQL sql, OrderWarehouse param) {

            Assert.notNull(param,"查询参数不能为空");
		if (null!=param.getId()  && param.getId().intValue()> 0 )	sql.AND().WHERE("id="+param.getId());
		if (null!=param.getOrderId()  && param.getOrderId().intValue()> 0 )	sql.AND().WHERE("orders_id="+param.getOrderId());
		if (null!=param.getWarehouseId()  && param.getWarehouseId().intValue()> 0 )	sql.AND().WHERE("warehouse_id="+param.getWarehouseId());
		if (null!=param.getWarehouseMemo()  && "" != param.getWarehouseMemo() )	sql.AND().WHERE("warehouse_memo="+param.getWarehouseMemo());
		if (null!=param.getCreateAt() )	sql.AND().WHERE("create_at="+param.getCreateAt());
		if (null!=param.getUpdateAt() )	sql.AND().WHERE("update_at="+param.getUpdateAt());
		if (null!=param.getCreatorId()  && param.getCreatorId().intValue()> 0 )	sql.AND().WHERE("creator_id="+param.getCreatorId());
		if (null!=param.getCreator()  && "" != param.getCreator() )	sql.AND().WHERE("creator="+param.getCreator());



        }

    }

}