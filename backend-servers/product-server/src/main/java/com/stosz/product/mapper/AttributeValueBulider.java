package com.stosz.product.mapper;

import com.stosz.plat.mapper.AbstractBuilder;
import com.stosz.product.ext.model.AttributeValue;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

public class AttributeValueBulider extends AbstractBuilder<AttributeValue> {

	@Override
	public void buildSelectOther(SQL sql) {
		
	}

	@Override
	public void buildJoin(SQL sql) {
		
	}

	@Override
	public void buildWhere(SQL sql, AttributeValue param) {
		eq(sql, "version", "version", param.getVersion());
		eq(sql, "attribute_id", "attributeId", param.getAttributeId());
	}
	
	public String countByTitleAttId(AttributeValue param){
		SQL sql = new SQL();
		sql.SELECT(" COUNT(1) ");
		sql.FROM(getTableName());
		countWhere(sql,param);
		return sql.toString();
	}

	private void countWhere(SQL sql, AttributeValue param) {
		eq(sql, "attribute_id", "attributeId", param.getAttributeId());
		eq(sql, "title", "title", param.getTitle());
		neq(sql, "id", "id", param.getId());
	}
	
    public void neq(SQL sql, String column, String field, Object value) {
        if (notEmpty(value)) {
            sql.WHERE(String.format("%s!=#{%s}", column, field));
        }
    }
/******************************************************属性值的查询(version字段)*******************************************************/
//select id , attribute_id, title from attribute_value where title = #{title} and attribute_id = #{attributeId}    
    public String getByTitleAndAttribute(@Param("version") Integer version,
    		@Param("title") String title, @Param("attributeId") Integer attributeId){
		SQL sql  = new SQL();
		sql.SELECT(" id , attribute_id, title ");
		sql.FROM(getTableName());
		getByTitleAndAttributeWhere(sql,version,title,attributeId);
		return sql.toString();
	}
	public void getByTitleAndAttributeWhere(SQL sql,Integer version ,String title,Integer attributeId) {
		eq(sql, "version", "version", version);
		eq(sql, "title", "title", title);
		eq(sql, "attribute_id", "attributeId", attributeId);
	}

	
	public String findByAttributeValue(AttributeValue param){
		SQL sql = new SQL();
		sql.SELECT(" av.*, IF(rel.id IS NULL,FALSE,TRUE) bindIs ");
		sql.FROM(" attribute_value av ");
		sql.LEFT_OUTER_JOIN(" product_attribute_value_rel rel ON av.id = rel.attribute_value_id AND rel.product_id = "+param.getProductId()+"");
		findByAttributeValueWhere(sql,param);
		sql.ORDER_BY(" bindIs DESC ");
		return sql.toString();
	}
	
	public void findByAttributeValueWhere(SQL sql,AttributeValue param){
		eq(sql, "av.version", "version", param.getVersion());
		eq(sql, "av.attribute_id", "attributeId", param.getAttributeId());
		if(param.getBindIs() != null){
			StringBuilder sb = new StringBuilder();
			if(param.getBindIs()){
				sb.append(" rel.id IS NOT NULL ");
			}else{
				sb.append(" rel.id IS NULL ");
			}
			sql.WHERE(sb.toString());
		}
		
	}
	
	
	
}