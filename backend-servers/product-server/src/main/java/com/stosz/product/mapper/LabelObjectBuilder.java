package com.stosz.product.mapper;

import com.stosz.product.ext.model.LabelObject;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;
import com.stosz.plat.mapper.AbstractBuilder;

public class LabelObjectBuilder extends AbstractBuilder<LabelObject>{

	@Override
	public void buildSelectOther(SQL sql) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void buildJoin(SQL sql) {
		
	}

	@Override
	public void buildWhere(SQL sql, LabelObject param) {
		// TODO Auto-generated method stub
		
	}


	/**
	 * 标签数据labelId, objectId, objectType 唯一性
	 */
	public String lableObjectCheck(@Param("id") Integer id, @Param("labelId") Integer labelId,
								   @Param("objectId") Integer objectId, @Param("objectType") String objectType){
		SQL sql = new SQL();
		sql.SELECT(" COUNT(1) ");
		sql.FROM(getTableNameThis());
		lableObjectCheckWhere(sql, id, labelId, objectId, objectType);
		return sql.toString();
	}
	private void lableObjectCheckWhere(SQL sql, Integer id, Integer labelId, Integer objectId, String objectType) {
		eq(sql, "label_id", "labelId", labelId);
		eq(sql, "object_id", "objectId", objectId);
		eq(sql, "object_type", "objectType", objectType);
		neq(sql, "id", "id", id);
	}
	public void neq(SQL sql, String column, String field, Object value) {
		if (notEmpty(value)) {
			sql.WHERE(String.format("%s!=#{%s}", column, field));
		}
	}






}
