//package com.stosz.tms.service.transport.impl;//package com.stosz.tms.service.transport.impl;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.stosz.tms.dto.OrderLinkDto;
//import com.stosz.tms.dto.TransportOrderRequest;
//import com.stosz.tms.dto.TransportOrderResponse;
//import com.stosz.tms.model.PhilippineXhContent;
//import com.stosz.tms.model.ShippingExtend;
//import com.stosz.tms.model.YiTaiContent;
//import com.stosz.tms.service.transport.AbstractTransportHandler;
//import com.stosz.tms.utils.DesUtil;
//import com.stosz.tms.utils.HttpClientUtils;
//import com.stosz.tms.utils.JsonUtils;
//import org.apache.http.entity.ContentType;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.util.Assert;
//
//import java.math.BigDecimal;
//import java.util.Map;
//
///**
// * 亿泰 物流接口,废弃不接
// * json传递
// * @author xiepengcheng
// * @version [1.0,2017年12月5日]
// */
//public class YiTaiTransportHandler extends AbstractTransportHandler {
//
//	private final Logger logger = LoggerFactory.getLogger(getClass());
//
//	@Override
//	public TransportOrderResponse transportPlaceOrder(TransportOrderRequest orderRequest, ShippingExtend shippingExtend) throws Exception {
//
//		YiTaiContent yiTaiContent = new YiTaiContent();
//        yiTaiContent = getTransportContent(orderRequest,shippingExtend);
//
//		ObjectMapper objectMapper = new ObjectMapper();
//		// 转为jsonStr
//		String data = objectMapper.writeValueAsString(philippineXhContent);
//
//		// Content-Type：application/json
//		String responseBody = HttpClientUtils.doPost(shippingExtend.getInterfaceUrl(), data, ContentType.APPLICATION_JSON, "UTF-8");
//
//		Map result = JsonUtils.jsonToLinkedMap(responseBody, Map.class);
//
//		TransportOrderResponse response = new TransportOrderResponse();
//		if (("true").equals(result.get("success"))) {
//			response.setCode(TransportOrderResponse.SUCCESS);
//			response.setTrackingNo(result.get("data") + "");
//		} else {
//			response.setCode(TransportOrderResponse.FAIL);
//			response.setErrorMsg(result.get("error") + "");
//		}
//		return response;
//	}
//
//	private YiTaiContent getTransportContent(TransportOrderRequest orderRequest,ShippingExtend extendInfo) {
//
//        YiTaiContent yiTaiContent = new YiTaiContent();
//
//		OrderLinkDto orderLinkDto = orderRequest.getOrderLinkDto();
//
//        yiTaiContent.setBuyerid("123");//TODO ?
//        yiTaiContent.setConsignee_address(orderLinkDto.getAddress());//收件地址
//        yiTaiContent.setConsignee_city("城市编码");//TODO 城市代码,由亿泰提供文档
//
//		BigDecimal amount = orderRequest.getOrderAmount().setScale(3, BigDecimal.ROUND_HALF_DOWN);
//		if (orderRequest.getPayState().equals(0)) {
//			philippineXhContent.setCODAmount(amount + "");// 代收货款
//		}
//		philippineXhContent.setDeclaredValue(orderRequest.getOrderAmount() + "");// 申报货值
//		philippineXhContent.setRemark(orderRequest.getRemark());// 备注
//
//		String token = this.getSign(philippineXhContent,extendInfo);
//
//		philippineXhContent.setToken(token);// TODO 令牌
//
//		return philippineXhContent;
//	}
//
//	@Override
//	public boolean validate(TransportOrderRequest orderRequest,ShippingExtend extendInfo) {
//		return false;
//	}
//
//	/**
//	 * 生成令牌
//	 * @param philippineXhContent
//	 * @return
//	 */
//	private String getSign(PhilippineXhContent philippineXhContent,ShippingExtend shippingExtend) {
//		Assert.isNull(philippineXhContent, "传入的参数philippineXHContent为空");
//		DesUtil desUtil = new DesUtil(shippingExtend.getMd5Key());
//
//		// TODO 文档缺乏对ticket的解释,待确定
//
//		String ticket = "1111111";
//		try {
//			String token = desUtil.encryption(philippineXhContent.getWaybillNumber() + ticket);
//		} catch (Exception e) {
//			logger.trace("philippineXhContent 生成令牌错误，入参{}", JsonUtils.toJson(philippineXhContent));
//		}
//		return null;
//	}
//
//	/**
//	 * 身份认证
//	 * @param uesrname
//     * @param password
//     * @param password
//	 * @return
//	 */
//	public String identify(String uesrname, String password,String url) {
//		Assert.isNull(uesrname, "运单号不能为空");
//		Assert.isNull(password, "请求地址不能为空");
//
//		String param = "?uesrname=" + uesrname + "&password="+password;
//		String result = null;
//		try {
//			result = HttpClientUtils.get(url + param, "UTF-8", null, null);
//		} catch (Exception e) {
//			logger.trace("亿泰物流 identify() 身份认证失败 ，用户名{}", uesrname);
//		}
//		return result;
//	}
//}
