package com.stosz.tms.service.transport.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.http.entity.ContentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stosz.plat.utils.DateUtils;
import com.stosz.tms.dto.OrderLinkDto;
import com.stosz.tms.dto.TransportOrderRequest;
import com.stosz.tms.dto.TransportOrderResponse;
import com.stosz.tms.enums.HandlerTypeEnum;
import com.stosz.tms.model.api.CxcContent;
import com.stosz.tms.model.api.ShippingExtend;
import com.stosz.tms.service.transport.AbstractPlaceOrderTransportHandler;
import com.stosz.tms.utils.HttpClientUtils;
import com.stosz.tms.utils.JsonUtils;
import org.springframework.util.Assert;

/**
 * cxc 物流接口 调通
 *
 * cmCode测试环境为：001008
 *       正式环境为：98171006
 *
 * @author xiepengcheng
 * @version [1.0,2017年12月1日]
 */
@Component
public class CxcTransportHandler extends AbstractPlaceOrderTransportHandler {

	private Logger logger = LoggerFactory.getLogger(CxcTransportHandler.class);

	@Override
	public TransportOrderResponse transportPlaceOrder(TransportOrderRequest orderRequest, ShippingExtend extendInfo) throws Exception {
		TransportOrderResponse response = new TransportOrderResponse(true);
		String requestBody = null;
		String responseBody = null;
		try {
			CxcContent cxcContent = getTransportContent(orderRequest, extendInfo);

			List<CxcContent> list = new ArrayList<>();
			list.add(cxcContent);

			Map<String, Object> map = new HashMap<>();
			map.put("username", extendInfo.getAccount());// "CBXGCN1684945"
			map.put("password", extendInfo.getMd5Key());// "C3625XCBG48N4"

			HashMap<String, Object> paramMap = new HashMap<>();
			paramMap.put("data", list);
			paramMap.put("key", map);

			requestBody = JsonUtils.toJson(paramMap);
			responseBody = HttpClientUtils.doPost(extendInfo.getInterfaceUrl(), "data=" + requestBody, ContentType.APPLICATION_FORM_URLENCODED,
					"UTF-8", this.getPostHeader(extendInfo));
			Map result = this.getResultMap(responseBody);
			if (("0").equals(result.get("status") + "")) {
				response.setCode(TransportOrderResponse.SUCCESS);
				response.setTrackingNo(result.get("billCode") + "");
			} else {
				response.setCode(TransportOrderResponse.FAIL);
				response.setErrorMsg(result.get("message") + "");
			}
		} catch (Exception e) {
			logger.info("CxcTransportHandler code={},intefaceUrl={},Exception={}", getCode(), extendInfo.getInterfaceUrl(), e);
		} finally {
			logService.addLog(getCode(), orderRequest.getOrderNo(), extendInfo.getInterfaceUrl(), requestBody, responseBody);
		}

		return response;
	}

	private CxcContent getTransportContent(TransportOrderRequest orderRequest, ShippingExtend extendInfo) {

		CxcContent cxcContent = new CxcContent();
		OrderLinkDto orderLinkDto = orderRequest.getOrderLinkDto();

		// cxcContent.setBdId(orderRequest.getTrackNo());//运单ID
		cxcContent.setBdCode(orderRequest.getTrackNo());// 运单编号
		// cxcContent.setCmId(12312L);// TODO 测试环境
		// cxcContent.setCmId(105465L);//TODO 正式环境
		// cxcContent.setCmCode("001008");// TODO 测试环境
		cxcContent.setCmCode("98171006");// TODO 正式环境
		cxcContent.setBdShipmentno(orderRequest.getOrderNo());// 批号，与运单号一致
		cxcContent.setBdCaddress(this.getDetailAddress(orderLinkDto));// 详细地址
		cxcContent.setBdConsigneename(this.getReviceFrontName(orderLinkDto));// 收件联系人
		cxcContent.setBdConsigneephone(orderLinkDto.getTelphone());// 收件人电话
		cxcContent.setBdWeidght(1d);// 重量
		cxcContent.setCreatedate(orderRequest.getOrderDate());// 订单时间
		cxcContent.setBdGoodsnum(1);// 订单数量
		cxcContent.setBdPackageno(orderRequest.getTrackNo());// 包号，与运单编号同
		cxcContent.setBdProductprice(orderRequest.getOrderAmount());// 金额
		cxcContent.setBdPurprice(orderRequest.getOrderAmount());// 代收货款

		return cxcContent;
	}

	@Override
	protected void validate(TransportOrderRequest orderRequest, ShippingExtend extendInfo) {
		Assert.notNull(extendInfo, "寄件信息不能为空");
		Assert.notNull(orderRequest, "订单信息不能为空");
		OrderLinkDto orderLinkDto = orderRequest.getOrderLinkDto();

		Assert.hasText(orderRequest.getOrderNo(), "订单号不能为空");
		Assert.hasText(orderRequest.getTrackNo(), "运单编号不能为空");
		Assert.notNull(orderRequest.getOrderDate(), "创建时间不能为空");
		Assert.notNull(orderRequest.getOrderAmount(), "订单金额不能为空");

		Assert.notNull(orderLinkDto.getProvince(), "收件人省不能为空");
		Assert.notNull(orderLinkDto.getCity(), "收件人市不能为空");
		Assert.notNull(orderLinkDto.getArea(), "收件人区不能为空");
		Assert.notNull(orderLinkDto.getAddress(), "收件人详细地址不能为空");
		Assert.notNull(orderLinkDto.getFirstName(), "收件人姓不能为空");
		Assert.notNull(orderLinkDto.getLastName(), "收件人名不能为空");
		Assert.notNull(orderLinkDto.getTelphone(), "收件人电话不能为空");

		Assert.hasText(extendInfo.getAccount(), "寄件公司账号不能为空");
		Assert.hasText(extendInfo.getMd5Key(), "寄件公司密码key不能为空");
		Assert.hasText(extendInfo.getInterfaceUrl(), "下单请求地址不能为空");
		Assert.notNull(orderRequest.getPayState(), "支付状态不能为空");
	}

	private Map<String, String> getPostHeader(ShippingExtend extendInfo) {
		Map<String, String> headers = new HashMap<String, String>();
		headers.put("content-Type", "application/x-www-form-urlencoded");
		return headers;
	}

	/**
	 * 获取返回结果
	 * @param str
	 * @return
	 */
	private Map getResultMap(String str) {
		Map result = JsonUtils.jsonToLinkedMap(str, Map.class);
		List list = (List) result.get("result");
		LinkedHashMap<String, Object> map = (LinkedHashMap<String, Object>) list.get(0);
		return map;
	}

	@Override
	public HandlerTypeEnum getCode() {
		return HandlerTypeEnum.CXC;
	}

}
