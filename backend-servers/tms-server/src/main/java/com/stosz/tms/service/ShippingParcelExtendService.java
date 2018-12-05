package com.stosz.tms.service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stosz.plat.utils.ArrayUtils;
import com.stosz.tms.enums.SyncStatusEnum;
import com.stosz.tms.mapper.ShippingParcelDetailMapper;
import com.stosz.tms.mapper.ShippingParcelMapper;
import com.stosz.tms.model.ShippingParcel;
import com.stosz.tms.model.ShippingParcelDetail;

@Service
public class ShippingParcelExtendService {

	@Autowired
	private ShippingParcelMapper mapper;

	@Autowired
	private ShippingParcelDetailMapper parcelDetailMapper;

	/**
	 * 查询需要抛送物流单的数据
	 * @return
	 */
	public List<ShippingParcel> queryListByParcel() {
		// 查询指派成功 ，推送状态为:(待同步/推送失败)
		List<ShippingParcel> shippingParcels = mapper.queryListByParcel(2,
				Arrays.asList(SyncStatusEnum.STAYSYNC.ordinal(), SyncStatusEnum.SYNC_FAIL.ordinal()));
		if (ArrayUtils.isNotEmpty(shippingParcels)) {
			List<Integer> parcelIds = shippingParcels.stream().map(ShippingParcel::getId).collect(Collectors.toList());
			List<ShippingParcelDetail> parcelDetails = parcelDetailMapper.queryListByParcelIds(parcelIds);
			Map<Integer, List<ShippingParcelDetail>> parcelDetailMap = parcelDetails.stream()
					.collect(Collectors.groupingBy(ShippingParcelDetail::getParcelId));

			for (ShippingParcel shippingParcel : shippingParcels) {
				shippingParcel.setParcelDetails(parcelDetailMap.get(shippingParcel.getId()));
			}
		}
		return shippingParcels;
	}
}
