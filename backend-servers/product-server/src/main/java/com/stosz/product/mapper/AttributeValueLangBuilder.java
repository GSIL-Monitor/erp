package com.stosz.product.mapper;

import com.stosz.plat.mapper.AbstractBuilder;
import com.stosz.plat.utils.StringUtils;
import com.stosz.product.ext.model.AttributeValueLang;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

import java.util.List;

public class AttributeValueLangBuilder extends AbstractBuilder<AttributeValueLang> {

	@Override
	public void buildSelectOther(SQL sql) {
		
	}

	@Override
	public void buildJoin(SQL sql) {
		
	}

	@Override
	public void buildWhere(SQL sql, AttributeValueLang param) {
		
	}

	public String findByAttributeValueIds(@Param("attributeValueIds")List<Integer> attributeValueIds){
		SQL sql = findSQL(new AttributeValueLang());
		if (attributeValueIds == null || attributeValueIds.isEmpty()) {
			sql.WHERE("1!=1");
		}else{
			sql.WHERE( "attribute_value_id in (" +  StringUtils.join(attributeValueIds,',') +")" );
		}
		return sql.toString();
	}
	
	public String countByNameCodeId(AttributeValueLang param){
		SQL sql = new SQL();
		sql.SELECT(" COUNT(1) ");
		sql.FROM(getTableName());
//      eq(sql, "name", "name", param.getName());
		eq(sql, "attribute_value_id", "attributeValueId", param.getAttributeValueId());
        eq(sql, "lang_code", "langCode", param.getLangCode());
        neq(sql, "id", "id", param.getId());
		return sql.toString();
	}

	
    public void neq(SQL sql, String column, String field, Object value) {
        if (notEmpty(value)) {
            sql.WHERE(String.format("%s!=#{%s}", column, field));
        }
    }
	
	
	
	
}