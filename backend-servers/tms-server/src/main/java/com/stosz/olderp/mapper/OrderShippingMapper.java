package com.stosz.olderp.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import com.stosz.olderp.model.ErpOrderShipping;

@Repository
public interface OrderShippingMapper {

	@Select("select count(*) from erp_order_shipping where id_order_shipping> #{maxShippingId}")
	public int getCount(@Param("maxShippingId") Integer maxShippingId);

	@Select("select * from erp_order_shipping where id_order_shipping> #{maxShippingId} order by id_order_shipping asc  limit ${start},${limit}")
	public List<ErpOrderShipping> getList(@Param("maxShippingId") Integer maxShippingId, @Param("start") int start, @Param("limit") int limit);
}
