package com.stosz.tms.service.transport.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import com.stosz.plat.utils.DateUtils;
import com.stosz.plat.utils.StringUtils;
import com.stosz.tms.dto.OrderLinkDto;
import com.stosz.tms.dto.TransportOrderDetail;
import com.stosz.tms.dto.TransportOrderRequest;
import com.stosz.tms.dto.TransportOrderResponse;
import com.stosz.tms.enums.HandlerTypeEnum;
import com.stosz.tms.model.api.ShippingExtend;
import com.stosz.tms.model.ght.GhtRequestDTO;
import com.stosz.tms.model.ght.Order;
import com.stosz.tms.service.transport.AbstractPlaceOrderTransportHandler;
import com.stosz.tms.utils.Base64;
import com.stosz.tms.utils.HttpClientUtils;
import com.stosz.tms.utils.JsonUtils;
import com.stosz.tms.utils.MD5Util;

/**
 * 柬埔寨GHT 物流接口 调通,预先导入运单号 (停止合作)
 * @author xiepengcheng
 * @version [1.0,2018年1月4日]
 */
@Component
public class CambodiaGhtTransportHandler extends AbstractPlaceOrderTransportHandler {

	private Logger logger = LoggerFactory.getLogger(CambodiaGhtTransportHandler.class);

	@Override
	public TransportOrderResponse transportPlaceOrder(TransportOrderRequest orderRequest, ShippingExtend extendInfo) throws Exception {
		TransportOrderResponse response = new TransportOrderResponse(true);
		String requestBody = null;
		String responseBody = null;
		try {
			Map<String,Object> paramMap = this.getTransportContent(orderRequest, extendInfo);
			responseBody = HttpClientUtils.doPost(extendInfo.getInterfaceUrl(),paramMap,"UTF-8");
			Map<String,Object> result = JsonUtils.jsonToObject(responseBody,LinkedHashMap.class);
			if (("1").equals(result.get("resultCode")+"")) {
				response.setCode(TransportOrderResponse.SUCCESS);	
				String trackNo = ((LinkedHashMap)result.get("result")).get("billNo")+"";
				response.setTrackingNo(trackNo);
			} else {
				response.setCode(TransportOrderResponse.FAIL);
				response.setErrorMsg(result.get("errorMsg") + "");
			}
		} catch (Exception e) {
			logger.info("CambodiaGhtTransportHandler code={},intefaceUrl={},Exception={}", getCode(), extendInfo.getInterfaceUrl(), e);
		} finally {
			logService.addLog(getCode(),orderRequest.getOrderNo(), extendInfo.getInterfaceUrl(), requestBody, responseBody);
		}

		return response;
	}

	private Map<String,Object> getTransportContent(TransportOrderRequest orderRequest, ShippingExtend extendInfo) {
		GhtRequestDTO ghtRequestDTO = new GhtRequestDTO();
		ghtRequestDTO.setRandom(StringUtils.randomCode(4));
//		ghtRequestDTO.setCardNo("YNHHZMZ1027");//测试月结账号
		ghtRequestDTO.setCardNo("100209");//正式月结账号
		ghtRequestDTO.setLan("ZH");//中文 语言
		ghtRequestDTO.setTimestamp(DateUtils.format(new Date(),"yyyy-MM-dd HH:mm:ss"));
		ghtRequestDTO.setUserId(extendInfo.getAccount());//第三方系统在同城快跑的用户标识 jianpz
		ghtRequestDTO.setCustCode(extendInfo.getAccount());//同custCode
		List<TransportOrderDetail> list = orderRequest.getOrderDetails();
		String trackNo = orderRequest.getTrackNo();//运单号
		OrderLinkDto linkDto = orderRequest.getOrderLinkDto();
		String destCont = linkDto.getFirstName()+linkDto.getLastName();//收货人
		String destTel = linkDto.getTelphone();//收货人电话
		String destCity = linkDto.getCity();//收货人城市
		String desAddr = linkDto.getAddress();//收货人地址
		BigDecimal cod = orderRequest.getOrderAmount();//金额
		List<Order> orderList = list.stream().map(item->{
			Order order = new Order();
			order.setOrderNo(trackNo);
			order.setCargo(item.getProductTitle());//名称
			order.setCargoCnt(1);//数量
			order.setWeight(new BigDecimal(1));//重量
			order.setDestCont(destCont);//收货人
			order.setDestCity(destCity);//收货人城市
			order.setDestAddr(desAddr);//收货人地址
			order.setDestTel(destTel);//收货人电话
			order.setCod(cod);//金额
			order.setSendName(extendInfo.getSenderContactName());//寄货人
			return order;
		}).collect(Collectors.toList());
		ghtRequestDTO.setOrders(orderList);
		String sign = this.getSign(ghtRequestDTO,extendInfo);
		ghtRequestDTO.setSign(sign);
		Map<String,Object> paramMap = new LinkedHashMap<>();

		paramMap.put("random",ghtRequestDTO.getRandom());
		paramMap.put("sign",ghtRequestDTO.getSign());
		paramMap.put("timestamp",ghtRequestDTO.getTimestamp());
		paramMap.put("userId",ghtRequestDTO.getUserId());
		paramMap.put("orders",JsonUtils.toJson(orderList));
		paramMap.put("lan","ZH");
		paramMap.put("cardNo",ghtRequestDTO.getCardNo());
		paramMap.put("custCode",ghtRequestDTO.getUserId());

		return paramMap;
	}

	@Override
	protected void validate(TransportOrderRequest orderRequest, ShippingExtend extendInfo) {
		Assert.notNull(extendInfo, "寄件信息不能为空");
		Assert.notNull(orderRequest, "订单信息不能为空");
		OrderLinkDto orderLinkDto = orderRequest.getOrderLinkDto();


		Assert.hasText(orderRequest.getOrderNo(), "客户编号不能为空");
		Assert.hasText(orderRequest.getTrackNo(), "运单编号不能为空");
		Assert.notNull(orderRequest.getOrderAmount(), "订单金额不能为空");

		Assert.notNull(orderLinkDto.getCity(), "收件人市不能为空");
		Assert.notNull(orderLinkDto.getAddress(), "收件人详细地址不能为空");
		Assert.notNull(orderLinkDto.getFirstName(), "收件人姓不能为空");
		Assert.notNull(orderLinkDto.getLastName(), "收件人名不能为空");
		Assert.notNull(orderLinkDto.getTelphone(), "收件人电话不能为空");

		List<TransportOrderDetail> orderDetails = orderRequest.getOrderDetails();
		// 多品名信息(中文品名,英文品名,数量,单位,单价,海关编码;)用英文逗号隔开，用封号结尾
		for (TransportOrderDetail orderDetail : orderDetails) {
			Assert.hasText(orderDetail.getProductTitle(),"商品名称不能为空");//产品标题
		}
		Assert.notNull(extendInfo.getSenderContactName(), "寄件联系人不能为空");
		
		Assert.hasText(extendInfo.getAccount(), "寄件公司账号不能为空");
		Assert.hasText(extendInfo.getMd5Key(), "寄件公司密码key不能为空");
		Assert.hasText(extendInfo.getInterfaceUrl(), "下单请求地址不能为空");
	}

	public String getSign(GhtRequestDTO ghtRequestDTO,ShippingExtend shippingExtend) {
		Map orginMap = new LinkedHashMap();
		orginMap.put("userId",ghtRequestDTO.getUserId());
		orginMap.put("custCode",ghtRequestDTO.getUserId());
		orginMap.put("random",ghtRequestDTO.getRandom());
		orginMap.put("cardNo",ghtRequestDTO.getCardNo());
		orginMap.put("timestamp",ghtRequestDTO.getTimestamp());
		orginMap.put("lan","ZH");
		orginMap.put("orders",JsonUtils.toJson(ghtRequestDTO.getOrders()));
		Set<Map.Entry<String, String>> entrySet = StringUtils.sortedmap(orginMap);
		StringBuilder sb = new StringBuilder();
		entrySet.stream().forEach(item -> {
			sb.append(item.getKey());
			sb.append("=");
			sb.append(item.getValue());
			sb.append("&");
		});
		String orgsign = sb.toString()+"key="+shippingExtend.getMd5Key();
		String sign = Base64.encode(MD5Util.md5(orgsign,"UTF-8"));
		return sign;
	}


	@Override
	public HandlerTypeEnum getCode() {
		return HandlerTypeEnum.GHT;
	}

}
