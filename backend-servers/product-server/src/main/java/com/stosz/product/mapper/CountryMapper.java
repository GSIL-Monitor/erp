package com.stosz.product.mapper;

import com.stosz.product.ext.model.Country;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CountryMapper {
	
	@InsertProvider(type = CountryBulider.class,method = "insert")
	int insert(Country param);

    @Insert("insert country set id=#{id},name = #{name},ename=#{ename},country_code=#{countryCode},word_code=#{wordCode}")
    int insertByOld(Country param);

	@DeleteProvider(type = CountryBulider.class, method = "delete")
	int delete(@Param("id") Integer id);

	@UpdateProvider(type = CountryBulider.class, method = "update")
	int update(Country param);

	@SelectProvider(type = CountryBulider.class, method = "getById")
	Country getById(@Param("id") Integer id);

	@SelectProvider(type = CountryBulider.class, method = "count")
	int count(Country param);

    @SelectProvider(type = CountryBulider.class, method = "find")
	List<Country> find(Country param);

	@Select("SELECT * FROM country")
	List<Country> findList(Country param);
    
	/**
	 * 通过区域编码获取区域信息
	 */
	@Select("select * from country where country_code = #{countryCode}")
	Country getByCode(@Param("countryCode") String countryCode);

	@Delete("delete from country")
	void truncate();

	@Select("select * from country")
	List<Country> findAll();
	
	@Select("SELECT * FROM country WHERE name LIKE CONCAT('%',#{name},'%') LIMIT 20")
	List<Country> likeName(@Param("name") String name);
	
	
}

