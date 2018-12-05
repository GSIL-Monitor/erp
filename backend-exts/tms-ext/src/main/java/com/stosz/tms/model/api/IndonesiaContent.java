package com.stosz.tms.model.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.math.BigDecimal;

/**
 * 印尼HY Content 实体
 * @author feiheping
 * @version [1.0,2017年11月30日]
 */
public class IndonesiaContent {

	public static final Logger logger = LoggerFactory.getLogger(IndonesiaContent.class);

	private String Jobno;// 运单号 哲盟返回运单号 N
	private String OrderNO;// 客户订单号 N
	private String Clno;// 客户代号 HYE 提供 Y
	private String HubIn;// 指定路线 HYE 提供 Y
	private String DestNO;// 目的地 HYE 提供 Y
	private String CCPayMent;// 付款方式 默认为 PP N
	private BigDecimal CODCharge;// 代收款
	private String DescrType;// 货物类型
	private String CCTax;// 税金付款 默认为 CC
	private BigDecimal Weig;// 收费实重
	private String Packing;// 包装类型 默认为 WPX
	private Integer Pcs;// 件数 默认为1
	private String SDTel;// 寄件电话
	private String SDAddr;// 寄件地址
	private String SDCity;// 寄件城市
	private String SDState;// 寄件州省
	private String SDZip;// 寄件邮编
	private String SDCountry;// 寄件国家
	private String SDName;// 寄件人名
	private String SDDistrict;// 寄件区县
	private String ReCompany;// 收件公司
	private String ReTel;// 收件人电话
	private String ReAddr;// 收件人地址
	private String ReCity;// 收件城市
	private String ReZip;// 收件邮编
	private String ReState;// 收件州省
	private String ReConsinee;// 收件人名称
	private String ReDistrict;// 收件区县
	private String Remark;// 备注
	private String TransType;// 收货方式
	private String InvoiceStr;// 多品名信息(中文品名,英文品名,数量,单位,单价,海关编码;)用英文逗号隔开，用封号结尾

	@JsonProperty(value = "Jobno")
	public String getJobno() {
		return Jobno;
	}

	@JsonProperty(value = "OrderNO")
	public String getOrderNO() {
		return OrderNO;
	}

	@JsonProperty(value = "Clno")
	public String getClno() {
		return Clno;
	}

	@JsonProperty(value = "HubIn")
	public String getHubIn() {
		return HubIn;
	}

	@JsonProperty(value = "DestNO")
	public String getDestNO() {
		return DestNO;
	}

	@JsonProperty(value = "CCPayMent")
	public String getCCPayMent() {
		return CCPayMent;
	}

	@JsonProperty(value = "CODCharge")
	public BigDecimal getCODCharge() {
		return CODCharge;
	}

	@JsonProperty(value = "DescrType")
	public String getDescrType() {
		return DescrType;
	}

	@JsonProperty(value = "CCTax")
	public String getCCTax() {
		return CCTax;
	}

	@JsonProperty(value = "Weig")
	public BigDecimal getWeig() {
		return Weig;
	}

	@JsonProperty(value = "Packing")
	public String getPacking() {
		return Packing;
	}

	@JsonProperty(value = "Pcs")
	public Integer getPcs() {
		return Pcs;
	}

	@JsonProperty(value = "SDTel")
	public String getSDTel() {
		return SDTel;
	}

	@JsonProperty(value = "SDAddr")
	public String getSDAddr() {
		return SDAddr;
	}

	@JsonProperty(value = "SDCity")
	public String getSDCity() {
		return SDCity;
	}

	@JsonProperty(value = "SDState")
	public String getSDState() {
		return SDState;
	}

	@JsonProperty(value = "SDZip")
	public String getSDZip() {
		return SDZip;
	}

	@JsonProperty(value = "SDCountry")
	public String getSDCountry() {
		return SDCountry;
	}

	@JsonProperty(value = "SDName")
	public String getSDName() {
		return SDName;
	}

	@JsonProperty(value = "SDDistrict")
	public String getSDDistrict() {
		return SDDistrict;
	}

	@JsonProperty(value = "ReCompany")
	public String getReCompany() {
		return ReCompany;
	}

	@JsonProperty(value = "ReTel")
	public String getReTel() {
		return ReTel;
	}

	@JsonProperty(value = "ReAddr")
	public String getReAddr() {
		return ReAddr;
	}

	@JsonProperty(value = "ReCity")
	public String getReCity() {
		return ReCity;
	}

	@JsonProperty(value = "ReZip")
	public String getReZip() {
		return ReZip;
	}

	@JsonProperty(value = "ReState")
	public String getReState() {
		return ReState;
	}

	@JsonProperty(value = "ReConsinee")
	public String getReConsinee() {
		return ReConsinee;
	}

	@JsonProperty(value = "ReDistrict")
	public String getReDistrict() {
		return ReDistrict;
	}

	@JsonProperty(value = "Remark")
	public String getRemark() {
		return Remark;
	}

	@JsonProperty(value = "TransType")
	public String getTransType() {
		return TransType;
	}

	@JsonProperty(value = "InvoiceStr")
	public String getInvoiceStr() {
		return InvoiceStr;
	}

	public void setJobno(String jobno) {
		Jobno = jobno;
	}

	public void setOrderNO(String orderNO) {
		OrderNO = orderNO;
	}

	public void setClno(String clno) {
		Clno = clno;
	}

	public void setHubIn(String hubIn) {
		HubIn = hubIn;
	}

	public void setDestNO(String destNO) {
		DestNO = destNO;
	}

	public void setCCPayMent(String cCPayMent) {
		CCPayMent = cCPayMent;
	}

	public void setCODCharge(BigDecimal cODCharge) {
		CODCharge = cODCharge;
	}

	public void setDescrType(String descrType) {
		DescrType = descrType;
	}

	public void setCCTax(String cCTax) {
		CCTax = cCTax;
	}

	public void setWeig(BigDecimal weig) {
		Weig = weig;
	}

	public void setPacking(String packing) {
		Packing = packing;
	}

	public void setPcs(Integer pcs) {
		Pcs = pcs;
	}

	public void setSDTel(String sDTel) {
		SDTel = sDTel;
	}

	public void setSDAddr(String sDAddr) {
		SDAddr = sDAddr;
	}

	public void setSDCity(String sDCity) {
		SDCity = sDCity;
	}

	public void setSDState(String sDState) {
		SDState = sDState;
	}

	public void setSDZip(String sDZip) {
		SDZip = sDZip;
	}

	public void setSDCountry(String sDCountry) {
		SDCountry = sDCountry;
	}

	public void setSDName(String sDName) {
		SDName = sDName;
	}

	public void setSDDistrict(String sDDistrict) {
		SDDistrict = sDDistrict;
	}

	public void setReCompany(String reCompany) {
		ReCompany = reCompany;
	}

	public void setReTel(String reTel) {
		ReTel = reTel;
	}

	public void setReAddr(String reAddr) {
		ReAddr = reAddr;
	}

	public void setReCity(String reCity) {
		ReCity = reCity;
	}

	public void setReZip(String reZip) {
		ReZip = reZip;
	}

	public void setReState(String reState) {
		ReState = reState;
	}

	public void setReConsinee(String reConsinee) {
		ReConsinee = reConsinee;
	}

	public void setReDistrict(String reDistrict) {
		ReDistrict = reDistrict;
	}

	public void setRemark(String remark) {
		Remark = remark;
	}

	public void setTransType(String transType) {
		TransType = transType;
	}

	public void setInvoiceStr(String invoiceStr) {
		InvoiceStr = invoiceStr;
	}

	public static void main(String[] args) {
		Field[] fields = IndonesiaContent.class.getDeclaredFields();
		for (Field field : fields) {
			String typeName = field.getType().getSimpleName();
			String name = Character.toUpperCase(field.getName().charAt(0)) + field.getName().substring(1);
			String getMethod = "@JsonProperty(value=\"" + field.getName() + "\") \npublic " + typeName + " get" + name + "(){return " + field.getName() + ";}";
			logger.info(getMethod);
		}
	}
}
