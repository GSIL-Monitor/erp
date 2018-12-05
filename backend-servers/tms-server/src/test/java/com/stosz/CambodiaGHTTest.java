package com.stosz;

import com.stosz.plat.utils.JsonUtils;
import com.stosz.tms.base.JUnitBaseTest;
import com.stosz.tms.dto.*;
import com.stosz.tms.model.api.ShippingExtend;
import com.stosz.tms.parse.startegy.impl.CambodiaGHTParseStartegy;
import com.stosz.tms.service.track.impl.CambodiaGHTTrackHandler;
import com.stosz.tms.service.transport.impl.CambodiaGhtTransportHandler;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 柬埔寨GHT 生产下单，查询轨迹通
 */
public class CambodiaGHTTest extends JUnitBaseTest {
	//
	// @Autowired
	// CambodiaGHTTrackHandler trackHandler;

	@Autowired
	CambodiaGhtTransportHandler transportHandler;

	@Test
	public void testAdd() {
		TransportOrderRequest orderRequest = new TransportOrderRequest();

		ShippingExtend extendInfo = new ShippingExtend();

		orderRequest.setTrackNo("testtrackno111");
		orderRequest.setOrderNo("test111");
		orderRequest.setOrderAmount(new BigDecimal(1));

		extendInfo.setMd5Key("jpz123456");
		//测试账号
//		extendInfo.setAccount("CSH201701");
		//todo 正式账号
		extendInfo.setAccount("jianpz");
		extendInfo.setSenderContactName("zhangsan");
		//测试地址
//		extendInfo.setInterfaceUrl("http://wxcs.hubeta.com/express_test/thirdOrder/addExpressTspOrder");
		//TODO 生产地址
		extendInfo.setInterfaceUrl("http://tckp.cd100.cn:9000/express/thirdOrder/addExpressTspOrder");
		OrderLinkDto linkDto = new OrderLinkDto();
		linkDto.setFirstName("li");
		linkDto.setLastName("si");
		linkDto.setTelphone("13333333333");
		linkDto.setCity("深圳市");
		linkDto.setAddress("动漫园");

		List<TransportOrderDetail> list = new ArrayList<>();
		TransportOrderDetail detail = new TransportOrderDetail();

		detail.setProductTitle("测试商品");
		detail.setOrderDetailQty(1);
		detail.setTotalWeight(new BigDecimal(1));

		list.add(detail);
		orderRequest.setOrderDetails(list);

		orderRequest.setOrderLinkDto(linkDto);
		try{
			transportHandler.transportPlaceOrder(orderRequest,extendInfo);
		}catch (Exception e) {

		}

	}

	@Test
	public void testQuery() {
		String trackNo = "200000005";
		CambodiaGHTTrackHandler cambodiaGHTTrackHandler = new CambodiaGHTTrackHandler(new CambodiaGHTParseStartegy());
		ShippingExtend shippingExtend = new ShippingExtend();
		shippingExtend.setMd5Key("jpz123456");
		// TODO 生产环境
		shippingExtend.setWaybilltrackUrl("http://tckp.cd100.cn:9000/express/thirdOrder/getTspTranceInfo");
		TransportTrackResponse trackResponse = cambodiaGHTTrackHandler.captureTransTrack(new TrackRequest(trackNo), shippingExtend);
		logger.info(JsonUtils.toJson(trackResponse));
	}

}
