package com.stosz.product.mapper;

import com.stosz.fsm.IFsmDao;
import com.stosz.product.ext.enums.ProductZoneState;
import com.stosz.product.ext.model.ProductNewZone;
import com.stosz.product.ext.model.ProductZone;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Repository
public interface ProductZoneMapper extends IFsmDao<ProductZone> {
	@InsertProvider(type = ProductZoneBuilder.class,method = "insert")
	int insert(ProductZone param);

	@Insert("<script>INSERT IGNORE INTO product_zone(id,create_at,product_id,zone_code,zone_name,currency_code,department_id,department_no,department_name,creator_id,creator,state,state_time,last_order_at) VALUES " +
			" <foreach item=\"item\" index=\"index\" collection=\"productZoneList\"  separator=\",\" >" +
			" (#{item.id}, #{item.createAt},#{item.productId},#{item.zoneCode},#{item.zoneName},#{item.currencyCode},#{item.departmentId},#{item.departmentNo},#{item.departmentName},#{item.creatorId},#{item.creator},#{item.state},#{item.stateTime},#{item.lastOrderAt})" +
			" </foreach>" +
			"</script>")
	Integer insertList(@Param("id") Integer id, @Param("productZoneList") List<ProductZone> productZoneList);

	@DeleteProvider(type = ProductZoneBuilder.class, method = "delete")
	int delete(@Param("id") Integer id);

	@UpdateProvider(type = ProductZoneBuilder.class, method = "update")
	int update(ProductZone param);

	@Update("update product_zone set last_order_at = #{lastOrderAt}, state = #{state}, state_time = #{stateTime} where id = #{id}")
	Integer updateLastOrderAt(ProductZone param);

	@Update("update product_zone set stock= #{stock},qty_preout = #{qtyPreout},qty_road = #{qtyRoad},state = #{state},state_time = #{stateTime} where id = #{id}")
	int updateStock(ProductZone productZone);

	@UpdateProvider(type = ProductZoneBuilder.class, method = "updateState")
	Integer updateState(@Param("id") Integer id, @Param("state") String state, @Param("stateTime") LocalDateTime stateTime);

	@Update("update product_zone set archive_by=#{loginid} , archive_at=current_timestamp() where id=#{id}")
	int updateArchiveUser(@Param("loginid") String loginid, @Param("id") Integer id);

	@Update("UPDATE product_zone SET update_at = #{updateAt}, creator_id = #{creatorId}, creator = #{creator} WHERE id = #{productZoneId}")
	int updateCreator(@Param("productZoneId") Integer productZoneId, @Param("creatorId") Integer creatorId, @Param("creator") String creator, @Param("updateAt") LocalDateTime updateAt);

	@SelectProvider(type = ProductZoneBuilder.class, method = "getById")
	ProductZone getById(@Param("id") Integer id);

	@Select("<script>SELECT * FROM product_zone WHERE id IN " +
			" <foreach item=\"item\" index=\"index\" collection=\"ids\" open=\"(\" separator=\",\" close=\")\">" +
			" #{item} " +
			" </foreach>" +
			"</script>")
	List<ProductZone> findByIds(@Param("ids") List<Integer> ids);

	@SelectProvider(type = ProductZoneBuilder.class, method = "find")
	List<ProductZone> find(ProductZone param);

	@SelectProvider(type = ProductZoneBuilder.class, method = "count")
	int count(ProductZone param);

	@Select("SELECT * FROM product_zone WHERE product_id = #{productId}")
	List<ProductZone> findByProductId(@Param("productId") Integer productId);

	@Select("SELECT COUNT(1) FROM product_zone WHERE product_id = #{productId}")
	int queryByProductIdCount(@Param("productId") Integer productId);

	@Select("<script>SELECT pz.*,z.`id` AS zoneId FROM product_zone pz LEFT JOIN zone z ON pz.`zone_code` = z.`code` WHERE pz.state != #{state} AND pz.product_id in " +
			" <foreach item=\"item\" index=\"index\" collection=\"ids\" open=\"(\" separator=\",\" close=\")\">" +
			" #{item} " +
			" </foreach>" +
			"</script>")
	List<ProductZone> findByProductIds(@Param("state") String state, @Param("ids") List<Integer> ids);


	@Select("select _this.* from product_zone _this join product p on _this.product_id=p.id where p.spu=#{spu}")
	List<ProductZone> findBySpu(@Param("spu") String spu);

	@Select("select _this.* from product_zone where product_new_id=#{productNewId}")
	List<ProductZone> findByProductNewId(@Param("productNewId") Integer productNewId);


	@Select("select * from product_zone where zone_code = #{zoneCode} and product_id = #{productId} and department_id = #{departmentId}")
	ProductZone getByUnique(@Param("zoneCode") String zoneCode, @Param("productId") Integer productId, @Param("departmentId") Integer departmentId);


	@Select("select * from product_zone where product_id=#{productId} and department_id=#{departmentId}")
	List<ProductZone> findByProductIdAndDepartmentId(@Param("productId") Integer productId, @Param("departmentId") Integer departmentId);
	@Select("select * from product_zone ")
	List<ProductNewZone> findList();

	@SelectProvider(type = ProductZoneBuilder.class, method = "findByDate")
	List<ProductZone> findByDate(@Param("param") ProductZone param);

	@Select("select count(1) from product_zone where update_at >= #{startTime} and update_at <= #{endTime}")
	int countByDate(@Param("startTime") LocalDateTime startTime, @Param("endTime") LocalDateTime endTime);

	@SelectProvider(type = ProductZoneBuilder.class, method = "findNoOrderFixDay")
	List<ProductZone> findNoOrderFixDay(@Param("days") Integer days, @Param("limit") Integer limit);


//    @Select("SELECT * FROM product_zone WHERE  ((last_order_at IS NULL AND create_at <= #{time}) " +
//            "OR (last_order_at IS NOT NULL AND last_order_at <= #{time} )) AND state_time <= #{time} AND state = #{state} " +
//            " LIMIT #{limit} OFFSET #{start} ")
//	List<ProductZone> findWarningForNoOrder(@Param("time") LocalDateTime time, @Param("state") String state,
//                                            @Param("limit") Integer limit, @Param("start") Integer start);

//	@Select("SELECT COUNT(1) FROM product_zone WHERE  ((last_order_at IS NULL AND create_at <= #{time}) " +
//            "OR (last_order_at IS NOT NULL AND last_order_at <= #{time} )) AND state_time <= #{time}  AND state = #{state}")
//    int countWarningForNoOrder(@Param("time") LocalDateTime time, @Param("state") String state);

	@Select("<script>SELECT COUNT(1) FROM product_zone " +
			"WHERE ((last_order_at IS NULL AND create_at &lt;= #{time}) OR (last_order_at IS NOT NULL AND last_order_at &lt;= #{time} )) " +
			"AND state_time &lt;= #{time} AND state = #{state} AND department_id NOT IN " +
			" <foreach item=\"item\" index=\"index\" collection=\"ids\" open=\"(\" separator=\",\" close=\")\">" +
			" #{item} " +
			" </foreach>" +
			"</script>")
	int countWarningForNoOrder(@Param("time") LocalDateTime time, @Param("state") String state, @Param("ids")Set<Integer> ids);

	@Select("<script>SELECT * FROM product_zone " +
			"WHERE ((last_order_at IS NULL AND create_at &lt;= #{time}) OR (last_order_at IS NOT NULL AND last_order_at &lt;= #{time} )) " +
			"AND state_time &lt;= #{time} AND state = #{state} AND department_id NOT IN " +
			" <foreach item=\"item\" index=\"index\" collection=\"ids\" open=\"(\" separator=\",\" close=\")\">" +
			" #{item} " +
			" </foreach>" +
			"</script>")
	List<ProductZone> findWarningForNoOrder(@Param("time") LocalDateTime time, @Param("state") String state,
											@Param("limit") Integer limit, @Param("start") Integer start, @Param("ids")Set<Integer> ids);


	@Select("SELECT * FROM product_zone WHERE  ((last_order_at IS NULL AND create_at BETWEEN #{minTime} AND #{maxTime}) " +
			"OR (last_order_at IS NOT NULL AND last_order_at BETWEEN #{minTime} AND #{maxTime})) AND state = #{state} " +
			" LIMIT #{limit} OFFSET #{start} ")
	List<ProductZone> findWarnForNoOrder(@Param("minTime") LocalDateTime minTime, @Param("maxTime") LocalDateTime maxTime,
                                         @Param("state") String state, @Param("limit") Integer limit, @Param("start") Integer start);

	@Select("SELECT COUNT(1) FROM product_zone WHERE ( (last_order_at IS NULL AND create_at BETWEEN #{minTime} AND #{maxTime}) " +
			"OR (last_order_at IS NOT NULL AND last_order_at BETWEEN #{minTime} AND #{maxTime}) ) AND state = #{state}")
	int countWarnForNoOrder(@Param("minTime") LocalDateTime minTime, @Param("maxTime") LocalDateTime maxTime, @Param("state") String state);


	@SelectProvider(type = ProductZoneBuilder.class, method = "countNorderFixDay")
	int countNorderFixDay(@Param("departmentId") Integer departmentId, @Param("productId") Integer productId, @Param("time") LocalDateTime time);

    /*查询部门产品上架的最后时间*/
    @Select("SELECT COUNT(1) FROM product_zone WHERE department_id = #{departmentId} AND product_id = #{productId} AND state_time > #{time} AND state = #{state}")
    int coutnLastOnsaleTime(@Param("departmentId") Integer departmentId, @Param("productId") Integer productId, @Param("time") LocalDateTime time, @Param("state") String state);


	@Select("SELECT COUNT(1) FROM product_zone WHERE department_id = #{departmentId} AND product_id = #{productId} AND last_order_at BETWEEN #{minTime} AND #{maxTime} ")
	int countWarningDay(@Param("departmentId") Integer departmentId, @Param("productId") Integer productId,
                        @Param("minTime") LocalDateTime minTime, @Param("maxTime") LocalDateTime maxTime);

	@Select("SELECT * FROM product_zone WHERE department_id = #{departmentId} AND product_id = #{productId} ")
	List<ProductZone> findByDeptPcId(@Param("departmentId") Integer departmentId, @Param("productId") Integer productId);

	@SelectProvider(type = ProductZoneBuilder.class, method = "findNoArchiveFixDay")
	List<ProductZone> findNoArchiveFixDay(@Param("days") Integer days, @Param("limit") Integer limit);

	@Select("SELECT COUNT(1) FROM product_zone WHERE zone_code = #{zoneCode}")
	int countByZoneCode(@Param("zoneCode") String zoneCode);

	@Select("SELECT COUNT(1) FROM product_zone WHERE state = #{state} AND state_time <= #{time}")
	int countWaitOffsale(@Param("state") String state, @Param("time") LocalDateTime time);

	@Select("SELECT * FROM product_zone WHERE state = #{state.name} AND state_time <= #{time} LIMIT #{limit} OFFSET #{start} ")
	List<ProductZone> findWaitOffSale(@Param("state") ProductZoneState state, @Param("time") LocalDateTime time, @Param("limit") Integer limit, @Param("start") Integer start);


	@Select("SELECT MAX(last_order_at) FROM product_zone WHERE product_id = #{productId} AND department_id = #{departmentId} AND last_order_at IS NOT NULL ")
	LocalDateTime maxLastOrderTime(@Param("departmentId") Integer departmentId, @Param("productId") Integer productId);

}

