package com.stosz;

import com.stosz.tms.dto.OrderLinkDto;
import com.stosz.tms.dto.TransportOrderDetail;
import com.stosz.tms.dto.TransportOrderRequest;
import com.stosz.tms.model.api.ShippingExtend;
import com.stosz.tms.service.transport.impl.PakistanTransportHandler;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 *巴基斯坦 测试环境下单通
 */
public class PakistanTest {

	public static final Logger logger = LoggerFactory.getLogger(PakistanTest.class);

//	@Autowired
	PakistanTransportHandler trackHandler;

	@Test
	public void testAdd() {
		TransportOrderRequest orderRequest = new TransportOrderRequest();
		ShippingExtend extendInfo = new ShippingExtend();
		OrderLinkDto orderLinkDto = new OrderLinkDto();

		orderLinkDto.setLastName("大三");
		orderLinkDto.setFirstName("李");
		orderLinkDto.setCountryCode("CN");
		orderLinkDto.setProvince("广东省");
		orderLinkDto.setCity("深圳市");
		orderLinkDto.setAddress("动漫园");
		orderLinkDto.setZipcode("2134342");
		orderLinkDto.setTelphone("0987381349");
		orderRequest.setOrderLinkDto(orderLinkDto);

		orderRequest.setOrderNo("testorder123");// 订单号

		List<TransportOrderDetail> listProduct = new ArrayList<>();

		TransportOrderDetail detail = new TransportOrderDetail();
		detail.setProductNameCN("中文产品名");
		detail.setProductNameEN("testEnglishPro");
		detail.setOrderDetailQty(1);
		detail.setOrderDetailQty(2);
		listProduct.add(detail);

		orderRequest.setOrderDetails(listProduct);

		extendInfo.setAccount("80000");//todo 测试账号
		extendInfo.setMd5Key("434a2ed2-8056-4579-9a47-cfaa48676ffb80000");//todo 测试秘钥
		extendInfo.setSenderContactName("张小四");
		extendInfo.setSenderTelphone("13333333333");
		extendInfo.setSenderAddress("动漫园");

		// todo 测试环境
		extendInfo.setInterfaceUrl("http://www.pfcexpress.com/webservice/v2/CreateShipment.aspx");

		try {
			PakistanTransportHandler trackHandler = new PakistanTransportHandler();
			trackHandler.transportPlaceOrder(orderRequest, extendInfo);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
	}

	@Test
	public void tesnimTrackHandlertQuery() {

	}

	public static void main(String[] args) {


	}

}
