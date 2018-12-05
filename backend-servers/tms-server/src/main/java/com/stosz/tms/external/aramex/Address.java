/**
 * Address.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.stosz.tms.external.aramex;

public class Address  implements java.io.Serializable {
    private java.lang.String line1;

    private java.lang.String line2;

    private java.lang.String line3;

    private java.lang.String city;

    private java.lang.String stateOrProvinceCode;

    private java.lang.String postCode;

    private java.lang.String countryCode;

    private java.math.BigDecimal longitude;

    private java.math.BigDecimal latitude;

    private java.lang.String buildingNumber;

    private java.lang.String buildingName;

    private java.lang.String floor;

    private java.lang.String apartment;

    private java.lang.String POBox;

    private java.lang.String description;

    public Address() {
    }

    public Address(
           java.lang.String line1,
           java.lang.String line2,
           java.lang.String line3,
           java.lang.String city,
           java.lang.String stateOrProvinceCode,
           java.lang.String postCode,
           java.lang.String countryCode,
           java.math.BigDecimal longitude,
           java.math.BigDecimal latitude,
           java.lang.String buildingNumber,
           java.lang.String buildingName,
           java.lang.String floor,
           java.lang.String apartment,
           java.lang.String POBox,
           java.lang.String description) {
           this.line1 = line1;
           this.line2 = line2;
           this.line3 = line3;
           this.city = city;
           this.stateOrProvinceCode = stateOrProvinceCode;
           this.postCode = postCode;
           this.countryCode = countryCode;
           this.longitude = longitude;
           this.latitude = latitude;
           this.buildingNumber = buildingNumber;
           this.buildingName = buildingName;
           this.floor = floor;
           this.apartment = apartment;
           this.POBox = POBox;
           this.description = description;
    }


    /**
     * Gets the line1 value for this Address.
     *
     * @return line1
     */
    public java.lang.String getLine1() {
        return line1;
    }


    /**
     * Sets the line1 value for this Address.
     *
     * @param line1
     */
    public void setLine1(java.lang.String line1) {
        this.line1 = line1;
    }


    /**
     * Gets the line2 value for this Address.
     *
     * @return line2
     */
    public java.lang.String getLine2() {
        return line2;
    }


    /**
     * Sets the line2 value for this Address.
     *
     * @param line2
     */
    public void setLine2(java.lang.String line2) {
        this.line2 = line2;
    }


    /**
     * Gets the line3 value for this Address.
     *
     * @return line3
     */
    public java.lang.String getLine3() {
        return line3;
    }


    /**
     * Sets the line3 value for this Address.
     *
     * @param line3
     */
    public void setLine3(java.lang.String line3) {
        this.line3 = line3;
    }


    /**
     * Gets the city value for this Address.
     *
     * @return city
     */
    public java.lang.String getCity() {
        return city;
    }


    /**
     * Sets the city value for this Address.
     *
     * @param city
     */
    public void setCity(java.lang.String city) {
        this.city = city;
    }


    /**
     * Gets the stateOrProvinceCode value for this Address.
     *
     * @return stateOrProvinceCode
     */
    public java.lang.String getStateOrProvinceCode() {
        return stateOrProvinceCode;
    }


    /**
     * Sets the stateOrProvinceCode value for this Address.
     *
     * @param stateOrProvinceCode
     */
    public void setStateOrProvinceCode(java.lang.String stateOrProvinceCode) {
        this.stateOrProvinceCode = stateOrProvinceCode;
    }


    /**
     * Gets the postCode value for this Address.
     *
     * @return postCode
     */
    public java.lang.String getPostCode() {
        return postCode;
    }


    /**
     * Sets the postCode value for this Address.
     *
     * @param postCode
     */
    public void setPostCode(java.lang.String postCode) {
        this.postCode = postCode;
    }


    /**
     * Gets the countryCode value for this Address.
     *
     * @return countryCode
     */
    public java.lang.String getCountryCode() {
        return countryCode;
    }


    /**
     * Sets the countryCode value for this Address.
     *
     * @param countryCode
     */
    public void setCountryCode(java.lang.String countryCode) {
        this.countryCode = countryCode;
    }


    /**
     * Gets the longitude value for this Address.
     *
     * @return longitude
     */
    public java.math.BigDecimal getLongitude() {
        return longitude;
    }


    /**
     * Sets the longitude value for this Address.
     *
     * @param longitude
     */
    public void setLongitude(java.math.BigDecimal longitude) {
        this.longitude = longitude;
    }


    /**
     * Gets the latitude value for this Address.
     *
     * @return latitude
     */
    public java.math.BigDecimal getLatitude() {
        return latitude;
    }


    /**
     * Sets the latitude value for this Address.
     *
     * @param latitude
     */
    public void setLatitude(java.math.BigDecimal latitude) {
        this.latitude = latitude;
    }


    /**
     * Gets the buildingNumber value for this Address.
     *
     * @return buildingNumber
     */
    public java.lang.String getBuildingNumber() {
        return buildingNumber;
    }


    /**
     * Sets the buildingNumber value for this Address.
     *
     * @param buildingNumber
     */
    public void setBuildingNumber(java.lang.String buildingNumber) {
        this.buildingNumber = buildingNumber;
    }


    /**
     * Gets the buildingName value for this Address.
     *
     * @return buildingName
     */
    public java.lang.String getBuildingName() {
        return buildingName;
    }


    /**
     * Sets the buildingName value for this Address.
     *
     * @param buildingName
     */
    public void setBuildingName(java.lang.String buildingName) {
        this.buildingName = buildingName;
    }


    /**
     * Gets the floor value for this Address.
     *
     * @return floor
     */
    public java.lang.String getFloor() {
        return floor;
    }


    /**
     * Sets the floor value for this Address.
     *
     * @param floor
     */
    public void setFloor(java.lang.String floor) {
        this.floor = floor;
    }


    /**
     * Gets the apartment value for this Address.
     *
     * @return apartment
     */
    public java.lang.String getApartment() {
        return apartment;
    }


    /**
     * Sets the apartment value for this Address.
     *
     * @param apartment
     */
    public void setApartment(java.lang.String apartment) {
        this.apartment = apartment;
    }


    /**
     * Gets the POBox value for this Address.
     *
     * @return POBox
     */
    public java.lang.String getPOBox() {
        return POBox;
    }


    /**
     * Sets the POBox value for this Address.
     *
     * @param POBox
     */
    public void setPOBox(java.lang.String POBox) {
        this.POBox = POBox;
    }


    /**
     * Gets the description value for this Address.
     *
     * @return description
     */
    public java.lang.String getDescription() {
        return description;
    }


    /**
     * Sets the description value for this Address.
     *
     * @param description
     */
    public void setDescription(java.lang.String description) {
        this.description = description;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Address)) return false;
        Address other = (Address) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true &&
            ((this.line1==null && other.getLine1()==null) ||
             (this.line1!=null &&
              this.line1.equals(other.getLine1()))) &&
            ((this.line2==null && other.getLine2()==null) ||
             (this.line2!=null &&
              this.line2.equals(other.getLine2()))) &&
            ((this.line3==null && other.getLine3()==null) ||
             (this.line3!=null &&
              this.line3.equals(other.getLine3()))) &&
            ((this.city==null && other.getCity()==null) ||
             (this.city!=null &&
              this.city.equals(other.getCity()))) &&
            ((this.stateOrProvinceCode==null && other.getStateOrProvinceCode()==null) ||
             (this.stateOrProvinceCode!=null &&
              this.stateOrProvinceCode.equals(other.getStateOrProvinceCode()))) &&
            ((this.postCode==null && other.getPostCode()==null) ||
             (this.postCode!=null &&
              this.postCode.equals(other.getPostCode()))) &&
            ((this.countryCode==null && other.getCountryCode()==null) ||
             (this.countryCode!=null &&
              this.countryCode.equals(other.getCountryCode()))) &&
            ((this.longitude==null && other.getLongitude()==null) ||
             (this.longitude!=null &&
              this.longitude.equals(other.getLongitude()))) &&
            ((this.latitude==null && other.getLatitude()==null) ||
             (this.latitude!=null &&
              this.latitude.equals(other.getLatitude()))) &&
            ((this.buildingNumber==null && other.getBuildingNumber()==null) ||
             (this.buildingNumber!=null &&
              this.buildingNumber.equals(other.getBuildingNumber()))) &&
            ((this.buildingName==null && other.getBuildingName()==null) ||
             (this.buildingName!=null &&
              this.buildingName.equals(other.getBuildingName()))) &&
            ((this.floor==null && other.getFloor()==null) ||
             (this.floor!=null &&
              this.floor.equals(other.getFloor()))) &&
            ((this.apartment==null && other.getApartment()==null) ||
             (this.apartment!=null &&
              this.apartment.equals(other.getApartment()))) &&
            ((this.POBox==null && other.getPOBox()==null) ||
             (this.POBox!=null &&
              this.POBox.equals(other.getPOBox()))) &&
            ((this.description==null && other.getDescription()==null) ||
             (this.description!=null &&
              this.description.equals(other.getDescription())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = 1;
        if (getLine1() != null) {
            _hashCode += getLine1().hashCode();
        }
        if (getLine2() != null) {
            _hashCode += getLine2().hashCode();
        }
        if (getLine3() != null) {
            _hashCode += getLine3().hashCode();
        }
        if (getCity() != null) {
            _hashCode += getCity().hashCode();
        }
        if (getStateOrProvinceCode() != null) {
            _hashCode += getStateOrProvinceCode().hashCode();
        }
        if (getPostCode() != null) {
            _hashCode += getPostCode().hashCode();
        }
        if (getCountryCode() != null) {
            _hashCode += getCountryCode().hashCode();
        }
        if (getLongitude() != null) {
            _hashCode += getLongitude().hashCode();
        }
        if (getLatitude() != null) {
            _hashCode += getLatitude().hashCode();
        }
        if (getBuildingNumber() != null) {
            _hashCode += getBuildingNumber().hashCode();
        }
        if (getBuildingName() != null) {
            _hashCode += getBuildingName().hashCode();
        }
        if (getFloor() != null) {
            _hashCode += getFloor().hashCode();
        }
        if (getApartment() != null) {
            _hashCode += getApartment().hashCode();
        }
        if (getPOBox() != null) {
            _hashCode += getPOBox().hashCode();
        }
        if (getDescription() != null) {
            _hashCode += getDescription().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Address.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "Address"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("line1");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "Line1"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("line2");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "Line2"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("line3");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "Line3"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("city");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "City"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("stateOrProvinceCode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "StateOrProvinceCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("postCode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "PostCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("countryCode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "CountryCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("longitude");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "Longitude"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("latitude");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "Latitude"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("buildingNumber");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "BuildingNumber"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("buildingName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "BuildingName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("floor");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "Floor"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("apartment");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "Apartment"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("POBox");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "POBox"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("description");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "Description"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
    }

    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

    /**
     * Get Custom Serializer
     */
    public static org.apache.axis.encoding.Serializer getSerializer(
           java.lang.String mechType,
           java.lang.Class _javaType,
           javax.xml.namespace.QName _xmlType) {
        return
          new  org.apache.axis.encoding.ser.BeanSerializer(
            _javaType, _xmlType, typeDesc);
    }

    /**
     * Get Custom Deserializer
     */
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           java.lang.String mechType,
           java.lang.Class _javaType,
           javax.xml.namespace.QName _xmlType) {
        return
          new  org.apache.axis.encoding.ser.BeanDeserializer(
            _javaType, _xmlType, typeDesc);
    }

}
