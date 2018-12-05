package com.stosz.tms.model.aramex;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.lang.reflect.Field;

/**
 * shipper-shipment-shipments-shiper
 * creatshipment 接口中shipper参数和ThirdParty参数字段一模一样!!!
 * @author xiepengcheng
 * @version [1.0,2017年12月6日]
 */
public class Shipper implements Serializable{

	public static final Logger logger = LoggerFactory.getLogger(Shipper.class);

	private String Reference1;

	private String Reference2;

	private String AccountNumber;

	private PartyAddress PartyAddress;

	private Contact Contact;

	public void setReference1(String reference1) {
		Reference1 = reference1;
	}

	public void setReference2(String reference2) {
		Reference2 = reference2;
	}

	public void setAccountNumber(String accountNumber) {
		AccountNumber = accountNumber;
	}

	public void setPartyAddress(com.stosz.tms.model.aramex.PartyAddress partyAddress) {
		PartyAddress = partyAddress;
	}

	public void setContact(com.stosz.tms.model.aramex.Contact contact) {
		Contact = contact;
	}

	@JsonProperty(value="Reference1")
	public String getReference1(){return Reference1;}
	@JsonProperty(value="Reference2")
	public String getReference2(){return Reference2;}
	@JsonProperty(value="AccountNumber")
	public String getAccountNumber(){return AccountNumber;}
	@JsonProperty(value="PartyAddress")
	public PartyAddress getPartyAddress(){return PartyAddress;}
	@JsonProperty(value="Contact")
	public Contact getContact(){return Contact;}

	public static void main(String[] args) {
		Field[] fields = Shipper.class.getDeclaredFields();
		for (Field field : fields) {
			String typeName = field.getType().getSimpleName();
			String name = Character.toUpperCase(field.getName().charAt(0)) + field.getName().substring(1);
			String getMethod = "@JsonProperty(value=\"" + field.getName() + "\") \npublic " + typeName + " get" + name + "(){return " + field.getName() + ";}";
			logger.info(getMethod);
		}
	}
}
