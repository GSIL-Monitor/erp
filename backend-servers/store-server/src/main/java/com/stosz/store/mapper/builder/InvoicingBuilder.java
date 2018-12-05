package com.stosz.store.mapper.builder;

import com.stosz.plat.mapper.AbstractBuilder;
import com.stosz.store.ext.model.Invoicing;
import org.apache.ibatis.jdbc.SQL;

/**
 * @Author:yangqinghui
 * @Description:InvoicingBuilder
 * @Created Time:2017/11/23 14:21
 * @Update Time:
 */
public class InvoicingBuilder extends AbstractBuilder<Invoicing> {
    @Override
    public void buildSelectOther(SQL sql) {

    }

    @Override
    public void buildJoin(SQL sql) {

    }

    @Override
    public void buildWhere(SQL sql, Invoicing invoicing) {
        eq(sql, "_this.wms_id", "wmsId", invoicing.getWmsId());
        eq(sql, "_this.dept_id", "deptId", invoicing.getDeptId());
        eq(sql, "_this.spu", "spu", invoicing.getSpu());
        eq(sql, "_this.sku", "sku", invoicing.getSku());
        eq(sql, "_this.type", "type", invoicing.getType());
        eq(sql, "_this.state", "state", invoicing.getState());
        eq(sql, "_this.plan_id", "planId", invoicing.getPlanId());
        eq(sql, "_this.voucher_No", "voucherNo", invoicing.getVoucherNo());
    }

    public String query(Invoicing invoicing) {
        SQL sql = new SQL();
        sql.SELECT(" _this.*");
        sql.SELECT(" w.name AS wmsName");
        sql.SELECT(" CONCAT_WS('-',p.plan_year,p.plan_month) AS planMonth ");
        sql.FROM(this.getTableNameThis());
        sql.LEFT_OUTER_JOIN(joinString("wms", "w", "id", "wms_id"));
        sql.LEFT_OUTER_JOIN(joinString("plan_record", "p", "id", "plan_id"));
        this.buildWhere(sql, invoicing);
        return sql.toString() + buildSearchPageSql(invoicing);
    }
}
