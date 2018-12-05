package com.stosz.store.mapper.builder;

import com.stosz.plat.mapper.AbstractBuilder;
import com.stosz.store.ext.dto.request.SearchTakeStockReq;
import com.stosz.store.ext.model.TakeStock;
import org.apache.ibatis.jdbc.SQL;

/**
 * @Author:ChenShifeng
 * @Description:TransitStockBuilder
 * @Created Time:2017/11/23 14:21
 * @Update Time:
 */
public class TakeStockBuilder extends AbstractBuilder<TakeStock> {

    @Override
    public void buildSelectOther(SQL sql) {

    }

    @Override
    public void buildJoin(SQL sql) {

    }

    @Override
    public void buildWhere(SQL sql, TakeStock param) {

    }

    /**
     * 搜索
     *
     * @return
     */
    public String getSearch(SearchTakeStockReq param) {
        SQL sql = new SQL();
        sql.SELECT(_this("*"));
        this.searchFrom(sql, param);

        StringBuilder searchPageSql = new StringBuilder();
        searchPageSql.append(" order by _this.id desc");
        searchPageSql.append(" limit ").append(param.getStart()).append(",").append(param.getLimit());

        return sql.toString() + searchPageSql.toString();
    }

    /**
     * 搜索 count条数
     *
     * @return
     */
    public String countSearch(SearchTakeStockReq param) {
        SQL sql = new SQL();
        sql.SELECT("count(1)");
        this.searchFrom(sql, param);

        return sql.toString();
    }


    private void searchFrom(SQL sql, SearchTakeStockReq param) {
        sql.FROM(getTableNameThis());

        eq(sql, _this("id"), "id", param.getId());
        eq(sql, _this("state"), "state", param.getState());
        eq(sql, _this("approver"), "approver", param.getApprover());
        eq(sql, _this("wms_id"), "wmsId", param.getWmsId());

        ge(sql, _this("create_at"), "createAtStartTime", param.getCreateAtStartTime());
        le(sql, _this("create_at"), "createAtEndTime", param.getCreateAtEndTime());

        SQL sqlIn=new SQL();
        sqlIn.SELECT_DISTINCT("it.take_stock_id");
        sqlIn.FROM(" take_stock_item it ");
        sqlIn.JOIN(" transit_stock tr on it.tracking_no_old=tr.tracking_no_old ");
        like_i(sqlIn, "tr.inner_name", "innerName", param.getInnerName());
        like_i(sqlIn, "tr.sku", "sku", param.getSku());

        sql.WHERE(String.format("_this.id in(%s) ",sqlIn.toString()));

    }

}
