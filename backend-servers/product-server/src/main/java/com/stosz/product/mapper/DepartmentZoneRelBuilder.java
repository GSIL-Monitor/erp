package com.stosz.product.mapper;

import com.stosz.plat.mapper.AbstractBuilder;
import com.stosz.product.ext.model.DepartmentZoneRel;
import org.apache.ibatis.jdbc.SQL;

public class DepartmentZoneRelBuilder extends AbstractBuilder<DepartmentZoneRel> {

	@Override
    public void buildSelectOther(SQL sql) {
	    
    }

	@Override
    public void buildJoin(SQL sql) {
	    
    }

	@Override
    public void buildWhere(SQL sql, DepartmentZoneRel param) {
	    eq(sql, "department_id", "departmentId", param.getDepartmentId());
	    eq(sql, "zone_id", "zoneId", param.getZoneId());
	    eq(sql, "usable", "usable", param.getUsable());
    }

    public String findAllList(DepartmentZoneRel param){
        SQL sql = new SQL();
        sql.SELECT("*");
        sql.FROM(getTableNameThis());
        findAllWhere(sql, param);
        return sql.toString();
    }
    private void findAllWhere(SQL sql, DepartmentZoneRel param){
        eq(sql, "department_id", "departmentId", param.getDepartmentId());
        eq(sql, "zone_id", "zoneId", param.getZoneId());
        eq(sql, "usable", "usable", param.getUsable());
    }

}
