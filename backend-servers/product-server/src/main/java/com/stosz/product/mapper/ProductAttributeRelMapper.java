package com.stosz.product.mapper;

import com.stosz.product.ext.model.CategoryAttributeRel;
import com.stosz.product.ext.model.ProductAttributeRel;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ProductAttributeRelMapper {
	
    @InsertProvider(type = ProductAttributeRelBulider.class, method = "insert")
    int insert(ProductAttributeRel productAttributeRel);

    @Insert("<script>INSERT IGNORE INTO product_attribute_rel(id,product_id,attribute_id, creator,creator_id,create_at) VALUES " +
            " <foreach item=\"item\" index=\"index\" collection=\"productAttributeRelList\"  separator=\",\" >" +
            " (#{item.id}, #{item.productId},#{item.attributeId},#{item.creator},#{item.creatorId},current_timestamp())" +
            " </foreach>" +
            "</script>")
    Integer insertList(@Param("id") Integer id, @Param("productAttributeRelList") List<ProductAttributeRel> productAttributeRelList);

    @Delete("DELETE FROM product_attribute_rel WHERE product_id = #{productId}")
    int deleteByProductId(@Param("productId") Integer productId);
    
    @Delete("DELETE FROM product_attribute_rel WHERE attribute_id = #{attributeId}")
    int deleteByAttributeId(@Param("attributeId") Integer attributeId);
    
    @Delete("DELETE FROM product_attribute_rel WHERE product_id = #{productId} AND attribute_id = #{attributeId}")
    int deleteByUnBind(ProductAttributeRel param);
    
    @DeleteProvider(type = ProductAttributeRelBulider.class, method = "delete")
    int delete(Integer id);

    @UpdateProvider(type = ProductAttributeRelBulider.class, method = "update")
    int update(ProductAttributeRel productAttributeRel);

   @Select("SELECT COUNT(1) FROM product_attribute_rel WHERE attribute_id = #{attributeId}")
    int countByAttributeId(@Param("attributeId") Integer attributeId);
    
    @Select("SELECT COUNT(1) FROM product_attribute_rel rel LEFT OUTER JOIN product p ON rel.product_id = p.id "
    		+ "WHERE rel.attribute_id = #{attributeId} AND p.category_id = #{categoryId}")
    int countByCategoryAttributeId(CategoryAttributeRel param);

    /**
     * 获取该时间段内修改过的记录数
     *
     * @author xiongchenyang 2017/9/9
     */
    @Select("select count(1) from product_attribute_rel where update_at >= #{startTime} and update_at <= #{endTime}")
    int countByDate(@Param("startTime") LocalDateTime startTime, @Param("endTime") LocalDateTime endTime);

    @SelectProvider(type = ProductAttributeRelBulider.class, method = "getById")
    ProductAttributeRel getById(Integer id);

    @Select("select * from product_attribute_rel where product_id = #{productId} and attribute_id = #{attributeId}")
    ProductAttributeRel getByAttrProductId(@Param("attributeId") Integer attributeId, @Param("productId") Integer productId);
    
    /**
     * 获取时间段内修改过的记录数，同时分页
     * 必须的入参，maxCreateTime ,minCreateTime,
     * 选择的入参，limit，start
     * @author xiongchenyang 2017/9/9
     */
    @SelectProvider(type = ProductAttributeRelBulider.class, method = "findByDate")
    List<ProductAttributeRel> findByDate(@Param("param") ProductAttributeRel param);
    
    @Select("SELECT * FROM product_attribute_rel WHERE product_id = #{productId} AND attribute_id = #{attributeId}")
	ProductAttributeRel findRelId(@Param("productId") Integer productId, @Param("attributeId") Integer attributeId);

    @Select("select _this.* , a.title as attributeTitle from product_attribute_rel _this left join attribute a on a.id = _this.attribute_id where _this.product_id = #{productId}")
    List<ProductAttributeRel> findByProductId(@Param("productId") Integer productId);
    
    @Select("SELECT COUNT(1) FROM product_attribute_rel WHERE product_id = #{productId}")
    Integer countByProductId(@Param("productId") Integer productId);

}
