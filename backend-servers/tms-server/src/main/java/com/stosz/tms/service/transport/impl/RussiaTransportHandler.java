package com.stosz.tms.service.transport.impl;

import com.stosz.plat.utils.*;
import com.stosz.tms.dto.OrderLinkDto;
import com.stosz.tms.dto.TransportOrderDetail;
import com.stosz.tms.dto.TransportOrderRequest;
import com.stosz.tms.dto.TransportOrderResponse;
import com.stosz.tms.enums.HandlerTypeEnum;
import com.stosz.tms.model.api.ShippingExtend;
import com.stosz.tms.model.russia.*;
import com.stosz.tms.model.russia.Package;
import com.stosz.tms.service.transport.AbstractPlaceOrderTransportHandler;
import com.stosz.tms.utils.HttpClientUtils;
import com.stosz.tms.utils.XStreamUtils;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.io.StringReader;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 俄罗斯物流接口 （停止合作）
 * @author feiheping
 * @version [1.0,2017年12月11日]
 */
@Component
public class RussiaTransportHandler extends AbstractPlaceOrderTransportHandler {

	private  static final Logger logger = LoggerFactory.getLogger(RussiaTransportHandler.class);

	private static final String ERROR_KEY = "error";

	@Override
	public TransportOrderResponse transportPlaceOrder(TransportOrderRequest orderRequest, ShippingExtend extendInfo) throws Exception {
		TransportOrderResponse orderResponse = new TransportOrderResponse(true);
		String requestBody = null;
		String responseBody = null;
		try {
			DeliveryRequest deliveryRequest = transferRussiaContent(orderRequest, extendInfo);
			String requestXml = XStreamUtils.jaxbToXml(deliveryRequest, false);
			HashMap<String, Object> paramMap = new HashMap<>();
			paramMap.put("xml_request", requestXml);

			requestBody = JsonUtils.toJson(paramMap);
			responseBody = HttpClientUtils.doPost(extendInfo.getInterfaceUrl(), paramMap, "UTF-8");
			HashMap<String, String> responseMap = this.resolveResponse(responseBody);
			if (StringUtils.hasText(responseMap.get("trackNumber"))) {
				orderResponse.setCode(TransportOrderResponse.SUCCESS);
				orderResponse.setTrackingNo(responseMap.get("trackNumber"));
			} else {
				orderResponse.setCode(TransportOrderResponse.FAIL);
				orderResponse.setErrorMsg(responseMap.get(ERROR_KEY));
			}
		} catch (Exception e) {
			logger.error("RussiaTransportHandler code={},intefaceUrl={},orderNo={},Exception={}", getCode(), extendInfo.getInterfaceUrl(), orderRequest.getOrderNo(), e);
		} finally {
			logService.addLog(getCode(),orderRequest.getOrderNo(), extendInfo.getInterfaceUrl(), requestBody, responseBody);
		}
		return orderResponse;
	}

	private DeliveryRequest transferRussiaContent(TransportOrderRequest orderRequest, ShippingExtend extendInfo) {
		DeliveryRequest deliveryRequest = new DeliveryRequest();
		deliveryRequest.setNumber("1");// 商户提供的传单号可以为1不重要的
		deliveryRequest.setDate(orderRequest.getOrderDate());// 订单日期
		deliveryRequest.setAccount(extendInfo.getAccount());// 商户对接的账户名
		String secure = this.getSecure(orderRequest, extendInfo);// 签名
		deliveryRequest.setSecure(secure);
		deliveryRequest.setOrderCount(1);// 有几笔订单,现在只有一笔订单

		OrderLinkDto orderLinkDto = orderRequest.getOrderLinkDto();
		Order order = new Order();
		order.setNumber(orderRequest.getOrderNo());// 订单号
		order.setSendCityCode(12683);// #TODO //始发城市ID
		order.setSendCityPostCode(extendInfo.getSenderZipcode()); // 始发城市邮编
		order.setRecCityPostCode(orderLinkDto.getZipcode());// 目的城市邮编
		order.setRecipientName(orderLinkDto.getFirstName() + orderLinkDto.getLastName());// 收件人全名
		order.setRecipientEmail(orderLinkDto.getEmail());// 收件人邮箱
		order.setPhone(orderLinkDto.getTelphone());// 收件人电话号码
		order.setTariffTypeCode(246);// 运率代码 //246 中国快递 库到门
		order.setDeliveryRecipientCost("0");// 快递员从收件人收取的运费
		order.setRecipientCurrency(orderRequest.getCurrencyCode());// 收件人的货币代码 RUB俄罗斯的卢布
		order.setComment("");
		order.setSellerName("");
		order.setItemsCurrency("CNY");// 投保价的货币代码
		order.setAddressAttr(this.getDetailAddress(orderLinkDto));

		Address address = new Address();
		address.setStreet(getDetailAddress(orderLinkDto));
		address.setHouse("-");
		address.setFlat("-");
		order.setAddress(address);

		Package parcel = new Package();
		parcel.setNumber("1");// 包裹序号1、2、3等等
		if (ArrayUtils.isNotEmpty(orderRequest.getOrderDetails())) {
			parcel.setBarCode(orderRequest.getOrderDetails().get(0).getSku());// 条码
		}
		parcel.setWeight("1");// 总量

		List<Item> parcelItems = orderRequest.getOrderDetails().stream().map(detail -> {
			Item item = new Item();
			item.setWareKey(detail.getSku());// 商品SKU
			item.setCost(detail.getPrice());// 单价
			item.setPayment(detail.getTotalAmount());// 明细总价 到付金额 预付的话=0
			item.setAmount(detail.getOrderDetailQty());// 数量
			item.setWeight(0);
			return item;
		}).collect(Collectors.toList());

		parcel.setItemList(parcelItems);
		order.setPackageItem(parcel);
		deliveryRequest.setOrder(order);
		return deliveryRequest;
	}

	public String getSecure(TransportOrderRequest request, ShippingExtend shippingExtend) {
		String date = DateUtils.format(request.getOrderDate(), "yyyy-MM-dd HH:mm:ss");
		String original = StringUtil.concat(date, "&", shippingExtend.getMd5Key());
		return EncryptUtils.md5(original);
	}

	@Override
	public HandlerTypeEnum getCode() {
		return HandlerTypeEnum.RUSSIA;
	}

	@Override
	protected void validate(TransportOrderRequest orderRequest, ShippingExtend extendInfo) {
//		super.validate(orderRequest, extendInfo);
		Assert.notNull(orderRequest, "订单信息不能为空");
		Assert.notNull(extendInfo, "发件信息不能为空");
		OrderLinkDto orderLinkDto = orderRequest.getOrderLinkDto();

		Assert.notNull(orderRequest.getOrderDate(), "订单日期不能为空");
//		Assert.notNull(orderRequest.getCurrencyCode(), "收件人货币代码不能为空");接口默认为rub

		Assert.hasText(extendInfo.getAccount(), "接口账号不能为空");
		Assert.hasText(extendInfo.getMd5Key(), "接口Md5Key不能为空");
		Assert.hasText(extendInfo.getSenderEmail(), "发件人邮编不能为空");

		Assert.hasText(orderLinkDto.getZipcode(), "收件人邮编不能为空");
		Assert.hasText(orderLinkDto.getEmail(), "收件人email不能为空");
		Assert.hasText(orderLinkDto.getTelphone(), "收件人电话不能为空");
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
		Assert.notNull(extendInfo.getInterfaceUrl(),"下单请求接口地址不能为空");

		List<TransportOrderDetail> orderDetails = orderRequest.getOrderDetails();
		// 多品名信息(中文品名,英文品名,数量,单位,单价,海关编码;)用英文逗号隔开，用封号结尾
		for (TransportOrderDetail orderDetail : orderDetails) {
			Assert.hasText(orderDetail.getSku(),"明细sku不能为空");
			Assert.notNull(orderDetail.getPrice(),"明细sku不能为空");
			Assert.notNull(orderDetail.getTotalWeight(),"明细总价不能为空");
			Assert.notNull(orderDetail.getOrderDetailQty(),"明细数量不能为空");
		}

	}

	private HashMap<String, String> resolveResponse(String response) {
		HashMap<String, String> resultMap = new HashMap<>();
		SAXReader saxReader = new SAXReader();
		try {
			Document doc = saxReader.read(new StringReader(response));
			List<Element> nodes = doc.selectNodes("/response/Order");
			for (Element element : nodes) {
				Attribute numberAttr = element.attribute("Number");
				Attribute trackAttr = element.attribute("DispatchNumber");
				Attribute errorAttr = element.attribute("ErrorCode");
				Attribute messAttr = element.attribute("Msg");
				if (numberAttr != null && trackAttr != null) {
					resultMap.put("orderNumber", numberAttr.getStringValue().trim());
					resultMap.put("trackNumber", trackAttr.getStringValue().trim());
				} else if (numberAttr != null && errorAttr != null && messAttr != null) {
					if (!resultMap.containsKey(ERROR_KEY)) {
						resultMap.put(ERROR_KEY, errorAttr.getStringValue() + ":" + messAttr.getStringValue());
					} else {
						String errorMsg = StringUtil.concat(resultMap.get(ERROR_KEY), "|", errorAttr.getStringValue(), ":", messAttr.getStringValue());
						resultMap.put(ERROR_KEY, errorMsg);
					}
				} else if (messAttr != null) {
					resultMap.put("msg", messAttr.getStringValue());
				}
			}
		} catch (DocumentException e) {
			logger.error("RussiaTransportHandler resolveResponse() response={},Exception={}", response, e);
		}
		return resultMap;
	}

	public static void main(String[] args) {
		HashMap<String, String> hashMap = new RussiaTransportHandler().resolveResponse(FileUtils.readFile("D:/3.txt"));
		logger.info(JsonUtils.toJson(hashMap));

	}

}
