package com.stosz.product.mapper;

import com.stosz.product.ext.model.ProductSku;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Repository
public interface ProductSkuMapper {

	@InsertProvider(type = ProductSkuBulider.class,method = "insert")
	int insert(ProductSku param);
	
	@DeleteProvider(type = ProductSkuBulider.class, method = "delete")
	int delete(@Param("id") Integer id);

	@Delete("DELETE FROM product_sku WHERE product_id = #{productId}")
	int deleteByProduct(@Param("productId") Integer productId);

	@UpdateProvider(type = ProductSkuBulider.class, method = "update")
	int update(ProductSku param);

	@SelectProvider(type = ProductSkuBulider.class, method = "find")
	List<ProductSku> find(ProductSku param);

	@SelectProvider(type = ProductSkuBulider.class, method = "count")
	int count(ProductSku param);

	@SelectProvider(type = ProductSkuBulider.class, method = "getById")
	ProductSku getById(@Param("id") Integer id);

	@Select("select * from product_sku where sku = #{sku}")
	ProductSku getBySku(@Param("sku") String sku);

	@SelectProvider(type = ProductSkuBulider.class, method = "findByDate")
	List<ProductSku> findByDate(@Param("param") ProductSku param);

	@Select("select count(1) from product_sku where update_at>= #{startTime} and update_at <=#{endTime}")
	int countByDate(@Param("startTime") LocalDateTime startTime, @Param("endTime") LocalDateTime endTime);
	
	@Select("SELECT COUNT(1) FROM product_sku WHERE product_id = #{productId}")
	int countByProductId(@Param("productId") Integer productId);
	
	@Select("SELECT * FROM product_sku WHERE product_id = #{productId}")
	List<ProductSku> getByProductId(@Param("productId") Integer productId);
	
    @SelectProvider(type = ProductSkuBulider.class, method = "findByProductId")
    List<ProductSku> findByProductId(@Param("productId") Integer productId);

    //    @InsertProvider(type = ProductSkuBulider.class,method = "insertList")
    @Select("<script>INSERT IGNORE INTO product_sku(id,product_id,spu,sku,barcode,create_at) VALUES " +
            " <foreach item=\"item\" index=\"index\" collection=\"productSkuList\"  separator=\",\" >" +
            " (#{item.id},#{item.productId}, #{item.spu},#{item.sku},#{item.barcode},current_timestamp())" +
            " </foreach>" +
            "</script>")
    Integer insertList(@Param("id") Integer id, @Param("productSkuList") List<ProductSku> productSkuList);
    
    @Select("<script>INSERT IGNORE INTO product_sku(product_id,spu,sku,barcode,create_at) VALUES " +
            " <foreach item=\"item\" index=\"index\" collection=\"productSkuList\"  separator=\",\" >" +
            " (#{item.productId}, #{item.spu},#{item.sku},#{item.barcode},current_timestamp())" +
            " </foreach>" +
            "</script>")
	Integer batchInsert(@Param("id") Integer id, @Param("productSkuList") List<ProductSku> productSkuList);

	@Select("select distinct sku from product_sku where id not in (select distinct object_id from wms_push where push_status=1 ) and create_at>'2017-10-27 00:00:00' order by create_at asc  limit 500")
	Set<String> findWmsPushErrSkus();

	//产品sku表属性值组合更新
	@Update("UPDATE product_sku SET attribute_value_title = #{title} WHERE id = #{id} ")
	int updateAttrValTitle(@Param("title") String title, @Param("id") Integer id);

	@Select("SELECT COUNT(1) FROM product_sku WHERE attribute_value_title IS NULL")
	int countSum();

	@Select("SELECT * FROM product_sku WHERE attribute_value_title IS NULL LIMIT #{limit}")
	List<ProductSku> skuList(@Param("limit") Integer limit);

	@Select("SELECT GROUP_CONCAT(title SEPARATOR '^') FROM attribute_value WHERE id IN ( SELECT DISTINCT  attribute_value_id  FROM  product_sku_attribute_value_rel WHERE sku=#{sku})")
	String getAttValTitle(@Param("sku") String sku);

	@Select("<script>SELECT product_id FROM product_sku where sku in " +
			" <foreach item=\"item\" index=\"index\" collection=\"skuList\" open=\"(\" separator=\",\" close=\")\">" +
			" #{item} " +
			" </foreach>" +
			"</script>")
	Set<Integer> findBySkus(@Param("skuList") List<String> skuList);


	@Select("<script>SELECT * FROM product_sku WHERE sku IN " +
			" <foreach item=\"item\" index=\"index\" collection=\"skuList\" open=\"(\" separator=\",\" close=\")\">" +
			" #{item} " +
			" </foreach>" +
			"</script>")
	List<ProductSku> findBySkuList(@Param("skuList") List<String> skuList);

	@Select("<script>SELECT * FROM product_sku WHERE product_id IN " +
			" <foreach item=\"item\" index=\"index\" collection=\"productIds\" open=\"(\" separator=\",\" close=\")\">" +
			" #{item} " +
			" </foreach>" +
			"</script>")
	List<ProductSku> findByProductIds(@Param("productIds") List<Integer> productIds);

	@Select("SELECT * FROM product_sku WHERE sku = #{sku}")
	ProductSku getAttrValueTitleBySku(@Param("sku") String sku);

}






