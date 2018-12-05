/**
 * ClientInfo.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.stosz.tms.external.aramex;

public class ClientInfo  implements java.io.Serializable {
    private java.lang.String userName;

    private java.lang.String password;

    private java.lang.String version;

    private java.lang.String accountNumber;

    private java.lang.String accountPin;

    private java.lang.String accountEntity;

    private java.lang.String accountCountryCode;

    private java.lang.Integer source;

    private java.lang.String preferredLanguageCode;

    public ClientInfo() {
    }

    public ClientInfo(
           java.lang.String userName,
           java.lang.String password,
           java.lang.String version,
           java.lang.String accountNumber,
           java.lang.String accountPin,
           java.lang.String accountEntity,
           java.lang.String accountCountryCode,
           java.lang.Integer source,
           java.lang.String preferredLanguageCode) {
           this.userName = userName;
           this.password = password;
           this.version = version;
           this.accountNumber = accountNumber;
           this.accountPin = accountPin;
           this.accountEntity = accountEntity;
           this.accountCountryCode = accountCountryCode;
           this.source = source;
           this.preferredLanguageCode = preferredLanguageCode;
    }


    /**
     * Gets the userName value for this ClientInfo.
     *
     * @return userName
     */
    public java.lang.String getUserName() {
        return userName;
    }


    /**
     * Sets the userName value for this ClientInfo.
     *
     * @param userName
     */
    public void setUserName(java.lang.String userName) {
        this.userName = userName;
    }


    /**
     * Gets the password value for this ClientInfo.
     *
     * @return password
     */
    public java.lang.String getPassword() {
        return password;
    }


    /**
     * Sets the password value for this ClientInfo.
     *
     * @param password
     */
    public void setPassword(java.lang.String password) {
        this.password = password;
    }


    /**
     * Gets the version value for this ClientInfo.
     *
     * @return version
     */
    public java.lang.String getVersion() {
        return version;
    }


    /**
     * Sets the version value for this ClientInfo.
     *
     * @param version
     */
    public void setVersion(java.lang.String version) {
        this.version = version;
    }


    /**
     * Gets the accountNumber value for this ClientInfo.
     *
     * @return accountNumber
     */
    public java.lang.String getAccountNumber() {
        return accountNumber;
    }


    /**
     * Sets the accountNumber value for this ClientInfo.
     *
     * @param accountNumber
     */
    public void setAccountNumber(java.lang.String accountNumber) {
        this.accountNumber = accountNumber;
    }


    /**
     * Gets the accountPin value for this ClientInfo.
     *
     * @return accountPin
     */
    public java.lang.String getAccountPin() {
        return accountPin;
    }


    /**
     * Sets the accountPin value for this ClientInfo.
     *
     * @param accountPin
     */
    public void setAccountPin(java.lang.String accountPin) {
        this.accountPin = accountPin;
    }


    /**
     * Gets the accountEntity value for this ClientInfo.
     *
     * @return accountEntity
     */
    public java.lang.String getAccountEntity() {
        return accountEntity;
    }


    /**
     * Sets the accountEntity value for this ClientInfo.
     *
     * @param accountEntity
     */
    public void setAccountEntity(java.lang.String accountEntity) {
        this.accountEntity = accountEntity;
    }


    /**
     * Gets the accountCountryCode value for this ClientInfo.
     *
     * @return accountCountryCode
     */
    public java.lang.String getAccountCountryCode() {
        return accountCountryCode;
    }


    /**
     * Sets the accountCountryCode value for this ClientInfo.
     *
     * @param accountCountryCode
     */
    public void setAccountCountryCode(java.lang.String accountCountryCode) {
        this.accountCountryCode = accountCountryCode;
    }


    /**
     * Gets the source value for this ClientInfo.
     *
     * @return source
     */
    public java.lang.Integer getSource() {
        return source;
    }


    /**
     * Sets the source value for this ClientInfo.
     *
     * @param source
     */
    public void setSource(java.lang.Integer source) {
        this.source = source;
    }


    /**
     * Gets the preferredLanguageCode value for this ClientInfo.
     *
     * @return preferredLanguageCode
     */
    public java.lang.String getPreferredLanguageCode() {
        return preferredLanguageCode;
    }


    /**
     * Sets the preferredLanguageCode value for this ClientInfo.
     *
     * @param preferredLanguageCode
     */
    public void setPreferredLanguageCode(java.lang.String preferredLanguageCode) {
        this.preferredLanguageCode = preferredLanguageCode;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ClientInfo)) return false;
        ClientInfo other = (ClientInfo) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true &&
            ((this.userName==null && other.getUserName()==null) ||
             (this.userName!=null &&
              this.userName.equals(other.getUserName()))) &&
            ((this.password==null && other.getPassword()==null) ||
             (this.password!=null &&
              this.password.equals(other.getPassword()))) &&
            ((this.version==null && other.getVersion()==null) ||
             (this.version!=null &&
              this.version.equals(other.getVersion()))) &&
            ((this.accountNumber==null && other.getAccountNumber()==null) ||
             (this.accountNumber!=null &&
              this.accountNumber.equals(other.getAccountNumber()))) &&
            ((this.accountPin==null && other.getAccountPin()==null) ||
             (this.accountPin!=null &&
              this.accountPin.equals(other.getAccountPin()))) &&
            ((this.accountEntity==null && other.getAccountEntity()==null) ||
             (this.accountEntity!=null &&
              this.accountEntity.equals(other.getAccountEntity()))) &&
            ((this.accountCountryCode==null && other.getAccountCountryCode()==null) ||
             (this.accountCountryCode!=null &&
              this.accountCountryCode.equals(other.getAccountCountryCode()))) &&
            ((this.source==null && other.getSource()==null) ||
             (this.source!=null &&
              this.source.equals(other.getSource()))) &&
            ((this.preferredLanguageCode==null && other.getPreferredLanguageCode()==null) ||
             (this.preferredLanguageCode!=null &&
              this.preferredLanguageCode.equals(other.getPreferredLanguageCode())));
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
        if (getUserName() != null) {
            _hashCode += getUserName().hashCode();
        }
        if (getPassword() != null) {
            _hashCode += getPassword().hashCode();
        }
        if (getVersion() != null) {
            _hashCode += getVersion().hashCode();
        }
        if (getAccountNumber() != null) {
            _hashCode += getAccountNumber().hashCode();
        }
        if (getAccountPin() != null) {
            _hashCode += getAccountPin().hashCode();
        }
        if (getAccountEntity() != null) {
            _hashCode += getAccountEntity().hashCode();
        }
        if (getAccountCountryCode() != null) {
            _hashCode += getAccountCountryCode().hashCode();
        }
        if (getSource() != null) {
            _hashCode += getSource().hashCode();
        }
        if (getPreferredLanguageCode() != null) {
            _hashCode += getPreferredLanguageCode().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ClientInfo.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "ClientInfo"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("userName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "UserName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("password");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "Password"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("version");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "Version"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("accountNumber");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "AccountNumber"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("accountPin");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "AccountPin"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("accountEntity");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "AccountEntity"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("accountCountryCode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "AccountCountryCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("source");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "Source"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("preferredLanguageCode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "PreferredLanguageCode"));
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
