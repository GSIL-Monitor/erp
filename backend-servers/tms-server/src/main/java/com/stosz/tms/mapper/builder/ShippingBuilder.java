package com.stosz.tms.mapper.builder;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.stosz.plat.mapper.AbstractBuilder;
import com.stosz.tms.model.Shipping;

public class ShippingBuilder extends AbstractBuilder<Shipping> {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	public void buildSelectOther(SQL sql) {

	}

	@Override
	public void buildJoin(SQL sql) {

	}

	@Override
	public void buildWhere(SQL sql, Shipping param) {
		in(sql, "shipping_code", "shippingCode", param.getInShppingCodes());
		eq(sql,"shipping_name","shippingName",param.getShippingName());
	}

	public String getShippingList(@Param("name") String name, @Param("start") int start, @Param("limit") int limit) {
		SQL sql = new SQL();

		sql.SELECT("*");
		sql.FROM(getTableNameThis());

		if (name != null) {
			sql.WHERE("name == #{name}");
		}

		logger.debug(sql.toString() + " limit " + start + "," + limit);
		return sql.toString() + " limit " + start + "," + limit;
	}

}
