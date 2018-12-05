package com.stosz.product.mapper;

import com.stosz.fsm.IFsmDao;
import com.stosz.product.ext.model.ExcludeRepeat;
import com.stosz.product.ext.model.FsmHistory;
import com.stosz.product.ext.model.ProductNew;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ProductNewMapper extends IFsmDao<ProductNew> {


	@InsertProvider(type = ProductNewBuilder.class,method = "insert")
	int insert(ProductNew record);

	@Insert("<script>INSERT IGNORE INTO product_new(id,create_at,creator_id ,creator,category_id,state,title,purchase_url,source_url, classify_enum,main_image_url,memo,attribute_desc,state_time) VALUES " +
			" <foreach item=\"item\" index=\"index\" collection=\"productNewList\"  separator=\",\" >" +
			" (#{item.id}, #{item.createAt}, #{item.creatorId}, #{item.creator}, #{item.categoryId}, #{item.state},#{item.title}, #{item.purchaseUrl},#{item.sourceUrl}, #{item.classifyEnum}, #{item.mainImageUrl},#{item.memo},#{item.attributeDesc}, #{item.stateTime})" +
			" </foreach>" +
			"</script>")
	int insertBatch(@Param("id") Integer id, @Param("productNewList") List<ProductNew> productNewList);

	@DeleteProvider(type = ProductNewBuilder.class, method = "delete")
	int delete(@Param("id") Integer id);

	@UpdateProvider(type = ProductNewBuilder.class, method = "update")
	int update(ProductNew param);

	@UpdateProvider(type = ProductNewBuilder.class, method = "updateState")
	Integer updateState(@Param("id") Integer id, @Param("state") String state, @Param("stateTime") LocalDateTime stateTime);

	@Update("UPDATE product_new SET category_id = #{categoryId} WHERE id = #{id} ")
	int updateCategory(@Param("id") Integer id, @Param("categoryId") Integer categoryId);

	@Update("<script>UPDATE product_new SET category_id = #{categoryId} WHERE id IN  " +
			" <foreach item=\"item\" index=\"index\" collection=\"ids\" open=\"(\" separator=\",\" close=\")\">" +
			" #{item} " +
			" </foreach>" +
			"</script>")
	int updateCategoryBatch(@Param("ids") List<Integer> ids, @Param("categoryId") Integer categoryId);

    @Update("update product_new set spu = #{spu} where id = #{id}")
    int updateSpu(@Param("id") Integer id, @Param("spu") String spu);

	@Update("UPDATE product_new SET memo = #{memo} WHERE id = #{id}")
	int updateMemo(@Param("id") Integer id, @Param("memo") String memo);

	@Update("update product_new set checker_id=#{checkerId} , checker=#{checker} where id=#{id}")
	int updateCheckUserInfo(@Param("id") Integer id, @Param("checkerId") Integer checkerId, @Param("checker") String checker);

	@SelectProvider(type = ProductNewBuilder.class, method = "getById")
    ProductNew getById(Integer id);

	@Select("<script>SELECT * FROM product_new WHERE id IN  " +
			" <foreach item=\"item\" index=\"index\" collection=\"ids\" open=\"(\" separator=\",\" close=\")\">" +
			" #{item} " +
			" </foreach>" +
			"</script>")
	List<ProductNew> findByIds(@Param("ids") List<Integer> ids);

    @SelectProvider(type = ProductNewBuilder.class, method = "find")
	List<ProductNew> find(ProductNew param);

    //    @Select("select * from product_new where inner_name = #{innerName} limit 1")
    @SelectProvider(type = ProductNewBuilder.class, method = "getByInnerName")
    ProductNew getByInnerName(ProductNew productNew);

    @SelectProvider(type = ProductNewBuilder.class, method = "count")
	int count(ProductNew param);

    @Select("SELECT COUNT(1) FROM product_new WHERE creator_id = #{creatorId} AND submit_time > #{minCreateAt} AND submit_time < #{maxCreateAt}")
    int countTimeNumber(ProductNew param);

	@Select("SELECT COUNT(1) FROM product_new WHERE state_time BETWEEN #{minCreateAt} AND #{maxCreateAt} AND checker_id IS NOT NULL ")
	int countProductNewReport(@Param("minCreateAt") LocalDateTime minCreateAt, @Param("maxCreateAt") LocalDateTime maxCreateAt);

	@Select("SELECT DISTINCT(checker_id) FROM product_new WHERE state_time  >=  #{minCreateAt} AND state_time <= #{maxCreateAt} AND checker_id IS NOT NULL")
	List<Integer> excludeRepeatUser(@Param("minCreateAt") LocalDateTime minCreateAt, @Param("maxCreateAt") LocalDateTime maxCreateAt);

	/*==================================排重报表===============================================*/
	/**
	 *统计点击某一状态的次数
	 */
	@Select("SELECT fh.*,pn.department_id AS departmentId " +
			"FROM fsm_history fh LEFT JOIN product_new pn ON fh.object_id = pn.id " +
			"WHERE fh.create_at  >= #{minCreateAt} AND fh.create_at <= #{maxCreateAt} AND fh.fsm_name = #{fsmName} AND fh.dst_state = #{state} AND opt_uid = #{optUid}")
	List<FsmHistory> findStateNumber(@Param("minCreateAt") LocalDateTime minCreateAt, @Param("maxCreateAt") LocalDateTime maxCreateAt,
                                     @Param("fsmName") String fsmName, @Param("state") String state, @Param("optUid") String optUid);

	@Select("SELECT DISTINCT(object_id),fh.*,pn.department_id AS departmentId " +
			"FROM fsm_history fh LEFT JOIN product_new pn ON fh.object_id = pn.id " +
			"WHERE fh.create_at  >= #{minCreateAt} AND fh.create_at <= #{maxCreateAt} AND fh.fsm_name = #{fsmName} AND fh.dst_state = #{state} AND opt_uid = #{optUid}")
	List<FsmHistory> findNoRepeatStateNumber(@Param("minCreateAt") LocalDateTime minCreateAt, @Param("maxCreateAt") LocalDateTime maxCreateAt,
                                             @Param("fsmName") String fsmName, @Param("state") String state, @Param("optUid") String optUid);
	/**
	 * dstState:查重最终状态
	 * otherState:包含的其他状态
	 */
	@Select("SELECT * FROM (" +
			"SELECT fh.*,pn.department_id AS departmentId FROM fsm_history fh LEFT JOIN product_new pn ON fh.object_id = pn.id WHERE fh.fsm_name = #{fsmName} AND fh.dst_state = #{dstState} AND fh.create_at BETWEEN #{minCreateAt} AND #{maxCreateAt} AND fh.opt_uid = #{optUid} " +
			"UNION " +
			"SELECT fh.*,pn.department_id AS departmentId FROM fsm_history fh LEFT JOIN product_new pn ON fh.object_id = pn.id WHERE fh.fsm_name = #{fsmName} AND fh.dst_state = #{otherState} AND fh.create_at BETWEEN #{minCreateAt} AND #{maxCreateAt} ) a " +
			"GROUP BY object_id HAVING COUNT(object_id) > 1 ")
	List<FsmHistory> findFsmHistoryDept(ExcludeRepeat param);

	/**
	 *统计某个排重专员操作的产品数量的总和
	 */
	@Select("SELECT DISTINCT(object_id), pn.* " +
			"FROM fsm_history fh LEFT JOIN product_new pn ON fh.object_id = pn.id " +
			"WHERE fh.create_at  >= #{minCreateAt} AND fh.create_at <= #{maxCreateAt} AND fh.fsm_name = #{fsmName} AND opt_uid = #{optUid} ")
	List<ProductNew> findCheckDay(@Param("minCreateAt") LocalDateTime minCreateAt, @Param("maxCreateAt") LocalDateTime maxCreateAt,
                                  @Param("fsmName") String fsmName, @Param("optUid") String optUid);

}