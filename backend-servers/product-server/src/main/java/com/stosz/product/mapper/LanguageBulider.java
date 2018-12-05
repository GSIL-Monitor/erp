package com.stosz.product.mapper;

import com.stosz.plat.mapper.AbstractBuilder;
import com.stosz.product.ext.model.Language;
import org.apache.ibatis.jdbc.SQL;

public class LanguageBulider extends AbstractBuilder<Language> {

	@Override
	public void buildSelectOther(SQL sql) {
		
	}

	@Override
	public void buildJoin(SQL sql) {
		
	}

	@Override
	public void buildWhere(SQL sql, Language param) {
		param.setOrderBy("sort");
		param.setOrder(false);
		like_i(sql, "name", "name", param.getName());
	}

}
