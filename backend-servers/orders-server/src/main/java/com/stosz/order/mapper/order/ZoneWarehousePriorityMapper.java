package com.stosz.order.mapper.order;

import com.stosz.order.ext.model.ZoneWarehousePriority;
import com.stosz.plat.mapper.AbstractBuilder;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Set;

/**
 * Created by carter on 2017-11-09. Copyright © 2016 －2017 深圳布谷鸟信息技术有限公司
 * mapper代码
 */

@Repository
public interface ZoneWarehousePriorityMapper {

    @Insert("<script>"
            +"insert into zone_warehouse_priority(zone_id, warehouse_id, priority, zone_name,warehouse_name,forbid_spu,status,creator_id,creator)"
            + "values "
            + " <foreach collection =\"list\" item=\"item\" index= \"index\" separator =\",\"> "
            + "(#{item.zoneId}, #{item.warehouseId},#{item.priority},#{item.zoneName},#{item.warehouseName},#{item.forbidSpu},#{item.status},#{item.creatorId},#{item.creator})"
            + "</foreach> "
            + "</script>")
    Integer insertBatch(List<ZoneWarehousePriority> record);

    @SelectProvider(type = ZoneWarehousePriorityBuilder.class, method = "insert")
    Integer insert(ZoneWarehousePriority record);

    @SelectProvider(type = ZoneWarehousePriorityBuilder.class, method = "delete")
    Integer delete(@Param("id") Integer id);

    @UpdateProvider(type = ZoneWarehousePriorityBuilder.class, method = "update")
    Long update(ZoneWarehousePriority record);

    @SelectProvider(type = ZoneWarehousePriorityBuilder.class, method = "getById")
    ZoneWarehousePriority getById(@Param("id") Integer id);

    @SelectProvider(type = ZoneWarehousePriorityBuilder.class, method = "findByParam")
    List<ZoneWarehousePriority> findByParam(@Param("param") ZoneWarehousePriority param);


    @Select("SELECT * FROM zone_warehouse_priority")
    List<ZoneWarehousePriority> findAll();

    @Select("SELECT * FROM zone_warehouse_priority z where z.zone_id = #{zoneId} and z.warehouse_id = #{warehouseId}")
    ZoneWarehousePriority getByZoneIdAndWarehouseId(@Param("zoneId") Integer zoneId, @Param("warehouseId") Integer warehouseId);

    @SelectProvider(type = ZoneWarehousePriorityBuilder.class, method = "findCountByParam")
    Integer findCountByParam(@Param("param") ZoneWarehousePriority param);

    @Select("SELECT * FROM zone_warehouse_priority WHERE zone_id=#{zoneId} ORDER BY PRIORITY ASC ")
    List<ZoneWarehousePriority> findByZoneId(@Param("zoneId") Integer zoneId);

    /**
     * 不满足条件的区域
     * @param wmsId
     * @param spu
     * @return
     */
//    @Select("SELECT DISTINCT zone_id FROM zone_warehouse_priority WHERE warehouse_id <> #{wmsId} OR   find_in_set(#{spu},forbid_spu) ")
    @Select("SELECT DISTINCT zone_id FROM zone_warehouse_priority WHERE warehouse_id = #{wmsId} AND  !find_in_set(#{spu},forbid_spu) ")
    Set<Integer> findZoneSetByWarehouseIdAndSpu(@Param("wmsId") Integer wmsId, @Param("spu") String spu);


    class ZoneWarehousePriorityBuilder extends AbstractBuilder<ZoneWarehousePriority> {
        @Override
        public void buildSelectOther(SQL sql) {

        }

        @Override
        public void buildJoin(SQL sql) {

        }

        @Override
        public void buildWhere(SQL sql, ZoneWarehousePriority param) {

        }

        public String findByParam(@Param("param") ZoneWarehousePriority param) {
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


        public String findCountByParam(@Param("param") ZoneWarehousePriority param) {
            SQL sql = new SQL();
            sql.SELECT("count(1)");
            sql.FROM(getTableNameThis());
            buildWhereByParam(sql, param);
            StringBuilder sb = new StringBuilder();
            return sql.toString() + sb.toString();
        }

        public void buildWhereByParam(SQL sql, ZoneWarehousePriority param) {

            Assert.notNull(param, "查询参数不能为空");
            if (null != param.getId() && param.getId().intValue() > 0) sql.AND().WHERE("id=" + param.getId());
            if (null != param.getZoneId() && param.getZoneId().intValue() > 0)
                sql.AND().WHERE("zone_id=" + param.getZoneId());
            if (null != param.getWarehouseId() && param.getWarehouseId().intValue() > 0)
                sql.AND().WHERE("warehouse_id=" + param.getWarehouseId());
            if (null != param.getPriority() && param.getPriority().intValue() > 0)
                sql.AND().WHERE("priority=" + param.getPriority());
            if (null != param.getStatus())
                sql.AND().WHERE("status=" + param.getStatus().ordinal());
            if (null != param.getCreateAt()) sql.AND().WHERE("create_at=" + param.getCreateAt());


        }

    }

}