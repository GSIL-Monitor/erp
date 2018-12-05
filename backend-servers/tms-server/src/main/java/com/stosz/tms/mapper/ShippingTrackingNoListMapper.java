package com.stosz.tms.mapper;

import java.util.List;

import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import com.stosz.tms.dto.ShippingTrackingNoSectionAddDto;
import com.stosz.tms.dto.ShippingTrackingNumberListAddDto;
import com.stosz.tms.mapper.builder.ShippingTrackingNoListBuilder;
import com.stosz.tms.model.ShippingTrackingNoList;

@Repository
public interface ShippingTrackingNoListMapper {

	@Select("SELECT * FROM shipping_tracking_no_list WHERE track_status=0 and shipping_way_id=#{shippingWayId} AND wms_id=#{wmsId} AND product_type=#{productType} "
			+ " and  id >= ((SELECT MAX(id) FROM shipping_tracking_no_list)-(SELECT MIN(id) FROM shipping_tracking_no_list)) * RAND() + (SELECT MIN(id) FROM shipping_tracking_no_list) LIMIT 1")
	public List<ShippingTrackingNoList> queryUsableTrackNo(@Param("shippingWayId") Integer shippingWayId, @Param("wmsId") Integer wmsId,
			@Param("productType") Integer productType);

	@SelectProvider(type = ShippingTrackingNoListBuilder.class, method = "find")
	public List<ShippingTrackingNoList> queryList(ShippingTrackingNoList shippingZoneStoreRel);

	@SelectProvider(type = ShippingTrackingNoListBuilder.class, method = "count")
	public int count(ShippingTrackingNoList shippingZoneStoreRel);

	@InsertProvider(type = ShippingTrackingNoListBuilder.class, method = "sectionAdd")
	@Options(useGeneratedKeys = false)
	public int sectionAdd(@Param("sectionAddDto") ShippingTrackingNoSectionAddDto sectionAddDto,@Param("trackNumbers")List<String> trackNumbers);

	@InsertProvider(type = ShippingTrackingNoListBuilder.class, method = "numberListAdd")
	@Options(useGeneratedKeys = false)
	public int numberListAdd(@Param("numberListAddDto") ShippingTrackingNumberListAddDto numberListAddDto);

	@Update("update shipping_tracking_no_list set track_status = 2,modifier=#{modifier},modifier_id=#{modifierId} where id =#{id}")
	public int disable(@Param("id") int id, @Param("modifier") String modifier, @Param("modifierId") Integer modifierId);

	@Update("update shipping_tracking_no_list set track_status=#{trackStatus},version=version+1 where id=#{id} and version=#{version}")
	public int updateTrackingUse(ShippingTrackingNoList trackingNoItem);

	@Select("SELECT count(*) FROM shipping_tracking_no_list WHERE track_status=0 AND shipping_way_id=#{shippingWayId} and wms_id=#{wmsId} and product_type=#{productType}")
	public int queryUsableTrackCount(@Param("shippingWayId") Integer shippingWayId, @Param("wmsId") Integer wmsId,
			@Param("productType") Integer productType);
}
