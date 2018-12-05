package com.stosz.tms.service;

import com.stosz.tms.mapper.ShippingExtendMapper;
import com.stosz.tms.model.api.ShippingExtend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ShipingExtendService {

	@Autowired
	private ShippingExtendMapper mapper;

	public ShippingExtend queryShipingExtend(String code) {
		return mapper.queryShipingByCode(code);
	}
}
