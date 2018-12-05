package com.stosz.product.mapper;

import com.stosz.product.ext.model.ProductZoneDomain;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ProductZoneDomainMapper {
    @InsertProvider(type = ProductZoneDomainBuilder.class, method = "insert")
    int insert(ProductZoneDomain param);

    @Insert("<script>INSERT IGNORE INTO product_zone_domain(create_at,domain,web_directory,loginid,product_id,zone_id,department_id,seo_loginid,site_product_id) VALUES " +
            " <foreach item=\"item\" index=\"index\" collection=\"productZoneDomainList\"  separator=\",\" >" +
            " (current_timestamp(),#{item.domain},#{item.webDirectory},#{item.loginid},#{item.productId},#{item.zoneId},#{item.departmentId},#{item.seoLoginid},#{item.siteProductId})" +
            " </foreach>" +
            "</script>")
    Integer insertList(@Param("id") Integer id, @Param("productZoneDomainList") List<ProductZoneDomain> productZoneDomainList);

	@DeleteProvider(type = ProductZoneDomainBuilder.class, method = "delete")
	int delete(@Param("id") Integer id);

	@UpdateProvider(type = ProductZoneDomainBuilder.class, method = "update")
	int update(ProductZoneDomain param);


	@UpdateProvider(type = ProductZoneDomainBuilder.class, method = "updateState")
	Integer updateState(@Param("id") Integer id, @Param("state") String state, @Param("stateTime") LocalDateTime stateTime);

	@SelectProvider(type = ProductZoneDomainBuilder.class, method = "find")
	List<ProductZoneDomain> find(ProductZoneDomain param);

	@SelectProvider(type = ProductZoneDomainBuilder.class, method = "count")
	int count(ProductZoneDomain param);

	@SelectProvider(type = ProductZoneDomainBuilder.class, method = "getById")
	ProductZoneDomain getById(@Param("id") Integer id);


	@Update("update product_zone_domain set domain=#{domain} , web_directory=#{webDirectory} where site_product_id=#{siteProductId}")
	void updateBySiteProductId(@Param("siteProductId") String siteProductId, @Param("domain") String domain, @Param("webDirectory") String webDirectory);
}

