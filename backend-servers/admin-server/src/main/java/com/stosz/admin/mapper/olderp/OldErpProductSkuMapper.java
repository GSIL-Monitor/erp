package com.stosz.admin.mapper.olderp;

import com.stosz.olderp.ext.model.OldErpProductSku;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OldErpProductSkuMapper {

	//	@Select("SELECT * FROM erp_product_sku ")
//	List<OldErpProductSku> findList();
	@Insert("insert erp_product_sku set id_product = #{idProduct}, sku = #{sku}, model = #{model}, barcode = #{barcode}, option_value = #{optionValue}, title = #{title}")
	int insert(OldErpProductSku oldErpProductSku);

	@Update("update erp_product_sku set id_product = #{idProduct}, sku = #{sku}, model = #{model}, barcode = #{barcode}, option_value = #{optionValue}, title = #{title} where id_product_sku = #{idProductSku}")
	int update(OldErpProductSku oldErpProductSku);

	@Update("<script>UPDATE `erp_product_sku` SET sku_label = #{skuLabel} WHERE id_product_sku IN " +
			" <foreach item=\"item\" index=\"index\" collection=\"skuIds\" open=\"(\" separator=\",\" close=\")\">" +
			" #{item} " +
			" </foreach>" +
			"</script>")
	int updateBatchSkuLabelBatch(@Param("skuIds") List<Integer> skuIds, @Param("skuLabel") Integer skuLabel);

	@SelectProvider(type = OldErpProductSkuBuilder.class,method = "findList")
	List<OldErpProductSku> findList(@Param("limit") Integer limit, @Param("start") Integer start);

	@Select("SELECT COUNT(1) FROM erp_product_sku")
	int countList();


	@SelectProvider(type = OldErpProductSkuBuilder.class, method = "getByAttributeValueId")
	List<OldErpProductSku> getByAttributeValueId(@Param("optionValues") List<String> optionValues);

	@Select("select * from erp_product_sku where sku = #{sku}")
    OldErpProductSku getBySku(@Param("sku") String sku);

	@Select("select * from erp_product_sku where id_product_sku = #{id}")
    OldErpProductSku getById(@Param("id") Integer id);

    @Select("select * from erp_product_sku where id_product = #{productId}")
    List<OldErpProductSku> findByProductId(@Param("productId") Integer productId);

    @Select("select group_concat(option_value) from erp_product_sku where id_product = #{productId}")
	String getOptionValueByProduct(@Param("productId") Integer productId);

    @Delete("delete from erp_product_sku where id_product = #{productId}")
    int deleteByProductId(@Param("productId") Integer productId);

}
