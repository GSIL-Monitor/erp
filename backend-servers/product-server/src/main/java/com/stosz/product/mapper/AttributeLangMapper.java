package com.stosz.product.mapper;

import com.stosz.product.ext.model.AttributeLang;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AttributeLangMapper {

	@InsertProvider(type = AttributeLangBuilder.class, method = "insert")
	Integer insert(AttributeLang param);

	@DeleteProvider(type = AttributeLangBuilder.class, method = "delete")
	Integer delete(@Param("id") Integer id);

	@Delete("DELETE FROM attribute_lang WHERE attribute_id = #{attributeId}")
	Integer deleteByAttributeId(@Param("attributeId") Integer attributeId);

	@UpdateProvider(type = AttributeLangBuilder.class, method = "update")
	Integer update(AttributeLang param);
	
	@Update("UPDATE attribute_lang SET lang_code = #{langCode} WHERE lang_code = #{code}")
	Integer updateByLangCode(@Param("langCode") String langCode, @Param("code") String code);

	@SelectProvider(type = AttributeLangBuilder.class, method = "getById")
	AttributeLang getById(@Param("id") Integer id);

	@SelectProvider(type = AttributeLangBuilder.class, method = "count")
	Integer count(AttributeLang param);

	@SelectProvider(type = AttributeLangBuilder.class, method = "find")
	List<AttributeLang> find(AttributeLang param);

	@SelectProvider(type = AttributeLangBuilder.class, method = "countByNameCodeId")
	Integer countByNameCodeId(AttributeLang param);

	@Select("SELECT al.*,l.name langName FROM attribute_lang al LEFT OUTER JOIN language l ON al.lang_code = l.lang_code WHERE attribute_id = #{attributeId}")
	List<AttributeLang> findByAttributeId(@Param("attributeId") Integer attributeId);

	@Select("<script>SELECT al.*,l.name langName FROM attribute_lang al "
			+ "LEFT OUTER JOIN language l ON al.lang_code = l.lang_code WHERE attribute_id IN " +
            " <foreach item=\"item\" index=\"index\" collection=\"ids\" open=\"(\" separator=\",\" close=\")\">" +
            " #{item} " +
            " </foreach>" +
            "</script>")
	List<AttributeLang> findByAttributeIds(@Param("ids") List<Integer> ids);

	@Select("SELECT COUNT(1) FROM attribute_lang WHERE lang_code = #{langCode}")
	int countLangCode(@Param("langCode") String langCode);
}
