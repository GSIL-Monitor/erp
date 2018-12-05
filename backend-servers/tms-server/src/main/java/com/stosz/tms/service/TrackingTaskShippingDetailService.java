package com.stosz.tms.service;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.stosz.tms.model.api.KerryStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stosz.tms.mapper.TrackingTaskShippingDetailMapper;
import com.stosz.tms.model.TrackingTaskShippingDetail;
import org.springframework.util.Assert;

@Service
public class TrackingTaskShippingDetailService {

	@Autowired
	private TrackingTaskShippingDetailMapper mapper;

	public HashSet<String> queryDetailHashExists(List<String> hashValues) {
		return mapper.queryDetailHashExists(hashValues);
	}

	public int addTrackDeatils(List<TrackingTaskShippingDetail> trackDetails) {
		return mapper.addTrackDeatils(trackDetails);
	}

	public Map<String, List<TrackingTaskShippingDetail>> queryShippingDetailsByTrackNo(List<String> trackNoList) {
		List<TrackingTaskShippingDetail> taskBlackcatDetails = mapper.queryDetails(trackNoList);
		Map<String, List<TrackingTaskShippingDetail>> collectMap = taskBlackcatDetails.stream()
				.collect(Collectors.groupingBy(TrackingTaskShippingDetail::getTrackNo));
		return collectMap;
	}

	public void insertKerryTrack(KerryStatus status) {
		int i=mapper.insertKerryTrack(status);
		Assert.isTrue(i==1,"插入kerry运单轨迹失败");
	}
}
