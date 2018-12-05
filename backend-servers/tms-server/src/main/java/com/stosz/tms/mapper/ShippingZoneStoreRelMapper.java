package com.stosz.tms.mapper;

import java.util.List;

import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import com.stosz.tms.mapper.builder.ShippingZoneStoreRelBuilder;
import com.stosz.tms.model.ShippingZoneStoreRel;

@Repository
public interface ShippingZoneStoreRelMapper {

	@Select("SELECT distinct r.wms_id,r.zone_id,r.shipping_way_id,r.allowed_product_type FROM shipping_zone_store_rel r LEFT JOIN shipping_store_rel s ON s.shipping_way_id=r.shipping_way_id AND s.wms_id=r.wms_id"
			+ " WHERE zone_id=#{zoneId} AND r.wms_id=#{wmsId} and r.enable=1 and s.enable=1")
	public List<ShippingZoneStoreRel> queryShippingStoreRel(@Param("zoneId") Integer zoneId, @Param("wmsId") Integer wmsId);

	@Select("SELECT * from shipping_zone_store_rel where shipping_way_id = #{shippingWayId} and  wms_id = #{wmsId} and enable=1")
	public List<ShippingZoneStoreRel> queryByShippingWayIdAndWmsId(@Param("shippingWayId") Integer shippingWayId, @Param("wmsId") Integer wmsId);

	@SelectProvider(type = ShippingZoneStoreRelBuilder.class, method = "find")
	public List<ShippingZoneStoreRel> queryList(ShippingZoneStoreRel shippingZoneStoreRel);

	@SelectProvider(type = ShippingZoneStoreRelBuilder.class, method = "count")
	public int count(ShippingZoneStoreRel shippingZoneStoreRel);

	@InsertProvider(type = ShippingZoneStoreRelBuilder.class, method = "insert")
	public int add(ShippingZoneStoreRel shippingZoneStoreRel);

	@UpdateProvider(type = ShippingZoneStoreRelBuilder.class, method = "updateSelective")
	public int update(ShippingZoneStoreRel shippingStoreRel);

	/**
	 * 根据仓库查询所有已关联的物流方式
	 * @param storeId
	 * @return
	 */
	@Select("SELECT * FROM shipping_zone_store_rel WHERE wms_id=#{storeId} ")
	public List<ShippingZoneStoreRel> queryByStoreId(Integer storeId);

	@InsertProvider(type = ShippingZoneStoreRelBuilder.class, method = "batchInsert")
	@Options(useGeneratedKeys = false)
	public int batchInsert(@Param("zoneStoreRels")List<ShippingZoneStoreRel> zoneStoreRels);
}
