package com.stosz.tms.service.transport.impl;

import com.stosz.plat.utils.DateUtils;
import com.stosz.plat.utils.StringUtil;
import com.stosz.plat.utils.StringUtils;
import com.stosz.tms.dto.OrderLinkDto;
import com.stosz.tms.dto.TransportOrderDetail;
import com.stosz.tms.dto.TransportOrderRequest;
import com.stosz.tms.dto.TransportOrderResponse;
import com.stosz.tms.enums.HandlerTypeEnum;
import com.stosz.tms.model.api.CxcContent;
import com.stosz.tms.model.api.ShippingExtend;
import com.stosz.tms.model.imile.ImileRequestDto;
import com.stosz.tms.service.track.impl.DfTrackHandler;
import com.stosz.tms.service.transport.AbstractPlaceOrderTransportHandler;
import com.stosz.tms.utils.HttpClientUtils;
import com.stosz.tms.utils.JsonUtils;
import com.stosz.tms.utils.MD5Util;
import org.apache.http.entity.ContentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.*;

/**
 * 阿联酋imile下单
 * @version [1.0,201年12月1日]
 */
@Component
public class ImileTransportHandler extends AbstractPlaceOrderTransportHandler {
	private Logger logger = LoggerFactory.getLogger(ImileTransportHandler.class);

	@Override
	public TransportOrderResponse transportPlaceOrder(TransportOrderRequest orderRequest, ShippingExtend extendInfo) throws Exception {
		TransportOrderResponse response = new TransportOrderResponse(true);
		Map<String,Object> paramMap = new LinkedHashMap<>();
		String responseBody = null;
		try {
			paramMap = this.getTransportContent(orderRequest,extendInfo);
			responseBody = HttpClientUtils.doPost(extendInfo.getInterfaceUrl(), paramMap,"UTF-8");
			Map result = this.getResultMap(responseBody);
			if (("4").equals(result.get("stauts") + "")) {
				response.setCode(TransportOrderResponse.SUCCESS);
				response.setTrackingNo(((LinkedHashMap)result.get("data")).get("shipping_number")+"");
			} else {
				response.setCode(TransportOrderResponse.FAIL);
				response.setErrorMsg(result.get("msg") + "");
			}
		} catch (Exception e) {
			logger.trace("ImileTransportHandler 错误 orderRequest ={}, extendInfo={},intefaceUrl={},responseBody = {}，Exception={}", orderRequest,extendInfo, extendInfo.getInterfaceUrl(),responseBody, e);
		} finally {
			logService.addLog(getCode(), orderRequest.getOrderNo(), extendInfo.getInterfaceUrl(), JsonUtils.toJson(paramMap), responseBody);
		}
		return response;
	}

	private Map<String,Object> getTransportContent(TransportOrderRequest orderRequest, ShippingExtend extendInfo) {
		StringBuilder sb = new StringBuilder();
		sb.append("[{");

		Map<String,Object> paramMap = new LinkedHashMap<>();
		List<TransportOrderDetail> detailList = orderRequest.getOrderDetails();
		OrderLinkDto orderLinkDto = orderRequest.getOrderLinkDto();

		sb.append("\"logisticsId\":\"");
		sb.append(orderRequest.getOrderNo()+"\",");
		sb.append("\"dicCode\":\"");
		sb.append(orderRequest.getOrderNo()+"\",");
		sb.append("\"orderCreateTime\":\"");
		sb.append(DateUtils.format(orderRequest.getOrderDate(),"yyyy-MM-dd HH:mm:ss")+"\",");
		sb.append("\"senderName\":\"");
		sb.append(extendInfo.getSenderContactName()+"\",");
		sb.append("\"senderCompany\":\"");
		sb.append(extendInfo.getSenderContactName()+"\",");
		sb.append("\"senderAddress\":\"");
		sb.append(extendInfo.getSenderAddress()+"\",");
		sb.append("\"senderMobile\":\"");
		sb.append(extendInfo.getSenderTelphone()+"\",");
		sb.append("\"receiverName\":\"");
		sb.append(StringUtil.concat(orderLinkDto.getFirstName(),orderLinkDto.getLastName())+"\",");
		sb.append("\"receiverCompany\":\"");
		sb.append(StringUtil.concat(orderLinkDto.getFirstName(),orderLinkDto.getLastName())+"\",");
		sb.append("\"receiverCounty\":\"");
		sb.append(orderLinkDto.getArea()+"\",");
		sb.append("\"receiverCity\":\"");
		sb.append(orderLinkDto.getCity()+"\",");
		sb.append("\"receiverProvince\":\"");
		sb.append(orderLinkDto.getProvince()+"\",");
		sb.append("\"receiverAddress\":\"");
		sb.append(orderLinkDto.getAddress()+"\",");
		sb.append("\"receiverMobile\":\"");
		sb.append(orderLinkDto.getTelphone()+"\",");
		sb.append("\"pieceNumber\":\"");
		sb.append("1"+"\",");
		sb.append("\"packageWeight\":\"");
		sb.append("1"+"\",");
		sb.append("\"paymentType\":\"");
		sb.append("月结"+"\",");
		if(orderRequest.getPayState()==0){
			sb.append("\"goodsPayment\":\"");
			sb.append(orderRequest.getOrderAmount()+"\",");
		}
		String orderTitle = "";
		for(TransportOrderDetail detail:detailList){
			orderTitle = orderTitle + detail.getProductTitle();
		}
		sb.append("\"goodsName\":\"");
		sb.append(orderTitle+"\",");
		sb.append("\"orderSource\":\"");
		sb.append("BUGUNIAO TECHNOLOGY(HK) CO.,LIMITED"+"\",");
		sb.append("\"receiverCountry\":\"");
		sb.append("UAE"+"\"");
		sb.append("}]");

		String logistics_interface = sb.toString();
		String sign = MD5Util.MD5Encode(StringUtil.concat(logistics_interface,extendInfo.getMd5Key()),"UTF-8");
		paramMap.put("logistics_interface",logistics_interface);
		paramMap.put("data_digest",sign);
		return paramMap;
	}

	@Override
	protected void validate(TransportOrderRequest orderRequest, ShippingExtend extendInfo) {
		Assert.notNull(extendInfo, "寄件信息不能为空");
		Assert.notNull(orderRequest, "订单信息不能为空");

		OrderLinkDto orderLinkDto = orderRequest.getOrderLinkDto();
		Assert.notNull(orderRequest.getOrderDetails(), "订单明细不能为空");

		Assert.hasText(orderRequest.getOrderNo(), "订单号不能为空");
		if(orderRequest.getOrderNo().length()>20){
			throw new IllegalArgumentException("订单号长度不能超过20个字符");
		}
		Assert.notNull(orderRequest.getOrderDate(), "创建时间不能为空");
		Assert.notNull(orderRequest.getOrderAmount(), "订单金额不能为空");
		Assert.notNull(orderRequest.getGoodsQty(), "订单数量不能为空");
		Assert.notNull(orderRequest.getPayState(), "订单支付状态不能为空");
		if(orderRequest.getPayState()==0){
			Assert.notNull(orderRequest.getOrderAmount(), "订单金额不能为空");
		}
		for(TransportOrderDetail detail:orderRequest.getOrderDetails()){
			Assert.notNull(detail.getProductTitle(), "订单标题不能为空");
		}

		Assert.hasText(extendInfo.getSenderContactName(), "寄件人姓名不能为空");
		Assert.hasText(extendInfo.getSenderAddress(), "寄件人信息地址不能为空");
		Assert.hasText(extendInfo.getSenderTelphone(), "寄件人电话不能为空");
		Assert.hasText(extendInfo.getInterfaceUrl(), "下单请求地址不能为空");
		Assert.hasText(extendInfo.getMd5Key(), "下单请求秘钥不能为空");
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
		Assert.notNull(orderLinkDto.getTelphone(), "收件人电话不能为空");
		Assert.notNull(orderLinkDto.getProvince(), "收件人省不能为空");
		Assert.notNull(orderLinkDto.getCity(), "收件人市不能为空");
		Assert.notNull(orderLinkDto.getArea(), "收件人区不能为空");
		Assert.notNull(orderLinkDto.getAddress(), "收件人详细地址不能为空");

	}

	/**
	 * 获取返回结果
	 * @param str
	 * @return
	 */
	private Map getResultMap(String str) {
		Map result = JsonUtils.jsonToLinkedMap(str, LinkedHashMap.class);
		return result;
	}

	@Override
	public HandlerTypeEnum getCode() {
		return HandlerTypeEnum.IMILE;
	}

}
