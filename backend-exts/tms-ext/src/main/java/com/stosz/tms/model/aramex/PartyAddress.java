package com.stosz.tms.model.aramex;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.lang.reflect.Field;

public class PartyAddress implements Serializable{

    public static final Logger logger = LoggerFactory.getLogger(PartyAddress.class);
    private String Line1;

    private String Line2;

    private String Line3;

    private String City;

    private String StateOrProvinceCode;

    private String PostCode;

    private String CountryCode;

    public void setLine1(String line1) {
        Line1 = line1;
    }

    public void setLine2(String line2) {
        Line2 = line2;
    }

    public void setLine3(String line3) {
        Line3 = line3;
    }

    public void setCity(String city) {
        City = city;
    }

    public void setStateOrProvinceCode(String stateOrProvinceCode) {
        StateOrProvinceCode = stateOrProvinceCode;
    }

    public void setPostCode(String postCode) {
        PostCode = postCode;
    }

    public void setCountryCode(String countryCode) {
        CountryCode = countryCode;
    }

    @JsonProperty(value="Line1")
    public String getLine1(){return Line1;}
    @JsonProperty(value="Line2")
    public String getLine2(){return Line2;}
    @JsonProperty(value="Line3")
    public String getLine3(){return Line3;}
    @JsonProperty(value="City")
    public String getCity(){return City;}
    @JsonProperty(value="StateOrProvinceCode")
    public String getStateOrProvinceCode(){return StateOrProvinceCode;}
    @JsonProperty(value="PostCode")
    public String getPostCode(){return PostCode;}
    @JsonProperty(value="CountryCode")

    public static void main(String[] args) {
        Field[] fields = PartyAddress.class.getDeclaredFields();
        for (Field field : fields) {
            String typeName = field.getType().getSimpleName();
            String name = Character.toUpperCase(field.getName().charAt(0)) + field.getName().substring(1);
            String getMethod = "@JsonProperty(value=\"" + field.getName() + "\") \npublic " + typeName + " get" + name + "(){return " + field.getName() + ";}";
            logger.info(getMethod);
        }
    }
}
