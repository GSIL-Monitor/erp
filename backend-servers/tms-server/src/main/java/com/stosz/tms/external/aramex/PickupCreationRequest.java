/**
 * PickupCreationRequest.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.stosz.tms.external.aramex;

public class PickupCreationRequest  implements java.io.Serializable {
    private com.stosz.tms.external.aramex.ClientInfo clientInfo;

    private com.stosz.tms.external.aramex.Transaction transaction;

    private com.stosz.tms.external.aramex.Pickup pickup;

    private com.stosz.tms.external.aramex.LabelInfo labelInfo;

    public PickupCreationRequest() {
    }

    public PickupCreationRequest(
           com.stosz.tms.external.aramex.ClientInfo clientInfo,
           com.stosz.tms.external.aramex.Transaction transaction,
           com.stosz.tms.external.aramex.Pickup pickup,
           com.stosz.tms.external.aramex.LabelInfo labelInfo) {
           this.clientInfo = clientInfo;
           this.transaction = transaction;
           this.pickup = pickup;
           this.labelInfo = labelInfo;
    }


    /**
     * Gets the clientInfo value for this PickupCreationRequest.
     *
     * @return clientInfo
     */
    public com.stosz.tms.external.aramex.ClientInfo getClientInfo() {
        return clientInfo;
    }


    /**
     * Sets the clientInfo value for this PickupCreationRequest.
     *
     * @param clientInfo
     */
    public void setClientInfo(com.stosz.tms.external.aramex.ClientInfo clientInfo) {
        this.clientInfo = clientInfo;
    }


    /**
     * Gets the transaction value for this PickupCreationRequest.
     *
     * @return transaction
     */
    public com.stosz.tms.external.aramex.Transaction getTransaction() {
        return transaction;
    }


    /**
     * Sets the transaction value for this PickupCreationRequest.
     *
     * @param transaction
     */
    public void setTransaction(com.stosz.tms.external.aramex.Transaction transaction) {
        this.transaction = transaction;
    }


    /**
     * Gets the pickup value for this PickupCreationRequest.
     *
     * @return pickup
     */
    public com.stosz.tms.external.aramex.Pickup getPickup() {
        return pickup;
    }


    /**
     * Sets the pickup value for this PickupCreationRequest.
     *
     * @param pickup
     */
    public void setPickup(com.stosz.tms.external.aramex.Pickup pickup) {
        this.pickup = pickup;
    }


    /**
     * Gets the labelInfo value for this PickupCreationRequest.
     *
     * @return labelInfo
     */
    public com.stosz.tms.external.aramex.LabelInfo getLabelInfo() {
        return labelInfo;
    }


    /**
     * Sets the labelInfo value for this PickupCreationRequest.
     *
     * @param labelInfo
     */
    public void setLabelInfo(com.stosz.tms.external.aramex.LabelInfo labelInfo) {
        this.labelInfo = labelInfo;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof PickupCreationRequest)) return false;
        PickupCreationRequest other = (PickupCreationRequest) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true &&
            ((this.clientInfo==null && other.getClientInfo()==null) ||
             (this.clientInfo!=null &&
              this.clientInfo.equals(other.getClientInfo()))) &&
            ((this.transaction==null && other.getTransaction()==null) ||
             (this.transaction!=null &&
              this.transaction.equals(other.getTransaction()))) &&
            ((this.pickup==null && other.getPickup()==null) ||
             (this.pickup!=null &&
              this.pickup.equals(other.getPickup()))) &&
            ((this.labelInfo==null && other.getLabelInfo()==null) ||
             (this.labelInfo!=null &&
              this.labelInfo.equals(other.getLabelInfo())));
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
        if (getClientInfo() != null) {
            _hashCode += getClientInfo().hashCode();
        }
        if (getTransaction() != null) {
            _hashCode += getTransaction().hashCode();
        }
        if (getPickup() != null) {
            _hashCode += getPickup().hashCode();
        }
        if (getLabelInfo() != null) {
            _hashCode += getLabelInfo().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(PickupCreationRequest.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", ">PickupCreationRequest"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("clientInfo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "ClientInfo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "ClientInfo"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("transaction");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "Transaction"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "Transaction"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("pickup");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "Pickup"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "Pickup"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("labelInfo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "LabelInfo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "LabelInfo"));
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
