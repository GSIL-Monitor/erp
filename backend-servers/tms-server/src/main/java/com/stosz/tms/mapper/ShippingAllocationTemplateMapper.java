package com.stosz.tms.mapper;

import java.util.List;

import com.stosz.tms.mapper.builder.ShippingAllocationTemplateBuilder;
import com.stosz.tms.mapper.builder.ShippingWayBuilder;
import com.stosz.tms.mapper.builder.ShippingZoneStoreRelBuilder;
import com.stosz.tms.model.ShippingWay;
import com.stosz.tms.model.ShippingZoneStoreRel;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import com.stosz.tms.model.ShippingAllocationTemplate;

@Repository
public interface ShippingAllocationTemplateMapper {

	@Select("<script>SELECT * FROM shipping_allocation_template s WHERE s.shipping_way_id IN "
			+ "<foreach item=\"item\" collection=\"shippingWayIds\" open=\"(\" separator=\",\" close=\")\">"
            +"${item}"
            + "</foreach> AND (s.wms_id=#{wmsId} or s.wms_id=-1)   and enable=1 </script>")
	public List<ShippingAllocationTemplate> queryAssignTemplate(@Param("shippingWayIds")List<Integer> shippingWayIds,@Param("wmsId") Integer wmsId);

	@Select("SELECT * from shipping_allocation_template where shipping_way_id = #{shippingWayId} and  wms_id = #{wmsId}")
	public List<ShippingAllocationTemplate> queryByShippingWayIdAndWmsId(@Param("shippingWayId") Integer shippingWayId,@Param("wmsId")Integer wmsId);

	@SelectProvider(type = ShippingAllocationTemplateBuilder.class, method = "count")
	public int count(ShippingAllocationTemplate allocationTemplate);

	@SelectProvider(type = ShippingAllocationTemplateBuilder.class, method = "find")
	public List<ShippingAllocationTemplate> queryList(ShippingAllocationTemplate allocationTemplate);

	@InsertProvider(type = ShippingAllocationTemplateBuilder.class, method = "insert")
	public int add(ShippingAllocationTemplate allocationTemplate);

	@UpdateProvider(type=ShippingAllocationTemplateBuilder.class,method="updateSelective")
	public int update(ShippingAllocationTemplate allocationTemplate);

}
