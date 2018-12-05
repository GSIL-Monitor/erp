package com.stosz.product.mapper;

import com.stosz.plat.mapper.AbstractBuilder;
import com.stosz.product.ext.model.Zone;
import org.apache.ibatis.jdbc.SQL;

public class ZoneBuilder extends AbstractBuilder<Zone> {

	@Override
	public void buildSelectOther(SQL sql) {
		
	}

	@Override
	public void buildJoin(SQL sql) {
		
	}

	@Override
	public void buildWhere(SQL sql, Zone param) {
		param.setOrderBy("sort");
		param.setOrder(false);
		like_i(sql, "title", "title", param.getTitle());
	}

	public String findList(Zone param) {
		param.setOrderBy("sort ASC ,id");
		param.setOrder(true);
		SQL sql = new SQL();
		sql.SELECT("c.name AS countryName,z.*");
		sql.FROM("zone z LEFT JOIN country c ON z.country_id = c.id");
		findZoneWhere(sql, param);
		String pageStr = buildSearchPageSql(param);
		return sql.toString() + pageStr;
	}

	private void findZoneWhere(SQL sql, Zone param){
		like_i(sql, "title", "title", param.getTitle());
	}
}
