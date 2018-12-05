package com.stosz.tms.service;

import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stosz.tms.mapper.TrackingTaskDetailMapper;
import com.stosz.tms.model.TrackingTaskDetail;

@Service
public class TrackingTaskDetailService {

	@Autowired
	private TrackingTaskDetailMapper mapper;

	public HashSet<String> queryDetailHashExists(List<String> hashValues) {
		return mapper.queryDetailHashExists(hashValues);
	}

	public int addTrackDeatils(List<TrackingTaskDetail> trackDetails) {
		return mapper.addTrackDeatils(trackDetails);
	}

}
