package com.stosz.product.mapper;

import com.stosz.product.ext.model.CategoryAttributeRel;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryAttributeRelMapper {
	
	@InsertProvider(type = CategoryAttributeRelBuilder.class,method = "insert")
	int insert(CategoryAttributeRel param);

    @Insert("<script>INSERT IGNORE INTO category_attribute_rel(category_id, attribute_id , creator, creator_id,create_at) VALUES " +
            " <foreach item=\"item\" index=\"index\" collection=\"categoryAttributeRelList\"  separator=\",\" >" +
            " (#{item.categoryId}, #{item.attributeId},#{item.creator},#{item.creatorId},current_timestamp())" +
            " </foreach>" +
            "</script>")
    Integer insertList(@Param("id") Integer id, @Param("categoryAttributeRelList") List<CategoryAttributeRel> categoryAttributeRelList);

	
	@Delete("DELETE FROM category_attribute_rel WHERE category_id = #{categoryId} AND attribute_id = #{attributeId}")
	int deleteByBind(CategoryAttributeRel param);
	
	@Delete("DELETE FROM category_attribute_rel WHERE attribute_id = #{attributeId}")
	int deleteByAttributeId(@Param("attributeId") Integer attributeId);
	
	/**
	 * 根据联合主键查询
	 */
	@Select("SELECT * FROM category_attribute_rel WHERE category_id = #{categoryId} AND attribute_id = #{attributeId}")
	CategoryAttributeRel getBycategoryIdAndAttributeId(CategoryAttributeRel param);
	
	@SelectProvider(type = CategoryAttributeRelBuilder.class, method = "count")
	int count(CategoryAttributeRel rel);
	
	@Select("SELECT COUNT(1) FROM category_attribute_rel WHERE category_id = #{categoryId} AND attribute_id = #{attributeId}")
	int countByAttrCateID(@Param("categoryId") Integer categoryId, @Param("attributeId") Integer attributeId);
	

	/**
	 * 获取到产品属性表中查出的品类属性总数
	 *
	 * @author xiongchenyang 2017/9/7
	 */
	@Select("SELECT count(*) from product_attribute_rel pa left JOIN product p on pa.product_id = p.id")
	int countByProduct();

	/**
	 * 根据产品属性表关联查询品类属性的关系，用于品类和属性的绑定
	 *
	 * @author xiongchenyang 2017/9/7
	 */
	@Select("SELECT category_id ,attribute_id from product_attribute_rel pa left JOIN product p on pa.product_id = p.id limit #{limit} offset #{start}")
	List<CategoryAttributeRel> findByProduct(@Param("limit") Integer limit, @Param("start") Integer start);
	
}


