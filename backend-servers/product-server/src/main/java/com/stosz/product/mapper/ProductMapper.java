package com.stosz.product.mapper;

import com.stosz.fsm.IFsmDao;
import com.stosz.olderp.ext.model.OldErpProduct;
import com.stosz.product.ext.enums.ProductState;
import com.stosz.product.ext.model.Product;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;


@Repository
public interface ProductMapper extends IFsmDao<Product> {


	@InsertProvider(type = ProductBuilder.class,method = "insert")
	int insert(Product param);

    /**
     * 旧产品批量同步到新产品库
     */
    @Insert({"<script>INSERT IGNORE INTO product(id,create_at,update_at,creator_id ,creator,category_id,state,title,purchase_price,inner_name,purchase_url,source_url, classify_enum,height,longness,weight,width,main_image_url,memo,spu,state_time, checker, checker_id) VALUES " +
            " <foreach item=\"item\" index=\"index\" collection=\"productList\"  separator=\",\" >" +
            " (#{item.id}, #{item.createAt}, #{item.updateAt}, #{item.creatorId}, #{item.creator}, #{item.categoryId}, #{item.state},#{item.title}, #{item.purchasePrice}, #{item.innerName},#{item.purchaseUrl},#{item.sourceUrl}, #{item.classifyEnum}, #{item.height}, #{item.longness}, #{item.weight}, #{item.width}, #{item.mainImageUrl},#{item.memo}, #{item.spu}, #{item.stateTime}, #{item.checker}, #{item.checkerId})" +
            " </foreach>" +
            "</script>"})
    void insertList(@Param("id") Integer id, @Param("productList") List<Product> productList);

	@DeleteProvider(type = ProductBuilder.class, method = "delete")
	int delete(@Param("id") Integer id);

	@UpdateProvider(type = ProductBuilder.class, method = "update")
	int update(Product param);

	@Update("UPDATE product SET state = #{fallbackState.name},state_time = #{stateTime} WHERE id = #{productId}")
	int updateProductState(@Param("productId") Integer productId, @Param("fallbackState") ProductState fallbackState, @Param("stateTime") LocalDateTime stateTime);

	@Update("UPDATE product SET classify_enum = #{classifyEnum} WHERE id = #{id}")
	int updateProductClassify(@Param("id") Integer id, @Param("classifyEnum") String classifyEnum);

	@UpdateProvider(type = ProductBuilder.class, method = "updateState")
	Integer updateState(@Param("id") Integer id, @Param("state") String state, @Param("stateTime") LocalDateTime stateTime);

    @UpdateProvider(type = ProductBuilder.class, method = "updateStateByIds")
    Integer updateStateByIds(@Param("ids") List<Integer> ids, @Param("state") String sate);

    @Update("update product set spu=#{spu} where id=#{id}")
    Integer updateSpu(@Param("id") Integer id, @Param("spu") String spu);

	/**
	 * 旧产品批量更新同步到新产品库
	 */

	Integer updateProduct(OldErpProduct pd);

	@Update("UPDATE product SET inner_name = #{innerName} WHERE id = #{id} ")
	int updateById(@Param("innerName") String innerName, @Param("id") Integer id);

	@Update("update product set category_id = #{categoryId} where id = #{id}")
	int updateCategoryById(@Param("categoryId") Integer categoryId, @Param("id") Integer id);

	@Update("update product set total_stock = #{totalStock},state=#{state},state_time=#{stateTime} where id = #{id}")
	int updateTotalStock(Product product);

//	@SelectProvider(type = ProductBuilder.class, method = "getById")
	@Select("SELECT p.*,c.en_category AS enCategory FROM product p LEFT JOIN category c ON p.category_id = c.id WHERE p.id = #{id}")
	Product getById(@Param("id") Integer id);

    @Select("<script>" +
            "SELECT * FROM product WHERE id IN " +
            "<foreach item=\"item\" index=\"index\" collection=\"list\"\n" +
            "     open=\"(\" separator=\",\" close=\")\">\n" +
            "        #{item}\n" +
            "  </foreach>" +
            "</script>")
    List<Product> getByIds(List<Integer> ids);

	@SelectProvider(type = ProductBuilder.class, method = "getBySpu")
	Product getBySpu(@Param("spu") String spu);

	@Select("select * from product where inner_name = #{innerName} limit 1")
	Product getByInnerName(@Param("innerName") String innerName);


    @SelectProvider(type = ProductBuilder.class, method = "count")
	int count(Product param);

    @SelectProvider(type = ProductBuilder.class, method = "find")
	List<Product> find(Product param);

    //产品品类
    @SelectProvider(type = ProductBuilder.class, method = "countListing")
	int countListing(Product param);

    @SelectProvider(type = ProductBuilder.class, method = "findListing")
	List<Product> findListing(Product param);

	@SelectProvider(type = ProductBuilder.class, method = "countImage")
	int countImage(Product param);

	@SelectProvider(type = ProductBuilder.class, method = "findImage")
	List<Product> findImage(Product param);

    //产品,产品区域表
    @SelectProvider(type = ProductBuilder.class, method = "countQueryList")
	int countQueryList(Product param);

    @SelectProvider(type = ProductBuilder.class, method = "queryList")
	List<Product> queryList(Product param);

    @SelectProvider(type = ProductBuilder.class, method = "countQueryListPz")
   	int countQueryListPz(Product param);

    @SelectProvider(type = ProductBuilder.class, method = "queryListPz")
   	List<Product> queryListPz(Product param);

    @Select("SELECT COUNT(1) FROM product WHERE spu = #{spu} ")
    int countCheckSpu(@Param("spu") String spu);

    @Select("SELECT p.*,c.name,c.no FROM product p LEFT OUTER JOIN category c ON p.category_id = c.id WHERE p.id = #{id}")
	Product getDetails(@Param("id") Integer id);

    /**
     * 根据品类查询产品
     */
	@Select("SELECT * FROM product WHERE category_id = #{categoryId}")
    List<Product> findProductByCategoryId(@Param("categoryId") Integer categoryId);

    /**
	 * 查询产品中的最大id
	 */
	@Select("SELECT  MAX(id) FROM product")
	Integer getMaxId();

    /**
	 * 旧产品批量更新同步到新产品库
	 */
	@Update("update product set spu = #{spu}, title = #{title}, category_id = #{categoryId},classify_enum =#{classifyEnum},inner_name = #{innerName},main_image_url = #{mainImageUrl}, purchase_price = #{purchasePrice}, longness=#{longness}, width = #{width}, height = #{height}, weight = #{weight},state = #{state},state_time= #{stateTime},memo=#{memo},creator_id = #{creatorId}, creator = #{creator} where id = #{id}")
	Integer updateProduct(Product pd);

    @Insert("insert into product(id,create_at,spu,title,category_id,classify_enum,inner_name,main_image_url,purchase_price,longness,width,height,weight,state,state_time,memo,creator_id,creator) values (#{id},#{createAt},#{spu},#{title},#{categoryId},#{classifyEnum},#{innerName},#{mainImageUrl},#{purchasePrice},#{longness},#{width},#{height},#{weight},#{state},#{stateTime},#{memo},#{creatorId},#{creator})")
    Integer insertOld(Product product);

    @SelectProvider(type = ProductBuilder.class, method = "findByDate")
    List<Product> findByDate(@Param("param") Product param);

    @Select("select count(1) from product where update_at >= #{startTime} and update_at <= #{endTime}")
    int countByDate(@Param("startTime") LocalDateTime startTime, @Param("endTime") LocalDateTime endTime);

    @Select("SELECT COUNT(1) FROM product WHERE id = #{id}")
    int countById(@Param("id") Integer id);

    @Select("select * from product where spu=#{spu} limit 1")
	Product findProductBySpu(@Param("spu") String spu);

    @Select("<script>select id from product where main_image_url in " +
            " <foreach item=\"item\" index=\"index\" collection=\"imagePaths\" open=\"(\" separator=\",\" close=\")\">" +
            " #{item} " +
            " </foreach>" +
            "</script>")
    List<Integer> findByImagePaths(@Param("imagePaths") List<String> imagePaths);


	@SelectProvider(type = ProductBuilder.class, method = "findByProductNew")
	List<Product> findByProductNew(@Param("product") Product product);

	@Select("select count(1) from product_new_sift_image pn inner join product p on pn.product_id = p.id LEFT OUTER JOIN category c ON p.category_id = c.id where pn.product_new_id = #{productNewId} ")
	Integer countByProductNewId(Integer productNewId);

    List<Integer> countTest();

    @SelectProvider(type = ProductBuilder.class, method = "countByDivide")
	Integer countByDivide(@Param("product") Product product);

    @SelectProvider(type = ProductBuilder.class, method = "findByDivide")
	List<Product> findByDivide(@Param("product") Product product);

	@Update("<script>update product set category_id = #{categoryId} where id in " +
			" <foreach item=\"item\" index=\"index\" collection=\"productIds\" open=\"(\" separator=\",\" close=\")\">" +
			" #{item} " +
			" </foreach>" +
			"</script>")
	void updateCategoryBatchById(@Param("productIds") List<Integer> productIds, @Param("categoryId") Integer categoryId);

	@Select("<script>SELECT id,spu,title,main_image_url FROM product WHERE spu IN "
			+ "<foreach item=\"item\" index=\"index\" collection=\"spuList\" open=\"(\" separator=\",\" close=\")\">"
			+"#{item}"
			+ "</foreach></script>")
	List<Product> findBySpuList(@Param("spuList")Set<String> spuList);

	@Select("SELECT p.source_url FROM product_sku pk LEFT JOIN product p ON pk.product_id = p.id WHERE pk.id = #{skuId}")
	String getProductSourceUrl(@Param("skuId") Integer skuId);

	@Select("SELECT p.* FROM product_zone_domain pzd LEFT JOIN product p ON pzd.product_id = p.id WHERE pzd.web_directory = #{webDirectory} or pzd.web_directory = #{httpWebDirectory}")
	List<Product> findByWebDirectory(@Param("webDirectory") String webDirectory,@Param("httpWebDirectory")String httpWebDirectory);

	@Select("<script>SELECT pk.sku,p.* FROM product_sku pk LEFT OUTER JOIN product p ON pk.product_id = p.id WHERE pk.sku IN "
			+ "<foreach item=\"item\" index=\"index\" collection=\"skuList\" open=\"(\" separator=\",\" close=\")\">"
			+"#{item}"
			+ "</foreach></script>")
	List<Product> findBySkuList(@Param("skuList")List<String> skuList);

}
