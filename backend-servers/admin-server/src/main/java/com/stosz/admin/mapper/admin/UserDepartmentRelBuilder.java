package com.stosz.admin.mapper.admin;

import com.stosz.plat.mapper.AbstractBuilder;
import com.stosz.product.ext.model.UserDepartmentRel;
import org.apache.ibatis.jdbc.SQL;

public class UserDepartmentRelBuilder extends AbstractBuilder<UserDepartmentRel> {

	@Override
    public void buildSelectOther(SQL sql) {
	    
    }

	@Override
    public void buildJoin(SQL sql) {
	    
    }

	@Override
    public void buildWhere(SQL sql, UserDepartmentRel param) {
		eq(sql, "usable", "usable", param.getUsable());
	    eq(sql, "user_id", "userId", param.getUserId());
	    eq(sql, "department_id", "departmentId", param.getDepartmentId());
        eq(sql, "department_no", "departmentNo", param.getDepartmentNo());

    }

}
