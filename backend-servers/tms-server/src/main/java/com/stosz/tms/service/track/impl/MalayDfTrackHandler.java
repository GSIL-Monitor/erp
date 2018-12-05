package com.stosz.tms.service.track.impl;

import com.stosz.plat.utils.ArrayUtils;
import com.stosz.tms.dto.TrackRequest;
import com.stosz.tms.dto.TransportTrackResponse;
import com.stosz.tms.enums.TrackApiTypeEnum;
import com.stosz.tms.model.TrackingTaskDetail;
import com.stosz.tms.model.api.ShippingExtend;
import com.stosz.tms.parse.startegy.AbstractParseStartegy;
import com.stosz.tms.service.track.AbstractSingleTrackHandler;
import com.stosz.tms.utils.HttpClientUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 马来东丰 查询物流轨迹
 * @version [1.0,2018年1月18日]
 */
@Component
public class MalayDfTrackHandler extends AbstractSingleTrackHandler {

	public MalayDfTrackHandler(@Qualifier("malayDfParseStartegy") AbstractParseStartegy parseStartegy) {
		super(parseStartegy);
	}

	@Override
	public TransportTrackResponse captureTransTrack(TrackRequest request, ShippingExtend shippingExtend) {
		TransportTrackResponse trackResponse = new TransportTrackResponse();
		try {
			String wayBillTrackUrl = getRequestAction(shippingExtend.getWaybilltrackUrl());
			StringBuilder requestAction = new StringBuilder(wayBillTrackUrl);
			Map<String, String> paramMap = this.getParamMap(request.getTrackingNo());

			String response = HttpClientUtils.get(requestAction.toString(), paramMap, "UTF-8");
			List<TrackingTaskDetail> trackDetails = parseStartegy.parseTrackContent(response);
			if(ArrayUtils.isEmpty(trackDetails)){
				trackResponse.setCode(TransportTrackResponse.FAIL);
			}else {
				trackResponse.setTrackDetails(trackDetails);
				trackResponse.setCode(TransportTrackResponse.SUCCESS);
			}
		} catch (Exception e) {
			logger.trace("MalayDfTrackHandler 物流轨迹抓取异常,trackingNo={},Exception={}", request.getTrackingNo(), e);
			trackResponse.fail("MalayDfTrackHandler 物流轨迹抓取异常");
		}
		return trackResponse;
	}


	public Map<String,String> getParamMap(String trackingNo){
		Map<String,String> map = new LinkedHashMap<>();
		map.put("jvCd","42dec70a4f25166d");
		map.put("sTimeDifference","671cb6bcdedd72c7");
		map.put("sSelectedLanguage","73f9a3a3f2cf56bc");
		map.put("sCountryCd","0968983fa1feb763");
		map.put("sLanguageMode","0");
		map.put("CHAR_SET","3f572693955bb3ff");
		map.put("sDefCharSet","3f572693955bb3ff");
		map.put("sCharSetCsv","3f572693955bb3ff");
		map.put("action","GDXTX010S10Action_doSearch:Track");
		map.put("tTrackingNoInputVal1",trackingNo);
		map.put("tTrackingNoInputVal2","");
		map.put("tTrackingNoInputVal3","");
		map.put("tTrackingNoInputVal4","");
		map.put("tTrackingNoInputVal5","");
		map.put("tTrackingNoInputVal6","");
		map.put("tTrackingNoInputVal7","");
		map.put("tTrackingNoInputVal8","");
		map.put("tTrackingNoInputVal9","");
		map.put("tTrackingNoInputVal10","");
		return map;
	}


	@Override
	public TrackApiTypeEnum getCode() {
		return TrackApiTypeEnum.MALAYDF;
	}

}
