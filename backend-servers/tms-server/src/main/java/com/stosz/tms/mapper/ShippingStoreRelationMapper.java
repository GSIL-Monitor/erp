package com.stosz.tms.mapper;

import com.stosz.tms.mapper.builder.ShippingStoreRelationBuilder;
import com.stosz.tms.model.ShippingStoreRel;
import com.stosz.tms.model.ShippingWay;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface ShippingStoreRelationMapper {

	/**
	 * 查询物流方式关联的仓库
	 * @param shippingWayId
	 * @return
	 */
	@Select("select * from shipping_store_rel where shipping_way_id = #{shippingWayId} and enable = 1")
	public List<ShippingStoreRel> selectByShippingWayId(@Param("shippingWayId") Integer shippingWayId);

	/**
	 * 根据仓库查询所有已关联的物流方式
	 * @param storeId
	 * @return
	 */
	@Select("SELECT * FROM shipping_store_rel WHERE wms_id=#{storeId} ")
	public List<ShippingStoreRel> selectStoreRelByStoreId(Integer storeId);

	/**
	 * 根据仓库查询可用的物流方式
	 * @param wmsId
	 * @return
	 */
	@Select("SELECT * FROM shipping_store_rel WHERE wms_id=#{wmsId} AND ENABLE=1")
	public List<ShippingStoreRel> selectStoreRelByWmsId(Integer wmsId);

	@SelectProvider(type = ShippingStoreRelationBuilder.class, method = "find")
	public List<ShippingStoreRel> select(ShippingStoreRel shippingStoreRel);

	@SelectProvider(type = ShippingStoreRelationBuilder.class, method = "count")
	public int count(ShippingStoreRel shippingStoreRel);

	@InsertProvider(type = ShippingStoreRelationBuilder.class, method = "insertSelective")
	public int add(ShippingStoreRel shippingStoreRel);

	@UpdateProvider(type = ShippingStoreRelationBuilder.class, method = "updateSelective")
	public int update(ShippingStoreRel shippingStoreRel);

	@SelectProvider(type = ShippingStoreRelationBuilder.class, method = "getById")
	public ShippingStoreRel getById(Integer id);

	@Select("SELECT * FROM shipping_store_rel WHERE shipping_general_name = #{name} or shipping_specia_name = #{name}")
	public ShippingStoreRel selectStoreRelByName(String name);

	@Select("SELECT * FROM shipping_store_rel WHERE shipping_general_code = #{code} or shipping_specia_code = #{code}")
	public ShippingStoreRel selectStoreRelByCode(String code);


	@Select("<script>SELECT * FROM shipping_store_rel WHERE shipping_way_id in  "
			+ "<foreach item=\"item\" collection=\"shippingWayIdSet\" open=\"(\" separator=\",\" close=\")\">"
            +"${item}"
            + "</foreach>  AND ENABLE=1 and wms_id=#{wmsId}</script>")
	public List<ShippingStoreRel> selectStoreRelByWayIdList(@Param("shippingWayIdSet")Set<Integer> shippingWayIdSet,@Param("wmsId") Integer wmsId);

}
