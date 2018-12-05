package com.stosz.plat.wms.bean;

import java.util.List;


/**
 * 销售订单主信息实体bean
 * @author lb
 *
 */
public class SaleOrderHeadInfoBean {
	/*
	 * 1.销售订单
	 */
	private String orderCode;	
	/*
	 * 2.订单类型
	 * EBUS：线上订单
	 * OFFLINE：线下订单
	 * SPECIAL：特殊订单	
	 */
	private String bity;
	/*
	 * 3.仓库编码
	 */
	private String warehouseCode;
	/*
	 * 4.来源类型
	 */
	private String fromType;
	/*
	 * 5.来源单号
	 */
	private String fromCode;
	/*
	 * 6.来源平台编码
	 */
	private String platformCode;
	/*
	 * 7.来源平台名称
	 */
	private String platformName;
	/*
	 * 8.是否紧急(0普通,1紧急)
	 */
	private String isUrgency;
	/*
	 * 9.下单时间(yyyy/MM/dd HH:mm:ss)
	 */
	private String downDate;	
	/*
	 * 10.支付时间(yyyy/MM/dd HH:mm:ss)
	 */
	private String payTime;
	/*
	 *11. 审核时间（即生成单据的时间）
	 */
	private String auditTime;
	/*
	 * 12.审核人编码
	 */
	private String auditUserCode;
	/*
	 * 13.物流公司编码
	 */
	private String logisticsCompanyCode;
	/*
	 * 14.物流公司名称 
	 */
	private String logisticsCompanyName;
	/*
	 * 15.快递单号
	 */
	private String expressNo;
	/*
	 * 16.邮费
	 */
	private String postage;
	/*
	 * 17.是否货到付款(true/false) 
	 */
	private String isDeliveryPay;
	/*
	 * 18.店铺编码
	 */
	private String shopCode;	
	/*
	 * 19.店铺名称
	 */
	private String shopName;	
	/*
	 * 20.会员昵称
	 */
	private String bunick;	
	/*
	 * 21.收货人姓名
	 */
	private String consignee;	
	/*
	 * 22.邮码
	 */
	private String postCode;
	/*
	 * 23.省名称 
	 */
	private String provinceName;	
	/*
	 * 24.市名称
	 */
	private String cityName;	
	/*
	 * 25.区名称
	 */
	private String areaName;	
	/*
	 * 26.收件地址
	 */
	private String address;	
	/*
	 * 27.移动电话
	 */
	private String mobile;	
	/*
	 * 28.固定电话
	 */
	private String tel;	
	/*
	 * 29.卖家留言
	 */
	private String sellersMessage;	
	/*
	 * 30.买家留言
	 */
	private String buyerMessage;	
	/*
	 *31. 商家留言
	 */
	private String merchantMessage;	
	/*
	 * 32.内部便签
	 */
	private String InternalMemo;
	/*
	 * 33.订单金额 
	 */
	private String orderPrice;
	/*
	 * 34.应收金额 
	 */
	private String amountReceivable;
	/*
	 * 35.邮费是否到付true/false
	 */
	private String isPostagePay;
	/*
	 * 36.是否需要发票true/false
	 */
	private String isInvoice;
	/*
	 * 37.实际支付
	 */
	private String actualPayment;
	/*
	 * 38.发票抬头
	 */
	private String invoiceName;
	/*
	 * 39.发票金额
	 */
	private String invoicePrice;
	/*
	 * 40.发票内容
	 */
	private String invoiceText;	
	/*
	 * 41.货主名
	 */
	private String goodsOwner;
	/*
	 * 42.备用字段1
	 */
	private String gwf1;
	/*
	 * 43.备用字段2
	 */
	private String gwf2;
	/*
	 * 44.备用字段3
	 */
	private String gwf3;
	/*
	 * 45.备用字段4
	 */
	private String gwf4;
	/*
	 * 46.备用字段5
	 */
	private String gwf5;
	/*
	 * 47.备用字段5
	 */
	private String gwf6;
	/*
	 * 48.备用字段5
	 */
	private String gwf7;
	/*
	 * 49.备用字段5
	 */
	private String gwf8;
	/*
	 * 50.备用字段9
	 */
	private String gwf9;
	/*
	 * 47.转单时间
	 */
	private String eddtTime;
	
	private List<SaleOrderDetailInfoBean> detailList;

	public String getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

	public String getBity() {
		return bity;
	}

	public void setBity(String bity) {
		this.bity = bity;
	}

	public String getWarehouseCode() {
		return warehouseCode;
	}

	public void setWarehouseCode(String warehouseCode) {
		this.warehouseCode = warehouseCode;
	}

	public String getFromType() {
		return fromType;
	}

	public void setFromType(String fromType) {
		this.fromType = fromType;
	}

	public String getFromCode() {
		return fromCode;
	}

	public void setFromCode(String fromCode) {
		this.fromCode = fromCode;
	}

	public String getPlatformCode() {
		return platformCode;
	}

	public void setPlatformCode(String platformCode) {
		this.platformCode = platformCode;
	}

	public String getPlatformName() {
		return platformName;
	}

	public void setPlatformName(String platformName) {
		this.platformName = platformName;
	}

	public String getIsUrgency() {
		return isUrgency;
	}

	public void setIsUrgency(String isUrgency) {
		this.isUrgency = isUrgency;
	}

	public String getDownDate() {
		return downDate;
	}

	public void setDownDate(String downDate) {
		this.downDate = downDate;
	}

	public String getPayTime() {
		return payTime;
	}

	public void setPayTime(String payTime) {
		this.payTime = payTime;
	}

	public String getAuditTime() {
		return auditTime;
	}

	public void setAuditTime(String auditTime) {
		this.auditTime = auditTime;
	}

	public String getLogisticsCompanyCode() {
		return logisticsCompanyCode;
	}

	public void setLogisticsCompanyCode(String logisticsCompanyCode) {
		this.logisticsCompanyCode = logisticsCompanyCode;
	}

	public String getLogisticsCompanyName() {
		return logisticsCompanyName;
	}

	public void setLogisticsCompanyName(String logisticsCompanyName) {
		this.logisticsCompanyName = logisticsCompanyName;
	}

	public String getExpressNo() {
		return expressNo;
	}

	public void setExpressNo(String expressNo) {
		this.expressNo = expressNo;
	}

	public String getPostage() {
		return postage;
	}

	public void setPostage(String postage) {
		this.postage = postage;
	}

	public String getIsDeliveryPay() {
		return isDeliveryPay;
	}

	public void setIsDeliveryPay(String isDeliveryPay) {
		this.isDeliveryPay = isDeliveryPay;
	}

	public String getShopCode() {
		return shopCode;
	}

	public void setShopCode(String shopCode) {
		this.shopCode = shopCode;
	}

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public String getBunick() {
		return bunick;
	}

	public void setBunick(String bunick) {
		this.bunick = bunick;
	}

	public String getConsignee() {
		return consignee;
	}

	public void setConsignee(String consignee) {
		this.consignee = consignee;
	}

	public String getPostCode() {
		return postCode;
	}

	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}

	public String getProvinceName() {
		return provinceName;
	}

	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getSellersMessage() {
		return sellersMessage;
	}

	public void setSellersMessage(String sellersMessage) {
		this.sellersMessage = sellersMessage;
	}

	public String getBuyerMessage() {
		return buyerMessage;
	}

	public void setBuyerMessage(String buyerMessage) {
		this.buyerMessage = buyerMessage;
	}

	public String getMerchantMessage() {
		return merchantMessage;
	}

	public void setMerchantMessage(String merchantMessage) {
		this.merchantMessage = merchantMessage;
	}

	public String getInternalMemo() {
		return InternalMemo;
	}

	public void setInternalMemo(String internalMemo) {
		InternalMemo = internalMemo;
	}

	public String getOrderPrice() {
		return orderPrice;
	}

	public void setOrderPrice(String orderPrice) {
		this.orderPrice = orderPrice;
	}

	public String getAmountReceivable() {
		return amountReceivable;
	}

	public void setAmountReceivable(String amountReceivable) {
		this.amountReceivable = amountReceivable;
	}

	public String getIsPostagePay() {
		return isPostagePay;
	}

	public void setIsPostagePay(String isPostagePay) {
		this.isPostagePay = isPostagePay;
	}

	public String getIsInvoice() {
		return isInvoice;
	}

	public void setIsInvoice(String isInvoice) {
		this.isInvoice = isInvoice;
	}

	public String getActualPayment() {
		return actualPayment;
	}

	public void setActualPayment(String actualPayment) {
		this.actualPayment = actualPayment;
	}

	public String getInvoiceName() {
		return invoiceName;
	}

	public void setInvoiceName(String invoiceName) {
		this.invoiceName = invoiceName;
	}

	public String getInvoicePrice() {
		return invoicePrice;
	}

	public void setInvoicePrice(String invoicePrice) {
		this.invoicePrice = invoicePrice;
	}

	public String getInvoiceText() {
		return invoiceText;
	}

	public void setInvoiceText(String invoiceText) {
		this.invoiceText = invoiceText;
	}

	public String getGoodsOwner() {
		return goodsOwner;
	}

	public void setGoodsOwner(String goodsOwner) {
		this.goodsOwner = goodsOwner;
	}

	public String getGwf1() {
		return gwf1;
	}

	public void setGwf1(String gwf1) {
		this.gwf1 = gwf1;
	}

	public String getGwf2() {
		return gwf2;
	}

	public void setGwf2(String gwf2) {
		this.gwf2 = gwf2;
	}

	public String getGwf3() {
		return gwf3;
	}

	public void setGwf3(String gwf3) {
		this.gwf3 = gwf3;
	}

	public String getGwf4() {
		return gwf4;
	}

	public void setGwf4(String gwf4) {
		this.gwf4 = gwf4;
	}

	public String getGwf5() {
		return gwf5;
	}

	public void setGwf5(String gwf5) {
		this.gwf5 = gwf5;
	}

	public List<SaleOrderDetailInfoBean> getDetailList() {
		return detailList;
	}

	public void setDetailList(List<SaleOrderDetailInfoBean> detailList) {
		this.detailList = detailList;
	}

	public String getAuditUserCode() {
		return auditUserCode;
	}

	public void setAuditUserCode(String auditUserCode) {
		this.auditUserCode = auditUserCode;
	}

	public String getEddtTime() {
		return eddtTime;
	}

	public void setEddtTime(String eddtTime) {
		this.eddtTime = eddtTime;
	}

}
