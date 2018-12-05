package com.stosz.product.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

import com.stosz.plat.mapper.AbstractBuilder;
import com.stosz.product.ext.model.Label;

public class LabelBuilder extends AbstractBuilder<Label> {

	@Override
	public void buildSelectOther(SQL sql) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void buildJoin(SQL sql) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void buildWhere(SQL sql, Label param) {
		// TODO Auto-generated method stub
		
	}


	/**
	 * 根据id和name统计重复数量
	 */
	public String countByName(@Param("id") Integer id, @Param("parentId") Integer parentId, @Param("name") String name){
		SQL sql = new SQL();
		sql.SELECT(" COUNT(1) ");
		sql.FROM(getTableNameThis());
		countByNameWhere(sql, name, id, parentId);
		return sql.toString();
	}
	private void countByNameWhere(SQL sql, String name, Integer id, Integer parentId) {
		eq(sql, "name", "name", name);
		eq(sql, "parent_id", "parentId", parentId);
		neq(sql, "id", "id", id);
	}
	public void neq(SQL sql, String column, String field, Object value) {
		if (notEmpty(value)) {
			sql.WHERE(String.format("%s!=#{%s}", column, field));
		}
	}

}
