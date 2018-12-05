package com.stosz.store.mapper.builder;

import com.stosz.plat.mapper.AbstractBuilder;
import com.stosz.store.ext.model.TransitStock;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.ibatis.jdbc.SQL;

import java.util.List;

/**
 * @Author:ChenShifeng
 * @Description:TransitStockBuilder
 * @Created Time:2017/11/23 14:21
 * @Update Time:
 */
public class TransitStockBuilder extends AbstractBuilder<TransitStock> {
    @Override
    public void buildSelectOther(SQL sql) {
        sql.SELECT("DATEDIFF(NOW(),create_at) AS storageAge ");
    }

    @Override
    public void buildJoin(SQL sql) {

    }

    @Override
    public void buildWhere(SQL sql, TransitStock param) {
        eq(sql, "dept_id", "deptId", param.getDeptId());
        eq(sql, "wms_id", "wmsId", param.getWmsId());
        eq(sql, "state", "state", param.getState());
        ge(sql, "create_at", "inStorageStartTime", param.getInStorageStartTime());
        lt(sql, "create_at", "inStorageEndTime", param.getInStorageEndTime());
        ge(sql, "order_at", "orderStartTime", param.getOrderStartTime());
        lt(sql, "order_at", "orderEndTime", param.getOrderEndTime());
        ge(sql, "order_at", "pickingStartTime", param.getPickingStartTime());
        lt(sql, "order_at", "pickingEndTime", param.getPickingEndTime());
        eq(sql, "order_id_new", "orderIdNew", param.getOrderIdNew());
        like_i(sql, "inner_name", "innerName", param.getInnerName());
        like_i(sql, "sku", "sku", param.getSku());
        ge(sql, "DATEDIFF(NOW(),create_at)", "storageAge", param.getStorageAge());
        eq(sql, "tracking_no_old", "trackingNoOld", param.getTrackingNoOld());
        eq(sql, "tracking_no_new", "trackingNoNew", param.getTrackingNoNew());

        eq(sql, "order_id_old", "orderIdOld", param.getOrderIdOld());

        in(sql, "tracking_no_old", "trackingNoOldBat", param.getTrackingNoOldBat());
    }


    /**
     * 根据id更新转寄库存状态和新订单号
     *
     * @return
     */
    public String updateTransitStateById() {
        SQL sql = new SQL();
        sql.UPDATE(getTableName());
        this.set(sql, "state", "state");
        this.set(sql, "order_id_new", "orderIdNew");
        this.set(sql, "update_at", "updateAt");
        this.set(sql, "order_at", "orderAt");
        this.where(sql, "id", "id");
        return sql.toString();
    }

    public String getTransitList(TransitStock param) {
        SQL sql = findSQL(param);
        return sql.toString();
    }

    public String updateAll(TransitStock param) {
        SQL sql = new SQL();
        sql.UPDATE(getTableName());
        setColumn(sql, TransitStock.class);
        sql.WHERE("id=#{id}");
        return sql.toString();
    }

    private void setColumn(SQL sql, Class<?> cls) {
        List<Pair<String, String>> rs = getColumnField(cls);
        for (Pair<String, String> pair : rs) {
            sql.SET(String.format("%s=#{%s}", pair.getLeft(), pair.getRight()));
        }
    }

}
