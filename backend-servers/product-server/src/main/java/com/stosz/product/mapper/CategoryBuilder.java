package com.stosz.product.mapper;

import com.stosz.plat.mapper.AbstractBuilder;
import com.stosz.product.ext.model.Category;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

import java.text.MessageFormat;
import java.util.List;

public class CategoryBuilder extends AbstractBuilder<Category> {

	@Override
	public void buildSelectOther(SQL sql) {

	}

	@Override
	public void buildJoin(SQL sql) {

	}

	@Override
	public void buildWhere(SQL sql, Category param) {
		eq(sql, _this("id"), "id", param.getId());
		eq(sql, _this("usable"), "usable", param.getUsable());
		like_r(sql, _this("no"), "no", param.getNo());
		like_i(sql, _this("name"), "name", param.getName());
		eq(sql, _this("parent_id"), "parentId", param.getParentId());

	}

	public String insertBatch(List<Category> categoryList) {
		StringBuilder sb = new StringBuilder();
		sb.append("INSERT INTO category ");
		sb.append("(id, name, parent_id, no, usable, create_at,)");
		sb.append("VALUES ");
		MessageFormat mf = new MessageFormat("(#'{'categoryList[{0}].id}, #'{'categoryList[{0}].name},#'{'categoryList[{0}].parentId}, #'{'categoryList[{0}].usable}, current_timestamp())");
		for (int i = 0; i < categoryList.size(); i++) {
			String j = i + "";
			sb.append(mf.format(new Object[]{j}));
			if (i < categoryList.size() - 1) {
				sb.append(",");
			}
		}
		return sb.toString();
	}

	// @Select("SELECT COUNT(1) FROM category WHERE parent_id = #{parentId} AND NAME LIKE CONCAT('%',#{name},'%')")
	public String categorySearch(Category param) {
		SQL sql = new SQL();
		sql.SELECT(" * ");
		sql.FROM(getTableNameThis());
		categorySearchWhere(sql, param);
//		String pageStr = buildSearchPageSql(param);
		return sql.toString();
	}

	public String categorySearchCount(Category param) {
		SQL sql = new SQL();
		sql.SELECT("COUNT(1)");
		sql.FROM(getTableName());
		categorySearchWhere(sql, param);
		return sql.toString();
	}

	public void categorySearchWhere(SQL sql, Category param) {
		eq(sql, "parent_id", "parentId", param.getParentId());
		like_i(sql, "name", "name", param.getName());
		like_r(sql, "no", "no", param.getNo());
		eq(sql, "leaf", "leaf", param.getLeaf());
	}

	/**
	 * 根据父id和name统计重复数量
	 */
	public String checkCategoryName(Category param) {
		SQL sql = new SQL();
		sql.SELECT(" COUNT(1) ");
		sql.FROM(getTableNameThis());
		checkCategoryNameWhere(sql, param);
		return sql.toString();
	}

	private void checkCategoryNameWhere(SQL sql, Category param) {
		eq(sql, "parent_id", "parentId", param.getParentId());
		eq(sql, "name", "name", param.getName().trim());
		neq(sql, "id", "id", param.getId());
	}

	public void neq(SQL sql, String column, String field, Object value) {
		if (notEmpty(value)) {
			sql.WHERE(String.format("%s!=#{%s}", column, field));
		}
	}

	public String findByCategoryNos(@Param("nos") List<String> nos) {
		SQL sql = new SQL();
		sql.SELECT("*");
		sql.FROM(getTableName());
		findByCategoryNosWhere(sql, nos);
		return sql.toString();
	}

	private void findByCategoryNosWhere(SQL sql, List<String> nos) {
		if(nos != null){
			if(nos.isEmpty()){
				sql.WHERE("1 != 1");
				return;
			}
			if (nos.size() == 1) {
				sql.WHERE(String.format("no like '%s%%'", nos.get(0)));
			} else {
				StringBuilder sb = new StringBuilder();
				for (String no : nos) {
					sb.append(" no like CONCAT('" + no + "','%') or");
				}
				String s = sb.toString();
				if (sb.length() > 0) {
					s = sb.substring(0, sb.length() - " or".length());
				}
				sql.WHERE(s);
			}
		}
	}
	
	/**
	 * 品类模糊搜索:值搜索叶子节点的品类
	 */
	public String leafSearch(Category param){
		SQL sql = new SQL();
		sql.SELECT("*");
		sql.FROM(getTableName());
		leafSearchWhere(sql, param);
		return sql.toString();
	}
	
	private void leafSearchWhere(SQL sql, Category param){
		like_l(sql, "no", "no", param.getNo());
		like_i(sql, "name", "name", param.getName());
		eq(sql, "leaf", "leaf", param.getLeaf());
	}

}
