package com.stosz.tms.model.aramex;


import com.fasterxml.jackson.annotation.JsonProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.List;

/**
 * shipment -> shipments -> aramexContent
 * @author xiepengcheng
 * @version [1.0,2017年12月6日]
 */
public class Shipment implements Serializable{

	public static final Logger logger = LoggerFactory.getLogger(Shipment.class);

	private String Number;

	private List<Attachment> Attachment;

	private Shipper Shipper;

	private ThirdParty thirdParty;

	private String Reference1;

	private String Reference2;

	private String Reference3;

	private String ForeignHAWB;

	private String TransportType;

	private String PickupLocation;

	private String PickupGUID;

	private String Comments;

	private String AccountingInstrcutions;

	private String OperationsInstructions;

	public void setReference1(String reference1) {
		Reference1 = reference1;
	}

	public void setReference2(String reference2) {
		Reference2 = reference2;
	}

	public void setReference3(String reference3) {
		Reference3 = reference3;
	}

	public void setNumber(String number) {
		Number = number;
	}

	public void setAttachment(List<com.stosz.tms.model.aramex.Attachment> attachment) {
		Attachment = attachment;
	}

	public void setForeignHAWB(String foreignHAWB) {
		ForeignHAWB = foreignHAWB;
	}

	public void setTransportType(String transportType) {
		TransportType = transportType;
	}

	public void setShipper(com.stosz.tms.model.aramex.Shipper shipper) {
		Shipper = shipper;
	}

	public void setThirdParty(ThirdParty thirdParty) {
		this.thirdParty = thirdParty;
	}

	public void setPickupLocation(String pickupLocation) {
		PickupLocation = pickupLocation;
	}

	public void setPickupGUID(String pickupGUID) {
		PickupGUID = pickupGUID;
	}

	public void setComments(String comments) {
		Comments = comments;
	}

	public void setAccountingInstrcutions(String accountingInstrcutions) {
		AccountingInstrcutions = accountingInstrcutions;
	}

	public void setOperationsInstructions(String operationsInstructions) {
		OperationsInstructions = operationsInstructions;
	}

	@JsonProperty(value="Reference1")
	public String getReference1(){return Reference1;}
	@JsonProperty(value="Reference2")
	public String getReference2(){return Reference2;}
	@JsonProperty(value="Reference3")
	public String getReference3(){return Reference3;}
	@JsonProperty(value="Number")
	public String getNumber(){return Number;}
	@JsonProperty(value="Attachment")
	public List getAttachment(){return Attachment;}
	@JsonProperty(value="ForeignHAWB")
	public String getForeignHAWB(){return ForeignHAWB;}
	@JsonProperty(value="TransportType")
	public String getTransportType(){return TransportType;}
	@JsonProperty(value="Shipper")
	public Shipper getShipper(){return Shipper;}
	@JsonProperty(value="ThirdParty")
	public ThirdParty getThirdParty(){return thirdParty;}
	@JsonProperty(value="PickupLocation")
	public String getPickupLocation(){return PickupLocation;}
	@JsonProperty(value="PickupGUID")
	public String getPickupGUID(){return PickupGUID;}
	@JsonProperty(value="Comments")
	public String getComments(){return Comments;}
	@JsonProperty(value="AccountingInstrcutions")
	public String getAccountingInstrcutions(){return AccountingInstrcutions;}
	@JsonProperty(value="OperationsInstructions")
	public String getOperationsInstructions(){return OperationsInstructions;}
	public static void main(String[] args) {
		Field[] fields = Shipment.class.getDeclaredFields();
		for (Field field : fields) {
			String typeName = field.getType().getSimpleName();
			String name = Character.toUpperCase(field.getName().charAt(0)) + field.getName().substring(1);
			String getMethod = "@JsonProperty(value=\"" + field.getName() + "\") \npublic " + typeName + " get" + name + "(){return " + field.getName() + ";}";
			logger.info(getMethod);
		}
	}
}
