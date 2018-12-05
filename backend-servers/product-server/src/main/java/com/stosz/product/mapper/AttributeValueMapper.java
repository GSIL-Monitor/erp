package com.stosz.product.mapper;

import com.stosz.product.ext.model.AttributeValue;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AttributeValueMapper {
	@InsertProvider(type = AttributeValueBulider.class,method = "insert")
    Integer insert(AttributeValue param);

    @Insert("<script>INSERT IGNORE INTO attribute_value(id, title,attribute_id,version, create_at) VALUES " +
            " <foreach item=\"item\" index=\"index\" collection=\"attributeValueList\"  separator=\",\" >" +
            " (#{item.id},#{item.title},#{item.attributeId},#{item.version},current_timestamp())" +
            " </foreach>" +
            "</script>")
    Integer insertList(@Param("id") Integer id, @Param("attributeValueList") List<AttributeValue> attributeValueList);

    @DeleteProvider(type = AttributeValueBulider.class, method = "delete")
	Integer delete(@Param("id") Integer id);
	
	@Delete("DELETE FROM attribute_value WHERE attribute_id = #{attributeId}")
	Integer deleteByAttributeId(Integer attributeId);
    
	@UpdateProvider(type = AttributeValueBulider.class, method = "update")
	Integer update(AttributeValue param);
	
    @SelectProvider(type = AttributeValueBulider.class, method = "find")
	List<AttributeValue> find(AttributeValue param);

    @SelectProvider(type = AttributeValueBulider.class, method = "count")
	Integer count(AttributeValue param);
    
    @SelectProvider(type = AttributeValueBulider.class, method = "getById")
	AttributeValue getById(@Param("id") Integer id);

	@Select("<script>SELECT * FROM attribute_value WHERE id IN " +
			" <foreach item=\"item\" index=\"index\" collection=\"ids\" open=\"(\" separator=\",\" close=\")\">" +
			" #{item} " +
			" </foreach>" +
			"</script>")
	List<AttributeValue> findByIds(@Param("ids") List<Integer> ids);

    @Select("select * from attribute_value where attribute_id =#{attributeId} and title = #{title} and version = #{version}  ")
    AttributeValue getByTitleAndAttribute(@Param("version") Integer version,
                                          @Param("title") String title, @Param("attributeId") Integer attributeId);


    @Select("SELECT id FROM attribute_value WHERE version = #{version} AND title = #{title} AND attribute_id = #{attributeId}")
   	AttributeValue getByTitleAndAttrId(@Param("version") Integer version, @Param("attributeId") Integer attributeId, @Param("title") String title);

    /**
     * version表示版本,1为新erp,0为老erp
     * product:表示产品id==>必须有
     * attributeId:表示属性id
     * bindIs:表示是否绑定
     */
    @SelectProvider(type = AttributeValueBulider.class, method = "findByAttributeValue")
    List<AttributeValue> findByAttributeValue(AttributeValue param);

    @Select("SELECT av.* FROM product_attribute_value_rel rel LEFT JOIN attribute_value av ON rel.attribute_value_id = av.id "
    		+ "WHERE product_id = #{productId} AND product_attribute_id IN "
    		+ "(SELECT id FROM product_attribute_rel "
    		+ "WHERE product_id = #{productId} )")
    List<AttributeValue> findAttrValueByProductId(@Param("productId") Integer productId);

    @Select("SELECT COUNT(1) FROM product_attribute_value_rel rel LEFT JOIN attribute_value av ON rel.attribute_value_id = av.id "
    		+ "WHERE product_id = #{productId} AND product_attribute_id IN "
    		+ "(SELECT id FROM product_attribute_rel "
    		+ "WHERE product_id = #{productId} AND attribute_id = #{attributeId}) ")
    int countProductSkuList(@Param("productId") Integer productId, @Param("attributeId") Integer attributeId);

	@Select("select * from attribute_value av "
			+ "join product_attribute_value_rel rel on av.id=rel.attribute_value_id "
			+ "where av.version = #{version} and rel.product_id=#{productId}")
    List<AttributeValue> findByProductId(@Param("version") Integer version, @Param("productId") Integer productId);

	@Select("select *,rel.id productAttributeValueRelId from attribute_value av "
			+ "join product_attribute_value_rel rel on av.id=rel.attribute_value_id "
			+ "where  rel.product_id=#{productId}")
	List<AttributeValue> findByProductIdIgnoreVersion(@Param("productId") Integer productId);

	@SelectProvider(type = AttributeValueBulider.class, method = "countByTitleAttId")
	Integer countByTitleAttId(AttributeValue param);

	@Select("SELECT * FROM attribute_value WHERE version = #{version} AND attribute_id = #{attributeId} AND title = #{title} ")
	AttributeValue getByTitle(@Param("version") Integer version, @Param("attributeId") Integer attributeId, @Param("title") String title);

	@Select("SELECT * FROM attribute_value WHERE VERSION = #{version} AND attribute_id = #{attributeId} ")
	List<AttributeValue> findByAttId(@Param("version") Integer version, @Param("attributeId") Integer attributeId);

	@Select("SELECT COUNT(1) FROM attribute_value WHERE VERSION = #{version} AND attribute_id = #{attributeId} ")
	int countByAttId(@Param("version") Integer version, @Param("attributeId") Integer attributeId);

	@Select(" SELECT * FROM attribute_value WHERE id in ( SELECT DISTINCT  attribute_value_id  FROM  product_sku_attribute_value_rel WHERE sku=#{sku})")
    List<AttributeValue> getValueListBySku(@Param("sku") String sku);
}




