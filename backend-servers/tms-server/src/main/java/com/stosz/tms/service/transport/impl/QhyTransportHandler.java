package com.stosz.tms.service.transport.impl;


import com.stosz.plat.utils.JsonUtil;
import com.stosz.plat.utils.StringUtil;
import com.stosz.tms.dto.OrderLinkDto;
import com.stosz.tms.dto.TransportOrderDetail;
import com.stosz.tms.dto.TransportOrderRequest;
import com.stosz.tms.dto.TransportOrderResponse;
import com.stosz.tms.enums.HandlerTypeEnum;
import com.stosz.tms.external.aramex.*;
import com.stosz.tms.external.qhy.*;
import com.stosz.tms.model.api.ShippingExtend;
import com.stosz.tms.service.transport.AbstractPlaceOrderTransportHandler;
import com.stosz.tms.utils.HttpClientUtils;
import com.stosz.tms.utils.JsonUtils;
import com.stosz.tms.utils.U;
import org.apache.http.entity.ContentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 印度QHY 物流接口 配送方式待定
 * @version [1.0,2018年1月10日]
 */
@Component
public class QhyTransportHandler extends AbstractPlaceOrderTransportHandler {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	private static final String METHOD= "createOrder";//创建订单

	@Override
	public TransportOrderResponse transportPlaceOrder(TransportOrderRequest orderRequest, ShippingExtend shippingExtend) throws Exception {
		TransportOrderResponse response = new TransportOrderResponse();
		String requestBody = null;
		String responseBody = null;

		ShipmentCreationRequest shipmentCreationRequest = new ShipmentCreationRequest();
		try{
			requestBody = this.getRequestXml(orderRequest, shippingExtend);
			responseBody= HttpClientUtils.doPost(shippingExtend.getInterfaceUrl(),requestBody, ContentType.APPLICATION_XML,"UTF-8");
			Map map = U.parse(responseBody);
			String responseJson = map.get("response").toString();
			Map<String,Object> resultMap = JsonUtils.jsonToMap(responseJson, LinkedHashMap.class);
			if(("success").equalsIgnoreCase(resultMap.get("ask").toString())){
				response.setCode(TransportOrderResponse.SUCCESS);
				response.setTrackingNo(resultMap.get("order_code").toString());//运单号
			}else {
				response.setCode(TransportOrderResponse.FAIL);
				response.setErrorMsg(((LinkedHashMap)resultMap.get("Error")).get("errMessage").toString());
			}
		}catch (Exception e){
			logger.info("QhyTransportHandler code={},intefaceUrl={},Exception={}", getCode(), shippingExtend.getInterfaceUrl(), e);
		}finally {
			logService.addLog(getCode(),orderRequest.getOrderNo(), shippingExtend.getInterfaceUrl(), JsonUtils.toJson(shipmentCreationRequest), JsonUtils.toJson(responseBody));
		}
		return response;
	}

	/**
	 * 获得请求xml
	 * @param orderRequest
	 * @param shippingExtend
	 * @param
	 * @return
	 */
	private String getRequestXml(TransportOrderRequest orderRequest, ShippingExtend shippingExtend) {
		String paramsJson = this.getContent(orderRequest,shippingExtend);
		StringBuilder sb = new StringBuilder();
		sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<SOAP-ENV:Envelope xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\"\n" +
                "xmlns:ns1=\"http://www.example.org/Ec/\">\n" +
                "<SOAP-ENV:Body>\n" +
                "<ns1:callService>\n" +
                "<paramsJson>");
		sb.append(paramsJson);
		sb.append("</paramsJson><appToken>");
		sb.append(shippingExtend.getAccount());
		sb.append("</appToken><appKey>");
		sb.append(shippingExtend.getMd5Key());
		sb.append("</appKey><service>");
		sb.append(METHOD);
		sb.append("</service>\n" +
                "</ns1:callService>\n" +
                "</SOAP-ENV:Body>\n" +
                "</SOAP-ENV:Envelope>");
		return sb.toString();
	}


	@Override
	public HandlerTypeEnum getCode() {
		return HandlerTypeEnum.QHY;
	}

	private String getContent(TransportOrderRequest orderRequest, ShippingExtend shippingExtend){
		List<TransportOrderDetail> detailList = orderRequest.getOrderDetails();
		OrderLinkDto orderLinkDto = orderRequest.getOrderLinkDto();
		RequestDto requestDto = new RequestDto();
		requestDto.setReference_no(orderRequest.getOrderNo());//客户参考号,订单号
		if(orderRequest.getAreaId()==26){//TODO 印度
			requestDto.setShipping_method("PK0036");//配送方式
		}else if(orderRequest.getAreaId()==107) {//TODO 土耳其
			requestDto.setShipping_method("TRXB");//配送方式
		}else {
			throw new IllegalArgumentException("QHY物流只支持印度和土耳其区域");
		}
		requestDto.setCountry_code(orderLinkDto.getCountryCode());//收件人国家二字码
		requestDto.setOrder_weight(1f);//订单重量kg
		requestDto.setOrder_pieces(orderRequest.getGoodsQty());//外包装件数
		requestDto.setMail_cargo_type("4");//包裹申报种类，默认4
		requestDto.setInsurance_value(orderRequest.getOrderAmount().floatValue());//投保金额
		requestDto.setLength(1);
		requestDto.setWidth(1);
		requestDto.setHeight(1);
		requestDto.setIs_return(0);//是否退回,包裹无人签收时是否退回，1-退回，0-不退回

		Consignee consignee = new Consignee();
		consignee.setConsignee_province(orderLinkDto.getProvince());//收件人省
		consignee.setConsignee_city(orderLinkDto.getCity());//收件人城市
		consignee.setConsignee_name(StringUtil.concat(orderLinkDto.getFirstName(),orderLinkDto.getLastName()));//收件人姓名
		consignee.setConsignee_company(StringUtil.concat(orderLinkDto.getFirstName(),orderLinkDto.getLastName()));//收件人公司
		consignee.setConsignee_street(orderLinkDto.getAddress());//收件人详细地址
		consignee.setConsignee_telephone(orderLinkDto.getTelphone());//收件人电话
		consignee.setConsignee_postcode(orderLinkDto.getZipcode());//收件人邮编

		Shipper shipper = new Shipper();
		shipper.setShipper_name(shippingExtend.getSenderContactName());//发件人
		shipper.setShipper_telephone(shippingExtend.getSenderTelphone());//发件人电话
		shipper.setShipper_countrycode(shippingExtend.getSenderCountry());//发件国家二字码
		shipper.setShipper_province(shippingExtend.getSenderProvince());//发件人省
		shipper.setShipper_city(shippingExtend.getSenderCity());//发件人城市
		shipper.setShipper_street(shippingExtend.getSenderAddress());//发件人详细地址

		//海关申报信息
		List<ItemArr> list = new ArrayList<>();

		detailList.forEach(item->{
			ItemArr itemArr = new ItemArr();
			itemArr.setInvoice_enname(item.getProductNameEN());//海关申报品名，英文
			itemArr.setInvoice_cnname(item.getProductNameCN());//中文海关品名
			itemArr.setInvoice_quantity(item.getOrderDetailQty());//数量
			itemArr.setInvoice_unitcharge(item.getPrice().floatValue());//单价
			itemArr.setInvoice_weight(1f);//重量
			list.add(itemArr);
		});
		requestDto.setConsignee(consignee);
		requestDto.setShipper(shipper);
		requestDto.setItemArr(list);

		return JsonUtils.toJson(requestDto);
	}
	
	protected void validate(TransportOrderRequest orderRequest, ShippingExtend extendInfo) {
		Assert.notNull(extendInfo, "寄件信息不能为空");
		Assert.notNull(orderRequest, "订单信息不能为空");
		OrderLinkDto orderLinkDto = orderRequest.getOrderLinkDto();

		Assert.hasText(orderRequest.getOrderNo(), "客户编号不能为空");
		Assert.hasText(orderRequest.getOrderAmount().toString(),"金额不能为空");
		Assert.hasText(orderRequest.getGoodsQty().toString(),"数量不能为空");
		Assert.notNull(orderRequest.getAreaId(),"目的地区域id不能为空");

		List<TransportOrderDetail> orderDetails = orderRequest.getOrderDetails();
		for (TransportOrderDetail orderDetail : orderDetails) {
			Assert.notNull(orderDetail.getOrderDetailQty(),"明细数量不能为空");
			Assert.notNull(orderDetail.getPrice(),"明细单价不能为空");
			Assert.notNull(orderDetail.getProductNameEN(),"明细英文名不能为空");
			Assert.notNull(orderDetail.getProductNameCN(),"明细中文名不能为空");
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
		Assert.hasText(orderLinkDto.getTelphone(), "收件人电话不能为空");
		Assert.hasText(orderLinkDto.getAddress(), "收件人详细地址不能为空");
		Assert.hasText(orderLinkDto.getZipcode(),"收件人所在地邮编不能为空");
		Assert.hasText(orderLinkDto.getCountryCode(),"收件人所在国家二字码不能为空，印度为IN,土耳其为TR");
		Assert.hasText(orderLinkDto.getProvince(),"收件人省不能为空");
		Assert.hasText(orderLinkDto.getCity(),"收件人所在城市不能为空");
		Assert.hasText(orderLinkDto.getAddress(),"收件人详细地址不能为空");
		if (StringUtils.isEmpty(orderLinkDto.getCompanyName())) {
			orderLinkDto.setCompanyName(this.getReviceFrontName(orderLinkDto));
		}

		Assert.notNull(extendInfo.getSenderContactName(),"发件人不能为空");
		Assert.notNull(extendInfo.getSenderTelphone(),"发件人电话不能为空");
		Assert.notNull(extendInfo.getSenderCountry(),"发件人国家不能为空，填二字码");
		Assert.notNull(extendInfo.getSenderProvince(),"发件人省不能为空");
		Assert.notNull(extendInfo.getSenderCity(),"发件人市不能为空");
		Assert.notNull(extendInfo.getSenderAddress(),"发件人详细地址不能为空");
		Assert.notNull(extendInfo.getSender(), "发件人公司不能为空");
		Assert.notNull(extendInfo.getAccount(), "请求账号不能为空");
		Assert.notNull(extendInfo.getMd5Key(), "请求秘钥不能为空");
		Assert.notNull(extendInfo.getInterfaceUrl(), "请求下单地址不能为空");

	}
}
