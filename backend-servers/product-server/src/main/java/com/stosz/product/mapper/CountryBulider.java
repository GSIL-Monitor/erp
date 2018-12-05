package com.stosz.product.mapper;

import com.stosz.plat.mapper.AbstractBuilder;
import com.stosz.product.ext.model.Country;
import org.apache.ibatis.jdbc.SQL;

public class CountryBulider extends AbstractBuilder<Country> {

	@Override
	public void buildSelectOther(SQL sql) {
		
	}

	@Override
	public void buildJoin(SQL sql) {
		
	}

	@Override
	public void buildWhere(SQL sql, Country param) {
		like_i(sql, "name", "name",param.getName());
	}
	
}
