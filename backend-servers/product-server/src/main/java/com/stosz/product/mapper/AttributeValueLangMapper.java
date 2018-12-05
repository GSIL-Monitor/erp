package com.stosz.product.mapper;

import com.stosz.product.ext.model.AttributeValueLang;
import com.stosz.product.ext.model.Language;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AttributeValueLangMapper {
	/**
	 * 插入
	 */
	@InsertProvider(type = AttributeValueLangBuilder.class,method = "insert")
    int insert(AttributeValueLang param);
	
	/**
	 * 删除
	 */
	@DeleteProvider(type = AttributeValueLangBuilder.class, method = "delete")
	int delete(@Param("id") Integer id);
	
	@Delete("DELETE FROM attribute_value_lang WHERE attribute_value_id = #{attributeValueId}")
	int deleteByAttributeValueId(@Param("attributeValueId") Integer attributeValueId);

	@Delete("<script>DELETE FROM attribute_value_lang WHERE attribute_value_id IN " +
			" <foreach item=\"item\" index=\"index\" collection=\"ids\" open=\"(\" separator=\",\" close=\")\">" +
			" #{item} " +
			" </foreach>" +
			"</script>")
	int deleteByAttrValIds(@Param("ids") List<Integer> ids);

	/**
	 * 修改
	 */
	@UpdateProvider(type = AttributeValueLangBuilder.class, method = "update")
	int update(AttributeValueLang param);
	
	/**
	 * 根据编码修改编码
	 */
	@Update("UPDATE attribute_value_lang SET lang_code = #{langCode} WHERE lang_code = #{code}")
	int updateByLangCode(@Param("langCode") String langCode, @Param("code") String code);

	@SelectProvider(type = AttributeValueLangBuilder.class, method = "getById")
	AttributeValueLang getById(@Param("id") Integer id);

    @SelectProvider(type = AttributeValueLangBuilder.class, method = "find")
	List<AttributeValueLang> find(Language param);

    @SelectProvider(type = AttributeValueLangBuilder.class, method = "count")
	int count(AttributeValueLang param);

    @SelectProvider(type = AttributeValueLangBuilder.class, method = "countByNameCodeId")
    int countByNameCodeId(AttributeValueLang param);

    /**
     * 通过属性值id获取语言包
     *
     */
    @Select("SELECT al.*,l.name langName FROM attribute_value_lang al "
    		+ "LEFT OUTER JOIN language l ON al.lang_code = l.lang_code WHERE attribute_value_id = #{attributeValueId}")
    List<AttributeValueLang> findByAttributeValueId(Integer attributeValueId);

	@Select("<script>SELECT al.*,l.name langName FROM attribute_value_lang al "
			+ "LEFT OUTER JOIN language l ON al.lang_code = l.lang_code WHERE attribute_value_id IN " +
            " <foreach item=\"item\" index=\"index\" collection=\"ids\" open=\"(\" separator=\",\" close=\")\">" +
            " #{item} " +
            " </foreach>" +
            "</script>")
	List<AttributeValueLang> findByAttributeValueIds(@Param("ids") List<Integer> ids);

	@Select("SELECT COUNT(1) FROM attribute_value_lang WHERE lang_code = #{langCode}")
	int countLangCode(@Param("langCode") String langCode);
}