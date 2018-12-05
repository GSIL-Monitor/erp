package com.stosz.tms.service.transport.impl;

import com.stosz.tms.dto.OrderLinkDto;
import com.stosz.tms.dto.TransportOrderDetail;
import com.stosz.tms.dto.TransportOrderRequest;
import com.stosz.tms.dto.TransportOrderResponse;
import com.stosz.tms.enums.HandlerTypeEnum;
import com.stosz.tms.model.api.CxcContent;
import com.stosz.tms.model.api.ShippingExtend;
import com.stosz.tms.model.pakistan.PakistanRequestDto;
import com.stosz.tms.model.pakistan.Product;
import com.stosz.tms.service.transport.AbstractPlaceOrderTransportHandler;
import com.stosz.tms.utils.HttpClientUtils;
import com.stosz.tms.utils.JsonUtils;
import org.apache.http.entity.ContentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 皇家巴基斯坦 物流接口
 * @version [1.0,2018年1月19日]
 */
@Component
public class PakistanTransportHandler extends AbstractPlaceOrderTransportHandler {

	private Logger logger = LoggerFactory.getLogger(PakistanTransportHandler.class);

	@Override
	public TransportOrderResponse transportPlaceOrder(TransportOrderRequest orderRequest, ShippingExtend extendInfo) throws Exception {
		TransportOrderResponse response = new TransportOrderResponse(true);
		String requestBody = null;
		String responseBody = null;
		try {
			PakistanRequestDto pakistanRequestDto = this.getTransportContent(orderRequest, extendInfo);

			requestBody = JsonUtils.toJson(pakistanRequestDto);

			Map<String, String> headMap = new HashMap<>();
			headMap.put("Authorization","Bearer "+extendInfo.getMd5Key());
			responseBody = HttpClientUtils.doPost(extendInfo.getInterfaceUrl(), requestBody, ContentType.APPLICATION_JSON,
					"UTF-8", headMap);
			Map<String,Object> resultMap = JsonUtils.jsonToMap(responseBody,LinkedHashMap.class);
			if(("200").equals(resultMap.get("status"))){
				String trackNo = ((Map)resultMap.get("data")).get("OrderNo").toString();
				response.setCode(TransportOrderResponse.SUCCESS);
				response.setTrackingNo(trackNo);
			}else {
				response.setCode(TransportOrderResponse.FAIL);
				response.setErrorMsg(resultMap.get("msg").toString());
			}
		} catch (Exception e) {
			logger.info("PakistanTransportHandler code={},intefaceUrl={},Exception={}", getCode(), extendInfo.getInterfaceUrl(), e);
		} finally {
			logService.addLog(getCode(), orderRequest.getOrderNo(), extendInfo.getInterfaceUrl(), requestBody, responseBody);
		}

		return response;
	}

	private PakistanRequestDto getTransportContent(TransportOrderRequest orderRequest, ShippingExtend extendInfo) {
		OrderLinkDto linkDto = orderRequest.getOrderLinkDto();
		PakistanRequestDto pakistanRequestDto = new PakistanRequestDto();
		List<Product> products = new ArrayList<>();

		pakistanRequestDto.setCsRefNo(orderRequest.getOrderNo());
		pakistanRequestDto.setType(2);
		pakistanRequestDto.setCustomerId(extendInfo.getAccount());//
		pakistanRequestDto.setChannelId("PFC-PK");// 渠道id
		pakistanRequestDto.setSender(extendInfo.getSenderContactName());//发件人姓名
		pakistanRequestDto.setSendPhone(extendInfo.getSenderTelphone());//发件人电话
		pakistanRequestDto.setSendAddress(extendInfo.getSenderAddress());//发件人地址
		pakistanRequestDto.setShipToName(linkDto.getFirstName()+linkDto.getLastName());//收件人
		pakistanRequestDto.setShipToCountry(linkDto.getCountryCode());//收件人国家二字码
		pakistanRequestDto.setShipToState(linkDto.getProvince());//收件人州
		pakistanRequestDto.setShipToCity(linkDto.getCity());//收件人城市
		pakistanRequestDto.setShipToAdress1(linkDto.getAddress());//收件人地址
		pakistanRequestDto.setShipToZipCode(linkDto.getZipcode());//收件人邮编
		pakistanRequestDto.setOrderStatus(1);//订单状态
		pakistanRequestDto.setShipToPhoneNumber(linkDto.getTelphone());//收件人电话

		products = orderRequest.getOrderDetails().stream().map(item ->{
			Product product = new Product();
			product.setSKU(item.getSku());
			product.setCnName(item.getProductNameCN());
			product.setEnName(item.getProductNameEN());
			product.setMaterialQuantity(item.getOrderDetailQty());
			product.setPrice(item.getPrice());
			return product;
		}).collect(Collectors.toList());

		pakistanRequestDto.setProducts(products);

		return pakistanRequestDto;
	}

	@Override
	protected void validate(TransportOrderRequest orderRequest, ShippingExtend extendInfo) {
		Assert.notNull(extendInfo, "寄件信息不能为空");
		Assert.notNull(orderRequest, "订单信息不能为空");
		OrderLinkDto orderLinkDto = orderRequest.getOrderLinkDto();

		Assert.hasText(orderRequest.getOrderNo(), "订单号不能为空");

		Assert.notNull(orderLinkDto.getProvince(), "收件人省不能为空");
		Assert.notNull(orderLinkDto.getCity(), "收件人市不能为空");
		Assert.notNull(orderLinkDto.getAddress(), "收件人详细地址不能为空");
		Assert.notNull(orderLinkDto.getFirstName(), "收件人姓不能为空");
		Assert.notNull(orderLinkDto.getLastName(), "收件人名不能为空");
		Assert.notNull(orderLinkDto.getTelphone(), "收件人电话不能为空");
		Assert.notNull(orderLinkDto.getCountryCode(), "收件人国家二字码不能为空");
		Assert.notNull(orderLinkDto.getZipcode(), "收件人邮编不能为空");

		Assert.hasText(extendInfo.getAccount(), "寄件公司账号不能为空");
		Assert.hasText(extendInfo.getSenderContactName(),"寄件人不能为空");
		Assert.hasText(extendInfo.getSenderTelphone(),"寄件人电话不能为空");
		Assert.hasText(extendInfo.getSenderAddress(),"寄件人地址不能为空");
		Assert.hasText(extendInfo.getInterfaceUrl(), "下单请求地址不能为空");

		List<TransportOrderDetail> orderDetails = orderRequest.getOrderDetails();
		// 多品名信息(中文品名,英文品名,数量,单位,单价,海关编码;)用英文逗号隔开，用封号结尾
		for (TransportOrderDetail orderDetail : orderDetails) {
			Assert.hasText(orderDetail.getSku(),"明细sku不能为空");
			Assert.notNull(orderDetail.getPrice(),"明细单价不能为空");
			Assert.notNull(orderDetail.getOrderDetailQty(),"明细数量不能为空");
			Assert.notNull(orderDetail.getProductNameEN(),"明细英文不能为空");
			Assert.notNull(orderDetail.getProductNameCN(),"明细中文文不能为空");
		}
	}

	@Override
	public HandlerTypeEnum getCode() {
		return HandlerTypeEnum.PAKISTAN;
	}

}
