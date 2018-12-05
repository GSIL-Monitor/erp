package com.stosz.tms.vo;

import java.util.List;
import java.util.Map;

import com.stosz.tms.model.TrackStatusClassify;

public class TrackStatusClassifyVo {

	private Map<String, TrackStatusClassify> trackStatusMap;

	private List<TrackStatusClassify> patternTrackStatusList;

	private Map<String, TrackStatusClassify> trackStatusCodeMap;

	public Map<String, TrackStatusClassify> getTrackStatusMap() {
		return trackStatusMap;
	}

	public void setTrackStatusMap(Map<String, TrackStatusClassify> trackStatusMap) {
		this.trackStatusMap = trackStatusMap;
	}

	public List<TrackStatusClassify> getPatternTrackStatusList() {
		return patternTrackStatusList;
	}

	public void setPatternTrackStatusList(List<TrackStatusClassify> patternTrackStatusList) {
		this.patternTrackStatusList = patternTrackStatusList;
	}

	public Map<String, TrackStatusClassify> getTrackStatusCodeMap() {
		return trackStatusCodeMap;
	}

	public void setTrackStatusCodeMap(Map<String, TrackStatusClassify> trackStatusCodeMap) {
		this.trackStatusCodeMap = trackStatusCodeMap;
	}
}
