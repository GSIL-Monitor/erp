package com.stosz.product.mapper;

import com.stosz.product.ext.model.ProductNewZone;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface ProductNewZoneMapper {

    @InsertProvider(type = ProductNewZoneBuilder.class, method = "insert")
    int insert(ProductNewZone record);

    @DeleteProvider(type = ProductNewZoneBuilder.class, method = "delete")
    int delete(Integer id);

    @Delete("delete from product_new_zone where product_new_id=#{id}")
    int deleteByProductNewId(Integer id);

    @UpdateProvider(type = ProductNewZoneBuilder.class, method = "update")
    int update(ProductNewZone param);

    @SelectProvider(type = ProductNewZoneBuilder.class, method = "find")
    List<ProductNewZone> find(ProductNewZone param);
    
    @Select("SELECT pwz.*,z.title AS zoneName,z.code AS zoneCode FROM product_new_zone pwz LEFT OUTER JOIN zone z ON pwz.zone_id = z.id WHERE pwz.product_new_id = #{id}")
    List<ProductNewZone> findByProductNewId(@Param("id") Integer id);
    
    @SelectProvider(type = ProductNewZoneBuilder.class, method = "getByUnique")
    ProductNewZone getByUnique(@Param("productNewId") Integer productNewId, @Param("zoneId") Integer zoneId);

    /**
     * 根据新品id集合返回所有的地区信息
     * sqg 将多次查询数据库的方法改为一次查询，提高效率
     *
     * @param productNewIds
     * @return
     */
    @SelectProvider(type = ProductNewZoneBuilder.class, method = "findByProductNewIds")
    List<ProductNewZone> findByProductNewIds(@Param("productNewIds") Collection<Integer> productNewIds);

    @Insert("<script>INSERT IGNORE INTO product_new_zone(id,create_at,product_new_id,zone_id) VALUES " +
            " <foreach item=\"item\" index=\"index\" collection=\"productNewZoneList\"  separator=\",\" >" +
            " (#{item.id}, #{item.createAt}, #{item.productNewId}, #{item.zoneId})" +
            " </foreach>" +
            "</script>")
    int insertBatch(@Param("id") Integer id, @Param("productNewZoneList") List<ProductNewZone> productNewZoneList);
    
    @Select("SELECT COUNT(1) FROM product_new_zone WHERE zone_id = #{zoneId}")
    int countByZoneId(@Param("zoneId") Integer zoneId);

    @Select("SELECT COUNT(1) FROM product_new_zone WHERE product_new_id = #{productNewId}")
    int countByProductNewId(@Param("productNewId") Integer productNewId);
}