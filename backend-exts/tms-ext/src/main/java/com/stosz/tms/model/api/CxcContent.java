package com.stosz.tms.model.api;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Cxc content 实体
 * @author xiepengcheng
 * @version [1.0,2017年12月1日]
 */
public class CxcContent implements Serializable{

//	private Long cmId;

	private String cmCode;// 客户编号	Y	CXC系统中的客户编号

//	private String cmName;//客户名称

	private String bdShipmentno;// 批号	N	可与订单号一致

	private String bdId;

	private String bdCode;// 运单编号	Y	快件运单编号

	private String bdCaddress;// 派送详细地址	Y

	private String bdConsigneename;// 收件人	Y

	private String bdConsigneephone;// 收件人电话	Y

	private Integer bdGoodsnum;// 货物数量	N

	private Double bdWeidght;// 重量（KG）	Y

	private Double bdLength;// 长度	N

	private Double bdHeight;// 高度	N

	private Double bdWidth;// 宽度	N

	private String bdBoxcode;// 标准箱代码	N

	private String bdCodpay;// COD到付(到付业务)	N

	private String bdRemark;// 备注	N

	private Date createdate;// 创建时间	Y	yyyy-MM-dd HH:mm:ss

	private String bdPackageno;// 包号	Y

	private BigDecimal bdProductprice;// 保价	N

	private BigDecimal bdPremium;// 保费	N	例如：0.005

	private BigDecimal bdProcedurefee;// 手续费	N	例如：0.003

	private BigDecimal bdPurprice;// 代收货款（COD业务）	N

	private String bdProductname;// 商品名称	N	产品名称


	public String getBdId() {
		return bdId;
	}

	public void setBdId(String bdId) {
		this.bdId = bdId;
	}

	public String getBdPackageno() {
		return bdPackageno;
	}

	public void setBdPackageno(String bdPackageno) {
		this.bdPackageno = bdPackageno;
	}

	public String getCmCode() {
		return cmCode;
	}

	public void setCmCode(String cmCode) {
		this.cmCode = cmCode;
	}

	public String getBdShipmentno() {
		return bdShipmentno;
	}

	public void setBdShipmentno(String bdShipmentno) {
		this.bdShipmentno = bdShipmentno;
	}

	public String getBdCode() {
		return bdCode;
	}

	public void setBdCode(String bdCode) {
		this.bdCode = bdCode;
	}

	public String getBdCaddress() {
		return bdCaddress;
	}

	public void setBdCaddress(String bdCaddress) {
		this.bdCaddress = bdCaddress;
	}

	public String getBdConsigneename() {
		return bdConsigneename;
	}

	public void setBdConsigneename(String bdConsigneename) {
		this.bdConsigneename = bdConsigneename;
	}

	public String getBdConsigneephone() {
		return bdConsigneephone;
	}

	public void setBdConsigneephone(String bdConsigneephone) {
		this.bdConsigneephone = bdConsigneephone;
	}

	public Integer getBdGoodsnum() {
		return bdGoodsnum;
	}

	public void setBdGoodsnum(Integer bdGoodsnum) {
		this.bdGoodsnum = bdGoodsnum;
	}

	public Double getBdWeidght() {
		return bdWeidght;
	}

	public void setBdWeidght(Double bdWeidght) {
		this.bdWeidght = bdWeidght;
	}

	public Double getBdLength() {
		return bdLength;
	}

	public void setBdLength(Double bdLength) {
		this.bdLength = bdLength;
	}

	public Double getBdHeight() {
		return bdHeight;
	}

	public void setBdHeight(Double bdHeight) {
		this.bdHeight = bdHeight;
	}

	public Double getBdWidth() {
		return bdWidth;
	}

	public void setBdWidth(Double bdWidth) {
		this.bdWidth = bdWidth;
	}

	public String getBdBoxcode() {
		return bdBoxcode;
	}

	public void setBdBoxcode(String bdBoxcode) {
		this.bdBoxcode = bdBoxcode;
	}

	public String getBdCodpay() {
		return bdCodpay;
	}

	public void setBdCodpay(String bdCodpay) {
		this.bdCodpay = bdCodpay;
	}

	public String getBdRemark() {
		return bdRemark;
	}

	public void setBdRemark(String bdRemark) {
		this.bdRemark = bdRemark;
	}

	public Date getCreatedate() {
		return createdate;
	}

	public void setCreatedate(Date createdate) {
		this.createdate = createdate;
	}

	public BigDecimal getBdProductprice() {
		return bdProductprice;
	}

	public void setBdProductprice(BigDecimal bdProductprice) {
		this.bdProductprice = bdProductprice;
	}

	public BigDecimal getBdPremium() {
		return bdPremium;
	}

	public void setBdPremium(BigDecimal bdPremium) {
		this.bdPremium = bdPremium;
	}

	public BigDecimal getBdProcedurefee() {
		return bdProcedurefee;
	}

	public void setBdProcedurefee(BigDecimal bdProcedurefee) {
		this.bdProcedurefee = bdProcedurefee;
	}

	public BigDecimal getBdPurprice() {
		return bdPurprice;
	}

	public void setBdPurprice(BigDecimal bdPurprice) {
		this.bdPurprice = bdPurprice;
	}

	public String getBdProductname() {
		return bdProductname;
	}

	public void setBdProductname(String bdProductname) {
		this.bdProductname = bdProductname;
	}
}
