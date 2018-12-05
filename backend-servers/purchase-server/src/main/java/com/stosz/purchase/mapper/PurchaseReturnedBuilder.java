package com.stosz.purchase.mapper;

import com.stosz.plat.mapper.AbstractBuilder;
import com.stosz.purchase.ext.model.PurchaseReturned;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.util.StringUtils;

public class PurchaseReturnedBuilder extends AbstractBuilder<PurchaseReturned> {

    @Override
    public void buildSelectOther(SQL sql) {
        // TODO Auto-generated method stub

    }

    @Override
    public void buildJoin(SQL sql) {

    }

    @Override
    public void buildWhere(SQL sql, PurchaseReturned param) {
    }

    public String queryCount(@Param("purchase_returned") PurchaseReturned purchaseReturned) {
        SQL sql = this.queryBase(purchaseReturned);
        String result = "select count(*) from (%s)c";
        result = String.format(result, sql.toString());
        return result;
    }

    public SQL queryBase(PurchaseReturned purchaseReturned) {
        SQL sql = new SQL();
        sql.SELECT("p.*,sp.name supplierName,l.name plat_name,u.buyer,u.purchase_no,u.purchase_time");
        sql.LEFT_OUTER_JOIN(" purchase u ON p.purchase_id=u.id");

        eq(sql, "bu_dept_id", "buDeptId", purchaseReturned.getBuDeptId());
        eq(sql, "state", "state", purchaseReturned.getState());
        like_r(sql, "u.purchase_no", "purchaseNo", purchaseReturned.getPurchaseNo());
        like_i(sql, "u.plat_orders_no", "platOrdersNo", purchaseReturned.getPlatOrdersNo());

        if (StringUtils.hasText(purchaseReturned.getSku())) {
            sql.WHERE(" EXISTS (SELECT 1 FROM purchase_returned_item i WHERE p.id=i.returned_id AND CONCAT(i.sku,'%'))");
        }
       /* ge(sql, " DATE_FORMAT(p.create_at,'%Y-%m-%d') ", "startTime", purchaseReturned.getStartTime());
        le(sql, " DATE_FORMAT(p.create_at,'%Y-%m-%d')", "endTime", purchaseReturned.getEndTime());*/
        return sql;
    }

    public String queryList(PurchaseReturned purchaseReturned) {
        SQL sql = this.queryBase(purchaseReturned);
        StringBuilder orderBuilder = new StringBuilder();
        if (StringUtils.hasText(purchaseReturned.getOrderBy())) {
            orderBuilder.append(" order by ").append(purchaseReturned.getOrderBy());
            if (purchaseReturned.getOrder()) {
                orderBuilder.append(" desc");
            } else {
                orderBuilder.append(" asc");
            }
        } else {
            orderBuilder.append(" ORDER BY pc.create_at ASC");
        }
        if (purchaseReturned.getLimit() != null) {
            if (purchaseReturned.getStart() != null) {
                orderBuilder.append(" limit ").append(purchaseReturned.getStart()).append(",").append(purchaseReturned.getLimit());
            } else {
                orderBuilder.append(" limit ").append(purchaseReturned.getLimit());
            }
        }
        return sql.toString() + orderBuilder.toString();
    }

    public String query(PurchaseReturned purchaseReturned) {
        SQL sql = new SQL();
        StringBuilder orderBuilder = new StringBuilder();
        sql.SELECT(" _this.*");
        sql.FROM(this.getTableNameThis());
        if (!StringUtils.isEmpty(purchaseReturned.getSku())) {
            sql.LEFT_OUTER_JOIN(" purchase_returned_item i ON i.returned_id=_this.id");
            eq(sql, "i.sku", "sku", purchaseReturned.getSku());
        }
        eq(sql, "_this.id", "id", purchaseReturned.getId());
        eq(sql, "_this.bu_dept_id", "buDeptId", purchaseReturned.getBuDeptId());
        eq(sql, "_this.state", "state", purchaseReturned.getState());
        eq(sql, "_this.purchase_no", "purchaseNo", purchaseReturned.getPurchaseNo());
        eq(sql, "_this.plat_orders_no", "platOrdersNo", purchaseReturned.getPlatOrdersNo());
        ge(sql, " DATE_FORMAT(_this.create_at,'%Y-%m-%d') ", "minCreateAt", purchaseReturned.getMinCreateAt());
        le(sql, " DATE_FORMAT(_this.create_at,'%Y-%m-%d')", "maxCreateAt", purchaseReturned.getMaxCreateAt());

        if (!StringUtils.isEmpty(purchaseReturned.getOrderBy())) {
            orderBuilder.append(" order by ").append(purchaseReturned.getOrderBy());
            if (purchaseReturned.getOrder()) {
                orderBuilder.append(" desc");
            } else {
                orderBuilder.append(" asc");
            }
        } else {
            orderBuilder.append(" order by _this.id desc");
        }
        if (purchaseReturned.getLimit() != null) {
            if (purchaseReturned.getStart() != null) {
                orderBuilder.append(" limit ").append(purchaseReturned.getStart()).append(",").append(purchaseReturned.getLimit());
            } else {
                orderBuilder.append(" limit ").append(purchaseReturned.getLimit());
            }
        }

        return sql.toString() + orderBuilder.toString();
    }

}
