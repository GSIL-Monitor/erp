package com.stosz.product.mapper;

import com.stosz.plat.mapper.AbstractBuilder;
import com.stosz.product.ext.model.CurrencyLog;
import org.apache.ibatis.jdbc.SQL;

public class CurrencyLogBulider extends AbstractBuilder<CurrencyLog> {

	@Override
	public void buildSelectOther(SQL sql) {
		
	}

	@Override
	public void buildJoin(SQL sql) {
		
	}

	@Override
	public void buildWhere(SQL sql, CurrencyLog param) {
		like_i(sql, "name", "name", param.getName());
		eq(sql, "currency_code", "currencyCode", param.getCurrencyCode());
		between_and(sql, "update_at", "minCreateAt", "maxCreateAt", param.getMinCreateAt(), param.getMaxCreateAt());
	}
	
	
    public void between_and(SQL sql, String column, String field, String realm, Object fieldv , Object realmv) {
        if (notEmpty(fieldv) && notEmpty(realmv)) {
            sql.WHERE(String.format("%s BETWEEN #{%s} AND #{%s} ", column, field , realm));
        }
    }
	
	
}
