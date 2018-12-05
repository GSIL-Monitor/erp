package com.stosz.admin.mapper.olderp;

import com.stosz.olderp.ext.model.OldErpProductZone;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * @author xiongchenyang
 * @version [1.0 , 2017/9/5]
 */
@Repository
public interface OldErpProductZoneMapper {


    @Insert("insert ignore into erp_product_department (id, id_product , id_department, id_zone ,utime , status ) values (#{id}, #{productId},  #{departmentId}, #{zoneId},#{createdAt},#{status})")
    int insert(OldErpProductZone oldErpProductZone);

    @Update("update erp_product_department set id_product = #{productId}, id_department = #{departmentId}, id_zone = #{zoneId}, status = #{status} where id = #{id}")
    int update(OldErpProductZone oldErpProductZone);

    @Delete("delete from erp_product_department where id = #{id}")
    int delete(Integer id);

    @Select("select id , id_product, id_department, id_zone, status from erp_product_department where id =#{id}")
    OldErpProductZone getById(Integer id);

    @Select("select count(1) from erp_product_department")
    int count();

    @Select("select id, id_product as product_id , id_department as department_id, id_zone as zone_id, utime as created_at, status from erp_product_department order by id  limit #{limit} offset #{start}")
    List<OldErpProductZone> findByLimit(@Param("limit") Integer limit, @Param("start") Integer start);

    @Select("select id, id_product as product_id , id_department as department_id, id_zone as zone_id, status from erp_product_department where utime >= date_format(#{startTim}，'%y-%m-%d') and utime <= date_format(#{endTime},'%y-%m-%d')")
    List<OldErpProductZone> findByDate(@Param("startTime") Date startTime, @Param("endTime") Date endTime);

    @Select("select id, id_product as product_id , id_department as department_id, id_zone as zone_id, status, utime as created_at from erp_product_department where id_product = #{productId} and status = 1 and id_department is not null and id_zone is not null")
    List<OldErpProductZone> findByProductId(@Param("productId") Integer productId);

    @Select("select id ,id_product as product_id, id_department as department_id, id_zone as zone_id ,utime as created_at,status from erp_product_department where id_product = #{productId} and id_department = #{departmentId} and id_zone = #{zoneId}")
    List<OldErpProductZone> getByUnique(@Param("productId") Integer productId, @Param("departmentId") Integer departmentId, @Param("zoneId") Integer zoneId);

}  
