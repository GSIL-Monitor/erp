package com.stosz.tms.mapper;

import com.stosz.tms.mapper.builder.ShippingLogBuilder;
import com.stosz.tms.model.api.ShippingLog;

import org.apache.ibatis.annotations.InsertProvider;
import org.springframework.stereotype.Repository;

@Repository
public interface ShippingLogMapper {

	@InsertProvider(type = ShippingLogBuilder.class, method = "insert")
	public int addLog(ShippingLog shippingLog);
}
