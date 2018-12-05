package com.stosz.admin.mapper.olderp;

//import com.stosz.product.ext.model.Product;

import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * @author xiongchenyang
 * @version [1.0 , 2017/9/6]
 */
@Repository
public interface OldErpQuantityMapper {
//
//    @Select("SELECT id_product as id,sum(quantity) as total_stock from erp_warehouse_product GROUP BY id_product limit #{limit} OFFSET #{start}")
//    List<Product> findByLimit(@Param("limit") Integer limit, @Param("start") Integer start);

    @Select("select count(1) from (SELECT id_product as id,sum(quantity) as total_stock from erp_warehouse_product GROUP BY id_product) a ")
    int count();
}  
