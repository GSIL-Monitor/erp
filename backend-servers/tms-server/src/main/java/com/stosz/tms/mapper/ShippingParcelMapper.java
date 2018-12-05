package com.stosz.tms.mapper;

import com.stosz.tms.mapper.builder.ShippingParcelBuilder;
import com.stosz.tms.model.ShippingParcel;

import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShippingParcelMapper {

	@SelectProvider(type = ShippingParcelBuilder.class, method = "count")
	public int count(ShippingParcel shippingParcel);

	@SelectProvider(type = ShippingParcelBuilder.class, method = "find")
	public List<ShippingParcel> queryList(ShippingParcel shippingParcel);

	@Select("select * from shipping_parcel where id = #{id}")
	public ShippingParcel queryById(@Param("id") Integer id);

	@Select("select * from shipping_parcel where order_id = #{orderId}")
	public ShippingParcel queryByOrderId(@Param("orderId") Integer orderId);

	@InsertProvider(type = ShippingParcelBuilder.class, method = "insertSelective")
	public int add(ShippingParcel shippingParcel);

	@UpdateProvider(type = ShippingParcelBuilder.class, method = "updateSelective")
	public int updateSelective(ShippingParcel shippingParcel);

	@Select("<script>SELECT * FROM shipping_parcel WHERE parcel_state=#{parcelState} AND sync_status in "
			+ "<foreach item=\"item\" collection=\"syncStatusList\" open=\"(\" separator=\",\" close=\")\">"
            +"${item}"
            + "</foreach></script>")
	public List<ShippingParcel> queryListByParcel(@Param("parcelState") int parcelState, @Param("syncStatusList")List<Integer> syncStatusList);
	
	
	@Select("select count(*) from shipping_parcel where order_id=#{orderId}")
	public int queryParcelCount(@Param("orderId")Integer orderId);

}
