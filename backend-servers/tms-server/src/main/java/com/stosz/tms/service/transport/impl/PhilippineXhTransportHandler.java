package com.stosz.tms.service.transport.impl;//package com.stosz.tms.service.transport.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.stosz.tms.dto.TransportOrderDetail;
import org.apache.http.entity.ContentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stosz.plat.utils.DateUtils;
import com.stosz.tms.dto.OrderLinkDto;
import com.stosz.tms.dto.TransportOrderRequest;
import com.stosz.tms.dto.TransportOrderResponse;
import com.stosz.tms.enums.HandlerTypeEnum;
import com.stosz.tms.model.api.PhilippineXhContent;
import com.stosz.tms.model.api.ShippingExtend;
import com.stosz.tms.service.transport.AbstractPlaceOrderTransportHandler;
import com.stosz.tms.utils.DESCryptography;
import com.stosz.tms.utils.HttpClientUtils;
import com.stosz.tms.utils.JsonUtils;

/**
 * 菲律宾XH 物流接口 调通 预先导入订单号
 * @author xiepengcheng
 * @version [1.0,2017年12月5日]
 */
@Component
public class PhilippineXhTransportHandler extends AbstractPlaceOrderTransportHandler {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	public static final String STRENCRKEY = "L6PfT1qKA3nOyoA1";//md5

	@Override
	public TransportOrderResponse transportPlaceOrder(TransportOrderRequest orderRequest, ShippingExtend shippingExtend) throws Exception {
		TransportOrderResponse orderResponse = new TransportOrderResponse(true);
		String requestBody = null;
		String responseBody = null;
		try{
			PhilippineXhContent philippineXhContent = new PhilippineXhContent();
			philippineXhContent = this.getTransportContent(orderRequest,shippingExtend);

			ObjectMapper objectMapper = new ObjectMapper();

			List<PhilippineXhContent> list = new ArrayList<>();
			list.add(philippineXhContent);
			requestBody = objectMapper.writeValueAsString(list);

			responseBody=HttpClientUtils.doPost(shippingExtend.getInterfaceUrl(),requestBody, ContentType.APPLICATION_JSON,"UTF-8");

			Map result = JsonUtils.jsonToLinkedMap(responseBody, Map.class);

			if ((boolean)result.get("result")) {
				orderResponse.setCode(TransportOrderResponse.SUCCESS);
				orderResponse.setTrackingNo(result.get("data") + "");
			} else {
				orderResponse.setCode(TransportOrderResponse.FAIL);
				orderResponse.setErrorMsg(result.get("error") + "");
			}
		}catch (Exception e){
			logger.info("PhilippineXhTransportHandler code={},intefaceUrl={},Exception={}", getCode(), shippingExtend.getInterfaceUrl(), e);
			logger.trace(e.getMessage());
		}finally {
			logService.addLog(getCode(),orderRequest.getOrderNo(), shippingExtend.getInterfaceUrl(), requestBody, responseBody);
		}

		return orderResponse;
	}


	private PhilippineXhContent getTransportContent(TransportOrderRequest orderRequest,ShippingExtend extendInfo) {

		PhilippineXhContent philippineXhContent = new PhilippineXhContent();

		OrderLinkDto orderLinkDto = orderRequest.getOrderLinkDto();

		philippineXhContent.setCustNo("Cuckoo");// 客户编号
		philippineXhContent.setMerchantOrderNumber(orderRequest.getOrderNo());// 商家订单号
		philippineXhContent.setWaybillNumber(orderRequest.getTrackNo());// 运单号
		philippineXhContent.setSender(extendInfo.getSender());// 发件人
		philippineXhContent.setSenderAddress(extendInfo.getSenderAddress());// 发件人地址

		philippineXhContent.setSenderTelphone(extendInfo.getSenderTelphone());// 发件人电话
		philippineXhContent.setRecipientName(orderLinkDto.getLastName() + orderLinkDto.getFirstName());// 收件人
		philippineXhContent.setRecipientTelephone(orderLinkDto.getTelphone());// 收件人电话
		philippineXhContent.setRecipientAddress(orderLinkDto.getAddress());// 收件人地址
		philippineXhContent.setSenderProvince(extendInfo.getSenderProvince());
		philippineXhContent.setSenderCity(extendInfo.getSenderCity());
		philippineXhContent.setSenderTown(extendInfo.getSenderTown());

		philippineXhContent.setRecipientProvince(orderLinkDto.getProvince());
		philippineXhContent.setRecipientCity(orderLinkDto.getCity());
		philippineXhContent.setRecipientTown(orderLinkDto.getArea());

		philippineXhContent.setReportWeight("1");// 报告重量

		BigDecimal amount = orderRequest.getOrderAmount().setScale(0, BigDecimal.ROUND_HALF_DOWN);
		if (orderRequest.getPayState().equals(0)) {
			philippineXhContent.setCODAmount(amount + "");// 代收货款
		}
		philippineXhContent.setDeclaredValue(amount + "");// 申报货值
		philippineXhContent.setRemark(orderRequest.getRemark());// 备注

		String token = this.getSign(philippineXhContent,extendInfo);

		philippineXhContent.setToken(token);//令牌

		return philippineXhContent;
	}

	@Override
	protected void validate(TransportOrderRequest orderRequest,ShippingExtend extendInfo) {
		Assert.notNull(extendInfo, "寄件信息不能为空");
		Assert.notNull(orderRequest, "订单信息不能为空");
		OrderLinkDto orderLinkDto = orderRequest.getOrderLinkDto();

		Assert.notNull(extendInfo.getSender(), "发件公司不能为空");
		Assert.hasText(extendInfo.getSenderTelphone(), "发件人电话不能为空");
		Assert.notNull(extendInfo.getSenderProvince(), "发件人省不能为空");
		Assert.notNull(extendInfo.getSenderCity(), "发件人市不能为空");
		Assert.notNull(extendInfo.getSenderTown(), "发件人区县不能为空");
		Assert.hasText(extendInfo.getSenderAddress(), "发件人详细地址不能为空");
		Assert.notNull(extendInfo.getInterfaceUrl(),"下单请求接口地址不能为空");

		Assert.hasText(orderRequest.getOrderNo(), "订单号不能为空");
		Assert.notNull(orderRequest.getTrackNo(), "运单号不能为空");
		Assert.notNull(orderRequest.getOrderAmount(), "订单金额不能为空");
		Assert.notNull(orderRequest.getGoodsQty(), "订单数量不能为空");
		Assert.notNull(orderRequest.getPayState(), "订单状态不能为空");

		Assert.hasText(orderLinkDto.getProvince(), "收件人省不能为空");
		Assert.hasText(orderLinkDto.getCity(), "收件人市不能为空");
		Assert.hasText(orderLinkDto.getArea(), "收件人区不能为空");
		Assert.hasText(orderLinkDto.getAddress(), "收件人详细地址不能为空");
		Assert.hasText(orderLinkDto.getFirstName(), "收件人姓不能为空");
		Assert.hasText(orderLinkDto.getLastName(), "收件人名不能为空");
		Assert.hasText(orderLinkDto.getTelphone(), "收件人电话不能为空");
	}

	/**
	 * 生成令牌
	 * @param philippineXhContent
	 * @return
	 */
	private String getSign(PhilippineXhContent philippineXhContent,ShippingExtend extendInfo) {
		Assert.notNull(philippineXhContent, "传入的参数philippineXHContent为空");
		String ticket = DateUtils.getCurrentMonth();
		String token = null;
		try {
			token = DESCryptography.byteToHexString(DESCryptography.DES_CBC_Encrypt(
					(philippineXhContent.getWaybillNumber()+"-"+ticket).getBytes(),
					(extendInfo.getMd5Key().substring(0,8)).getBytes()));
		} catch (Exception e) {
			logger.trace("philippineXhContent 生成令牌错误，入参{}", JsonUtils.toJson(philippineXhContent));
		}
		return token;
	}

	@Override
	public HandlerTypeEnum getCode() {
		return HandlerTypeEnum.PHILIPPINEXH;
	}


}
