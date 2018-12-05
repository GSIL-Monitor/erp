package com.stosz.product.mapper;
import com.stosz.plat.mapper.AbstractBuilder;
import com.stosz.product.ext.model.CategoryUserRel;
import org.apache.ibatis.jdbc.SQL;

public class CategoryUserRelBuilder extends AbstractBuilder<CategoryUserRel> {
   
	@Override
    public void buildSelectOther(SQL sql) {
        sql.SELECT("c.name as categoryName ");
    }

    @Override
    public void buildJoin(SQL sql) {
         sql.LEFT_OUTER_JOIN("category c on " + _this("category_id") + "=c.id");
    }

    @Override
    public void buildWhere(SQL sql, CategoryUserRel param) {
    	 eq(sql, _this("user_id"), "userId", param.getUserId()); 		     //用户查询
		 eq(sql, _this("usable"), "usable", param.getUsable());        		 //是否有效
		 eq(sql, _this("category_id"), "categoryId", param.getCategoryId()); //品类查询
		 eq(sql, _this("user_type"), "userType", param.getUserType());
		 like_r(sql,_this("department_no"),"departmentNo",param.getDepartmentNo()); //部门查询
    }

    public String findTopCategoryByUserId(CategoryUserRel param){
        SQL sql = new SQL();
        sql.SELECT(_this("category_id"));
        sql.FROM(getTableNameThis());
        eq(sql, _this("user_id"), "userId", param.getUserId());
        eq(sql, _this("user_type"), "userType", param.getUserType());
        eq(sql, _this("usable"), "usable", param.getUsable());
        return sql.toString();
    }
    
    public String indexCount(CategoryUserRel param){
    	SQL sql = new SQL();
    	sql.SELECT(" COUNT(1) ");
    	sql.FROM(getTableNameThis());
    	indexCountWhere(sql,param);
    	return sql.toString();
    }
    
    public void indexCountWhere(SQL sql,CategoryUserRel param){
    	 eq(sql, _this("user_id"), "userId", param.getUserId());
    	 eq(sql, _this("category_id"), "categoryId", param.getCategoryId());
    	 eq(sql, _this("user_type"), "userType", param.getUserType());
    }
}
