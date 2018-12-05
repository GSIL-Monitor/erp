package com.stosz.tms.model.aramex;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.lang.reflect.Field;

/**
 * 发货人信息
 * Contact -> Shipper -> Shipment -> shipments
 */
public class Contact implements Serializable{

    public static final Logger logger = LoggerFactory.getLogger(Contact.class);

    private String Department;

    private String PersonName;

    private String Title;

    private String CompanyName;

    private String PhoneNumber1;

    private String PhoneNumber1Ext;

    private String PhoneNumber2;

    private String PhoneNumber2Ext;

    private String FaxNumber;

    private String CellPhone;

    private String EmailAddress;

    private String Type;

    public void setDepartment(String department) {
        Department = department;
    }

    public void setPersonName(String personName) {
        PersonName = personName;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public void setCompanyName(String companyName) {
        CompanyName = companyName;
    }

    public void setPhoneNumber1(String phoneNumber1) {
        PhoneNumber1 = phoneNumber1;
    }

    public void setPhoneNumber1Ext(String phoneNumber1Ext) {
        PhoneNumber1Ext = phoneNumber1Ext;
    }

    public void setPhoneNumber2(String phoneNumber2) {
        PhoneNumber2 = phoneNumber2;
    }

    public void setPhoneNumber2Ext(String phoneNumber2Ext) {
        PhoneNumber2Ext = phoneNumber2Ext;
    }

    public void setFaxNumber(String faxNumber) {
        FaxNumber = faxNumber;
    }

    public void setCellPhone(String cellPhone) {
        CellPhone = cellPhone;
    }

    public void setEmailAddress(String emailAddress) {
        EmailAddress = emailAddress;
    }

    public void setType(String type) {
        Type = type;
    }

    @JsonProperty(value="Department")
    public String getDepartment(){return Department;}
    @JsonProperty(value="PersonName")
    public String getPersonName(){return PersonName;}
    @JsonProperty(value="Title")
    public String getTitle(){return Title;}
    @JsonProperty(value="CompanyName")
    public String getCompanyName(){return CompanyName;}
    @JsonProperty(value="PhoneNumber1")
    public String getPhoneNumber1(){return PhoneNumber1;}
    @JsonProperty(value="PhoneNumber1Ext")
    public String getPhoneNumber1Ext(){return PhoneNumber1Ext;}
    @JsonProperty(value="PhoneNumber2")
    public String getPhoneNumber2(){return PhoneNumber2;}
    @JsonProperty(value="PhoneNumber2Ext")
    public String getPhoneNumber2Ext(){return PhoneNumber2Ext;}
    @JsonProperty(value="FaxNumber")
    public String getFaxNumber(){return FaxNumber;}
    @JsonProperty(value="CellPhone")
    public String getCellPhone(){return CellPhone;}
    @JsonProperty(value="EmailAddress")
    public String getEmailAddress(){return EmailAddress;}
    @JsonProperty(value="Type")
    public String getType(){return Type;}
    public static void main(String[] args) {
        Field[] fields = Contact.class.getDeclaredFields();
        for (Field field : fields) {
            String typeName = field.getType().getSimpleName();
            String name = Character.toUpperCase(field.getName().charAt(0)) + field.getName().substring(1);
            String getMethod = "@JsonProperty(value=\"" + field.getName() + "\") \npublic " + typeName + " get" + name + "(){return " + field.getName() + ";}";
            logger.info(getMethod);
        }
    }
}
