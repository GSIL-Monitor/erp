package com.stosz.admin.mapper.olderp;

import com.stosz.olderp.ext.model.OldErpCheckProduct;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 新品产品同步接口
 *
 * @author he_guitang
 * @version [版本号, 2017年8月22日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Repository
public interface OldErpCheckProductMapper {


    @Select("select p.id_product as id, p.id_users ,u.user_nicename as user_name , p.title,p.id_category, p.id_classify,p.thumbs,p.sale_url,p.purchase_url,p.status,p.desc,p.id_department,p.id_zone from erp_product_temp p left join erp_users u on p.id_users = u.id where p.status = 0 or p.status =1 ")
    List<OldErpCheckProduct> findAll();


}