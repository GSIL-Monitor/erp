package com.stosz.admin.mapper.admin;

import com.stosz.admin.ext.model.Menu;
import com.stosz.plat.mapper.AbstractBuilder;
import org.apache.ibatis.jdbc.SQL;

/**
 * @author xiongchenyang
 * @version [1.0 , 2017/8/28]
 */
public class MenuBuilder extends AbstractBuilder<Menu> {


    @Override
    public void buildSelectOther(SQL sql) {

    }

    @Override
    public void buildJoin(SQL sql) {

    }

    @Override
    public void buildWhere(SQL sql, Menu param) {
        eq(sql, "status", "status", param.getStatus());
        eq(sql, "sattus", "status", param.getIsShow());
    }


    public String findByJobId(Integer jobId) {
        SQL sql = new SQL();
        Menu menu = new Menu();
        menu.setStatus("1");
        menu.setIsShow("1");
        sql.SELECT("*");
        sql.FROM(getTableNameThis());
        sql.LEFT_OUTER_JOIN(joinString("job_menu", "jb", "menu_id", "id"));
        sql.WHERE("m.job_id = #'{'jobId}");
        buildWhere(sql, menu);
        return sql.toString();
    }

}
