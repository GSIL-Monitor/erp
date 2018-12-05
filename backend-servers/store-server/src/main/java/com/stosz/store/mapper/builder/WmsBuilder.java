package com.stosz.store.mapper.builder;


import com.stosz.plat.mapper.AbstractBuilder;
import com.stosz.store.ext.model.Wms;
import org.apache.ibatis.jdbc.SQL;

/**
 * @auther carter
 * create time    2017-11-23
 */
public class WmsBuilder extends AbstractBuilder<Wms> {
    @Override
    public void buildSelectOther(SQL sql) {

    }

    @Override
    public void buildJoin(SQL sql) {

    }

    @Override
    public void buildWhere(SQL sql, Wms wms) {
        eq(sql, _this("id"), "id", wms.getId());
        eq(sql, _this("zone_id"), "zoneId", wms.getZoneId());
        eq(sql, _this("type"), "type", wms.getType());
        eq(sql, _this("deleted"), "deleted", wms.getDeleted());
    }

    public String query(Wms wms) {
        SQL sql = new SQL();
        sql.SELECT(" _this.*");
        sql.FROM(this.getTableNameThis());
        eq(sql, "_this.id", "id", wms.getId());
        eq(sql, "_this.zone_id", "zoneId", wms.getZoneId());
        eq(sql, "_this.type", "type", wms.getType());
        eq(sql, "_this.deleted", "deleted", wms.getDeleted());

        String pageStr = buildSearchPageSql(wms);
        return sql.toString() + pageStr;
    }
}
