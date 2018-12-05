package com.stosz.tms.service.transport.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stosz.order.ext.enums.PayStateEnum;
import com.stosz.plat.utils.ArrayUtils;
import com.stosz.plat.utils.DateUtils;
import com.stosz.plat.utils.StringUtil;
import com.stosz.tms.dto.OrderLinkDto;
import com.stosz.tms.dto.TransportOrderDetail;
import com.stosz.tms.dto.TransportOrderRequest;
import com.stosz.tms.dto.TransportOrderResponse;
import com.stosz.tms.enums.HandlerTypeEnum;
import com.stosz.tms.external.aramex.*;
import com.stosz.tms.model.api.ShippingExtend;
import com.stosz.tms.service.transport.AbstractPlaceOrderTransportHandler;
import com.stosz.tms.utils.DecimalUtils;
import com.stosz.tms.utils.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.net.URL;
import java.util.*;

/**
 * Aramex 物流接口
 * @version [1.0,2017年12月18日]
 */
@Component
public class AramexTransportHandler extends AbstractPlaceOrderTransportHandler {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	public TransportOrderResponse transportPlaceOrder(TransportOrderRequest orderRequest, ShippingExtend shippingExtend) throws Exception {
		TransportOrderResponse response = new TransportOrderResponse();
		ShipmentCreationResponse responseBody = null;
		OrderLinkDto orderLinkDto = orderRequest.getOrderLinkDto();
		ShipmentCreationRequest shipmentCreationRequest = new ShipmentCreationRequest();
		try{
			javax.xml.rpc.Service service = new Service_1_0_ServiceLocator();
			URL url = new URL(shippingExtend.getInterfaceUrl());
			BasicHttpBinding_Service_1_0Stub stub = new BasicHttpBinding_Service_1_0Stub(url,service);

			//1.shipments
			Shipment shipment = this.getShipment(orderRequest, shippingExtend, orderLinkDto);
			Shipment[] shipments = new Shipment[]{shipment};
			//1.shipments
			shipmentCreationRequest.setShipments(shipments);
			ClientInfo clientInfo = this.getClientInfo();
			//2.ClientInfo
			shipmentCreationRequest.setClientInfo(clientInfo);
			Transaction transaction = this.getTransaction();
			//3.Transaction
			shipmentCreationRequest.setTransaction(transaction);
			LabelInfo labelInfo = this.getLabelInfo();
			//4.LabelInfo
			shipmentCreationRequest.setLabelInfo(labelInfo);

			responseBody = stub.createShipments(shipmentCreationRequest);
			if(!responseBody.getHasErrors()){
				response.setCode(TransportOrderResponse.SUCCESS);
				response.setTrackingNo(responseBody.getShipments()[0].getID());//运单号
			}else {
				response.setCode(TransportOrderResponse.FAIL);
			}
		}catch (Exception e){
			logger.info("AramexTransportHandler code={},intefaceUrl={},Exception={}", getCode(), shippingExtend.getInterfaceUrl(), e);
		}finally {
			logService.addLog(getCode(),orderRequest.getOrderNo(), shippingExtend.getInterfaceUrl(), JsonUtils.toJson(shipmentCreationRequest), JsonUtils.toJson(responseBody));
		}
		return response;
	}

	private String getErrorMessage(ShipmentCreationResponse responseBody) {
		StringBuilder errorBuilder = new StringBuilder();
		if (ArrayUtils.isNotEmpty(responseBody.getShipments())) {
			for (ProcessedShipment processedShipment : responseBody.getShipments()) {
				if (ArrayUtils.isEmpty(processedShipment.getNotifications())) {
					continue;
				}
				for (Notification notification : processedShipment.getNotifications()) {
					if (errorBuilder.length() != 0) {
						errorBuilder.append(",");
					}
					errorBuilder.append(notification.getCode());
					errorBuilder.append(":");
					errorBuilder.append(notification.getMessage());
				}
			}
		}
		return errorBuilder.toString();

	}

	private Shipment getShipment(TransportOrderRequest orderRequest, ShippingExtend shippingExtend, OrderLinkDto orderLinkDto) {
		Shipment shipment = new Shipment();
		// Number
		shipment.setNumber("");// 运单号
		Attachment[] attachments = this.getAttachments();
		// Attachments
		shipment.setAttachments(attachments);
		Party shipper = this.getParty(shippingExtend);
		// shipper
		shipment.setShipper(shipper);
		Party consignee = this.getConsigneetParty(orderLinkDto);
		// consignee
		shipment.setConsignee(consignee);
		Party thirdParty = this.getThirdParty();
		// thirdPaty
		shipment.setThirdParty(thirdParty);
		shipment.setReference1("");
		shipment.setReference2("");
		shipment.setReference3("");
		shipment.setForeignHAWB("");
		shipment.setTransportType_x0020_(0);
		shipment.setShippingDateTime(Calendar.getInstance());
		shipment.setDueDate(Calendar.getInstance());
		shipment.setPickupLocation("");
		shipment.setPickupGUID("");
		shipment.setComments("");
		shipment.setAccountingInstrcutions("");
		shipment.setOperationsInstructions("OperationsInstructions");

		ShipmentDetails details = this.getShipmentDetails(orderRequest);

		// details
		shipment.setDetails(details);
		return shipment;
	}

	private LabelInfo getLabelInfo() {
		LabelInfo labelInfo = new LabelInfo();
		labelInfo.setReportID(9201);
		labelInfo.setReportType("URL");
		return labelInfo;
	}

	private Transaction getTransaction() {
		Transaction transaction = new Transaction();
		transaction.setReference1("");
		transaction.setReference2("");
		transaction.setReference3("");
		transaction.setReference4("");
		return transaction;
	}

	private ClientInfo getClientInfo() {
		ClientInfo clientInfo = new ClientInfo();
		clientInfo.setAccountCountryCode("HK");
		clientInfo.setAccountEntity("HKG");
		clientInfo.setAccountNumber("70546291");
		clientInfo.setAccountPin("123123");
		clientInfo.setUserName("testingapi@aramex.com");
		clientInfo.setPassword("R123456789$r");
		clientInfo.setVersion("v1");
		return clientInfo;
	}

	private ShipmentDetails getShipmentDetails(TransportOrderRequest orderRequest) {
		ShipmentDetails details = new ShipmentDetails();

		Dimensions dimensions = this.getDimensions();
		// dimensions
		details.setDimensions(dimensions);

		Weight weight = this.getWeight();

		// weight
		details.setActualWeight(weight);
		details.setProductGroup("EXP");
		details.setProductType("EPX");
		details.setPaymentType("P");
		details.setPaymentOptions("");
		details.setServices("CODS");
		details.setNumberOfPieces(orderRequest.getGoodsQty());// 数量
		details.setDescriptionOfGoods(orderRequest.getRemark());// 订单名称
		details.setGoodsOriginCountry("HK");

		Money money = this.getMoney(orderRequest);

		// money
		details.setCashOnDeliveryAmount(money);

		Money insuMoney = this.getInsuMoney();

		// insu-money
		details.setInsuranceAmount(insuMoney);

		Money collmoney = this.getCollMoney();
		// collec-money
		details.setCollectAmount(collmoney);

		Money cashmoney = this.getCashMoney();

		// cash-money
		details.setCashAdditionalAmount(cashmoney);

		details.setCashAdditionalAmountDescription("");

		Money cusmoney = this.getCusMoney(orderRequest);
		// cus-Money
		details.setCustomsValueAmount(cusmoney);

		// 订单明细
		ShipmentItem[] items = this.getShipmentItems(orderRequest);
		// items
		details.setItems(items);
		return details;
	}

	private ShipmentItem[] getShipmentItems(TransportOrderRequest orderRequest) {
		int detailSize = orderRequest.getOrderDetails().size();
		ShipmentItem[] items = new ShipmentItem[detailSize];
		for (int i = 0; i < detailSize; i++) {
			TransportOrderDetail transportOrderDetail = orderRequest.getOrderDetails().get(i);
			ShipmentItem shipmentItem = new ShipmentItem();
			shipmentItem.setPackageType("box");// 单位 box
			Weight itemWeight1 = this.getItemWeight(transportOrderDetail);
			// item-weight
			shipmentItem.setWeight(itemWeight1);
			shipmentItem.setComments("");
			shipmentItem.setReference("");
			Dimensions[] piecesDimensions = this.getPiecesDimensions();
			// piecesDimensions
			shipmentItem.setPiecesDimensions(piecesDimensions);
			shipmentItem.setCommodityCode("");
			shipmentItem.setGoodsDescription(transportOrderDetail.getProductTitle());
			shipmentItem.setCountryOfOrigin("");
			Money itemcusval = new Money();
			itemcusval.setCurrencyCode("USD");
			itemcusval.setValue(transportOrderDetail.getTotalAmount().longValue());// 明细金额
			// item-customsValue
			shipmentItem.setCustomsValue(itemcusval);
			// shipmentItem
			items[i] = shipmentItem;
		}
		return items;
	}

	private Weight getItemWeight(TransportOrderDetail transportOrderDetail) {
		Weight itemWeight1 = new Weight();
		itemWeight1.setUnit("KG");// 重量单位
		itemWeight1.setValue(0.5);// 重量
		return itemWeight1;
	}

	private Dimensions[] getPiecesDimensions() {
		Dimensions itemDimensions = this.getItemDimensions();

		return new Dimensions[] { itemDimensions };
	}

	private Dimensions getItemDimensions() {
		Dimensions itemDimensions = new Dimensions();
		itemDimensions.setLength(0);
		itemDimensions.setWidth(0);
		itemDimensions.setHeight(0);
		itemDimensions.setUnit("cm");
		return itemDimensions;
	}

	private Money getCusMoney(TransportOrderRequest orderRequest) {
		Money cusmoney = new Money();
		cusmoney.setValue(orderRequest.getOrderAmount().longValue());// 订单金额
		cusmoney.setCurrencyCode("USD");// 币种
		return cusmoney;
	}

	private Money getCashMoney() {
		Money cashmoney = new Money();
		cashmoney.setValue(0);
		cashmoney.setCurrencyCode("");
		return cashmoney;
	}

	private Money getCollMoney() {
		Money collmoney = new Money();
		collmoney.setValue(0);
		collmoney.setCurrencyCode("");
		return collmoney;
	}

	private Money getInsuMoney() {
		Money insuMoney = new Money();
		insuMoney.setValue(0);
		insuMoney.setCurrencyCode("");
		return insuMoney;
	}

	private Money getMoney(TransportOrderRequest orderRequest) {
		Money money = new Money();
		if (PayStateEnum.unPaid.ordinal() == orderRequest.getPayState()) {// 未支付的订单
			money.setValue(orderRequest.getOrderAmount().longValue());// 订单金额
			money.setCurrencyCode(orderRequest.getCurrencyCode());// 币种
		} else {
			money.setValue(0);// 订单金额
			money.setCurrencyCode(orderRequest.getCurrencyCode());// 币种
		}

		return money;
	}

	private Weight getWeight() {
		Weight weight = new Weight();
		weight.setValue(0.5);
		weight.setUnit("KG");
		return weight;
	}

	private Dimensions getDimensions() {
		Dimensions dimensions = new Dimensions();
		dimensions.setHeight(2);
		dimensions.setLength(2);
		dimensions.setWidth(2);
		dimensions.setUnit("cm");
		return dimensions;
	}

	private Party getThirdParty() {
		Party thirdParty = new Party();
		thirdParty.setReference1("");
		thirdParty.setReference2("");
		thirdParty.setAccountNumber("");
		Address thirpartyAddress = this.getThirdpartyAddress();
		thirdParty.setPartyAddress(thirpartyAddress);

		Contact thirdConcat = this.getThirdContact();

		thirdParty.setContact(thirdConcat);
		return thirdParty;
	}

	private Contact getThirdContact() {
		Contact thirdConcat = new Contact();
		thirdConcat.setDepartment("");
		thirdConcat.setPersonName("");
		thirdConcat.setTitle("");
		thirdConcat.setCompanyName("");
		thirdConcat.setPhoneNumber1("");
		thirdConcat.setPhoneNumber1Ext("");
		thirdConcat.setPhoneNumber2("");
		thirdConcat.setPhoneNumber2Ext("");
		thirdConcat.setFaxNumber("");
		thirdConcat.setCellPhone("");
		thirdConcat.setEmailAddress("");
		thirdConcat.setType("");
		return thirdConcat;
	}

	private Address getThirdpartyAddress() {
		Address thirpartyAddress = new Address();
		thirpartyAddress.setLine1("");
		thirpartyAddress.setLine2("");
		thirpartyAddress.setLine3("");
		thirpartyAddress.setCity("");
		thirpartyAddress.setStateOrProvinceCode("");
		thirpartyAddress.setPostCode("");
		thirpartyAddress.setCountryCode("");
		return thirpartyAddress;
	}

	private Party getConsigneetParty(OrderLinkDto orderLinkDto) {
		Party consignee = new Party();
		consignee.setReference1("");
		consignee.setReference2("");
		consignee.setAccountNumber("");
		Address cneeAddress = this.getCneeAddress(orderLinkDto);
		// cnee--Address
		consignee.setPartyAddress(cneeAddress);
		Contact cneeConcat = this.getContact(orderLinkDto);
		// conee--contact
		consignee.setContact(cneeConcat);
		return consignee;
	}

	private Contact getContact(OrderLinkDto orderLinkDto) {
		Contact cneeConcat = new Contact();
		cneeConcat.setDepartment("");
		cneeConcat.setPersonName(orderLinkDto.getFirstName() + orderLinkDto.getLastName());// 收件人姓名
		cneeConcat.setTitle("");
		cneeConcat.setCompanyName(orderLinkDto.getCompanyName());// 必传收件人公司
		cneeConcat.setPhoneNumber1(orderLinkDto.getTelphone());// 收件人电话
		cneeConcat.setPhoneNumber1Ext(orderLinkDto.getTelphone());
		cneeConcat.setPhoneNumber2("");
		cneeConcat.setPhoneNumber2Ext("");
		cneeConcat.setFaxNumber("");
		cneeConcat.setCellPhone(orderLinkDto.getTelphone());
		cneeConcat.setEmailAddress(orderLinkDto.getEmail());// 收件人邮箱
		cneeConcat.setType("");
		return cneeConcat;
	}

	private Address getCneeAddress(OrderLinkDto orderLinkDto) {
		Address cneeAddress = new Address();
		cneeAddress.setLine1(orderLinkDto.getAddress());
		cneeAddress.setLine2("");
		cneeAddress.setLine3("");
		cneeAddress.setCity(orderLinkDto.getCity());//收件城市
		cneeAddress.setStateOrProvinceCode("");
		cneeAddress.setPostCode(orderLinkDto.getZipcode());
		cneeAddress.setCountryCode(orderLinkDto.getCountryCode());
		return cneeAddress;
	}

	private Party getParty(ShippingExtend shippingExtend) {
		Party shipper = new Party();
		shipper.setReference1("");
		shipper.setReference2("");
		shipper.setAccountNumber("70546291");
		Address partyAddress = this.getAddress();
		// PartyAddress
		shipper.setPartyAddress(partyAddress);

		Contact contact = this.getContact(shippingExtend);

		// contact
		shipper.setContact(contact);
		return shipper;
	}

	private Contact getContact(ShippingExtend shippingExtend) {
		Contact contact = new Contact();
		contact.setDepartment("");
		contact.setPersonName(shippingExtend.getSenderContactName());// 发件人姓名
		contact.setTitle("");
		contact.setCompanyName(shippingExtend.getSender());// 发件人公司
		contact.setPhoneNumber1(shippingExtend.getSenderTelphone());// 发件人电话
		contact.setPhoneNumber1Ext(shippingExtend.getSenderTelphone());
		contact.setPhoneNumber2("");
		contact.setPhoneNumber2Ext("");
		contact.setFaxNumber("");
		contact.setCellPhone(shippingExtend.getSenderTelphone());// 发件人电话
		contact.setEmailAddress(shippingExtend.getSenderEmail());// 发件人邮箱
		contact.setType("");
		return contact;
	}

	private Address getAddress() {
		Address partyAddress = new Address(); // 仿照php写死
		partyAddress.setLine1("Hongkong");
		partyAddress.setLine2("707-713 Nathan Road, mongkok, Kowloon");
		partyAddress.setLine3("Room A37, 9th floor, yingao international building");
		partyAddress.setCity("Hong Kong");
		partyAddress.setStateOrProvinceCode("");
		partyAddress.setPostCode("");
		partyAddress.setCountryCode("HK");
		return partyAddress;
	}

	private Attachment[] getAttachments() {
		Attachment attachment = new Attachment();
		attachment.setFileName("");
		attachment.setFileExtension("");
		attachment.setFileExtension("");
		return new Attachment[] { attachment };
	}

	@Override
	public HandlerTypeEnum getCode() {
		return HandlerTypeEnum.ARAMEX;
	}
	
	
	protected void validate(TransportOrderRequest orderRequest, ShippingExtend extendInfo) {
		Assert.notNull(extendInfo, "寄件信息不能为空");
		Assert.notNull(orderRequest, "订单信息不能为空");
		OrderLinkDto orderLinkDto = orderRequest.getOrderLinkDto();

		Assert.hasText(orderRequest.getOrderNo(), "客户编号不能为空");
		Assert.hasText(orderRequest.getWeight().toString(),"重量不能为空");
		Assert.hasText(orderRequest.getOrderAmount().toString(),"金额不能为空");
		Assert.hasText(orderRequest.getGoodsQty().toString(),"数量不能为空");
		Assert.hasText(orderRequest.getCurrencyCode(),"订单币种不能为空");

		List<TransportOrderDetail> orderDetails = orderRequest.getOrderDetails();
		// 多品名信息(中文品名,英文品名,数量,单位,单价,海关编码;)用英文逗号隔开，用封号结尾
		for (TransportOrderDetail orderDetail : orderDetails) {
			Assert.hasText(orderDetail.getProductTitle(),"商品名称不能为空");//产品标题
			Assert.notNull(orderDetail.getTotalAmount(),"明细总价不能为空");//
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
		Assert.hasText(orderLinkDto.getEmail(),"收件人email不能为空");
		Assert.hasText(orderLinkDto.getZipcode(),"收件人所在地邮编不能为空");
		Assert.hasText(orderLinkDto.getCountryCode(),"收件人所在国家编码不能为空，沙特阿拉伯编码为 SA");
		Assert.hasText(orderLinkDto.getCity(),"收件人所在城市不能为空");
		if (StringUtils.isEmpty(orderLinkDto.getCompanyName())) {
			orderLinkDto.setCompanyName(this.getReviceFrontName(orderLinkDto));
		}
		Assert.notNull(orderLinkDto.getCompanyName(), "收件人公司不能为空，无公司可填姓名");

		Assert.notNull(extendInfo.getSenderContactName(),"发件人不能为空");
		Assert.notNull(extendInfo.getSenderTelphone(),"发件人电话不能为空");
		Assert.notNull(extendInfo.getSender(), "发件人公司不能为空");
		Assert.notNull(extendInfo.getSenderEmail(), "发件人email不能为空");
		Assert.notNull(extendInfo.getInterfaceUrl(), "请求下单地址不能为空");

	}
}
