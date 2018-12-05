package com.stosz.tms.service.track.impl;

import com.stosz.plat.utils.JsonUtils;
import com.stosz.plat.utils.StringUtil;
import com.stosz.tms.dto.TrackRequest;
import com.stosz.tms.dto.TransportTrackResponse;
import com.stosz.tms.enums.TrackApiTypeEnum;
import com.stosz.tms.model.TrackingTaskDetail;
import com.stosz.tms.model.api.ShippingExtend;
import com.stosz.tms.parse.startegy.AbstractParseStartegy;
import com.stosz.tms.parse.startegy.impl.TimesParseStartegy;
import com.stosz.tms.service.track.AbstractSingleTrackHandler;
import com.stosz.tms.utils.HttpClientUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * times 物流轨迹抓取
 * @author feiheping
 * @version [1.0,2017年12月8日]
 */
@Component
public class TimesTrackHandler extends AbstractSingleTrackHandler {
	private static  final Logger logger= LoggerFactory.getLogger(TimesTrackHandler.class);
	public TimesTrackHandler(@Qualifier("timesParseStartegy") AbstractParseStartegy parseStartegy) {
		super(parseStartegy);
	}

	@Override
	public TransportTrackResponse captureTransTrack(TrackRequest request, ShippingExtend shippingExtend) {
		TransportTrackResponse trackResponse = new TransportTrackResponse();
		try {
			// 抓取物流轨迹地址
			String wayBillTrackUrl = getRequestAction(shippingExtend.getWaybilltrackUrl());
			StringBuilder requestAction = new StringBuilder(wayBillTrackUrl);
			requestAction.append("/");
			requestAction.append(request.getTrackingNo());
			requestAction.append(StringUtil.concat("?token=", shippingExtend.getToken()));
			long t2=System.currentTimeMillis();
			String response = HttpClientUtils.get(requestAction.toString(), "UTF-8", getHeader(shippingExtend));
			long t3=System.currentTimeMillis();
			if((t3-t2)/1000>15){
				logger.info("EXTREMELY CALL OVER TIMEtime{} seconds",(t3-t2)/1000);
			}
			if((t3-t2)/1000>3){
				logger.info(" CALL OVER TIME seconds",(t3-t2)/1000);
			}
			List<TrackingTaskDetail> trackDetails = parseStartegy.parseTrackContent(response);
			trackResponse.setTrackDetails(trackDetails);
			trackResponse.setCode(TransportTrackResponse.SUCCESS);
		} catch (Exception e) {
			logger.info("times 物流轨迹抓取异常,trackingNo={},Exception={}", request.getTrackingNo(), e);
			trackResponse.fail("times物流轨迹抓取异常");
		}
		return trackResponse;
	}

	public Map<String, String> getHeader(ShippingExtend shippingExtend) {
		Map<String, String> headMap = new HashMap<>();
		headMap.put("Authorization", StringUtil.concat("Bearer ", shippingExtend.getMd5Key()));
		return headMap;
	}

	@Override
	public TrackApiTypeEnum getCode() {
		return TrackApiTypeEnum.TIMES;
	}

	public static void main(String[] args) {
//		String text = "{     \"trackingNumber\": \"TECTH00733425\",     \"milestones\": {         \"manfiest_receive\": \"2017-10-07 00:00:00\",         \"upload\": \"2017-10-07 17:53:00\",         \"sort_in\": \"2017-10-09 10:49:42\",         \"sort_out\": \"2017-10-09 11:01:43\",         \"close_box\": \"2017-10-09 11:03:36\",         \"handover_linehaul\": \"2017-10-10 12:37:58\",         \"pickup\": \"2017-10-10 17:43:00\",         \"export\": \"2017-10-10 20:00:24\",         \"etd\": \"2017-10-10 20:00:24\",         \"eta\": \"2017-10-15 00:00:24\",         \"uplift\": null,         \"ata\": null,         \"import\": null,         \"handover_lastmile\": null,         \"delivering\": \"2017-10-24 08:34:53\",         \"pending\": \"2017-10-24 10:44:00\",         \"pending_reason\": \"Delivery unsuccessful due to Cannot contact via phone\",         \"reject\": \"2017-10-24 11:50:00\",         \"reject_reason\": \"什么破玩意\",         \"return\": null,         \"receive\": \"2017-10-24 11:38:00\"     } }  ";
//		Parameter<String, Object> parameter = JsonUtils.jsonToObject(text, Parameter.class);
//		LinkedHashMap<String, String> dest = (LinkedHashMap<String, String>) parameter.get("milestones");
//		List<TrackingTaskDetail> details = new TimesParseStartegy().parseTrackContent(text);
//		for (TrackingTaskDetail trackDetail : details) {
//			logger.info(JsonUtils.toJson(trackDetail));
//		}
		TrackRequest trackRequest=new TrackRequest();
		trackRequest.setTrackingNo("TECTH00640796");
		ShippingExtend shippingExtend=new ShippingExtend();
		shippingExtend.setWaybilltrackUrl("https://timesoms.com/api/orders/");
		shippingExtend.setMd5Key("sbv99QoVncfr4twUlpByLwGLNKMMfLlSKtU0DIZYGFl85o5SlWeMvsShlIvl");
		shippingExtend.setToken("fJ83StsDzZPI50N0yksVUdaBVZIxR3FZqS4pKmG3yK2YQBVGQC0Pz7vNRuz0");
		TransportTrackResponse trackResponse=new TimesTrackHandler(new TimesParseStartegy()).captureTransTrack(trackRequest, shippingExtend);
		logger.info(JsonUtils.toJson(trackResponse));
	}

}
