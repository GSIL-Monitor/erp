package com.stosz.tms.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stosz.tms.mapper.ShippingZoneStoreRelMapper;
import com.stosz.tms.model.ShippingZoneStoreRel;

@Service
public class ShippingZoneStoreRelService {

	@Autowired
	private ShippingZoneStoreRelMapper mapper;

	/**
	 * 根据仓库ID 区域ID 查询物流方式
	 * @param wmsId 仓库ID
	 * @param zoneId 区域ID
	 * @return
	 */
	public List<ShippingZoneStoreRel> queryShippingZoneStore(Integer wmsId, Integer zoneId) {
		return mapper.queryShippingStoreRel(zoneId, wmsId);
	}
}
