package com.stosz.tms.service.transport.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.http.entity.ContentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import com.stosz.plat.utils.StringUtil;
import com.stosz.tms.dto.OrderLinkDto;
import com.stosz.tms.dto.Parameter;
import com.stosz.tms.dto.TransportOrderRequest;
import com.stosz.tms.dto.TransportOrderResponse;
import com.stosz.tms.enums.AllowedProductTypeEnum;
import com.stosz.tms.enums.HandlerTypeEnum;
import com.stosz.tms.model.api.ShippingExtend;
import com.stosz.tms.model.api.TimesContent;
import com.stosz.tms.model.api.TimesDetail;
import com.stosz.tms.service.transport.AbstractPlaceOrderTransportHandler;
import com.stosz.tms.utils.DecimalUtils;
import com.stosz.tms.utils.HttpClientUtils;
import com.stosz.tms.utils.JsonUtils;

/**
 * times 物流接口
 * @author feiheping
 * @version [1.0,2017年12月4日]
 */
// 目前只支持泰國（Thailand）及台灣（Taiwan） 到货country字段
@Component
public class TimesTransportHandler extends AbstractPlaceOrderTransportHandler {

	private Logger logger = LoggerFactory.getLogger(TimesTransportHandler.class);

	@Override
	public TransportOrderResponse transportPlaceOrder(TransportOrderRequest orderRequest, ShippingExtend extendInfo) throws Exception {
		TransportOrderResponse orderResponse = new TransportOrderResponse(true);
		String requestBody = null;
		String response = null;
		try {
			TimesContent timesContent = transferTimesContent(orderRequest, extendInfo);
			requestBody = JsonUtils.toJson(timesContent);
			response = HttpClientUtils.doPost(extendInfo.getInterfaceUrl(), requestBody, ContentType.APPLICATION_JSON, "UTF-8",
					this.getPostHeader(extendInfo));
			Parameter<String, Object> resultMap = JsonUtils.jsonToParameter(response);
			String trackNumber = resultMap.getString("trackingNumber");
			if (!StringUtils.hasText(trackNumber)) {
				Object remarks = resultMap.get("remarks");
				if (remarks instanceof Map) {
					Map<String, Object> remarkMap = ((Map<String, Object>) remarks);
					trackNumber = StringUtil.objToStr(remarkMap.get("trackingNumber"));
				}
			}
			if (StringUtils.hasText(trackNumber)) {
				orderResponse.setTrackingNo(trackNumber);
				orderResponse.setCode(TransportOrderResponse.SUCCESS);
			} else {
				String message = resultMap.getString("message");
				orderResponse.setCode(TransportOrderResponse.FAIL);
				orderResponse.setErrorMsg(message);
			}
		} catch (Exception e) {
			logger.info("TimesTransportHandler code={},intefaceUrl={},Exception={}", getCode(), extendInfo.getInterfaceUrl(), e);
		} finally {
			logService.addLog(getCode(),orderRequest.getOrderNo(), extendInfo.getInterfaceUrl(), requestBody, response);
		}
		return orderResponse;
	}

	private Map<String, String> getPostHeader(ShippingExtend extendInfo) {
		Map<String, String> headers = new HashMap<String, String>();
		headers.put("Authorization", StringUtil.concat("Bearer ", extendInfo.getMd5Key()));
		return headers;
	}

	private TimesContent transferTimesContent(TransportOrderRequest orderRequest, ShippingExtend extendInfo) {
		TimesContent timesContent = new TimesContent();
		timesContent.setConsigneeCompanyName("");// 收货人公司
		timesContent.setConsigneeContactName("");// 收货人联系人
		OrderLinkDto orderLinkDto = orderRequest.getOrderLinkDto();
		timesContent.setConsigneePhone(orderLinkDto.getTelphone());// 收货人电话
		timesContent.setConsigneeSubdistrict("n/a");// 收货人分区份(英语)
		timesContent.setConsigneeDistrict(orderLinkDto.getCity());// 收货人区份(英语)
		timesContent.setConsigneeProvince(orderLinkDto.getProvince());// 收货人省份
		timesContent.setConsigneeCountry(orderLinkDto.getCountry());// 收货人国家
		timesContent.setConsigneePostalCode(orderLinkDto.getZipcode());// 收货人邮政编号
		String localeName = StringUtil.concat(orderLinkDto.getFirstName(), ".", orderLinkDto.getLastName());
		timesContent.setConsigneeCompanyNameLocale(localeName);// 收货人公司(目的地官方语言)
		timesContent.setConsigneeContactNameLocale(localeName);// 收货人联络人(目的地官方语言
		timesContent.setConsigneeAddressLocale(orderLinkDto.getAddress());// 收货人地址
		timesContent.setConsigneeAddress(orderLinkDto.getAddress());

		timesContent.setShipperCompanyName(extendInfo.getSender());// 发货人公司
		timesContent.setShipperContactName(extendInfo.getSenderContactName());// 发货人姓名
		timesContent.setShipperPhone(extendInfo.getSenderTelphone());// 发货人电话
		timesContent.setShipperAddress(extendInfo.getSenderAddress());// 发货人地址
		timesContent.setShipperSubdistrict(extendInfo.getSenderTown());// 发货人区/县
		timesContent.setShipperDistrict(extendInfo.getSenderTown());
		timesContent.setShipperProvince(extendInfo.getSenderProvince());// 发货人省份
		timesContent.setShipperCountry(extendInfo.getSenderCountry());// 发货人国家
		timesContent.setShipperPostalCode(extendInfo.getSenderZipcode());// 邮政编码

		if (orderRequest.getPayState().equals(0)) {// 未支付
			timesContent.setPaymentMethod("COD");
		} else {
			timesContent.setPaymentMethod("PREPAID");
		}
		timesContent.setParcelValue(DecimalUtils.formateDecimal(orderRequest.getOrderAmount(), "#.##"));// 包裹价值
		timesContent.setProductType("Express");// 寄货渠道Express = 专线; Postal = 邮政
		if (AllowedProductTypeEnum.general == orderRequest.getProductType()) {
			timesContent.setShipmentType("General Shipment");
		} else if (AllowedProductTypeEnum.sensitive == orderRequest.getProductType()) {
			timesContent.setShipmentType("Sensitive Shipment");
		}
		timesContent.setSalePlatformName(extendInfo.getSender());
		timesContent.setReferenceNumber(orderRequest.getOrderNo());// 客户参考编号

		List<TimesDetail> timesDetails = orderRequest.getOrderDetails().stream().map(item -> {
			TimesDetail timesDetail = new TimesDetail();
			timesDetail.setSku(item.getSku());// 货品SKU
			timesDetail.setCategoryId("");// 货品分类编号
			timesDetail.setCategoryName("");// 货品分类名称
			timesDetail.setDescription(item.getProductTitle());// 品名
			timesDetail.setBrand("");// 牌子. 如包裹类型为手机及平板计算机, 此项必填
			timesDetail.setModel("");// 型号. 如包裹类型为手机及平板计算机, 此项必填
			timesDetail.setPieces(item.getOrderDetailQty());// 单项SKU件数
			timesDetail.setUnitPrice(item.getPrice());// 单项SKU单价
			timesDetail.setUnitPriceCurrency("THB");// 货币单位, 使用ISO 4217标准
			timesDetail.setCODValue(item.getTotalAmount());// 单项SKU COD货价(件数*COD单价). 如付款方式为COD, 此项必填. 使用当地货币
			return timesDetail;
		}).collect(Collectors.toList());
		timesContent.setItems(timesDetails);
		return timesContent;
	}

	/**
	 * 校验参数是否合法
	 * @param orderRequest
	 * @return
	 */
	protected void validate(TransportOrderRequest orderRequest, ShippingExtend extendInfo) {
		Assert.notNull(extendInfo, "配置信息不能为空");
		Assert.hasText(extendInfo.getInterfaceUrl(), "接口地址为空,请检查");
		Assert.hasText(extendInfo.getSender(), "寄件公司不能为空");
		Assert.hasText(extendInfo.getSenderContactName(), "寄件联系人不能为空");
		Assert.hasText(extendInfo.getSenderTelphone(), "寄件联系人电话不能为空");
		Assert.hasText(extendInfo.getSenderAddress(), "寄件地址不能为空");
		Assert.hasText(extendInfo.getSenderTown(), "寄件区/县不能为空");
		Assert.hasText(extendInfo.getSenderProvince(), "寄件省份不能为空");
		Assert.hasText(extendInfo.getSenderCountry(), "寄件国家不能为空");
		Assert.hasText(extendInfo.getSenderZipcode(), "寄件邮编不能为空");

		OrderLinkDto orderLinkDto = orderRequest.getOrderLinkDto();
		Assert.notNull(orderLinkDto, "收件人信息不能为空");
		Assert.notNull(orderLinkDto.getFirstName(), "收件人姓不能为空");
		Assert.notNull(orderLinkDto.getLastName(), "收件人名不能为空");
		Assert.hasText(orderLinkDto.getTelphone(), "收件人电话不能为空");
		Assert.hasText(orderLinkDto.getCity(), "收件人城市不能为空");
		if(!com.stosz.plat.utils.StringUtils.isEnStr(orderLinkDto.getCity())){
			throw new IllegalArgumentException("收件人城市必须为英文");
		}
		Assert.hasText(orderLinkDto.getProvince(), "收件人省份不能为空");
		if(!com.stosz.plat.utils.StringUtils.isEnStr(orderLinkDto.getProvince())){
			throw new IllegalArgumentException("收件人省份必须为英文");
		}
		Assert.hasText(orderLinkDto.getCountry(), "收件人国家不能为空");
		if(!("Thailand").equalsIgnoreCase(orderLinkDto.getCountry().trim()) &&
				!("Taiwan").equalsIgnoreCase(orderLinkDto.getCountry().trim())){
			throw new IllegalArgumentException("收件人国家只能为Thailand或Taiwan");
		}
		Assert.hasText(orderLinkDto.getZipcode(), "收件人邮编不能为空");

		Assert.notNull(orderRequest, "物流下单请求对象不能为空");
		Assert.notNull(orderRequest.getPayState(), "订单状态不能为空");
		Assert.notNull(orderRequest.getOrderAmount(), "订单金额不能为空");
		Assert.notNull(orderRequest.getOrderNo(), "订单号不能为空");

		Assert.notEmpty(orderRequest.getOrderDetails(), "订单详情列表不能为空");
		orderRequest.getOrderDetails().forEach(item -> {
			Assert.notNull(item.getSku(), "产品SKU不能为空");
			Assert.notNull(item.getProductTitle(), "产品标题不能为空");
			Assert.notNull(item.getOrderDetailQty(), "单项SKU件数不能为空");
			Assert.notNull(item.getPrice(), "单项SKU单价不能为空");
			Assert.notNull(item.getTotalAmount(), "单项SKU总价不能为空");
		});
	}

	@Override
	public HandlerTypeEnum getCode() {
		return HandlerTypeEnum.TIMES;
	}
}
