package com.stosz.admin.mapper.olderp;

import com.stosz.olderp.ext.model.OldErpCountry;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author xiongchenyang
 * @version [1.0 , 2017/9/11]
 */
@Repository
public interface OldErpCountryMapper {

    @Select("select id_country as id , title, iso_code2 as ename, iso_code3 as code from erp_country ")
    List<OldErpCountry> findAll();

    @Insert("insert ignore into erp_country (id_country, title, iso_code2, iso_code3) values (#{id},#{title},#{ename},#{code})")
    int insert(OldErpCountry oldErpCountry);

    @Update("update erp_country set title = #{title}, iso_code2 = #{ename}, iso_code3 = #{code} where id_country = #{id}")
    int update(OldErpCountry oldErpCountry);

    @Select("select id_country as id , title, iso_code2 as ename, iso_code3 as code from erp_country where title = #{title}")
    OldErpCountry getByUnique(@Param("title") String title);

    @Select("select id_country as id , title, iso_code2 as ename, iso_code3 as code from erp_country where id_country = #{id}")
    OldErpCountry getById(@Param("id") Integer id);

    @Delete("delete from erp_country where id_country = #{id}")
    void deleteById(@Param("id") Integer id);
}
