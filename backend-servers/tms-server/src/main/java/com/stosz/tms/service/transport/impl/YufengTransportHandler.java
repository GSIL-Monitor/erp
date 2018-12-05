package com.stosz.tms.service.transport.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.entity.ContentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.stosz.tms.dto.OrderLinkDto;
import com.stosz.tms.dto.Parameter;
import com.stosz.tms.dto.TransportOrderRequest;
import com.stosz.tms.dto.TransportOrderResponse;
import com.stosz.tms.enums.HandlerTypeEnum;
import com.stosz.tms.enums.OrderTypeEnum;
import com.stosz.tms.model.api.ShippingExtend;
import com.stosz.tms.model.api.YufengContent;
import com.stosz.tms.service.transport.AbstractPlaceOrderTransportHandler;
import com.stosz.tms.utils.HttpClientUtils;
import com.stosz.tms.utils.XStreamUtils;
import com.thoughtworks.xstream.XStream;
import org.springframework.util.Assert;

/**
 * 裕丰 物流接口 调通
 * xml传递
 * @author xiepengcheng
 * @version [1.0,2017年11月30日]
 */
@Component
public class YufengTransportHandler extends AbstractPlaceOrderTransportHandler {

	private Logger logger = LoggerFactory.getLogger(YufengTransportHandler.class);

	@Override
	public TransportOrderResponse transportPlaceOrder(TransportOrderRequest orderRequest, ShippingExtend extendInfo) throws Exception {
		TransportOrderResponse response = new TransportOrderResponse(true);
		String requestBody = null;
		String responseBody = null;
		try{
			YufengContent content = getYufengContent(orderRequest);
			XStream stream = new XStream();
			stream.alias("order", YufengContent.class);
			String contentXml = "<request>"+XStreamUtils.toXml(content,null,true)+"</request>";

			// 请求物流接口
			requestBody = "service=tms_order_notify&content="+contentXml;
			responseBody = HttpClientUtils.doPost(extendInfo.getInterfaceUrl(), requestBody, ContentType.TEXT_PLAIN, "UTF-8",this.getPostHeader(extendInfo));
			Parameter<String, Object> parameter = XStreamUtils.xmlToMap(responseBody, null);

			String code = parameter.getString("is_success");
			if ("T".equals(code)) {
				response.setCode(TransportOrderResponse.SUCCESS);
				String trackingNo = parameter.getString("jobno");
				response.setTrackingNo(trackingNo);
			} else {
				response.setCode(TransportOrderResponse.FAIL);
				String error = parameter.getString("error");
				response.setErrorMsg(error);
			}
		}catch (Exception e){
			logger.info("YufengTransportHandler code={},intefaceUrl={},Exception={}", getCode(), extendInfo.getInterfaceUrl(), e);
		}finally {
			logService.addLog(getCode(),orderRequest.getOrderNo(), extendInfo.getInterfaceUrl(), requestBody, responseBody);
		}

		return response;
	}
	
	private YufengContent getYufengContent(TransportOrderRequest orderRequest) {
		YufengContent content = new YufengContent();
		// 必填参数
		content.setOrder_code(orderRequest.getTrackNo());// 运单号码
		content.setOrdertype(OrderTypeEnum.normal.display());// 订货订单
		content.setTms_service_code("4099");// 快递公司编号 做成可配置 #TODO php 是写死的

		// 选填参数
		OrderLinkDto orderLinkDto = orderRequest.getOrderLinkDto();
		content.setOrderno(orderRequest.getOrderNo());// 订单号码
		content.setReceiver_name(orderLinkDto.getLastName().concat(orderLinkDto.getLastName()));// 收货人名称
		content.setReceiver_province(orderLinkDto.getProvince());// 省份
		content.setReceiver_city(orderLinkDto.getCity());// 城市
		content.setReceiver_district(orderLinkDto.getArea());// 地区
		content.setReceiver_address(orderLinkDto.getAddress());// 地址
		content.setReceiver_mobile(orderLinkDto.getTelphone());// 手机
		content.setPcs(orderRequest.getGoodsQty());//数量
		content.setTotal_amount(orderRequest.getOrderAmount());//代收货款
		content.setRemark(orderRequest.getRemark());//备注
		content.setDescname(orderRequest.getRemark());//主单名称
		return content;
	}

	@Override
	protected void validate(TransportOrderRequest orderRequest, ShippingExtend extendInfo) {
		Assert.notNull(extendInfo, "配置信息不能为空");
		Assert.notNull(orderRequest,"订单信息不能为空");
		Assert.hasText(orderRequest.getTrackNo(), "运单号码不能为空");
		Assert.notNull(orderRequest.getGoodsQty(),"订单数量不能为空");
		Assert.notNull(orderRequest.getOrderAmount(),"订单金额不能为空");
		Assert.notNull(extendInfo,"寄件配置信息不能为空");
		Assert.notNull(extendInfo.getInterfaceUrl(),"下单请求接口地址不能为空");

		OrderLinkDto orderLinkDto = orderRequest.getOrderLinkDto();
		Assert.notNull(orderLinkDto, "收件人信息不能为空");
		String name=null;
		if(orderLinkDto.getFirstName()!=null)
		{
			name+=orderLinkDto.getFirstName();
		}
		if(orderLinkDto.getLastName()!=null)
		{
			name+=orderLinkDto.getLastName();
		}
		Assert.notNull(name, "收件人姓名能为空");
		Assert.hasText(orderLinkDto.getProvince(), "收件人省份不能为空");
		Assert.hasText(orderLinkDto.getCity(), "收件人城市不能为空");
		Assert.hasText(orderLinkDto.getArea(), "收件人区不能为空");
		Assert.hasText(orderLinkDto.getAddress(), "收件人地址不能为空");
		Assert.hasText(orderLinkDto.getTelphone(), "收件人电话不能为空");

	}

	private Map<String, String> getPostHeader(ShippingExtend extendInfo) {
		Map<String, String> headers = new HashMap<String, String>();
		headers.put("content-Type", "application/x-www-form-urlencoded");
		return headers;
	}

	@Override
	public HandlerTypeEnum getCode() {
		return HandlerTypeEnum.YUFENG;
	}

}
