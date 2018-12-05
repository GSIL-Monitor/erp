package com.stosz;

import com.stosz.plat.utils.JsonUtils;
import com.stosz.tms.dto.TrackRequest;
import com.stosz.tms.dto.TransportTrackResponse;
import com.stosz.tms.model.api.ShippingExtend;
import com.stosz.tms.parse.startegy.impl.ThailandBjtParseStartegy;
import com.stosz.tms.service.track.impl.ThailandBjtTrackHandler;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 泰国博佳图 人工下单 查询通
 */
public class ThailandBJTTest {
	public static final Logger logger = LoggerFactory.getLogger(ThailandBJTTest.class);
	@Autowired
	ThailandBjtTrackHandler trackHandler;

	@Test
	public void testAddShip() {
	}

	@Test
	public void testQueryRoute() {
		ShippingExtend shippingExtend = new ShippingExtend();
		// TODO 生产地址 英文
		 shippingExtend.setWaybilltrackUrl("https://th.kerryexpress.com/en/track/");
		//生产地址 泰文
		//shippingExtend.setWaybilltrackUrl("https://th.kerryexpress.com/th/track/");
		
		ThailandBjtTrackHandler trackHandler = new ThailandBjtTrackHandler(new ThailandBjtParseStartegy());
		TransportTrackResponse trackResponse = trackHandler.captureTransTrack(new TrackRequest("BJT1712060382"), shippingExtend);
		logger.info(JsonUtils.toJson(trackResponse));
	}

}
