package com.stosz.product.mapper;

import com.stosz.product.ext.model.Language;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LanguageMapper {
	
	@InsertProvider(type = LanguageBulider.class,method = "insert")
	int insert(Language param);

	@DeleteProvider(type = LanguageBulider.class, method = "delete")
	int delete(@Param("id") Integer id);

	@UpdateProvider(type = LanguageBulider.class, method = "update")
	int update(Language param);
	
    @SelectProvider(type = LanguageBulider.class, method = "find")
	List<Language> find(Language param);

    @SelectProvider(type = LanguageBulider.class, method = "count")
	int count(Language param);
    
    @Select("SELECT COUNT(1) FROM language WHERE lang_code = #{langCode}")
    int countByCode(@Param("langCode") String langCode);

    @SelectProvider(type=LanguageBulider.class, method = "getById")
	Language getById(@Param("id") Integer id);
	

}