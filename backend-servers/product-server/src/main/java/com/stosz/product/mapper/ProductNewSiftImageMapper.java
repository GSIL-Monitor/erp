package com.stosz.product.mapper;

import com.stosz.product.ext.model.ProductNewSiftImage;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

/**
 * @author xiongchenyang
 * @version [1.0 , 2017/9/26]
 */
@Repository
public interface ProductNewSiftImageMapper {

    @InsertProvider(type = ProductNewSiftImageBuilder.class, method = "insert")
    Integer insert(ProductNewSiftImage productNewSiftImage);

    @DeleteProvider(type = ProductNewSiftImageBuilder.class, method = "delete")
    Integer delete(@Param("id") Integer id);

    @Delete("delete from product_new_sift_image where product_new_id = #{productNewId}")
    Integer deleteByProductNewId(@Param("productNewId") Integer productNewId);

    @UpdateProvider(type = ProductNewSiftImageBuilder.class, method = "update")
    Integer update(ProductNewSiftImage productNewSiftImage);


    @SelectProvider(type = ProductNewSiftImageBuilder.class, method = "getById")
    ProductNewSiftImage getById(Integer id);


}
