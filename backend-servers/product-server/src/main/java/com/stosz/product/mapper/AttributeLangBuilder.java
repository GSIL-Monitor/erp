package com.stosz.product.mapper;

import com.stosz.plat.mapper.AbstractBuilder;
import com.stosz.product.ext.model.AttributeLang;
import org.apache.ibatis.jdbc.SQL;

public class AttributeLangBuilder extends AbstractBuilder<AttributeLang> {

	@Override
	public void buildSelectOther(SQL sql) {
	
		
	}

	@Override
	public void buildJoin(SQL sql) {
		
		
	}

	@Override
	public void buildWhere(SQL sql, AttributeLang param) {
		
		
	}
	
	
	public String countByNameCodeId(AttributeLang param){
		SQL sql = new SQL();
		sql.SELECT(" COUNT(1) ");
		sql.FROM(getTableName());
		countWhere(sql,param);
		return sql.toString();
	}

	private void countWhere(SQL sql, AttributeLang param) {
	//	eq(sql, "name", "name", param.getName());
		eq(sql, "attribute_id", "attributeId", param.getAttributeId());
		eq(sql, "lang_code", "langCode", param.getLangCode());
		neq(sql, "id", "id", param.getId());
	}
	
    public void neq(SQL sql, String column, String field, Object value) {
        if (notEmpty(value)) {
            sql.WHERE(String.format("%s!=#{%s}", column, field));
        }
    }
	
	
	
}