package com.stosz.admin.mapper.olderp;

import com.stosz.olderp.ext.model.OldErpCurrency;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author carter
 * @version [1.0 , 2017/9/11]
 */
@Repository
public interface OldErpCurrencyMapper {

    @Select("select id_currency as id , title, created_at, updated_at , code , value , symbol_left from erp_currency ")
    List<OldErpCurrency> findAll();

    @Insert("insert into erp_currency (id_currency, title, created_at, updated_at , code , value , symbol_left) values (#{id},#{title}, #{created_at}, #{updated_at} , #{code} , #{value} , #{symbol_left})")
    int insert(OldErpCurrency OldErpCurrency);

    @Update("update erp_currency set title = #{title}, created_at= #{created_at}, updated_at = #{updated_at} , code = #{code} , value = #{value} , symbol_left =#{symbol_left} where id_currency = #{id}")
    int update(OldErpCurrency OldErpCurrency);

    @Select("select id_currency as id , title, created_at, updated_at , code , value , symbol_left from erp_currency  where title = #{title}")
    OldErpCurrency getByUnique(@Param("title") String title);

    @Select("select id_currency as id , title, created_at, updated_at , code , value , symbol_left from erp_currency where id_currency = #{id}")
    OldErpCurrency getById(@Param("id") Integer id);

    @Select("select id_currency as id , title, created_at, updated_at , code , value , symbol_left from erp_currency where code = #{code}")
    OldErpCurrency getByCode(@Param("code") String code);

    @Delete("delete from erp_currency where id_currency = #{id}")
    void deleteById(@Param("id") Integer id);
}
