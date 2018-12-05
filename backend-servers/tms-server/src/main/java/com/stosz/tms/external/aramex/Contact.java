/**
 * Contact.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.stosz.tms.external.aramex;

public class Contact  implements java.io.Serializable {
    private java.lang.String department;

    private java.lang.String personName;

    private java.lang.String title;

    private java.lang.String companyName;

    private java.lang.String phoneNumber1;

    private java.lang.String phoneNumber1Ext;

    private java.lang.String phoneNumber2;

    private java.lang.String phoneNumber2Ext;

    private java.lang.String faxNumber;

    private java.lang.String cellPhone;

    private java.lang.String emailAddress;

    private java.lang.String type;

    public Contact() {
    }

    public Contact(
           java.lang.String department,
           java.lang.String personName,
           java.lang.String title,
           java.lang.String companyName,
           java.lang.String phoneNumber1,
           java.lang.String phoneNumber1Ext,
           java.lang.String phoneNumber2,
           java.lang.String phoneNumber2Ext,
           java.lang.String faxNumber,
           java.lang.String cellPhone,
           java.lang.String emailAddress,
           java.lang.String type) {
           this.department = department;
           this.personName = personName;
           this.title = title;
           this.companyName = companyName;
           this.phoneNumber1 = phoneNumber1;
           this.phoneNumber1Ext = phoneNumber1Ext;
           this.phoneNumber2 = phoneNumber2;
           this.phoneNumber2Ext = phoneNumber2Ext;
           this.faxNumber = faxNumber;
           this.cellPhone = cellPhone;
           this.emailAddress = emailAddress;
           this.type = type;
    }


    /**
     * Gets the department value for this Contact.
     *
     * @return department
     */
    public java.lang.String getDepartment() {
        return department;
    }


    /**
     * Sets the department value for this Contact.
     *
     * @param department
     */
    public void setDepartment(java.lang.String department) {
        this.department = department;
    }


    /**
     * Gets the personName value for this Contact.
     *
     * @return personName
     */
    public java.lang.String getPersonName() {
        return personName;
    }


    /**
     * Sets the personName value for this Contact.
     *
     * @param personName
     */
    public void setPersonName(java.lang.String personName) {
        this.personName = personName;
    }


    /**
     * Gets the title value for this Contact.
     *
     * @return title
     */
    public java.lang.String getTitle() {
        return title;
    }


    /**
     * Sets the title value for this Contact.
     *
     * @param title
     */
    public void setTitle(java.lang.String title) {
        this.title = title;
    }


    /**
     * Gets the companyName value for this Contact.
     *
     * @return companyName
     */
    public java.lang.String getCompanyName() {
        return companyName;
    }


    /**
     * Sets the companyName value for this Contact.
     *
     * @param companyName
     */
    public void setCompanyName(java.lang.String companyName) {
        this.companyName = companyName;
    }


    /**
     * Gets the phoneNumber1 value for this Contact.
     *
     * @return phoneNumber1
     */
    public java.lang.String getPhoneNumber1() {
        return phoneNumber1;
    }


    /**
     * Sets the phoneNumber1 value for this Contact.
     *
     * @param phoneNumber1
     */
    public void setPhoneNumber1(java.lang.String phoneNumber1) {
        this.phoneNumber1 = phoneNumber1;
    }


    /**
     * Gets the phoneNumber1Ext value for this Contact.
     *
     * @return phoneNumber1Ext
     */
    public java.lang.String getPhoneNumber1Ext() {
        return phoneNumber1Ext;
    }


    /**
     * Sets the phoneNumber1Ext value for this Contact.
     *
     * @param phoneNumber1Ext
     */
    public void setPhoneNumber1Ext(java.lang.String phoneNumber1Ext) {
        this.phoneNumber1Ext = phoneNumber1Ext;
    }


    /**
     * Gets the phoneNumber2 value for this Contact.
     *
     * @return phoneNumber2
     */
    public java.lang.String getPhoneNumber2() {
        return phoneNumber2;
    }


    /**
     * Sets the phoneNumber2 value for this Contact.
     *
     * @param phoneNumber2
     */
    public void setPhoneNumber2(java.lang.String phoneNumber2) {
        this.phoneNumber2 = phoneNumber2;
    }


    /**
     * Gets the phoneNumber2Ext value for this Contact.
     *
     * @return phoneNumber2Ext
     */
    public java.lang.String getPhoneNumber2Ext() {
        return phoneNumber2Ext;
    }


    /**
     * Sets the phoneNumber2Ext value for this Contact.
     *
     * @param phoneNumber2Ext
     */
    public void setPhoneNumber2Ext(java.lang.String phoneNumber2Ext) {
        this.phoneNumber2Ext = phoneNumber2Ext;
    }


    /**
     * Gets the faxNumber value for this Contact.
     *
     * @return faxNumber
     */
    public java.lang.String getFaxNumber() {
        return faxNumber;
    }


    /**
     * Sets the faxNumber value for this Contact.
     *
     * @param faxNumber
     */
    public void setFaxNumber(java.lang.String faxNumber) {
        this.faxNumber = faxNumber;
    }


    /**
     * Gets the cellPhone value for this Contact.
     *
     * @return cellPhone
     */
    public java.lang.String getCellPhone() {
        return cellPhone;
    }


    /**
     * Sets the cellPhone value for this Contact.
     *
     * @param cellPhone
     */
    public void setCellPhone(java.lang.String cellPhone) {
        this.cellPhone = cellPhone;
    }


    /**
     * Gets the emailAddress value for this Contact.
     *
     * @return emailAddress
     */
    public java.lang.String getEmailAddress() {
        return emailAddress;
    }


    /**
     * Sets the emailAddress value for this Contact.
     *
     * @param emailAddress
     */
    public void setEmailAddress(java.lang.String emailAddress) {
        this.emailAddress = emailAddress;
    }


    /**
     * Gets the type value for this Contact.
     *
     * @return type
     */
    public java.lang.String getType() {
        return type;
    }


    /**
     * Sets the type value for this Contact.
     *
     * @param type
     */
    public void setType(java.lang.String type) {
        this.type = type;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Contact)) return false;
        Contact other = (Contact) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true &&
            ((this.department==null && other.getDepartment()==null) ||
             (this.department!=null &&
              this.department.equals(other.getDepartment()))) &&
            ((this.personName==null && other.getPersonName()==null) ||
             (this.personName!=null &&
              this.personName.equals(other.getPersonName()))) &&
            ((this.title==null && other.getTitle()==null) ||
             (this.title!=null &&
              this.title.equals(other.getTitle()))) &&
            ((this.companyName==null && other.getCompanyName()==null) ||
             (this.companyName!=null &&
              this.companyName.equals(other.getCompanyName()))) &&
            ((this.phoneNumber1==null && other.getPhoneNumber1()==null) ||
             (this.phoneNumber1!=null &&
              this.phoneNumber1.equals(other.getPhoneNumber1()))) &&
            ((this.phoneNumber1Ext==null && other.getPhoneNumber1Ext()==null) ||
             (this.phoneNumber1Ext!=null &&
              this.phoneNumber1Ext.equals(other.getPhoneNumber1Ext()))) &&
            ((this.phoneNumber2==null && other.getPhoneNumber2()==null) ||
             (this.phoneNumber2!=null &&
              this.phoneNumber2.equals(other.getPhoneNumber2()))) &&
            ((this.phoneNumber2Ext==null && other.getPhoneNumber2Ext()==null) ||
             (this.phoneNumber2Ext!=null &&
              this.phoneNumber2Ext.equals(other.getPhoneNumber2Ext()))) &&
            ((this.faxNumber==null && other.getFaxNumber()==null) ||
             (this.faxNumber!=null &&
              this.faxNumber.equals(other.getFaxNumber()))) &&
            ((this.cellPhone==null && other.getCellPhone()==null) ||
             (this.cellPhone!=null &&
              this.cellPhone.equals(other.getCellPhone()))) &&
            ((this.emailAddress==null && other.getEmailAddress()==null) ||
             (this.emailAddress!=null &&
              this.emailAddress.equals(other.getEmailAddress()))) &&
            ((this.type==null && other.getType()==null) ||
             (this.type!=null &&
              this.type.equals(other.getType())));
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
        if (getDepartment() != null) {
            _hashCode += getDepartment().hashCode();
        }
        if (getPersonName() != null) {
            _hashCode += getPersonName().hashCode();
        }
        if (getTitle() != null) {
            _hashCode += getTitle().hashCode();
        }
        if (getCompanyName() != null) {
            _hashCode += getCompanyName().hashCode();
        }
        if (getPhoneNumber1() != null) {
            _hashCode += getPhoneNumber1().hashCode();
        }
        if (getPhoneNumber1Ext() != null) {
            _hashCode += getPhoneNumber1Ext().hashCode();
        }
        if (getPhoneNumber2() != null) {
            _hashCode += getPhoneNumber2().hashCode();
        }
        if (getPhoneNumber2Ext() != null) {
            _hashCode += getPhoneNumber2Ext().hashCode();
        }
        if (getFaxNumber() != null) {
            _hashCode += getFaxNumber().hashCode();
        }
        if (getCellPhone() != null) {
            _hashCode += getCellPhone().hashCode();
        }
        if (getEmailAddress() != null) {
            _hashCode += getEmailAddress().hashCode();
        }
        if (getType() != null) {
            _hashCode += getType().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Contact.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "Contact"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("department");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "Department"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("personName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "PersonName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("title");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "Title"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("companyName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "CompanyName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("phoneNumber1");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "PhoneNumber1"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("phoneNumber1Ext");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "PhoneNumber1Ext"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("phoneNumber2");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "PhoneNumber2"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("phoneNumber2Ext");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "PhoneNumber2Ext"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("faxNumber");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "FaxNumber"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cellPhone");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "CellPhone"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("emailAddress");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "EmailAddress"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("type");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "Type"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
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
