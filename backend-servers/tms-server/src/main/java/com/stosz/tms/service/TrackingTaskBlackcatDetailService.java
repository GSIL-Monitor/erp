package com.stosz.tms.service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stosz.tms.mapper.TrackingTaskBlackcatDetailMapper;
import com.stosz.tms.model.TrackingTaskBlackcatDetail;

@Service
public class TrackingTaskBlackcatDetailService {

	@Autowired
	private TrackingTaskBlackcatDetailMapper mapper;

	public void addList(List<TrackingTaskBlackcatDetail> blackcatDetails) {
		int batchSize = 5000;
		int size = blackcatDetails.size();
		int count = (int) Math.ceil(size / (double) batchSize);
		for (int i = 0; i < count; i++) {
			int endIndex = (i + 1) * batchSize;
			endIndex = endIndex > size ? size : endIndex;
			List<TrackingTaskBlackcatDetail> subLists = blackcatDetails.subList(i * batchSize, endIndex);
			mapper.addDeatils(subLists);
		}
	}

	public Map<String, List<TrackingTaskBlackcatDetail>> queryBlackCatByTrackNo(List<String> trackNoList) {
		List<TrackingTaskBlackcatDetail> taskBlackcatDetails = mapper.queryDetails(trackNoList);
		Map<String, List<TrackingTaskBlackcatDetail>> collectMap = taskBlackcatDetails.stream()
				.collect(Collectors.groupingBy(TrackingTaskBlackcatDetail::getTrackNo));
		return collectMap;
	}
}
