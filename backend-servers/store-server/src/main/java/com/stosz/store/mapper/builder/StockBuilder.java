package com.stosz.store.mapper.builder;

import com.stosz.plat.mapper.AbstractBuilder;
import com.stosz.store.ext.model.Stock;
import org.apache.ibatis.jdbc.SQL;

/**
 * @Author:yangqinghui
 * @Description:StockBuilder
 * @Created Time:2017/11/22 19:40
 * @Update Time:
 */
public class StockBuilder extends AbstractBuilder<Stock> {

    @Override
    public void buildSelectOther(SQL sql) {

    }

    @Override
    public void buildJoin(SQL sql) {

    }

    @Override
    public void buildWhere(SQL sql, Stock stock) {
        eq(sql, "_this.wms_id", "wmsId", stock.getWmsId());
        eq(sql, "_this.dept_id", "deptId", stock.getDeptId());
        eq(sql, "_this.spu", "spu", stock.getSpu());
        eq(sql, "_this.sku", "sku", stock.getSku());
    }

    public String query(Stock stock) {
        SQL sql = new SQL();
        sql.SELECT(" _this.*");
        sql.SELECT(" w.name AS wmsName");
        sql.FROM(this.getTableNameThis());
        sql.LEFT_OUTER_JOIN(joinString("wms", "w", "id", "wms_id"));
        this.buildWhere(sql, stock);
        stock.setOrderBy("id");
        return sql.toString() + buildSearchPageSql(stock);
    }


}
