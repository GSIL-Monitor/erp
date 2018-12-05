package com.stosz.purchase.mapper;

import com.stosz.plat.mapper.AbstractBuilder;
import com.stosz.purchase.ext.model.UserProductRel;
import org.apache.ibatis.jdbc.SQL;

public class UserProductRelBuilder extends AbstractBuilder<UserProductRel> {

	@Override
	public void buildSelectOther(SQL sql) {

	}

	@Override
	public void buildJoin(SQL sql) {

	}

	@Override
	public void buildWhere(SQL sql, UserProductRel param) {
		eq(sql, _this("user_id"), "userId", param.getUserId()); // 用户查询
		like_r(sql, _this("spu"), "spu", param.getSpu());
		eq(sql, _this("enable"), "enable", param.getEnable());
	}

}
