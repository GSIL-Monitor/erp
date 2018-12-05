package com.stosz.tms.mapper;

import java.util.List;
import java.util.Set;

import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.UpdateProvider;
import org.springframework.stereotype.Repository;

import com.stosz.tms.mapper.builder.ShippingWayBuilder;
import com.stosz.tms.model.ShippingWay;

@Repository
public interface ShippingWayMapper {

	@InsertProvider(type = ShippingWayBuilder.class, method = "insert")
	public int addShippingWay(ShippingWay shippingWay);

	@SelectProvider(type = ShippingWayBuilder.class, method = "count")
	public int count(ShippingWay shippingWay);

	@SelectProvider(type = ShippingWayBuilder.class, method = "find")
	public List<ShippingWay> queryList(ShippingWay shippingWay);
	
	@UpdateProvider(type=ShippingWayBuilder.class,method="updateSelective")
	public int update(ShippingWay shippingWay);

	@Select("<script>SELECT * FROM shipping_way where id in "
			+ "<foreach item=\"item\" collection=\"ids\" open=\"(\" separator=\",\" close=\")\">"
            +"${item}"
            + "</foreach>  </script>")
	public List<ShippingWay> queryByIds(@Param("ids")Set<Integer> ids);

	@Select("<script>SELECT * FROM shipping_way where shipping_way_code in "
			+ "<foreach item=\"item\" collection=\"codes\" open=\"(\" separator=\",\" close=\")\">"
			+"#{item}"
			+ "</foreach>  </script>")
	public List<ShippingWay> queryByCodes(@Param("codes")List<String> codes);


	@Select("<script>SELECT * FROM shipping_way where id not in "
			+ "<foreach item=\"item\" collection=\"ids\" open=\"(\" separator=\",\" close=\")\">"
			+"${item}"
			+ "</foreach>  </script>")
	public List<ShippingWay> queryByExceptIds(@Param("ids")Set<Integer> ids);


	@DeleteProvider(type=ShippingWayBuilder.class,method="delete")
	public int delete(@Param("id") Integer id);

	@Update("update shipping_way set state = #{state},modifier=#{modifier},modifier_id = #{modifierId}   where id = #{id}")
	public int updateState(ShippingWay shippingWay);

	@Select("select * from  shipping_way where shipping_way_code = #{shippingWayCode} ")
	public ShippingWay getByCode(@Param("shippingWayCode") String shippingWayCode);

	@Select("select * from  shipping_way where shipping_way_name = #{shippingWayName} ")
	public ShippingWay getByName(@Param("shippingWayName") String shippingWayName);
	
}
