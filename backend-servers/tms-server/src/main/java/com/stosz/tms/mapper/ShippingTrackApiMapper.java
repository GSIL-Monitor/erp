package com.stosz.tms.mapper;

import java.util.List;
import java.util.Set;

import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;
import org.springframework.stereotype.Repository;

import com.stosz.tms.mapper.builder.ShippingTrackApiBuilder;
import com.stosz.tms.model.ShippingTrackApi;

@Repository
public interface ShippingTrackApiMapper {

	@SelectProvider(type = ShippingTrackApiBuilder.class, method = "find")
	public List<ShippingTrackApi> queryList(ShippingTrackApi trackApi);

	@InsertProvider(type = ShippingTrackApiBuilder.class, method = "insert")
	public int addShippingWay(ShippingTrackApi shippingWay);

	@SelectProvider(type = ShippingTrackApiBuilder.class, method = "count")
	public int count(ShippingTrackApi shippingWay);

	@UpdateProvider(type = ShippingTrackApiBuilder.class, method = "updateSelective")
	public int update(ShippingTrackApi shippingWay);

	@SelectProvider(type = ShippingTrackApiBuilder.class, method = "getById")
	public ShippingTrackApi getById(Integer apiId);

	@Select("select * from  shipping_track_api where api_code = #{apiCode} ")
	public ShippingTrackApi getByCode(@Param("apiCode") String apiCode);

	@Select("<script>SELECT * FROM shipping_track_api where id in "
			+ "<foreach item=\"item\" collection=\"ids\" open=\"(\" separator=\",\" close=\")\">" + "${item}" + "</foreach>  </script>")
	public List<ShippingTrackApi> queryByIds(@Param("ids") Set<Integer> ids);
	
	@SelectProvider(type = ShippingTrackApiBuilder.class, method = "findAll")
	public List<ShippingTrackApi> queryListAll(ShippingTrackApi trackApi);
}
