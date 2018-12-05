package com.stosz.product.mapper;

import com.stosz.plat.mapper.AbstractBuilder;
import com.stosz.product.ext.model.CategoryLang;
import org.apache.ibatis.jdbc.SQL;

public class CategoryLangBuilder extends AbstractBuilder<CategoryLang> {

	@Override
	public void buildSelectOther(SQL sql) {
	
		
	}

	@Override
	public void buildJoin(SQL sql) {
		
		
	}

	@Override
	public void buildWhere(SQL sql, CategoryLang param) {
		
		
	}
	
	
	public String countByNameCodeId(CategoryLang param){
		SQL sql = new SQL();
		sql.SELECT(" COUNT(1) ");
		sql.FROM(getTableName());
		countWhere(sql,param);
		return sql.toString();
	}

	private void countWhere(SQL sql, CategoryLang param) {
	//	eq(sql, "name", "name", param.getName());
		eq(sql, "category_id", "categoryId", param.getCategoryId());
		eq(sql, "lang_code", "langCode", param.getLangCode());
		neq(sql, "id", "id", param.getId());
	}
	
    public void neq(SQL sql, String column, String field, Object value) {
        if (notEmpty(value)) {
            sql.WHERE(String.format("%s!=#{%s}", column, field));
        }
    }
	
	
	
}