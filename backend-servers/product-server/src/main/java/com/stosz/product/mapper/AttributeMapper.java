package com.stosz.product.mapper;

import com.stosz.product.ext.model.Attribute;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AttributeMapper{

	@InsertProvider(type = AttributeBulider.class,method = "insert")
    int insert(Attribute para);

	@Insert("<script>INSERT IGNORE INTO attribute(id, title,version, create_at) VALUES " +
			" <foreach item=\"item\" index=\"index\" collection=\"attributeList\"  separator=\",\" >" +
			" (#{item.id},#{item.title},#{item.version},current_timestamp())" +
			" </foreach>" +
			"</script>")
	Integer insertList(@Param("id") Integer id, @Param("attributeList") List<Attribute> attributeList);

	@DeleteProvider(type = AttributeBulider.class, method = "delete")
	int delete(@Param("id") Integer id);
    
	@UpdateProvider(type = AttributeBulider.class, method = "update")
	int update(Attribute para);
	
    @SelectProvider(type = AttributeBulider.class, method = "find")
	List<Attribute> find(Attribute param);

    @SelectProvider(type = AttributeBulider.class, method = "count")
	int count(Attribute param);

	@SelectProvider(type = AttributeBulider.class, method = "findCategoryBindCase")
	List<Attribute> findCategoryBindCase(Attribute param);

    @SelectProvider(type = AttributeBulider.class, method = "countCategoryBindCase")
	int countCategoryBindCase(Attribute param);
	
	@SelectProvider(type = AttributeBulider.class, method = "getById")
	Attribute getById(@Param("id") Integer id);

    @Select("select * from attribute where title = #{title} and version = #{version}")
    Attribute getByTitle(@Param("version") Integer version, @Param("title") String title);

	@SelectProvider(type = AttributeBulider.class, method = "findByCategoryId")
	List<Attribute> findByCategoryId(@Param("version") Integer version, @Param("categoryId") Integer categoryId);

	@Select("SELECT _this.*, IF(pa.id IS NULL,FALSE,TRUE) bindIs " +
			"FROM attribute _this JOIN category_attribute_rel rel ON _this.id = rel.attribute_id LEFT JOIN product_attribute_rel pa ON rel.attribute_id = pa.attribute_id AND pa.product_id = #{productId} " +
			"WHERE VERSION = #{version} AND rel.category_id = #{categoryId} ")
	List<Attribute> findByCateProId(@Param("version") Integer version, @Param("categoryId") Integer categoryId, @Param("productId") Integer productId);

	@SelectProvider(type = AttributeBulider.class, method = "findByProductId")
	List<Attribute> findByProductId(@Param("version") Integer version, @Param("productId") Integer productId);

    @Select("<script>SELECT rel.product_id,a.* FROM product_attribute_rel rel LEFT JOIN attribute a ON rel.attribute_id = a.id " +
            " WHERE rel.product_id IN " +
            " <foreach item=\"item\" index=\"index\" collection=\"ids\" open=\"(\" separator=\",\" close=\")\">" +
            " #{item} " +
            " </foreach>" +
            "</script>")
    List<Attribute> findByProductIds(@Param("ids") List<Integer> ids);

	@SelectProvider(type = AttributeBulider.class, method = "findByProductIdUseRelId")
	List<Attribute> findByProductIdUseRelId(@Param("version") Integer version, @Param("productId") Integer productId);

	@Select("SELECT a.* FROM attribute a LEFT OUTER JOIN product_attribute_rel rel ON a.id = rel.attribute_id WHERE product_id = #{id} AND rel.id IS NOT NULL ")
	List<Attribute> findByOldProductId(@Param("id") Integer id);
	
	@Select("SELECT a.* FROM product_attribute_rel rel LEFT JOIN attribute a ON rel.attribute_id = a.id WHERE product_id = #{productId} order by a.id")
	List<Attribute> queryByProductId(@Param("productId") Integer productId);

	@Select("SELECT a.* FROM product_attribute_rel rel LEFT OUTER JOIN attribute a ON rel.attribute_id = a.id WHERE rel.product_id = #{productId}")
	List<Attribute> findBindList(@Param("productId") Integer productId);
	
//	@Select("SELECT a.* FROM attribute a LEFT OUTER JOIN product_attribute_rel rel ON a.id = rel.attribute_id WHERE VERSION = #{version} AND product_id = #{id} AND rel.id IS NOT NULL ")
//	List<Attribute> findByOldProductId(@Param("version") Integer version, @Param("id") Integer id);
}
