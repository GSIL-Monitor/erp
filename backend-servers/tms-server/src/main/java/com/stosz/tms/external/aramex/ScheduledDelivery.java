/**
 * ScheduledDelivery.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.stosz.tms.external.aramex;

public class ScheduledDelivery  implements java.io.Serializable {
    private java.util.Calendar preferredDeliveryDate;

    private java.lang.String preferredDeliveryTimeFrame_x0020_;

    private java.lang.String preferredDeliveryTime;

    public ScheduledDelivery() {
    }

    public ScheduledDelivery(
           java.util.Calendar preferredDeliveryDate,
           java.lang.String preferredDeliveryTimeFrame_x0020_,
           java.lang.String preferredDeliveryTime) {
           this.preferredDeliveryDate = preferredDeliveryDate;
           this.preferredDeliveryTimeFrame_x0020_ = preferredDeliveryTimeFrame_x0020_;
           this.preferredDeliveryTime = preferredDeliveryTime;
    }


    /**
     * Gets the preferredDeliveryDate value for this ScheduledDelivery.
     *
     * @return preferredDeliveryDate
     */
    public java.util.Calendar getPreferredDeliveryDate() {
        return preferredDeliveryDate;
    }


    /**
     * Sets the preferredDeliveryDate value for this ScheduledDelivery.
     *
     * @param preferredDeliveryDate
     */
    public void setPreferredDeliveryDate(java.util.Calendar preferredDeliveryDate) {
        this.preferredDeliveryDate = preferredDeliveryDate;
    }


    /**
     * Gets the preferredDeliveryTimeFrame_x0020_ value for this ScheduledDelivery.
     *
     * @return preferredDeliveryTimeFrame_x0020_
     */
    public java.lang.String getPreferredDeliveryTimeFrame_x0020_() {
        return preferredDeliveryTimeFrame_x0020_;
    }


    /**
     * Sets the preferredDeliveryTimeFrame_x0020_ value for this ScheduledDelivery.
     *
     * @param preferredDeliveryTimeFrame_x0020_
     */
    public void setPreferredDeliveryTimeFrame_x0020_(java.lang.String preferredDeliveryTimeFrame_x0020_) {
        this.preferredDeliveryTimeFrame_x0020_ = preferredDeliveryTimeFrame_x0020_;
    }


    /**
     * Gets the preferredDeliveryTime value for this ScheduledDelivery.
     *
     * @return preferredDeliveryTime
     */
    public java.lang.String getPreferredDeliveryTime() {
        return preferredDeliveryTime;
    }


    /**
     * Sets the preferredDeliveryTime value for this ScheduledDelivery.
     *
     * @param preferredDeliveryTime
     */
    public void setPreferredDeliveryTime(java.lang.String preferredDeliveryTime) {
        this.preferredDeliveryTime = preferredDeliveryTime;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ScheduledDelivery)) return false;
        ScheduledDelivery other = (ScheduledDelivery) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true &&
            ((this.preferredDeliveryDate==null && other.getPreferredDeliveryDate()==null) ||
             (this.preferredDeliveryDate!=null &&
              this.preferredDeliveryDate.equals(other.getPreferredDeliveryDate()))) &&
            ((this.preferredDeliveryTimeFrame_x0020_==null && other.getPreferredDeliveryTimeFrame_x0020_()==null) ||
             (this.preferredDeliveryTimeFrame_x0020_!=null &&
              this.preferredDeliveryTimeFrame_x0020_.equals(other.getPreferredDeliveryTimeFrame_x0020_()))) &&
            ((this.preferredDeliveryTime==null && other.getPreferredDeliveryTime()==null) ||
             (this.preferredDeliveryTime!=null &&
              this.preferredDeliveryTime.equals(other.getPreferredDeliveryTime())));
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
        if (getPreferredDeliveryDate() != null) {
            _hashCode += getPreferredDeliveryDate().hashCode();
        }
        if (getPreferredDeliveryTimeFrame_x0020_() != null) {
            _hashCode += getPreferredDeliveryTimeFrame_x0020_().hashCode();
        }
        if (getPreferredDeliveryTime() != null) {
            _hashCode += getPreferredDeliveryTime().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ScheduledDelivery.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "ScheduledDelivery"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("preferredDeliveryDate");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "PreferredDeliveryDate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("preferredDeliveryTimeFrame_x0020_");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "PreferredDeliveryTimeFrame_x0020_"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("preferredDeliveryTime");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "PreferredDeliveryTime"));
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
