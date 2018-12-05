package com.stosz.product.mapper;

import com.stosz.plat.mapper.AbstractBuilder;
import com.stosz.plat.utils.StringUtils;
import com.stosz.product.ext.enums.ProductZoneState;
import com.stosz.product.ext.model.ProductZone;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

import java.time.LocalDateTime;

public class ProductZoneBuilder extends AbstractBuilder<ProductZone> {

    @Override
    public void buildSelectOther(SQL sql) {
        sql.SELECT(" z.id as zone_id");
    }

    @Override
    public void buildJoin(SQL sql) {
        sql.LEFT_OUTER_JOIN(joinString("zone", "z", "code", "zone_code"));

    }

    @Override
    public void buildWhere(SQL sql, ProductZone param) {
        eq(sql, "_this.product_id", "productId", param.getProductId());
        eq(sql, "_this.zone_code", "zoneCode", param.getZoneCode());
        eq(sql, "_this.department_id", "departmentId", param.getDepartmentId());
        eq(sql, "_this.state", "state",param.getState());
        eq(sql, "_this.creator_id", "creatorId", param.getCreatorId());
        eq(sql, "z.id", "zoneId", param.getZoneId());
        ge(sql, "_this.create_at", "minCreateAt", param.getMinCreateAt());
        le(sql, "_this.create_at", "maxCreateAt", param.getMaxCreateAt());
        like_r(sql, "_this.department_no", "departmentNo", param.getDepartmentNo());
        if(param.getDepartmentIds() != null){
            if (!param.getDepartmentIds().isEmpty()) {
                String val = StringUtils.join(param.getDepartmentIds(), ',');
                sql.WHERE("department_id in ( " + val + " )");
            }
        }

    }

    public String findByDate(@Param("param") ProductZone param) {
        SQL sql = new SQL();
        sql.SELECT("_this.product_id,z.id as zone_id, _this.department_id, _this.state");
        sql.FROM(getTableNameThis());
        sql.LEFT_OUTER_JOIN(joinString("zone", "z", "code", "zone_code"));
        ge(sql, "_this.update_at", "param.minCreateAt", param.getMinCreateAt());
        le(sql, "_this.update_at", "param.maxCreateAt", param.getMaxCreateAt());
        String page = buildSearchPageSql(param);
        return sql.toString() + page;
    }

    public String findNoOrderFixDay(@Param("days")Integer days,@Param("limit")Integer limit) {
        SQL sql = new SQL();
        sql.SELECT("*");
        sql.FROM(getTableName());
        sql.WHERE(" (last_order_at is null and create_at < date_sub(now() , INTERVAL "+ days +" day))" +
                " or (last_order_at is not null and last_order_at < date_sub(now() , INTERVAL " + days + " day))");
        return sql.toString() + " and state='" + ProductZoneState.onsale + "' order by department_id limit " + limit;
    }

    public String findNoArchiveFixDay(@Param("days")Integer days,@Param("limit")Integer limit){
        SQL sql = new SQL();
        sql.SELECT("*");
        sql.FROM(getTableName());
        sql.WHERE(" state='" + ProductZoneState.archiving + "'");
        sql.WHERE("state_time < date_sub(now() , INTERVAL " + days + " day)");
        return sql.toString() + " limit " + limit;
    }

    //@Select("SELECT COUNT(1) FROM product_zone WHERE department_id = #{departmentId} AND product_id = #{productId} AND last_order_at > DATE_SUB(NOW() , INTERVAL #{day} DAY)")
    public String countNorderFixDay(@Param("departmentId") Integer departmentId, @Param("productId") Integer productId, @Param("time") LocalDateTime time) {
        SQL sql = new SQL();
        sql.SELECT("COUNT(1)");
        sql.FROM(getTableName());
        countNorderFixDayWhere(sql, departmentId, productId, time);
        return sql.toString();
    }

    private void countNorderFixDayWhere(SQL sql, Integer departmentId, Integer productId, LocalDateTime time) {
        eq(sql, "department_id", "departmentId", departmentId);
        eq(sql, "product_id", "productId", productId);
        ge(sql, "last_order_at", "time", time);
//        if (day != null) {
//            sql.WHERE("last_order_at > DATE_SUB(NOW() , INTERVAL " + day + " DAY)");
//        }

    }
}