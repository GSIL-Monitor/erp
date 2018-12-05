package com.stosz.admin.mapper.olderp;

import com.stosz.olderp.ext.model.OldErpAttribute;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 老erp产品属性表对应的mapper
 *
 * @author xiongchenyang
 * @version [1.0 , 2017/8/29]
 */
@Repository
public interface OldErpAttributeMapper {

    @Insert("insert into erp_product_option(id_product_option,id_product, title, type) values (#{idProductOption},#{idProduct}, #{title}, 'drop_down')")
    int insert(OldErpAttribute oldErpAttribute);

    @Delete("delete from erp_product_option where id_product_option = #{id}")
    int delete(Integer id);

    @Update("update erp_product_option set id_product = #{idProduct}, title = #{title} where id_product_option = #{id} ")
    int update(OldErpAttribute oldErpAttribute);

    @Select("select * from erp_product_option where id_product_option = #{id}")
    OldErpAttribute getById(Integer id);

    @Select("select * from erp_product_option where id_product = #{productId} and trim(title) = #{title}")
    OldErpAttribute getByUnique(@Param("productId") Integer productId, @Param("title") String title);

    /**
     * 查询老erp的属性
     */
    @Select("select id_product_option, id_product, title from erp_product_option order by id_product_option asc limit #{limit} offset #{start}")
    List<OldErpAttribute> find(@Param("limit") Integer limit, @Param("start") Integer start);

    @Select("select count(1) from erp_product_option ")
    Integer count();

    @Select("select count(1) from erp_product_option where id_product = #{productId} and title = #{title}")
    int countByTitle(@Param("productId") Integer productId, @Param("title") String title);

    @Select("select id_product_option, id_product, title from erp_product_option where id_product = #{productId} ")
    List<OldErpAttribute> findByProductId(@Param("productId") Integer productId);

    //******************************老erp查重产品属性查找******************************//
    @Select("select id_product_option, id_product, title from erp_product_option_temp where id_product = #{productId}")
    List<OldErpAttribute> findCheckByProductId(@Param("productId") Integer productId);

}
