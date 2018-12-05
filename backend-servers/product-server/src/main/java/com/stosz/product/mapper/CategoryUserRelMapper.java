package com.stosz.product.mapper;

import com.stosz.product.ext.model.CategoryUserRel;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryUserRelMapper {

	@InsertProvider(type=CategoryUserRelBuilder.class,method="insert")
	int insert(CategoryUserRel record);

	@DeleteProvider(type=CategoryUserRelBuilder.class,method="delete")
	int delete(Integer id);

	@Update("update category_user_rel set usable =#{usable} where id = #{id}")
    int update(CategoryUserRel record);

	@SelectProvider(type=CategoryUserRelBuilder.class,method="getById")
    CategoryUserRel getById(Integer id);
	
	@SelectProvider(type=CategoryUserRelBuilder.class,method="find")
	List<CategoryUserRel> find(CategoryUserRel categoryUserRel);

	@Select("select * from category_user_rel ")
	List<CategoryUserRel> findAll();

	@SelectProvider(type=CategoryUserRelBuilder.class,method="count")
	int count(CategoryUserRel categoryUserRel);
	
	@SelectProvider(type=CategoryUserRelBuilder.class,method="indexCount")
	int indexCount(CategoryUserRel categoryUserRel);

	@SelectProvider(type=CategoryUserRelBuilder.class,method ="findTopCategoryByUserId" )
	List<Integer> findTopCategoryByUserId(CategoryUserRel param);
	
	@Select("SELECT COUNT(1) FROM category_user_rel WHERE category_id = #{categoryId}")
	int findByCategoryID(@Param("categoryId") Integer categoryId);
	
}