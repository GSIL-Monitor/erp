/**
 * ShipmentLabel.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.stosz.tms.external.aramex;

public class ShipmentLabel  implements java.io.Serializable {
    private java.lang.String labelURL;

    private byte[] labelFileContents;

    public ShipmentLabel() {
    }

    public ShipmentLabel(
           java.lang.String labelURL,
           byte[] labelFileContents) {
           this.labelURL = labelURL;
           this.labelFileContents = labelFileContents;
    }


    /**
     * Gets the labelURL value for this ShipmentLabel.
     *
     * @return labelURL
     */
    public java.lang.String getLabelURL() {
        return labelURL;
    }


    /**
     * Sets the labelURL value for this ShipmentLabel.
     *
     * @param labelURL
     */
    public void setLabelURL(java.lang.String labelURL) {
        this.labelURL = labelURL;
    }


    /**
     * Gets the labelFileContents value for this ShipmentLabel.
     *
     * @return labelFileContents
     */
    public byte[] getLabelFileContents() {
        return labelFileContents;
    }


    /**
     * Sets the labelFileContents value for this ShipmentLabel.
     *
     * @param labelFileContents
     */
    public void setLabelFileContents(byte[] labelFileContents) {
        this.labelFileContents = labelFileContents;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ShipmentLabel)) return false;
        ShipmentLabel other = (ShipmentLabel) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true &&
            ((this.labelURL==null && other.getLabelURL()==null) ||
             (this.labelURL!=null &&
              this.labelURL.equals(other.getLabelURL()))) &&
            ((this.labelFileContents==null && other.getLabelFileContents()==null) ||
             (this.labelFileContents!=null &&
              java.util.Arrays.equals(this.labelFileContents, other.getLabelFileContents())));
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
        if (getLabelURL() != null) {
            _hashCode += getLabelURL().hashCode();
        }
        if (getLabelFileContents() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getLabelFileContents());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getLabelFileContents(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ShipmentLabel.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "ShipmentLabel"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("labelURL");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "LabelURL"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("labelFileContents");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "LabelFileContents"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "base64Binary"));
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
