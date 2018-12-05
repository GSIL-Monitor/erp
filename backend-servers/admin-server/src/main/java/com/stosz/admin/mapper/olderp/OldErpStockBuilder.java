package com.stosz.admin.mapper.olderp;

import com.stosz.olderp.ext.model.OldErpStock;
import com.stosz.plat.mapper.AbstractBuilder;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

/**
 * @author xiongchenyang
 * @version [1.0 , 2017/9/8]
 */
public class OldErpStockBuilder extends AbstractBuilder<OldErpStock> {

    @Override
    public void buildSelectOther(SQL sql) {

    }

    @Override
    public void buildJoin(SQL sql) {

    }

    @Override
    public void buildWhere(SQL sql, OldErpStock param) {

    }

    public String findOrderDateByParam(@Param("param") OldErpStock param) {
        SQL sql = new SQL();
        sql.SELECT("_this.id_department as department_id, _this.id_zone as zone_id , oi.id_product as product_id, _this.created_at as last_order_at");
        sql.FROM("erp_order _this");
        sql.LEFT_OUTER_JOIN(joinString("erp_order_item", "oi", "id_order", "id_order"));
        ge(sql, "_this.created_at", "param.minCreateAt", param.getMinCreateAt());
        le(sql, "_this.created_at", "param.maxCreateAt", param.getMaxCreateAt());
        param.setOrderBy("_this.id_order");
        String pageStr = buildSearchPageSql(param);
        return sql.toString() + pageStr;
    }

    public String findStockByParam(@Param("param") OldErpStock param) {
        SQL sql = new SQL();
        sql.SELECT("_this.id_product as product_id, _this.id_department as department_id,w.id_zone as zone_id, SUM(_this.quantity) as stock ");
        sql.FROM("erp_warehouse_product _this");
        sql.LEFT_OUTER_JOIN(joinString("erp_warehouse", "w", "id_warehouse", "id_warehouse"));
        sql.WHERE("_this.id_department !=0 AND w.id_zone is not NULL");
        sql.GROUP_BY(" _this.id_product,_this.id_department, w.id_zone");
//        ge(sql,"create_at","param.minCreateAt",param.getMinCreateAt());
//        le(sql,"create_at","param.maxCreateAt",param.getMinCreateAt());
        param.setOrderBy("w.id_zone");
        String pageStr = buildSearchPageSql(param);
        return sql.toString() + pageStr;
    }


    public String findHasStock(@Param("param") OldErpStock param) {
        SQL sql = new SQL();
        sql.SELECT("_this.id_product as product_id, _this.id_department as department_id,w.id_zone as zone_id, SUM(_this.quantity) as stock, SUM(_this.qty_preout) as qty_preout , SUM(_this.road_num) as qty_road");
        sql.FROM("erp_warehouse_product _this");
        sql.LEFT_OUTER_JOIN(joinString("erp_warehouse", "w", "id_warehouse", "id_warehouse"));
        sql.WHERE("(_this.quantity > 0 OR _this.road_num>0 OR _this.qty_preout>0) and _this.id_product is not null and _this.id_department is not null and w.id_zone is not null");
        sql.GROUP_BY(" _this.id_product,_this.id_department, w.id_zone");
        param.setOrderBy("_this.id_product");
        String pageStr = buildSearchPageSql(param);
        return sql.toString() + pageStr;
    }

    public String findHasLastOrderAtByProductId(@Param("productId") Integer productId) {
        SQL sql = new SQL();
        sql.SELECT("_this.id_department as department_id,_this.id_zone as zone_id,oi.id_product as product_id,MAX(_this.created_at) as last_order_at");
        sql.FROM("erp_order _this");
        sql.LEFT_OUTER_JOIN(joinString("erp_order_item", "oi", "id_order", "id_order"));
        eq(sql, "id_product", "productId", productId);
        sql.GROUP_BY("oi.id_product,_this.id_department,_this.id_zone");
        return sql.toString();
    }
}
