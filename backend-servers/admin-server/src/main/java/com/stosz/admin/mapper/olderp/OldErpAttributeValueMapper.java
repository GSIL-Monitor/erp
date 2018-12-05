package com.stosz.admin.mapper.olderp;

import com.stosz.olderp.ext.model.OldErpAttributeValue;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 老erp属性值的mapper
 * @author xiongchenyang
 * @version [1.0 , 2017/8/30]
 */
@Repository
public interface OldErpAttributeValueMapper {

    @Insert("insert into erp_product_option_value(id_product_option_value,id_product_option, id_product, title) values (#{id},#{optionId}, #{productId}, #{title})")
    int insert(OldErpAttributeValue oldErpAttributeValue);

    @Delete("delete from erp_product_option_value where id_product_option_value = #{id}")
    int delete(Integer id);

    @Update("update erp_product_option_value set id_product_option = #{optionId}, id_product = #{productId}, title = #{title} where id_product_option_value = #{id}")
    int update(OldErpAttributeValue oldErpAttributeValue);

    @SelectProvider(type = OldErpAttributeValueBuilder.class, method = "count")
    int count();

    @SelectProvider(type = OldErpAttributeValueBuilder.class, method = "getById")
    OldErpAttributeValue getById(Integer id);

    @SelectProvider(type = OldErpAttributeValueBuilder.class, method = "findByLimit")
    List<OldErpAttributeValue> findByLimit(@Param("limit") Integer limit, @Param("start") Integer start);

    @Select("select id_product_option_value as id , id_product_option as option_id, id_product as product_id , title , price,code, image,sort  from erp_product_option_value where id_product = #{productId} and id_product_option = #{attributeId} and title = #{title} limit 1")
    OldErpAttributeValue getByUinque(@Param("productId") Integer productId, @Param("attributeId") Integer attributeId, @Param("title") String title);

    @Select("select id_product_option_value as id , id_product_option as option_id, id_product as product_id , title , price,code, image,sort from erp_product_option_value where id_product = #{productId} and id_product_option = #{attributeId}")
    List<OldErpAttributeValue> findByAttributeId(@Param("productId") Integer productId, @Param("attributeId") Integer attributeId);

    //******************************老erp查重产品属性查找******************************//
    @Select("select id_product_option_value as id , id_product_option as option_id, id_product as product_id , title , price,code, image,sort from erp_product_option_valtemp where id_product = #{productId} and id_product_option = #{attributeId}")
    List<OldErpAttributeValue> findCheckByAttributeId(@Param("productId") Integer productId, @Param("attributeId") Integer attributeId);

//    @Select("select id_product_option_value as id , id_product_option as option_id, id_product as product_id , title , price,code, image,sort  from erp_product_option_value from erp_product_option_value where id_product_option_value in(#{attributeValues})")
    @SelectProvider(type = OldErpAttributeValueBuilder.class, method = "findByAttributeValue")
    List<OldErpAttributeValue> findByAttributeValue(@Param("attributeValues") String attributeValues);
}  
