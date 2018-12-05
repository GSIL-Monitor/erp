package com.stosz.tms.service.transport.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

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
import com.stosz.tms.model.api.IndonesiaContent;
import com.stosz.tms.model.api.ShippingExtend;
import com.stosz.tms.service.transport.AbstractPlaceOrderTransportHandler;
import com.stosz.tms.utils.DecimalUtils;
import com.stosz.tms.utils.HttpClientUtils;
import com.stosz.tms.utils.XStreamUtils;
import org.springframework.util.Assert;

/**
 * 印尼HY 物流接口
 * @author feiheping
 * @version [1.0,2017年11月30日]
 */
@Component
public class IndonesiaTransportHandler extends AbstractPlaceOrderTransportHandler {

	private Logger logger = LoggerFactory.getLogger(IndonesiaTransportHandler.class);

	@Override
	public TransportOrderResponse transportPlaceOrder(TransportOrderRequest orderRequest, ShippingExtend extendInfo) throws Exception {
		String requestBody = null;
		String responseBody = null;
		TransportOrderResponse response = new TransportOrderResponse(true);
		try {
			IndonesiaContent content = getTransportContent(orderRequest, extendInfo);
			String contentXml = XStreamUtils.toXmlIncludeNull("request", content);
			String sign = this.sign(contentXml, extendInfo);

			HashMap<String, Object> paramMap = new HashMap<>();
			paramMap.put("service", "tms_order_notify");
			paramMap.put("content", contentXml);
			paramMap.put("sign", sign);

			requestBody = JsonUtils.toJson(paramMap);
			responseBody = HttpClientUtils.doPost(extendInfo.getInterfaceUrl(), paramMap, "UTF-8");

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
		} catch (Exception e) {
			logger.info("IndonesiaTransportHandler code={},intefaceUrl={},Exception={}", getCode(), extendInfo.getInterfaceUrl(), e);
		} finally {
			logService.addLog(getCode(), orderRequest.getOrderNo(),extendInfo.getInterfaceUrl(), requestBody, responseBody);
		}
		return response;
	}
	

	private IndonesiaContent getTransportContent(TransportOrderRequest orderRequest, ShippingExtend extendInfo) {
		IndonesiaContent content = new IndonesiaContent();
		content.setOrderNO(orderRequest.getOrderNo());
		content.setClno("AT70803");// 客户代号 HYE 提供 #TODO
		content.setHubIn("ID-SPE");// 指定路线 HYE 提供 #TODO
		content.setDestNO("ID");// 目的地 HYE 提供 #TODO
		content.setCCPayMent("PP");// 付款方式
		content.setCODCharge(orderRequest.getOrderAmount());// 订单金额
		content.setDescrType("");// 货物类型
		content.setWeig(new BigDecimal(1));// 收费实重
		content.setPacking("WPX");// 包装类型
		content.setPcs(orderRequest.getGoodsQty());// 件数

		content.setSDTel(extendInfo.getSenderTelphone());// 寄件人电话
		content.setSDCity(extendInfo.getSenderCity());// 寄件城市
		content.setSDState(extendInfo.getSenderProvince());// 寄件省份
		content.setSDZip(extendInfo.getSenderZipcode());// 寄件邮编
		content.setSDCountry(extendInfo.getSenderCountry());// 寄件国家
		content.setSDName(extendInfo.getSender());// 寄件人姓名
		content.setSDDistrict(extendInfo.getSenderTown());// 寄件人区县

		OrderLinkDto orderLinkDto = orderRequest.getOrderLinkDto();
		content.setReCompany(orderLinkDto.getLastName());// 收件人公司
		content.setReTel(orderLinkDto.getTelphone());// 收件人电话
		content.setReAddr(orderLinkDto.getAddress());// 收件人地址
		content.setReCity(orderLinkDto.getCity());// 收件人城市
		content.setReState(orderLinkDto.getProvince());// 收件人州省
		content.setReZip(orderLinkDto.getZipcode());// 收件人邮编
		content.setReConsinee(orderLinkDto.getLastName());
		content.setReDistrict(orderLinkDto.getArea());// 收件区县

		List<TransportOrderDetail> orderDetails = orderRequest.getOrderDetails();

		// 多品名信息(中文品名,英文品名,数量,单位,单价,海关编码;)用英文逗号隔开，用封号结尾
		StringBuilder invoiceBuilder = new StringBuilder();
		for (TransportOrderDetail orderDetail : orderDetails) {
			String productTitle=orderDetail.getProductTitle();//产品标题
			String scaleTitle=orderDetail.getProductTitle();//产品标题 前端建站的产品标题
			String price = DecimalUtils.formateDecimal(orderDetail.getPrice(),"#.##");// 单价
			String hsCode = orderDetail.getForeignHsCode();// 海关编码
			Integer detailQty=orderDetail.getOrderDetailQty();
			String itemStr = StringUtil.concatBySplit(",", productTitle, scaleTitle,detailQty.toString(), "Pieces", price, hsCode);
			if (invoiceBuilder.length() != 0) {
				invoiceBuilder.append(";");
			}
			invoiceBuilder.append(itemStr);
		}
		content.setInvoiceStr(invoiceBuilder.toString());
		return content;
	}

	@Override
	protected void validate(TransportOrderRequest orderRequest, ShippingExtend extendInfo) {
		Assert.notNull(extendInfo, "寄件信息不能为空");
		Assert.hasText(extendInfo.getSenderTelphone(), "寄件电话不能为空");
		Assert.hasText(extendInfo.getSenderAddress(), "寄件地址不能为空");
		Assert.hasText(extendInfo.getSenderCity(), "寄件城市不能为空");
		Assert.hasText(extendInfo.getSenderProvince(), "寄件州省不能为空");
		Assert.hasText(extendInfo.getSenderZipcode(), "寄件邮编不能为空");
		Assert.hasText(extendInfo.getSenderCountry(), "寄件国家不能为空");
		Assert.hasText(extendInfo.getSenderContactName(), "寄件人名不能为空");
		Assert.hasText(extendInfo.getSenderTown(), "寄件区县不能为空");
		Assert.notNull(extendInfo.getInterfaceUrl(),"下单请求接口地址不能为空");

		OrderLinkDto orderLinkDto = orderRequest.getOrderLinkDto();
		Assert.notNull(orderLinkDto, "收件人信息不能为空");
		Assert.hasText(orderLinkDto.getTelphone(), "收件人电话不能为空");
		Assert.hasText(orderLinkDto.getProvince(), "收件人所在省不能为空");
		Assert.hasText(orderLinkDto.getAddress(), "收件人地址不能为空");
		Assert.hasText(orderLinkDto.getCity(), "收件人城市不能为空");
		Assert.hasText(orderLinkDto.getCompanyName(), "收件公司");
		Assert.hasText(orderLinkDto.getZipcode(), "收件邮编不能为空");
		////InvoiceStr	varchar(2000)	英文品名，数量，单价

		List<TransportOrderDetail> orderDetails = orderRequest.getOrderDetails();
		// 多品名信息(中文品名,英文品名,数量,单位,单价,海关编码;)用英文逗号隔开，用封号结尾
		for (TransportOrderDetail orderDetail : orderDetails) {
			Assert.hasText(orderDetail.getProductTitle(),"产品标题不能为空");//产品标题
			Assert.notNull(orderDetail.getPrice(),"产品单价不能为空");// 单价
			Assert.hasText(orderDetail.getForeignHsCode(),"产品海关编码不能为空");// 海关编码
			Assert.notNull(orderDetail.getOrderDetailQty(),"产品数量不能为空");
		}

		String name=null;
		if(orderLinkDto.getFirstName()!=null)
		{
			name+=orderLinkDto.getFirstName();
		}
		if(orderLinkDto.getLastName()!=null)
		{
			name+=orderLinkDto.getFirstName();
		}
		Assert.hasText(name, "收件人名能为空");
		Assert.hasText(orderLinkDto.getArea(), "收件人区县不能为空");

	}


	private String sign(String contentXml, ShippingExtend extendInfo) {
		return EncryptUtils.md5(contentXml + extendInfo.getMd5Key());
	}


	@Override
	public HandlerTypeEnum getCode() {
		return HandlerTypeEnum.INDONESIA;
	}
}
