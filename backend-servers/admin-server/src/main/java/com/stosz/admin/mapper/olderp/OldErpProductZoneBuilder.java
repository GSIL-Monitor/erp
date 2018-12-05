package com.stosz.admin.mapper.olderp;

import com.stosz.olderp.ext.model.OldErpProductZone;
import com.stosz.plat.mapper.AbstractBuilder;
import org.apache.ibatis.jdbc.SQL;

/**
 * @author xiongchenyang
 * @version [1.0 , 2017/9/5]
 */
public class OldErpProductZoneBuilder extends AbstractBuilder<OldErpProductZone> {

    @Override
    public void buildSelectOther(SQL sql) {

    }

    @Override
    public void buildJoin(SQL sql) {

    }

    @Override
    public void buildWhere(SQL sql, OldErpProductZone param) {

    }

    public String findByLimit(Integer limit, Integer start) {
        SQL sql = new SQL();
        sql.SELECT("id,id_product as product_id, id_department as department_id, status");
        sql.FROM(getTableName());
        return sql.toString() + " limit " + " #{limit} " + " offset " + " #{start}";
    }

}
