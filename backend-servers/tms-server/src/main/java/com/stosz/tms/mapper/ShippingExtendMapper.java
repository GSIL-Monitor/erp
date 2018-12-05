package com.stosz.tms.mapper;

import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import com.stosz.tms.model.api.ShippingExtend;

@Repository
public interface ShippingExtendMapper {

	@Select("select * from shipping_extend where code=#{code}")
	public ShippingExtend queryShipingByCode(String code);
}
