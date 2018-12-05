package com.stosz.tms.mapper;

import java.util.List;
import java.util.Set;

import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;

import com.stosz.tms.mapper.builder.ShippingBuilder;
import com.stosz.tms.model.Shipping;
import org.springframework.stereotype.Repository;

@Repository
public interface ShippingMapper {

	@SelectProvider(type = ShippingBuilder.class, method = "getShippingList")
	public List<Shipping> getShippingList(@Param("name") String name, @Param("start") int start, @Param("limit") int limit);

	@InsertProvider(type = ShippingBuilder.class, method = "insert")
	public int addShipping(Shipping shipping);

	@SelectProvider(type = ShippingBuilder.class, method = "count")
	public int getShippingCount(Shipping shipping);

	@Select("select * from  shipping where shipping_code = #{shippingCode} ")
	public Shipping getShippingByCode(@Param("shippingCode") String shippingCode);

	@SelectProvider(type = ShippingBuilder.class, method = "getById")
	public Shipping getShippingById(@Param("id") Integer id);

	@UpdateProvider(type = ShippingBuilder.class, method = "updateSelective")
	public int updateShipping(Shipping shipping);

	@Select("<script>SELECT * FROM shipping where id in " + "<foreach item=\"item\" collection=\"ids\" open=\"(\" separator=\",\" close=\")\">"
			+ "${item}" + "</foreach>  </script>")
	public List<Shipping> queryByIds(@Param("ids") Set<Integer> ids);

	@SelectProvider(type = ShippingBuilder.class, method = "find")
	public List<Shipping> queryShipping(Shipping shipping);
	

	@Select("SELECT * FROM shipping WHERE id=(SELECT shipping_id FROM shipping_way WHERE id=#{shippingWayId})")
	public Shipping queryShippingByWayId(@Param("shippingWayId")Integer shippingWayId);


	@InsertProvider(type = ShippingBuilder.class,method = "batchInsertData")
	public int batchInsert(List<Shipping> shippings);
	
	
	@SelectProvider(type = ShippingBuilder.class, method = "findAll")
	public List<Shipping> queryShippingAll(Shipping shipping);
	

}
