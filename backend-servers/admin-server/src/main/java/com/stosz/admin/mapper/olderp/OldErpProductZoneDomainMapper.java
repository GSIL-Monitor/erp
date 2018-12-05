package com.stosz.admin.mapper.olderp;

import com.stosz.olderp.ext.model.OldErpProductZoneDomain;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author xiongchenyang
 * @version [1.0 , 2017/9/19]
 */
@Repository
public interface OldErpProductZoneDomainMapper {

    @Select("select product.id_checked as id , product.domain,product.extra_domain as web_directory, product.id_product as product_id , product.id_department as department_id, product.id_zone as zone_id , eu.user_login as loginid from erp_product_check product left join erp_users  eu on product.id_users = eu.id  where     product.id_product=#{productId} and product.id_department=#{departmentId} and product.id_zone = #{zoneId} and product.domain != '' limit 1")
    OldErpProductZoneDomain getByUnique(@Param("productId") Integer productId, @Param("departmentId") Integer departmentId, @Param("zoneId") Integer zoneId);

    @Select("select domain from erp_product_check where id_product=#{productId} and id_department=#{departmentId} and id_zone = #{zoneId}")
    String findDomainByUnique(@Param("productId") Integer productId, @Param("departmentId") Integer departmentId, @Param("zoneId") Integer zoneId);

    @Select("select product.id_checked as id , product.domain,product.extra_domain as web_directory, product.id_product as product_id , product.id_department as department_id, product.id_zone as zone_id , eu.user_login as loginid ,product.inner_name from erp_product_check product left join erp_users  eu on product.id_users = eu.id where product.domain != '' order by product_id  limit #{limit} offset #{start}")
    List<OldErpProductZoneDomain> findByLimit(@Param("limit") Integer limit, @Param("start") Integer start);

    @Select("select count(1) from erp_product_check product  where product.domain != ''")
    int count();

}
