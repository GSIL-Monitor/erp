package com.stosz.product.mapper;

import com.stosz.product.ext.model.CategoryLang;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryLangMapper {

	@InsertProvider(type = CategoryLangBuilder.class, method = "insert")
	Integer insert(CategoryLang param);

	@DeleteProvider(type = CategoryLangBuilder.class, method = "delete")
	Integer delete(@Param("id") Integer id);

	@Delete("DELETE FROM category_lang WHERE attribute_id = #{categoryId}")
	Integer deleteByCategoryId(@Param("categoryId") Integer categoryId);

	@UpdateProvider(type = CategoryLangBuilder.class, method = "update")
	Integer update(CategoryLang param);
	
	@Update("UPDATE category_lang SET lang_code = #{langCode} WHERE lang_code = #{code}")
	Integer updateByLangCode(@Param("langCode") String langCode, @Param("code") String code);

	@SelectProvider(type = CategoryLangBuilder.class, method = "getById")
	CategoryLang getById(@Param("id") Integer id);

	@SelectProvider(type = CategoryLangBuilder.class, method = "count")
	Integer count(CategoryLang param);

	@SelectProvider(type = CategoryLangBuilder.class, method = "find")
	List<CategoryLang> find(CategoryLang param);

	@SelectProvider(type = CategoryLangBuilder.class, method = "countByNameCodeId")
	Integer countByNameCodeId(CategoryLang param);

	@Select("SELECT cl.*,l.name langName FROM category_lang cl LEFT OUTER JOIN language l ON cl.lang_code = l.lang_code WHERE category_id = #{categoryId}")
	List<CategoryLang> findByCategoryId(@Param("categoryId") Integer categoryId);

	@Select("<script>SELECT cl.*,l.name langName FROM category_lang cl "
			+ "LEFT OUTER JOIN language l ON cl.lang_code = l.lang_code WHERE category_id IN " +
            " <foreach item=\"item\" index=\"index\" collection=\"ids\" open=\"(\" separator=\",\" close=\")\">" +
            " #{item} " +
            " </foreach>" +
            "</script>")
	List<CategoryLang> findByCategoryIds(@Param("ids") List<Integer> ids);

	@Select("SELECT COUNT(1) FROM category_lang WHERE lang_code = #{langCode}")
	int countLangCode(@Param("langCode") String langCode);
}
