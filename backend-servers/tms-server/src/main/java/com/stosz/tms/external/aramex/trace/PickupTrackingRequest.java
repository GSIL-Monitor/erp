/**
 * PickupTrackingRequest.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.stosz.tms.external.aramex.trace;

public class PickupTrackingRequest  implements java.io.Serializable {
    private com.stosz.tms.external.aramex.trace.ClientInfo clientInfo;

    private com.stosz.tms.external.aramex.trace.Transaction transaction;

    private java.lang.String reference;

    public PickupTrackingRequest() {
    }

    public PickupTrackingRequest(
           com.stosz.tms.external.aramex.trace.ClientInfo clientInfo,
           com.stosz.tms.external.aramex.trace.Transaction transaction,
           java.lang.String reference) {
           this.clientInfo = clientInfo;
           this.transaction = transaction;
           this.reference = reference;
    }


    /**
     * Gets the clientInfo value for this PickupTrackingRequest.
     * 
     * @return clientInfo
     */
    public com.stosz.tms.external.aramex.trace.ClientInfo getClientInfo() {
        return clientInfo;
    }


    /**
     * Sets the clientInfo value for this PickupTrackingRequest.
     * 
     * @param clientInfo
     */
    public void setClientInfo(com.stosz.tms.external.aramex.trace.ClientInfo clientInfo) {
        this.clientInfo = clientInfo;
    }


    /**
     * Gets the transaction value for this PickupTrackingRequest.
     * 
     * @return transaction
     */
    public com.stosz.tms.external.aramex.trace.Transaction getTransaction() {
        return transaction;
    }


    /**
     * Sets the transaction value for this PickupTrackingRequest.
     * 
     * @param transaction
     */
    public void setTransaction(com.stosz.tms.external.aramex.trace.Transaction transaction) {
        this.transaction = transaction;
    }


    /**
     * Gets the reference value for this PickupTrackingRequest.
     * 
     * @return reference
     */
    public java.lang.String getReference() {
        return reference;
    }


    /**
     * Sets the reference value for this PickupTrackingRequest.
     * 
     * @param reference
     */
    public void setReference(java.lang.String reference) {
        this.reference = reference;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof PickupTrackingRequest)) return false;
        PickupTrackingRequest other = (PickupTrackingRequest) obj;
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
            ((this.reference==null && other.getReference()==null) || 
             (this.reference!=null &&
              this.reference.equals(other.getReference())));
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
        if (getReference() != null) {
            _hashCode += getReference().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(PickupTrackingRequest.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", ">PickupTrackingRequest"));
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
        elemField.setFieldName("reference");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "Reference"));
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
