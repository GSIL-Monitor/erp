package com.stosz.product.mapper;

import com.stosz.product.ext.model.ProductAttributeValueRel;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ProductAttributeValueRelMapper {

	@InsertProvider(type = ProductAttributeValueRelBulider.class,method = "insert")
	Integer insert(ProductAttributeValueRel param);

	@DeleteProvider(type = ProductAttributeValueRelBulider.class, method = "delete")
	Integer delete(@Param("id") Integer id);

	@Delete("DELETE FROM product_attribute_value_rel WHERE product_id = #{productId}")
	Integer deleteByProductId(@Param("productId") Integer productId);
	
    @Delete("DELETE FROM product_attribute_value_rel WHERE product_id = #{productId} AND attribute_value_id = #{attributeValueId}")
    Integer deleteByProductValueId(ProductAttributeValueRel param);
	
    @Delete("DELETE FROM product_attribute_value_rel WHERE product_attribute_id = #{productAttributeId}")
    int deleteByProductAttributeId(@Param("productAttributeId") Integer productAttributeId);
    
	@Select("DELETE FROM product_attribute_value_rel WHERE attribute_value_id = #{attributeValueId}")
	Integer deleteByAttributeValueId(@Param("attributeValueId") Integer attributeValueId);


	/**
	 * 根据唯一建获取到对应的数据
	 */
	@Select("select * from product_attribute_value_rel where product_attribute_id = #{productAttributeId} and attribute_value_id = #{attributeValueId}")
	ProductAttributeValueRel getByUnique(@Param("productAttributeId") Integer productAttributeId, @Param("attributeValueId") Integer attributeValueId);

	@SelectProvider(type = ProductAttributeValueRelBulider.class, method = "findByDate")
	List<ProductAttributeValueRel> findByDate(@Param("param") ProductAttributeValueRel param);

	@Select("select count(1) from product_attribute_value_rel where update_at >= #{startTime} and update_at <= #{endTime}")
	Integer countByDate(@Param("startTime") LocalDateTime startTime, @Param("endTime") LocalDateTime endTime);

    @SelectProvider(type = ProductAttributeValueRelBulider.class, method = "findByProductId")
    List<ProductAttributeValueRel> findByProductId(@Param("productId") Integer productId);

    //    @InsertProvider(type = ProductAttributeValueRelBulider.class, method = "insertList")
    @Insert("<script>INSERT IGNORE INTO product_attribute_value_rel(id,product_attribute_id,attribute_value_id,product_id,creator,creator_id,create_at) VALUES " +
            " <foreach item=\"item\" index=\"index\" collection=\"productAttributeValueRelList\"  separator=\",\" >" +
            " (#{item.id},#{item.productAttributeId}, #{item.attributeValueId},#{item.productId},#{item.creator},#{item.creatorId},current_timestamp())" +
            " </foreach>" +
            "</script>")
    Integer insertList(@Param("id") Integer id, @Param("productAttributeValueRelList") List<ProductAttributeValueRel> productAttributeValueRelList);


    @Select("SELECT COUNT(1) FROM product_attribute_value_rel WHERE product_id = #{productId} ")
    int countByProductId(@Param("productId") Integer productId);

    @Select("SELECT * FROM product_attribute_value_rel WHERE product_id = #{productId} ")
    List<ProductAttributeValueRel> queryByProductId(@Param("productId") Integer productId);


	@Select("<script>SELECT * FROM product_attribute_value_rel WHERE product_id = #{productId} AND id IN "+
			"<foreach item=\"item\" index=\"index\" collection=\"idList\" open=\"(\" separator=\",\" close=\")\">"
			+"#{item}"
			+ "</foreach></script>")
	List<ProductAttributeValueRel> findByProductIds(@Param("productId") Integer productId, @Param("idList") List<Integer> idList);
}
