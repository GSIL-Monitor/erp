package com.stosz.store.mapper.builder;

import com.stosz.plat.mapper.AbstractBuilder;
import com.stosz.store.ext.model.SettlementMonth;
import org.apache.ibatis.jdbc.SQL;

/**
 * @Author:yangqinghui
 * @Description:InvoicingBuilder
 * @Created Time:2017/11/23 14:21
 * @Update Time:
 */
public class SettlementBuilder extends AbstractBuilder<SettlementMonth> {
    @Override
    public void buildSelectOther(SQL sql) {

    }

    @Override
    public void buildJoin(SQL sql) {

    }

    @Override
    public void buildWhere(SQL sql, SettlementMonth param) {

    }

    public String query(SettlementMonth settlementMonth) {
        SQL sql = new SQL();
        sql.SELECT(" _this.*");
        sql.FROM(this.getTableNameThis());
        eq(sql, "wms_id", "wmsId", settlementMonth.getWmsId());
        eq(sql, "dept_id", "deptId", settlementMonth.getDeptId());
        eq(sql, "spu", "spu", settlementMonth.getSpu());
        eq(sql, "sku", "sku", settlementMonth.getSku());
        eq(sql, "plan_id", "planId", settlementMonth.getPlanId());
        return sql.toString();
    }
}
