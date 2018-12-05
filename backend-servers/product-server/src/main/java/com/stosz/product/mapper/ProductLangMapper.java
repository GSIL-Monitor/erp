package com.stosz.product.mapper;

import com.stosz.product.ext.model.ProductLang;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductLangMapper {
	
	@InsertProvider(type = ProductLangBuilder.class,method = "insert")
	int insert(ProductLang param);

    @Insert({"<script>INSERT IGNORE INTO product_lang(create_at,name,lang_code,product_id) VALUES " +
            " <foreach item=\"item\" index=\"index\" collection=\"productLangList\"  separator=\",\" >" +
            " (current_timestamp(), #{item.name}, #{item.langCode}, #{item.productId})" +
            " </foreach>" +
            "</script>"})
    Integer insertList(@Param("id") Integer id, @Param("productLangList") List<ProductLang> productLangList);

	@DeleteProvider(type = ProductLangBuilder.class, method = "delete")
	int delete(@Param("id") Integer id);

	@UpdateProvider(type = ProductLangBuilder.class, method = "update")
	int update(ProductLang param);
	
	/**
	 * 根据编码修改编码
	 */
	@Update("UPDATE product_lang SET lang_code = #{langCode} WHERE lang_code = #{code}")
	int updateByLangCode(@Param("langCode") String langCode, @Param("code") String code);

    @SelectProvider(type = ProductLangBuilder.class, method = "find")
	List<ProductLang> find(ProductLang param);

    @SelectProvider(type = ProductLangBuilder.class, method = "count")
	int count(ProductLang param);

	@SelectProvider(type = ProductLangBuilder.class, method = "getById")
	ProductLang getById(@Param("id") Integer id);

	@SelectProvider(type = ProductLangBuilder.class, method = "countByNameCodeId")
	int countByNameCodeId(ProductLang param);

    /**
     * 根据产品id获取产品语言包
     *
     */
//	@Select("SELECT * FROM product_lang WHERE product_id = #{productId}")
	@Select("SELECT al.*,l.name langName FROM product_lang al "
			+ "LEFT OUTER JOIN language l ON al.lang_code = l.lang_code WHERE product_id = #{productId}")
    List<ProductLang> findProductLang(Integer productId);

    @Select("SELECT al.*,l.name langName FROM product_lang al "
            + "LEFT OUTER JOIN language l ON al.lang_code = l.lang_code WHERE product_id = #{productId} order by create_at desc limit 1")
    ProductLang getLastByProductId(Integer productId);

	@Select("<script>SELECT al.*,l.name langName FROM product_lang al "
			+ "LEFT OUTER JOIN language l ON al.lang_code = l.lang_code WHERE product_id IN " +
            " <foreach item=\"item\" index=\"index\" collection=\"ids\" open=\"(\" separator=\",\" close=\")\">" +
            " #{item} " +
            " </foreach>" +
            "</script>")
	List<ProductLang> findProductLangs(@Param("ids") List<Integer> ids);

	@Select("SELECT COUNT(1) FROM product_lang WHERE lang_code = #{langCode}")
	int countLangCode(@Param("langCode") String langCode);

}