package com.stosz.purchase.mapper;

import com.stosz.plat.mapper.AbstractBuilder;
import com.stosz.purchase.ext.model.UserBuDept;
import org.apache.ibatis.jdbc.SQL;

public class UserBuDeptBuilder extends AbstractBuilder<UserBuDept> {

	@Override
	public void buildSelectOther(SQL sql) {

	}

	@Override
	public void buildJoin(SQL sql) {

	}

	@Override
	public void buildWhere(SQL sql, UserBuDept param) {
		eq(sql, _this("user_id"), "userId", param.getUserId()); // 用户查询
		eq(sql, _this("bu_department_id"), "buDepartmentId", param.getBuDepartmentId()); // 部门
		eq(sql, _this("enable"), "enable", param.getEnable());// 是否有效
	}

}
