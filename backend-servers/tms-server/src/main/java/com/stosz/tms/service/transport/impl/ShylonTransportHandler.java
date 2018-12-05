package com.stosz.tms.service.transport.impl;

import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.stosz.plat.utils.EncryptUtils;
import com.stosz.plat.utils.JsonUtils;
import com.stosz.plat.utils.StringUtil;
import com.stosz.tms.dto.OrderLinkDto;
import com.stosz.tms.dto.Parameter;
import com.stosz.tms.dto.TransportOrderDetail;
import com.stosz.tms.dto.TransportOrderRequest;
import com.stosz.tms.dto.TransportOrderResponse;
import com.stosz.tms.enums.HandlerTypeEnum;
import com.stosz.tms.model.api.ShippingExtend;
import com.stosz.tms.model.api.ShylonContent;
import com.stosz.tms.model.api.ShylonDetail;
import com.stosz.tms.service.transport.AbstractPlaceOrderTransportHandler;
import com.stosz.tms.utils.HttpClientUtils;
import com.stosz.tms.utils.XStreamUtils;

/**
 * 祥龙物流接口 （停止合作）
 * @author feiheping
 * @version [1.0,2017年12月1日]
 */
@Component
public class ShylonTransportHandler extends AbstractPlaceOrderTransportHandler {

	private Logger logger = LoggerFactory.getLogger(ShylonTransportHandler.class);

	@Override
	public TransportOrderResponse transportPlaceOrder(TransportOrderRequest orderRequest, ShippingExtend extendInfo) throws Exception {
		String requestBody = null;
		String responseBody = null;
		TransportOrderResponse response = new TransportOrderResponse(true);
		try {
			ShylonContent shylonContent = getTransportContent(orderRequest, extendInfo);
			HashMap<String, Class<?>> aliasMap = new HashMap<>();
			aliasMap.put("order", ShylonContent.class);
			aliasMap.put("desc", ShylonDetail.class);

			String content = XStreamUtils.toXml(shylonContent, aliasMap, true);
			content = StringUtil.concat("<request>", content, "</request>");
			String sign = this.getSign(content, extendInfo);// md5 加签
			content = URLEncoder.encode(content, "UTF-8");

			HashMap<String, Object> paramMap = new HashMap<>();
			paramMap.put("service", "tms_order_notify");
			paramMap.put("sign", sign);
			paramMap.put("content", content);

			requestBody = JsonUtils.toJson(paramMap);
			responseBody = HttpClientUtils.doPost(extendInfo.getInterfaceUrl(), paramMap, "UTF-8");
			Parameter<String, Object> parameter = XStreamUtils.xmlToMap(responseBody, null);

			String code = parameter.getString("is_success");
			if ("T".equals(code)) {
				response.setCode(TransportOrderResponse.SUCCESS);
				response.setTrackingNo(shylonContent.getOrder_code());
			} else {
				response.setCode(TransportOrderResponse.FAIL);
				String error = parameter.getString("error");
				response.setErrorMsg(error);
			}
		} catch (Exception e) {
			logger.info("ShylonTransportHandler code={},intefaceUrl={},Exception={}", getCode(), extendInfo.getInterfaceUrl(), e);
		} finally {
			logService.addLog(getCode(),orderRequest.getOrderNo(), extendInfo.getInterfaceUrl(), requestBody, responseBody);
		}
		return response;
	}

	private ShylonContent getTransportContent(TransportOrderRequest orderRequest, ShippingExtend extendInfo) {
		ShylonContent shylonContent = new ShylonContent();
		shylonContent.setOrder_code(orderRequest.getTrackNo());// 运单号，从运单号库匹配
		shylonContent.setOrderno(orderRequest.getOrderNo());
		shylonContent.setOrdertype(orderRequest.getOrderTypeEnum().display());
		shylonContent.setTms_service_code("7550058971");// 快递公司编号
		Long amount = orderRequest.getOrderAmount().setScale(0, BigDecimal.ROUND_HALF_DOWN).longValue();
		shylonContent.setTotal_amount(amount);// 代收货款
		shylonContent.setCccharge(amount);// 待付款
		OrderLinkDto orderLinkDto = orderRequest.getOrderLinkDto();
		shylonContent.setReceiver_name(StringUtil.concat(orderLinkDto.getFirstName(), orderLinkDto.getLastName()));// 收货人姓名
		shylonContent.setReceiver_zip(orderLinkDto.getZipcode());// 收货地址邮编
		shylonContent.setReceiver_province(orderLinkDto.getProvince());// 收货地址省份
		shylonContent.setReceiver_city(orderLinkDto.getCity());// 收货人城市
		shylonContent.setReceiver_district(orderLinkDto.getArea());// 地区
		shylonContent.setReceiver_address(this.getDetailAddress(orderLinkDto));// 收货人地址
		shylonContent.setReceiver_mobile(orderLinkDto.getTelphone());// 收货人电话
		shylonContent.setReceiver_phone(orderLinkDto.getTelphone());

		shylonContent.setSd_name(extendInfo.getSender());// 寄件人姓名
		shylonContent.setSd_mobile(extendInfo.getSenderTelphone());// 寄件人电话
		shylonContent.setSd_phone(extendInfo.getSenderTelphone());

		shylonContent.setRemark(StringUtil.concat(orderRequest.getRemark(), ".", orderLinkDto.getZipcode()));

		shylonContent.setPcs(orderRequest.getGoodsQty());// 商品数量

		HashSet<String> productSet = new HashSet<>();
		List<TransportOrderDetail> orderDetails = orderRequest.getOrderDetails();
		List<ShylonDetail> shylonDetails = orderDetails.stream().map(item -> {
			productSet.add(item.getProductTitle());
			String categoryName = item.getProductTitle();
			ShylonDetail shylonDetail = new ShylonDetail();
			shylonDetail.setDesname(categoryName);
			shylonDetail.setChildno("");
			return shylonDetail;
		}).collect(Collectors.toList());
		shylonContent.setDesclist(shylonDetails);// 子单及子单商品名称集合
		String descName = productSet.stream().collect(Collectors.joining(","));
		shylonContent.setDescname(descName);// 主单商品名称

		return shylonContent;
	}

	@Override
	protected void validate(TransportOrderRequest orderRequest, ShippingExtend extendInfo) {
	}

	public String getSign(String content, ShippingExtend extendInfo) {
		return EncryptUtils.md5(content + extendInfo.getMd5Key());
	}

	@Override
	public HandlerTypeEnum getCode() {
		// TODO Auto-generated method stub
		return HandlerTypeEnum.SHYLON;
	}

}
