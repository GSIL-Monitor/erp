package com;

import com.stosz.plat.utils.JsonUtils;
import com.stosz.tms.dto.*;
import com.stosz.tms.model.api.ShippingExtend;
import com.stosz.tms.parse.startegy.impl.NimParseStartegy;
import com.stosz.tms.service.track.impl.NimTrackHandler;
import com.stosz.tms.service.transport.impl.NimTransportHandler;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 下单 轨迹 调通
 */
public class NimTest {

	public static final Logger logger = LoggerFactory.getLogger(NimTest.class);

	@Autowired
	NimTrackHandler nimTrackHandler;

	@Test
	public void testAdd() {
		TransportOrderRequest orderRequest = new TransportOrderRequest();
		ShippingExtend extendInfo = new ShippingExtend();
		OrderLinkDto orderLinkDto = new OrderLinkDto();

		orderLinkDto.setLastName("testperson");
		orderLinkDto.setFirstName("testperson");
		orderLinkDto.setProvince("testprovince");
		orderLinkDto.setCity("testcity");
		orderLinkDto.setArea("testarea");
		orderLinkDto.setAddress("ال بنك السعودي الفرنسي - الادارة الإقليمية - طريق الملك عبدالله مع");
		orderLinkDto.setTelphone("0987381349");

		orderRequest.setOrderLinkDto(orderLinkDto);

		orderRequest.setOrderNo("testorder123");// 订单号
		orderRequest.setGoodsQty(1);// 数量
		orderRequest.setOrderAmount(new BigDecimal(1));// 金额
		// orderRequest.setRemark("testremark");

		List<TransportOrderDetail> listProduct = new ArrayList<>();

		TransportOrderDetail detail = new TransportOrderDetail();
		detail.setProductTitle("testProdDetalName1");// 明细
		detail.setOrderDetailQty(1);
		// detail.setUnit("box");
		listProduct.add(detail);

		orderRequest.setOrderDetails(listProduct);

		// todo 測試環境
		extendInfo.setInterfaceUrl("https://demo.nimexpress.com/nss/AddBillPASMIERFromURL.htm");
		// todo 生產環境
		// extendInfo.setInterfaceUrl("https://service.nimexpress.com/nss/AddBillPASMIERFromURL.htm");

		NimTransportHandler transportHandler = new NimTransportHandler();

		try {
			transportHandler.transportPlaceOrder(orderRequest, extendInfo);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
	}

	@Test
	public void tesnimTrackHandlertQuery() {
		ShippingExtend shippingExtend = new ShippingExtend();
		shippingExtend.setWaybilltrackUrl("https://service.nimexpress.com/nss/TrackingPASMIER.htm");// TODO 生产环境
		NimTrackHandler nimTrackHandler = new NimTrackHandler(new NimParseStartegy());
		//6841801190903 91226049
		nimTrackHandler.captureTransTrack(new TrackRequest("6841801190903"), shippingExtend);
	}

	public static void main(String[] args) {

		ShippingExtend shippingExtend = new ShippingExtend();
		shippingExtend.setWaybilltrackUrl("https://service.nimexpress.com/nss/TrackingPASMIER.htm");// TODO 生产环境
		NimTrackHandler nimTrackHandler = new NimTrackHandler(new NimParseStartegy());
		TransportTrackResponse trackResponse=nimTrackHandler.captureTransTrack(new TrackRequest("6831801187614"), shippingExtend);
		logger.info(JsonUtils.toJson(trackResponse));
	}

}
