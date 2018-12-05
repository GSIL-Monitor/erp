package com.stosz.admin.mapper.olderp;

import com.stosz.olderp.ext.model.OldErpProduct;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 产品同步接口
 *
 * @author he_guitang
 * @version [版本号, 2017年8月22日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Repository
public interface OldErpProductMapper {

    @Insert("insert into erp_product (id_product, id_department, id_users, title, id_category, id_classify, inner_name , model, thumbs,foreign_title, purchase_price, length, width, height,weight,status,created_at , updated_at, en_category, top_category, top_catagory_id) " +
            "values ( #{id}, #{departmentId}, #{userId}, #{title},#{categoryId}, #{classify}, #{innerName}, #{model}, #{thumbs},#{foreignTitle}, #{purchasePrice}, #{length}, #{width},#{height},#{weight}, #{status},#{createdAt},#{updatedAt},#{enCategory},#{topCategory},#{topCatagoryId})")
    int insert(OldErpProduct oldErpProduct);



    @Update("update erp_product set status = #{status} where id = #{id}")
    int updateStatus(OldErpProduct oldErpProduct);


    @Update("update erp_product set id_department = #{departmentId}, id_users = #{userId}, title = #{title},id_category = #{categoryId},foreign_title = #{foreignTitle}, id_classify = #{classify}, inner_name = #{innerName}, model = #{model}, thumbs = #{thumbs}, purchase_price = #{purchasePrice}, " +
            "length = #{length}, width=#{width}, height=#{height},weight=#{weight},status = #{status}, updated_at =  #{updatedAt}, en_category = #{enCategory}, top_category = #{topCategory}, top_catagory_id = #{topCatagoryId} where id_product= #{id}")
    int update(OldErpProduct oldErpProduct);


    @SelectProvider(type = OldErpProductBuilder.class, method = "findByLimit")
    List<OldErpProduct> findByLimit(@Param("limit") Integer limit, @Param("start") Integer start);

    @Select("SELECT id_product as id,id_department as depratment_id, id_users as user_id, title, id_category as category_id, id_classify as classify, inner_name,model, thumbs, purchase_price, length, width, height, weight, status, created_at ,updated_at from erp_product  where id_product = #{id}")
    OldErpProduct getById(Integer id);

    @Select("select count(1) from erp_product ")
    int count();

    @Select("select quantity from erp_product_warehouse where id_product = #{productId}")
    Integer getTotalStockById(Integer productId);

    @Select("select id_product from erp_product")
    List<Integer> findAllId();

    @Select("select id_product as id, purchase_url, sale_url from erp_product_check where id_product = #{productId}")
    List<OldErpProduct> findByProductId(Integer productId);
}