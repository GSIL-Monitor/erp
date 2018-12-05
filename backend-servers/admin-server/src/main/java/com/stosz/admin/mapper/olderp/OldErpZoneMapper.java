package com.stosz.admin.mapper.olderp;

import com.stosz.olderp.ext.model.OldErpZone;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 老erp区域表的mapper
 *
 * @author xiongchenyang
 * @version [1.0 , 2017/9/12]
 */
@Repository
public interface OldErpZoneMapper {

    @Insert("insert erp_zone set id_zone = #{id}, parent_id = #{parentId}, id_country = #{countryId}, code = #{code}, title = #{title}, currency = #{currency}")
    int insert(OldErpZone oldErpZone);

    @Delete("update erp_zone set parent_id = #{parentId}, id_country = #{countryId}, code = #{code}, title = #{title}, currency = #{currency} where id_zone = #{id}")
    int update(OldErpZone oldErpZone);

    @Select("select id_zone as id ,parent_id, id_country as country_id, code , title, currency from erp_zone where id_zone = #{id}")
    OldErpZone getById(Integer id);

    @Select("select id_zone as id ,parent_id, id_country as country_id, code , title, currency from erp_zone")
    List<OldErpZone> findAll();

    @Delete("delete from erp_zone where id_zone=#{id}")
    void deleteByIdZone(@Param("id") Integer id);
}
