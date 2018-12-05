package com.stosz.product.mapper;

import com.stosz.plat.mapper.AbstractBuilder;
import com.stosz.product.ext.model.ProductZoneDomain;
import org.apache.ibatis.jdbc.SQL;

public class ProductZoneDomainBuilder extends AbstractBuilder<ProductZoneDomain> {

	@Override
	public void buildSelectOther(SQL sql) {

	}

	@Override
	public void buildJoin(SQL sql) {

	}
    @Override
	public void buildWhere(SQL sql, ProductZoneDomain param) {
		eq(sql, "domain", "domain", param.getDomain());
		eq(sql, "web_directory", "webDirectory", param.getWebDirectory());
		eq(sql, "loginid", "loginid", param.getLoginid());
//		eq(sql, "product_zone_id", "productZoneId", param.getProductZoneId());
		eq(sql, "site_product_id", "siteProductId", param.getSiteProductId());
	}


	public String updateBySiteProductId() {
		SQL sql = new SQL();
		sql.UPDATE(getTableName());
		sql.SET( "domain=#{domain}","web_directory=#{webDirectory}");
		sql.WHERE("siteProductId=#{siteProductId}");
		return sql.toString();
	}


}