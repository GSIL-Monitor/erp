package com.stosz.tms.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.stosz.tms.mapper.ShippingParcelDetailMapper;
import com.stosz.tms.model.ShippingParcelDetail;

@Service
public class ShippingParcelDetailService {

	@Resource
	private ShippingParcelDetailMapper mapper;

	public int count(ShippingParcelDetail shippingParcelDetail) {
		return mapper.count(shippingParcelDetail);
	}

	public List<ShippingParcelDetail> queryList(ShippingParcelDetail shippingParcelDetail) {
		// if (!StringUtils.hasText(shippingParcel.getOrderBy())) {
		// shippingParcel.setOrderBy(" update_at desc,create_at");
		// }
		return mapper.queryList(shippingParcelDetail);
	}

	public int addParcelDetails(List<ShippingParcelDetail> parcelDetails) {
		return mapper.addList(parcelDetails);
	}

	public List<ShippingParcelDetail> queryParcelDetail(Integer parcelId) {
		return mapper.queryParcelDetail(parcelId);
	}

}
