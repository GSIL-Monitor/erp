//package com.stosz.tms.service.transport.impl;
//
//import com.stosz.plat.utils.EncryptUtils;
//import com.stosz.plat.utils.JsonUtils;
//import com.stosz.tms.constants.TmSConstants;
//import com.stosz.tms.dto.*;
//import com.stosz.tms.enums.PayTypeEnum;
//import com.stosz.tms.model.api.IndonesiaContent;
//import com.stosz.tms.model.api.ShippingExtend;
//import com.stosz.tms.model.indonesia.*;
//import com.stosz.tms.service.transport.AbstractTransportHandler;
//import com.stosz.tms.utils.DecimalUtils;
//import com.stosz.tms.utils.HttpClientUtils;
//import com.stosz.tms.utils.XStreamUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.stereotype.Component;
//
//import javax.xml.bind.JAXBContext;
//import javax.xml.bind.Marshaller;
//import java.io.StringWriter;
//import java.math.BigDecimal;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//
///**
// * 印尼zwy 物流接口 暂停不接
// * @author xiepengcheng
// * @version [1.0,2017年12月15日]
// */
//@Component
//public class IndonesiazwyTransportHandler extends AbstractTransportHandler {
//
//	private Logger logger = LoggerFactory.getLogger(IndonesiazwyTransportHandler.class);
//
//	@Override
//	public TransportOrderResponse transportPlaceOrder(TransportOrderRequest orderRequest, ShippingExtend extendInfo) throws Exception {
//		String requestBody = null;
//		String responseBody = null;
//		TransportOrderResponse response = new TransportOrderResponse(true);
//		try {
//			IndonesiaContent content = this.getTransportContent(orderRequest, extendInfo);
//			String contentXml = XStreamUtils.toXmlIncludeNull("request", content);
//			String sign = this.sign(contentXml, extendInfo);
//
//			HashMap<String, Object> paramMap = new HashMap<>();
//			paramMap.put("service", "tms_order_notify");
//			paramMap.put("content", contentXml);
//			paramMap.put("sign", sign);
//
//			requestBody = JsonUtils.toJson(paramMap);
//			responseBody = HttpClientUtils.doPost(extendInfo.getInterfaceUrl(), paramMap, "UTF-8");
//
//			Parameter<String, Object> parameter = XStreamUtils.xmlToMap(responseBody, null);
//
//			String code = parameter.getString("is_success");
//			if ("T".equals(code)) {
//				response.setCode(TransportOrderResponse.SUCCESS);
//				String trackingNo = parameter.getString("jobno");
//				response.setTrackingNo(trackingNo);
//			} else {
//				response.setCode(TransportOrderResponse.FAIL);
//				String error = parameter.getString("error");
//				response.setErrorMsg(error);
//			}
//		} catch (Exception e) {
//			logger.info("IndonesiaTransportHandler code={},intefaceUrl={},Exception={}", getCode(), extendInfo.getInterfaceUrl(), e);
//		} finally {
//			logService.addLog(getCode(), extendInfo.getInterfaceUrl(), requestBody, responseBody);
//		}
//		return response;
//	}
//
//
//	private String getTransportContent(TransportOrderRequest orderRequest, ShippingExtend extendInfo) {
//		String xmlstr = null;
//		AccessRequest accessRequest = new AccessRequest();
//		Orders orders = new Orders();
//		List<Order> orderList = new ArrayList<>();
//		Items items = new Items();
//		List<Item> itemList = new ArrayList<>();
//		try{
//			OrderLinkDto orderLinkDto = orderRequest.getOrderLinkDto();
//
//			accessRequest.setUsername(extendInfo.getAccount());//username
//			accessRequest.setPassword(extendInfo.getMd5Key());//password
//
//			Order order = new Order();
//			order.setOrderNo(orderRequest.getOrderNo());//订单号
//			order.setDesCity(orderLinkDto.getCity());//目的城市名
//			order.setDesState(orderLinkDto.getProvince());//目的省
//			order.setDesSreet(orderLinkDto.getAddress());//目的街道名
//			order.setAgent();
//
//			for(TransportOrderDetail detail:orderRequest.getOrderDetails()){
//
//			}
//
//
//			StringWriter writer = new StringWriter();
//			JAXBContext jc = JAXBContext.newInstance(Order.class);
//			Marshaller ma = jc.createMarshaller();
//			ma.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
//			// 去掉生成xml的默认报文头
//			ma.setProperty(Marshaller.JAXB_FRAGMENT, true);
//			ma.marshal(order, writer);
//			StringBuilder sb = new StringBuilder();
//			sb.append(HEAD);
//			sb.append(writer.toString());
//			sb.append(END);
//			String returnStr = sb.toString().replace("<Cargos>","").replace("</Cargos>","");
//			return returnStr;
//	}
//
//	@Override
//	protected void validate(TransportOrderRequest orderRequest, ShippingExtend extendInfo) {
//	}
//
//	private String sign(String contentXml, ShippingExtend extendInfo) {
//		return EncryptUtils.md5(contentXml + extendInfo.getMd5Key());
//	}
//
//
//	@Override
//	public String getCode() {
//		return TmSConstants.INDONESIAZWY;
//	}
//}
