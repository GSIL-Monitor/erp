package com.stosz.product.mapper;

import com.stosz.plat.mapper.AbstractBuilder;
import com.stosz.plat.utils.StringUtils;
import com.stosz.product.ext.model.ProductNew;
import org.apache.ibatis.jdbc.SQL;

//
public class ProductNewBuilder extends AbstractBuilder<ProductNew> {

	  @Override
      public void buildSelectOther(SQL sql) {
        sql.SELECT("c.name as topCategoryName, c.no as topCategoryNo");
        sql.SELECT("d.name as categoryName, d.no as categoryNo");

     }

     @Override
     public void buildJoin(SQL sql) {
         sql.LEFT_OUTER_JOIN(joinString("category", "c", "id", "top_category_id"));
         sql.LEFT_OUTER_JOIN(joinString("category", "d", "id", "category_id"));

     }

    @Override
    public void buildWhere(SQL sql, ProductNew param) {
        eq(sql,_this("id"), "id",param.getId());
        eq(sql, _this("spu"), "spu", param.getSpu());
        like_i(sql, _this("title"), "title", param.getTitle());
        eq(sql,_this("top_category_id"),"topCategoryId" , param.getTopCategoryId());
        eq(sql, _this("category_id"), "categoryId", param.getCategoryId());
        eq(sql,_this("creator_Id"),"creatorId" , param.getCreatorId());
        eq(sql,_this("state"),"state" , param.getState());
        eq(sql, _this("checker_id"), "checkerId", param.getCheckerId());
        le(sql,_this("create_at"),"maxCreateAt" , param.getMinCreateAt());
        ge(sql,_this("create_at"),"minCreateAt" , param.getMaxCreateAt());
        if(param.getTopCategories()!=null){
//            if(param.getTopCategories().isEmpty()){
//                sql.WHERE("1!=1");
//                return ;
//            }
//            String val = StringUtils.join(param.getTopCategories(), ',');
//            sql.WHERE(_this("top_category_id in ( " + val + " )"));
            if (!param.getTopCategories().isEmpty()) {
                String val = StringUtils.join(param.getTopCategories(), ',');
                sql.WHERE(_this("top_category_id in ( " + val + " )"));
            }
        }
        if (param.getCategoryIds() != null) {
            if (!param.getCategoryIds().isEmpty()) {
                String val = StringUtils.join(param.getCategoryIds(), ',');
                sql.WHERE(_this("category_id IN ( " + val + " )"));
            }
        }
        if(param.getDepartmentIds() != null){
        	if(param.getDepartmentIds().isEmpty()){
        		sql.WHERE("1 != 1");
        		return;
        	}
        	String val = StringUtils.join(param.getDepartmentIds(), ',');
            sql.WHERE(_this("department_id IN ( " + val + " )"));
        }
        like_r(sql,_this("department_no") , "departmentNo" , param.getDepartmentNo());

    }

    public String getByInnerName(ProductNew productNew) {
        SQL sql = new SQL();
        sql.SELECT("*");
        sql.FROM(getTableName());
        sql.WHERE("inner_name = #{innerName}");
        if (productNew.getId() != null) {
            sql.WHERE("id != #{id}");
        }

        return sql.toString() + " limit 1";
    }


}
