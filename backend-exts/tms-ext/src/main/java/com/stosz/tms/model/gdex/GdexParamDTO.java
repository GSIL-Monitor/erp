package com.stosz.tms.model.gdex;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;

public class GdexParamDTO {

    public static final Logger logger = LoggerFactory.getLogger(GdexParamDTO.class);

    private String GdexCN;//运单号

    private String SendingAgentItemNumber;//

    private String Product;

    private String AccountNumber;

    private String ReferenceID;

    private String ConsignmentDate;

    private Integer Pieces;

    private String Weight;

    private String ConsigneeCompany;

    private String ConsigneeName;

    private String ConsigneeAddress1;

    private String ConsigneeAddress2;

    private String ConsigneeAddress3;

    private String Town;

    private String State;

    private String Postcode;

    private String ConsigneeContactNumber1;

    private String ConsigneeContactNumber2;

    private String ShipmentType;

    private String CODPayment;

    private String Remarks;

    private String Height;

    private String Width;

    private String Length;

    private String PickupType;

    private String ProductDesc;

    public void setGdexCN(String gdexCN) {
        GdexCN = gdexCN;
    }

    public void setSendingAgentItemNumber(String sendingAgentItemNumber) {
        SendingAgentItemNumber = sendingAgentItemNumber;
    }

    public void setProduct(String product) {
        Product = product;
    }

    public void setAccountNumber(String accountNumber) {
        AccountNumber = accountNumber;
    }

    public void setReferenceID(String referenceID) {
        ReferenceID = referenceID;
    }

    public void setConsignmentDate(String consignmentDate) {
        ConsignmentDate = consignmentDate;
    }

    public void setWeight(String weight) {
        Weight = weight;
    }

    public void setConsigneeCompany(String consigneeCompany) {
        ConsigneeCompany = consigneeCompany;
    }

    public void setConsigneeName(String consigneeName) {
        ConsigneeName = consigneeName;
    }

    public void setConsigneeAddress1(String consigneeAddress1) {
        ConsigneeAddress1 = consigneeAddress1;
    }

    public void setConsigneeAddress2(String consigneeAddress2) {
        ConsigneeAddress2 = consigneeAddress2;
    }

    public void setConsigneeAddress3(String consigneeAddress3) {
        ConsigneeAddress3 = consigneeAddress3;
    }

    public void setTown(String town) {
        Town = town;
    }

    public void setState(String state) {
        State = state;
    }

    public void setPostcode(String postcode) {
        Postcode = postcode;
    }

    public void setConsigneeContactNumber1(String consigneeContactNumber1) {
        ConsigneeContactNumber1 = consigneeContactNumber1;
    }

    public void setConsigneeContactNumber2(String consigneeContactNumber2) {
        ConsigneeContactNumber2 = consigneeContactNumber2;
    }

    public void setShipmentType(String shipmentType) {
        ShipmentType = shipmentType;
    }

    public void setCODPayment(String CODPayment) {
        this.CODPayment = CODPayment;
    }

    public void setRemarks(String remarks) {
        Remarks = remarks;
    }

    public void setHeight(String height) {
        Height = height;
    }

    public void setWidth(String width) {
        Width = width;
    }

    public void setLength(String length) {
        Length = length;
    }

    public void setPickupType(String pickupType) {
        PickupType = pickupType;
    }

    public void setProductDesc(String productDesc) {
        ProductDesc = productDesc;
    }

    public void setPieces(Integer pieces) {
        Pieces = pieces;
    }

    @JsonProperty(value="GdexCN")
    public String getGdexCN(){return GdexCN;}
    @JsonProperty(value="SendingAgentItemNumber")
    public String getSendingAgentItemNumber(){return SendingAgentItemNumber;}
    @JsonProperty(value="Product")
    public String getProduct(){return Product;}
    @JsonProperty(value="AccountNumber")
    public String getAccountNumber(){return AccountNumber;}
    @JsonProperty(value="ReferenceID")
    public String getReferenceID(){return ReferenceID;}
    @JsonProperty(value="ConsignmentDate")
    public String getConsignmentDate(){return ConsignmentDate;}
    @JsonProperty(value="Pieces")
    public Integer getPieces(){return Pieces;}
    @JsonProperty(value="Weight")
    public String getWeight(){return Weight;}
    @JsonProperty(value="ConsigneeCompany")
    public String getConsigneeCompany(){return ConsigneeCompany;}
    @JsonProperty(value="ConsigneeName")
    public String getConsigneeName(){return ConsigneeName;}
    @JsonProperty(value="ConsigneeAddress1")
    public String getConsigneeAddress1(){return ConsigneeAddress1;}
    @JsonProperty(value="ConsigneeAddress2")
    public String getConsigneeAddress2(){return ConsigneeAddress2;}
    @JsonProperty(value="ConsigneeAddress3")
    public String getConsigneeAddress3(){return ConsigneeAddress3;}
    @JsonProperty(value="Town")
    public String getTown(){return Town;}
    @JsonProperty(value="State")
    public String getState(){return State;}
    @JsonProperty(value="Postcode")
    public String getPostcode(){return Postcode;}
    @JsonProperty(value="ConsigneeContactNumber1")
    public String getConsigneeContactNumber1(){return ConsigneeContactNumber1;}
    @JsonProperty(value="ConsigneeContactNumber2")
    public String getConsigneeContactNumber2(){return ConsigneeContactNumber2;}
    @JsonProperty(value="ShipmentType")
    public String getShipmentType(){return ShipmentType;}
    @JsonProperty(value="CODPayment")
    public String getCODPayment(){return CODPayment;}
    @JsonProperty(value="Remarks")
    public String getRemarks(){return Remarks;}
    @JsonProperty(value="Height")
    public String getHeight(){return Height;}
    @JsonProperty(value="Width")
    public String getWidth(){return Width;}
    @JsonProperty(value="Length")
    public String getLength(){return Length;}
    @JsonProperty(value="PickupType")
    public String getPickupType(){return PickupType;}
    @JsonProperty(value="ProductDesc")
    public String getProductDesc(){return ProductDesc;}

    public static void main(String[] args) {
        Field[] fields = GdexParamDTO.class.getDeclaredFields();
        for (Field field : fields) {
            String typeName = field.getType().getSimpleName();
            String name = Character.toUpperCase(field.getName().charAt(0)) + field.getName().substring(1);
            String getMethod = "@JsonProperty(value=\"" + field.getName() + "\") \npublic " + typeName + " get" + name + "(){return " + field.getName() + ";}";
            logger.info(getMethod);
        }
    }

}
