package com.stosz.tms.service.track.impl;

import com.stosz.plat.utils.CollectionUtils;
import com.stosz.plat.utils.DateUtils;
import com.stosz.plat.utils.StringUtils;
import com.stosz.tms.dto.TrackRequest;
import com.stosz.tms.dto.TransportTrackResponse;
import com.stosz.tms.enums.TrackApiTypeEnum;
import com.stosz.tms.model.TrackingTaskDetail;
import com.stosz.tms.model.api.ShippingExtend;
import com.stosz.tms.parse.startegy.AbstractParseStartegy;
import com.stosz.tms.service.track.AbstractSingleTrackHandler;
import com.stosz.tms.utils.Base64;
import com.stosz.tms.utils.HttpClientUtils;
import com.stosz.tms.utils.MD5Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * 柬埔寨GHT 查询物流轨迹
 * @version [1.0,2017年12月26日]
 */
@Component
public class CambodiaGHTTrackHandler extends AbstractSingleTrackHandler {

	public static final Logger logger = LoggerFactory.getLogger(CambodiaGHTTrackHandler.class);

	public CambodiaGHTTrackHandler(@Qualifier("cambodiaGHTParseStartegy") AbstractParseStartegy parseStartegy) {
		super(parseStartegy);
	}

	@Override
	public TransportTrackResponse captureTransTrack(TrackRequest request, ShippingExtend shippingExtend) {
		TransportTrackResponse trackResponse = new TransportTrackResponse();
		try {
			String wayBillTrackUrl = getRequestAction(shippingExtend.getWaybilltrackUrl());
			StringBuilder requestAction = new StringBuilder(wayBillTrackUrl);
			Map<String, Object> paramMap = this.getParamMap(request.getTrackingNo(),shippingExtend);
			long t2=System.currentTimeMillis();
			String response = HttpClientUtils.doPost(requestAction.toString(), paramMap, "UTF-8");
			long t3=System.currentTimeMillis();
			if((t3-t2)/1000>15){
				logger.info("EXTREMELY CALL OVER TIMEtime{} seconds",(t3-t2)/1000);
			}
			if((t3-t2)/1000>3){
				logger.info(" CALL OVER TIME seconds",(t3-t2)/1000);
			}
			List<TrackingTaskDetail> trackDetails = parseStartegy.parseTrackContent(response);
			if(CollectionUtils.isNullOrEmpty(trackDetails)){
				trackResponse.setCode(TransportTrackResponse.FAIL);
			}else {
				trackResponse.setTrackDetails(trackDetails);
				trackResponse.setCode(TransportTrackResponse.SUCCESS);
			}
		} catch (Exception e) {
			logger.trace("CambodiaGHTTrackHandler 物流轨迹抓取异常,trackingNo={},Exception={}", request.getTrackingNo(), e);
			trackResponse.fail("CambodiaGHTTrackHandler 物流轨迹抓取异常");
		}
		return trackResponse;
	}

	@Override
	public TrackApiTypeEnum getCode() {
		return TrackApiTypeEnum.GHT;
	}

	private Map getParamMap(String billNo,ShippingExtend shippingExtend){
		LinkedHashMap<String,String> paramMap = new LinkedHashMap<>();
		String random = StringUtils.randomCode(4);
		String timestamp = DateUtils.format(new Date(),"yyyy-MM-dd HH:mm:ss");
		String md5str = shippingExtend.getMd5Key();

		Map orginMap = new LinkedHashMap();
		orginMap.put("userId","jianpz");
		orginMap.put("random",random);
		orginMap.put("billNo",billNo);
		orginMap.put("timestamp",timestamp);
		Set<Map.Entry<String, String>> entrySet = StringUtils.sortedmap(orginMap);
		StringBuilder sb = new StringBuilder();
		entrySet.stream().forEach(item -> {
			sb.append(item.getKey());
			sb.append("=");
			sb.append(item.getValue());
			sb.append("&");
		});
		String sign = sb.toString()+"key="+md5str;

		sign = Base64.encode(MD5Util.md5(sign,"UTF-8"));

		paramMap.put("timestamp",timestamp);
		paramMap.put("billNo",billNo);
		paramMap.put("random",random);
		paramMap.put("userId","jianpz");
		paramMap.put("sign",sign);
		return paramMap;
	}

	public static void main(String[] args) {

        //b7qldWwEZY6x4JV8PrBomQ==
		String sign = "billNo=200000732&random=6826&timestamp=2017-12-26 17:32:31&userId=jianpz&key=jpz123456";
		logger.info("mymd5==="+MD5Util.MD5Encode(sign,"UTF-8"));
		logger.info("hismd5=="+MD5Util.md5(sign,"UTF-8").toString());
		logger.info(Base64.encode(MD5Util.md5(sign,"UTF-8")));
	}

}
