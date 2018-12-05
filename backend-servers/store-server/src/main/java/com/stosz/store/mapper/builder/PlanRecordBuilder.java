package com.stosz.store.mapper.builder;

import com.stosz.plat.mapper.AbstractBuilder;
import com.stosz.store.ext.model.PlanRecord;
import org.apache.ibatis.jdbc.SQL;

/**
 * @Author:yangqinghui
 * @Description:
 * @Created Time:2017/11/24 17:20
 * @Update Time:
 */
public class PlanRecordBuilder extends AbstractBuilder<PlanRecord> {

    @Override
    public void buildSelectOther(SQL sql) {

    }

    @Override
    public void buildJoin(SQL sql) {

    }

    @Override
    public void buildWhere(SQL sql, PlanRecord param) {

    }

    public String query(PlanRecord planRecord) {
        SQL sql = new SQL();
        sql.SELECT(" _this.*");
        sql.FROM(this.getTableNameThis());
        eq(sql, "plan_year", "planYear", planRecord.getPlanYear());
        eq(sql, "plan_month", "planMonth", planRecord.getPlanMonth());
        return sql.toString();
    }
}
