package com.stosz.tms.service.transport.impl;//package com.stosz.tms.service.transport.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stosz.tms.dto.OrderLinkDto;
import com.stosz.tms.dto.TransportOrderDetail;
import com.stosz.tms.dto.TransportOrderRequest;
import com.stosz.tms.dto.TransportOrderResponse;
import com.stosz.tms.enums.HandlerTypeEnum;
import com.stosz.tms.model.api.NimContent;
import com.stosz.tms.model.api.NimDetail;
import com.stosz.tms.model.api.ShippingExtend;
import com.stosz.tms.service.transport.AbstractPlaceOrderTransportHandler;
import com.stosz.tms.utils.HttpClientUtils;
import com.stosz.tms.utils.JsonUtils;
import org.springframework.util.Assert;

/**
 * Nim 物流接口 调通
 * @author xiepengcheng
 * @version [1.0,2017年12月6日]
 */
@Component
public class NimTransportHandler extends AbstractPlaceOrderTransportHandler {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	public TransportOrderResponse transportPlaceOrder(TransportOrderRequest orderRequest, ShippingExtend shippingExtend) throws Exception {
		TransportOrderResponse response = new TransportOrderResponse(true);
		Map<String,Object> paramMap = new HashMap();
		String responseBody = null;

		try{
			NimContent nimContent = new NimContent();
			nimContent = getTransportContent(orderRequest,shippingExtend);
			ObjectMapper objectMapper = new ObjectMapper();
			String origindata = objectMapper.writeValueAsString(nimContent);
			String data = origindata.replace("longitude","long");
			paramMap.put("jsonString",data);
			responseBody = HttpClientUtils.doPost(shippingExtend.getInterfaceUrl(), paramMap,"UTF-8");
			Map result = JsonUtils.jsonToLinkedMap(responseBody, Map.class);

			if (result.containsKey("bill_no")) {
				response.setCode(TransportOrderResponse.SUCCESS);
				response.setTrackingNo(result.get("bill_no") + "");
				response.setResponseData(responseBody);
			} else {
				response.setCode(TransportOrderResponse.FAIL);
				response.setErrorMsg(result.get("Error Code") + "");
				response.setResponseData(responseBody);
			}
		}catch (Exception e){
			logger.info("NimTransportHandler code={},intefaceUrl={},Exception={}", getCode(), shippingExtend.getInterfaceUrl(), e);
		}finally {
			logService.addLog(getCode(),orderRequest.getOrderNo(), shippingExtend.getInterfaceUrl(),new ObjectMapper().writeValueAsString(paramMap), responseBody);
		}

		return response;
	}

	/**
	 * 封装请求物流接口参数
	 * @param orderRequest
	 * @param extendInfo
	 * @return
	 */
	private NimContent getTransportContent(TransportOrderRequest orderRequest,ShippingExtend extendInfo) {

		NimContent nimContent = new NimContent();
		OrderLinkDto orderLinkDto = orderRequest.getOrderLinkDto();
		nimContent.setUser_code("PASMIER");//TODO 测试、正式用户编号
		nimContent.setOrder_no(orderRequest.getOrderNo());//订单号

		nimContent.setRec_name(orderLinkDto.getLastName() + orderLinkDto.getFirstName());//收件人
		nimContent.setAddress(orderLinkDto.getAddress());//收件地址
		nimContent.setZipcode(20000L);//TODO 邮编固定
		nimContent.setRemark(orderRequest.getRemark());//备注
		nimContent.setMobile(orderLinkDto.getTelphone());//电话

		BigDecimal amount = orderRequest.getOrderAmount().setScale(0, BigDecimal.ROUND_HALF_DOWN);

		nimContent.setAmount_cod(amount);//订单总金额
		nimContent.setLat("");
		nimContent.setLongitude("");

		List<TransportOrderDetail> orderDetails = orderRequest.getOrderDetails();

		List<NimDetail> nimDetails = orderDetails.stream().map(item -> {
			String product_name = item.getProductTitle();
			Integer qty = item.getOrderDetailQty();//数量

			NimDetail nimDetail = new NimDetail();
			nimDetail.setProduct_name(product_name);//产品名
			nimDetail.setSize_code("STD2017_01_SS");//包装规则，和php及产品确定写死
			nimDetail.setQty(qty+"");//数量
			nimDetail.setUnit("box");//单位
			return nimDetail;
		}).collect(Collectors.toList());

		nimContent.setListProduct(nimDetails);

		return nimContent;
	}

	public void validate(TransportOrderRequest orderRequest,ShippingExtend extendInfo) {
		Assert.notNull(extendInfo, "寄件信息不能为空");
		Assert.notNull(extendInfo.getInterfaceUrl(),"下单请求接口地址不能为空");
		Assert.notNull(orderRequest, "订单信息不能为空");
		OrderLinkDto orderLinkDto = orderRequest.getOrderLinkDto();

		Assert.notNull(orderRequest.getOrderNo(), "订单号不能为空");
		Assert.notNull(orderRequest.getOrderAmount(), "订单金额不能为空");
		Assert.notNull(orderRequest.getGoodsQty(), "订单数量不能为空");

		Assert.notNull(orderLinkDto.getProvince(), "收件人省不能为空");
		Assert.notNull(orderLinkDto.getCity(), "收件人市不能为空");
		Assert.notNull(orderLinkDto.getArea(), "收件人区不能为空");
		Assert.notNull(orderLinkDto.getAddress(), "收件人详细地址不能为空");
		Assert.notNull(orderLinkDto.getFirstName(), "收件人姓不能为空");
		Assert.notNull(orderLinkDto.getLastName(), "收件人名不能为空");
		Assert.notNull(orderLinkDto.getTelphone(), "收件人电话不能为空");

		List<TransportOrderDetail> orderDetails = orderRequest.getOrderDetails();
		// 多品名信息(中文品名,英文品名,数量,单位,单价,海关编码;)用英文逗号隔开，用封号结尾
		for (TransportOrderDetail orderDetail : orderDetails) {
			Assert.hasText(orderDetail.getProductTitle(),"商品名称不能为空");
			Assert.notNull(orderDetail.getOrderDetailQty(),"明细数量不能为空");
		}

	}

	@Override
	public HandlerTypeEnum getCode() {
		return HandlerTypeEnum.NIM;
	}

}
