package com.stosz.product.mapper;

import com.stosz.plat.mapper.AbstractBuilder;
import com.stosz.product.ext.model.Attribute;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

import java.text.MessageFormat;
import java.util.List;

public class AttributeBulider extends AbstractBuilder<Attribute> {

	@Override
	public void buildSelectOther(SQL sql) {

		
	}

	@Override
	public void buildJoin(SQL sql) {

		
	}

	@Override
	public void buildWhere(SQL sql, Attribute param) {
		eq(sql, "version", "version", param.getVersion());
		like_i(sql, "title", "title", param.getTitle());
	}
	

	
	
	public void buildCountListWhere(SQL sql, Attribute param) {
		sql.WHERE(" car.category_id = #{categoryId} OR car.id IS NULL ");
		like_i(sql, "title", "title", param.getTitle());
		if(param.getBindIs()){
			sql.WHERE(" car.id IS NOT NULL ");
		}else{
			sql.WHERE(" car.id IS NULL ");
		}
	}

	private void fillCategoryBindCaseCondition(SQL sql,Attribute param){
		sql.FROM(getTableNameThis());
		sql.LEFT_OUTER_JOIN(" category_attribute_rel car ON _this.id = car.attribute_id AND car.category_id = #{categoryId}");
		eq(sql, "version", "version", param.getVersion());
		like_i(sql, "title", "title", param.getTitle());
		if(param.getBindIs() != null){
			if(param.getBindIs()){
				sql.WHERE(" car.id IS NOT NULL ");
			}else{
				sql.WHERE(" car.id IS NULL ");
			}
		}

	}
	
	/**
	 * 根据id和Title统计重复数量
	 */
	public String countByTitleId(@Param("title") String title,@Param("id") Integer id){
		SQL sql = new SQL();
		sql.SELECT(" COUNT(1) ");
		sql.FROM(getTableNameThis());
		countByTitleWhere(sql,title,id);
		return sql.toString();
	}
	
	private void countByTitleWhere(SQL sql, String title, Integer id) {
		eq(sql, "title", "title", title);
		neq(sql, "id", "id", id);
	}
	
    public void neq(SQL sql, String column, String field, Object value) {
        if (notEmpty(value)) {
            sql.WHERE(String.format("%s!=#{%s}", column, field));
        }
    }
	
	/**
	 * 品类下的属性列表
	 */
	public String findCategoryBindCase(Attribute param){
		param.setOrderBy(" bindIs DESC , _this.id ");
		SQL sql = new SQL();
		sql.SELECT(_this("*"));
		sql.SELECT(" IF(car.id IS NULL,FALSE,TRUE) bindIs ");
		fillCategoryBindCaseCondition(sql,param);
		String pageStr = buildSearchPageSql(param);
		return sql.toString() + pageStr;
	}
	
	/**
	 * 品类下的属性列表总数统计
	 */
	public String countCategoryBindCase(Attribute param){
		SQL sql = new SQL();
		sql.SELECT(" COUNT(1) ");
		fillCategoryBindCaseCondition(sql,param);
		return sql.toString();
	}

	public String insertBatch(List<Attribute> attributeList) {
		StringBuilder sb = new StringBuilder();
		sb.append("INSERT INTO attribute ");
		sb.append("(id, title, create_at)");
		sb.append("VALUES ");
		MessageFormat mf = new MessageFormat("(#'{'attributeList[{0}].id},  #'{'attributeList[{0}].title},current_timestamp())");
		for (int i = 0; i < attributeList.size(); i++) {
			String j = i + "";
			sb.append(mf.format(new Object[]{j}));
			if (i < attributeList.size() - 1) {
				sb.append(",");
			}
		}
		return sb.toString();
	}
	
/******************************************************属性值的查询(version字段)*******************************************************/	
	public String getByTitle(@Param("version") Integer version,@Param("title") String title){
		SQL sql  = new SQL();
		sql.SELECT("*");
		sql.FROM(getTableName());
		getByTitleWhere(sql,version,title);
		return sql.toString();
	}
	public void getByTitleWhere(SQL sql,Integer version ,String title) {
		eq(sql, "version", "version", version);
		eq(sql, "title", "title", title);
	}
//select _this.* from attribute _this join category_attribute_rel rel on _this.id=rel.attribute_id where rel.category_id=#{categoryId}
	public String findByCategoryId(@Param("version") Integer version,@Param("categoryId") Integer categoryId){
		SQL sql  = new SQL();
		sql.SELECT("_this.*, COUNT(1) AS bindingNumber ");
		sql.FROM(getTableNameThis());
		sql.LEFT_OUTER_JOIN(" category_attribute_rel rel ON _this.id = rel.attribute_id ");
		sql.LEFT_OUTER_JOIN(" product_attribute_rel pa ON rel.attribute_id = pa.attribute_id ");
		findByCategoryIdWhere(sql,version,categoryId);
		sql.GROUP_BY(" _this.id");
		return sql.toString();
	}
	public void findByCategoryIdWhere(SQL sql, Integer version,Integer categoryId) {
		eq(sql, "version", "version", version);
		eq(sql, "category_id", "categoryId", categoryId);
	}
//SELECT a.*, IF(rel.id IS NULL,FALSE,TRUE) bindIs FROM attribute a LEFT OUTER JOIN product_attribute_rel rel ON a.id = rel.attribute_id WHERE rel.product_id = #{productId}	
	public String findByProductId(@Param("version") Integer version,@Param("productId") Integer productId){
		SQL sql  = new SQL();
		sql.SELECT("_this.*");
		sql.SELECT(" IF(rel.id IS NULL,FALSE,TRUE) bindIs ");
		sql.FROM(getTableNameThis());
		sql.LEFT_OUTER_JOIN(" product_attribute_rel rel ON _this.id = rel.attribute_id ");
		findByProductIdWhere(sql,version,productId);
		return sql.toString();
	}


	public String findByProductIdUseRelId(@Param("version") Integer version,@Param("productId") Integer productId){
		SQL sql  = new SQL();
		sql.SELECT("_this.id id, rel.id productAttributeRelId ,_this.create_at create_at,_this.update_at update_at,_this.title title, _this.version version");
		sql.SELECT(" IF(rel.id IS NULL,FALSE,TRUE) bindIs ");
		sql.FROM(getTableNameThis());
		sql.LEFT_OUTER_JOIN(" product_attribute_rel rel ON _this.id = rel.attribute_id ");
		findByProductIdWhere(sql,version,productId);
		return sql.toString();
	}



	public void findByProductIdWhere(SQL sql, Integer version,Integer productId) {
		if(null!= version)
		{
			eq(sql, "version", "version", version);
		}
		eq(sql, "product_id", "productId", productId);
	}
	
	
	
	
	
}




