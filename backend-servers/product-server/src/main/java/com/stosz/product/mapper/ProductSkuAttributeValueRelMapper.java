package com.stosz.product.mapper;

import com.stosz.product.ext.model.ProductSkuAttributeValueRel;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductSkuAttributeValueRelMapper {

	@InsertProvider(type = ProductSkuAttributeValueRelBuilder.class,method = "insert")
	Integer insert(ProductSkuAttributeValueRel param);

    @Select("<script>INSERT IGNORE INTO product_sku_attribute_value_rel(sku,product_id,attribute_id,attribute_value_id,product_attribute_id) VALUES " +
            " <foreach item=\"item\" index=\"index\" collection=\"productSkuAttributeValueRelList\"  separator=\",\" >" +
            " (#{item.sku}, #{item.productId},#{item.attributeId},#{item.attributeValueId},#{item.productAttributeId})" +
            " </foreach>" +
            "</script>")
    Integer insertList(@Param("id") Integer id, @Param("productSkuAttributeValueRelList") List<ProductSkuAttributeValueRel> productSkuAttributeValueRelList);

	@DeleteProvider(type = ProductSkuAttributeValueRelBuilder.class, method = "delete")
	Integer delete(@Param("id") Integer id);

	@Delete("DELETE FROM product_sku_attribute_value_rel WHERE product_id = #{productId}")
	int deleteByProduct(@Param("productId") Integer productId);

	@Select("SELECT COUNT(1) FROM product_sku_attribute_value_rel WHERE attribute_id = #{attributeId}")
	Integer countByAttributeId(@Param("attributeId") Integer attributeId);

	@Select("SELECT COUNT(1) FROM product_sku_attribute_value_rel WHERE attribute_value_id = #{attributeValueId}")
	Integer countByAttributeValueId(@Param("attributeValueId") Integer attributeValueId);
	
	@Select("SELECT COUNT(1) FROM product_sku_attribute_value_rel WHERE product_id = #{productId} AND attribute_value_id = #{attributeValueId}")
	Integer countByPcAttrValId(@Param("productId") Integer productId, @Param("attributeValueId") Integer attributeValueId);

	@Select("SELECT COUNT(1) FROM product_sku_attribute_value_rel WHERE product_id = #{productId}")
	int countByProductId(@Param("productId") Integer productId);

	@SelectProvider(type = ProductSkuAttributeValueRelBuilder.class, method = "countById")
	int countById(ProductSkuAttributeValueRel param);

	@Select("Select * from product_sku_attribute_value_rel where attribute_id = #{attributeId} and sku = #{sku}")
	ProductSkuAttributeValueRel getByUnique(@Param("attributeId") Integer attributeId, @Param("sku") String sku);

    @SelectProvider(type = ProductSkuAttributeValueRelBuilder.class, method = "findBySku")
	List<ProductSkuAttributeValueRel> findBySku(@Param("sku") String sku);

	@Select("SELECT * FROM product_sku_attribute_value_rel WHERE product_id = #{productId} order by sku , attribute_id")
	List<ProductSkuAttributeValueRel> findByProductId(@Param("productId") Integer productId);

	@SelectProvider(type = ProductSkuAttributeValueRelBuilder.class, method = "countSku")
	int countSku(@Param("productId") Integer productId, @Param("attributeIds") List<Integer> attributeIds);

	@SelectProvider(type = ProductSkuAttributeValueRelBuilder.class, method = "getSkuByAttrValueIds")
	ProductSkuAttributeValueRel getSkuByAttrValueIds(@Param("productId") Integer productId, @Param("attributeIds") List<Integer> attributeIds);

	@Select("SELECT sku FROM product_sku_attribute_value_rel WHERE product_id = #{productId} ORDER BY sku DESC LIMIT 1")
	String maxSkuByProductId(@Param("productId") Integer productId);
	
	@SelectProvider(type = ProductSkuAttributeValueRelBuilder.class, method = "findByProductIdAttrValueIds")
	List<ProductSkuAttributeValueRel> findByProductIdAttrValueIds(ProductSkuAttributeValueRel param);

	//Select("SELECT sku FROM product_sku_attribute_value_rel WHERE product_id = #{productId} AND attribute_value_id IN (1022,1030) GROUP BY sku HAVING COUNT(1) = 2")
	@Select("<script>SELECT sku FROM product_sku_attribute_value_rel WHERE product_id = #{productId} AND attribute_value_id IN "+
			"<foreach item=\"item\" index=\"index\" collection=\"attValIds\" open=\"(\" separator=\",\" close=\")\">"
			+"#{item}"
			+ "</foreach> GROUP BY sku HAVING COUNT(1) = #{attValNumber} </script>")
	String getSkuByProductAttrVal(@Param("productId") Integer productId, @Param("attValIds")List<Integer> attValIds, @Param("attValNumber")Integer attValNumber);

}



