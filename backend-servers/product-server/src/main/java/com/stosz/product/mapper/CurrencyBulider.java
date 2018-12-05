package com.stosz.product.mapper;

import com.stosz.plat.mapper.AbstractBuilder;
import com.stosz.product.ext.model.Currency;
import org.apache.ibatis.jdbc.SQL;

public class CurrencyBulider extends AbstractBuilder<Currency> {

	@Override
	public void buildSelectOther(SQL sql) {
		
	}

	@Override
	public void buildJoin(SQL sql) {
		
	}

	@Override
	public void buildWhere(SQL sql, Currency param) {
		like_i(sql, "name", "name", param.getName());
		eq(sql,"usable","usable", param.getUsable());
	}

	public String getByCurrencyCode(){
		StringBuilder sb = new StringBuilder();
		sb.append("select * from currency where currency_code = #{currencyCode}");
		return sb.toString();
	}

	public String findList(Currency param) {
		SQL sql = new SQL();
		sql.SELECT("*");
		sql.FROM(getTableNameThis());
		findCurrencyWhere(sql, param);
		String pageStr = buildSearchPageSql(param);
		return sql.toString() + pageStr;
	}

	private void findCurrencyWhere(SQL sql, Currency param){
		like_i(sql, "name", "name", param.getName());
		eq(sql,"usable","usable", param.getUsable());
	}

	public String findAllList(Currency param) {
		SQL sql = new SQL();
		sql.SELECT("*");
		sql.FROM(getTableNameThis());
		findCurrencyWhere(sql, param);
		return sql.toString();
	}
}
