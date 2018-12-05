package com.stosz.store.mapper.builder;

import com.stosz.plat.mapper.AbstractBuilder;
import com.stosz.store.ext.dto.request.SearchInvalidReq;
import com.stosz.store.ext.model.Invalid;
import org.apache.ibatis.jdbc.SQL;

/**
 * @Author:ChenShifeng
 * @Description:报废
 * @Created Time:2017/11/23 14:21
 * @Update Time:
 */
public class InvalidBuilder extends AbstractBuilder<Invalid> {

    @Override
    public void buildSelectOther(SQL sql) {

    }

    @Override
    public void buildJoin(SQL sql) {

    }

    @Override
    public void buildWhere(SQL sql, Invalid param) {

    }

    /**
     * 搜索
     *
     * @return
     */
    public String getSearch(SearchInvalidReq param) {
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
    public String countSearch(SearchInvalidReq param) {
        SQL sql = new SQL();
        sql.SELECT("count(1)");
        this.searchFrom(sql, param);

        return sql.toString();
    }


    private void searchFrom(SQL sql, SearchInvalidReq param) {
        sql.FROM(getTableNameThis());
        eq(sql, _this("id"), "id", param.getId());
        eq(sql, _this("wms_id"), "transitId", param.getTransitId());
        eq(sql, _this("creator"), "creator", param.getCreator());
        ge(sql, _this("create_at"), "createAtStartTime", param.getCreateAtStartTime());
        le(sql, _this("create_at"), "createAtEndTime", param.getCreateAtEndTime());

        return;
    }

}
