package com.stosz.tms.model.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.lang.reflect.Field;

/**
 * 菲律宾XH content 实体
 * @author xiepengcheng
 */
public class PhilippineXhContent implements Serializable{

	public static final Logger logger = LoggerFactory.getLogger(PhilippineXhContent.class);

	private String Token;// 令牌

	private String CustNo;// 客户编号,如：GWICARGO

	private String MerchantOrderNumber;// 商家订单号

	private String WaybillNumber;// 运单号

	private String Sender;// 发件人

	private String SenderTelphone;// 发件人电话

	private String SenderAddress;// 发件人地址

	private String RecipientName;// 收件人

	private String RecipientTelephone;// 收件人电话

	private String ReportWeight;// 报告重量

	private String CODAmount;// 代收货款

	private String DeclaredValue;// 申报货值

	private String Remark;// 备注

	private String SenderProvince;

	private String SenderCity;

	private String SenderTown;

	private String RecipientAddress;

	private String RecipientProvince;

	private String RecipientCity;

	private String RecipientTown;

	public void setRecipientProvince(String recipientProvince) {
		RecipientProvince = recipientProvince;
	}

	public void setRecipientCity(String recipientCity) {
		RecipientCity = recipientCity;
	}

	public void setRecipientTown(String recipientTown) {
		RecipientTown = recipientTown;
	}

	public void setToken(String token) {
		Token = token;
	}

	public void setCustNo(String custNo) {
		CustNo = custNo;
	}

	public void setMerchantOrderNumber(String merchantOrderNumber) {
		MerchantOrderNumber = merchantOrderNumber;
	}

	public void setWaybillNumber(String waybillNumber) {
		WaybillNumber = waybillNumber;
	}

	public void setSender(String sender) {
		Sender = sender;
	}

	public void setSenderTelphone(String senderTelphone) {
		SenderTelphone = senderTelphone;
	}

	public void setSenderAddress(String senderAddress) {
		SenderAddress = senderAddress;
	}

	public void setRecipientName(String recipientName) {
		RecipientName = recipientName;
	}

	public void setRecipientTelephone(String recipientTelephone) {
		RecipientTelephone = recipientTelephone;
	}

	public void setReportWeight(String reportWeight) {
		ReportWeight = reportWeight;
	}

	public void setCODAmount(String CODAmount) {
		this.CODAmount = CODAmount;
	}

	public void setDeclaredValue(String declaredValue) {
		DeclaredValue = declaredValue;
	}

	public void setRemark(String remark) {
		Remark = remark;
	}

	public void setSenderProvince(String senderProvince) {
		SenderProvince = senderProvince;
	}

	public void setSenderCity(String senderCity) {
		SenderCity = senderCity;
	}

	public void setSenderTown(String senderTown) {
		SenderTown = senderTown;
	}

	public void setRecipientAddress(String recipientAddress) {
		RecipientAddress = recipientAddress;
	}

	@JsonProperty(value="RecipientProvince")
	public String getRecipientProvince(){return RecipientProvince;}
	@JsonProperty(value="RecipientCity")
	public String getRecipientCity(){return RecipientCity;}
	@JsonProperty(value="RecipientTown")
	public String getRecipientTown(){return RecipientTown;}

	@JsonProperty(value="Token")
	public String getToken(){return Token;}
	@JsonProperty(value="CustNo")
	public String getCustNo(){return CustNo;}
	@JsonProperty(value="MerchantOrderNumber")
	public String getMerchantOrderNumber(){return MerchantOrderNumber;}
	@JsonProperty(value="WaybillNumber")
	public String getWaybillNumber(){return WaybillNumber;}
	@JsonProperty(value="Sender")
	public String getSender(){return Sender;}
	@JsonProperty(value="SenderTelphone")
	public String getSenderTelphone(){return SenderTelphone;}
	@JsonProperty(value="SenderAddress")
	public String getSenderAddress(){return SenderAddress;}
	@JsonProperty(value="RecipientName")
	public String getRecipientName(){return RecipientName;}
	@JsonProperty(value="RecipientTelephone")
	public String getRecipientTelephone(){return RecipientTelephone;}
	@JsonProperty(value="ReportWeight")
	public String getReportWeight(){return ReportWeight;}
	@JsonProperty(value="CODAmount")
	public String getCODAmount(){return CODAmount;}
	@JsonProperty(value="DeclaredValue")
	public String getDeclaredValue(){return DeclaredValue;}
	@JsonProperty(value="Remark")
	public String getRemark(){return Remark;}
	@JsonProperty(value="SenderProvince")
	public String getSenderProvince(){return SenderProvince;}
	@JsonProperty(value="SenderCity")
	public String getSenderCity(){return SenderCity;}
	@JsonProperty(value="SenderTown")
	public String getSenderTown(){return SenderTown;}
	@JsonProperty(value="RecipientAddress")
	public String getRecipientAddress(){return RecipientAddress;}

	public static void main(String[] args) {
		Field[] fields = PhilippineXhContent.class.getDeclaredFields();
		for (Field field : fields) {
			String typeName = field.getType().getSimpleName();
			String name = Character.toUpperCase(field.getName().charAt(0)) + field.getName().substring(1);
			String getMethod = "@JsonProperty(value=\"" + field.getName() + "\") \npublic " + typeName + " get" + name + "(){return " + field.getName() + ";}";
			logger.info(getMethod);
		}
	}
}
